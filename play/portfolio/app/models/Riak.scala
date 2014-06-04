package models

import com.basho.riak.client.RiakFactory
import com.basho.riak.client.bucket.WriteBucket
import com.basho.riak.client.IRiakObject
import scala.concurrent._
import play.api.libs.concurrent.Execution.Implicits._
import scala.collection.JavaConversions._
import play.api.libs.json._
import scala.util.Try
import scala.util.{Success, Failure}
import scala.concurrent.duration._
import com.basho.riak.client.RiakLink
import com.basho.riak.client.bucket.Bucket
import com.basho.riak.client.query.WalkResult;
import com.basho.riak.client.query.indexes.BinIndex
import play.api.Play


class RiakException extends Exception

class RiakKey( s : String ) {
	val id = s;
}

class RiakContent( v : JsValue) {
	val content = Json.stringify(v)
}

object RiakClientWrapper {
	
	private val riakIp = Play.current.configuration.getString("portfolio.riakip");
	private var client = RiakFactory.httpClient("http://" + riakIp.get + "/riak")






	def storeWithIndex(bucketObj : Bucket,key : RiakKey, data : RiakContent, indexName : String, indexValue : String) : Try[IRiakObject] = {

		storeWithIndex(bucketObj,store(bucketObj, key, data).get,indexName,indexValue)
	}	

	def storeWithIndex(bucketObj : Bucket, toStore : IRiakObject,indexName : String, indexValue : String) : Try[IRiakObject] = {
		Success(bucketObj.store(toStore.addIndex(indexName, indexValue)).returnBody(true).execute)
	}


	def store(bucket : Bucket, key : RiakKey, data : RiakContent) : Try[IRiakObject] = {
		val storeObj = bucket.store(key.id, data.content).returnBody(true)
		
		var f = future { 
			storeObj.execute
		}
		Await.result(f,500 millis)

		f value match {
				case Some(Success(a : IRiakObject)) =>  Success(a)
				case e@_ =>    Failure(new RiakException)
			}
	}

	def fetchForIndex(bucket : Bucket, indexName : String, indexValue : String) : Try[List[IRiakObject]] = {
		Success((bucket.fetchIndex(BinIndex.named(indexName)).withValue(indexValue).execute toList) map {
			(x) => fetchValue(bucket, new RiakKey(x)) match {
				case Some( r : IRiakObject) => r
				case _ => null
			}
		})
	}

	def fetchAllForBucket(bucket : Bucket ) : Future[List[IRiakObject]] = {
		val strArray  = (bucket.keys.getAll map {_.toString}).toArray
		val f = future {
			bucket.multiFetch(strArray).execute map { _.get} toList
		}
		Await.result(f,500 millis)
		f
	}

	def fetchValue(bucket : Bucket, key : RiakKey) : Option[IRiakObject] = {
		bucket.fetch(key.id).execute match {
			case v : IRiakObject => Some(v)
			case _ => None
		}
	}

	def store(bucket : Bucket, r : IRiakObject) : Try[IRiakObject] = {
		bucket.store(r).returnBody(true).execute match {
			case a : IRiakObject =>   Success(a)
			case e@_ =>  Failure(new RiakException)
		}
	}

	def addLinkStep(srcBucket : Bucket, srcKey : String, targetBucket : String, targetKey : String, linkName : String) : Try[IRiakObject] = {

		srcBucket.store(srcBucket.fetch(srcKey).execute.addLink(new RiakLink(targetBucket,targetKey, linkName))).returnBody(true).execute match {
			case r : IRiakObject => Success(r)
			case _ => Failure(new RiakException)
		}
	}

	def getLinkStep(srcBucket : String, srcKey : String, targetBucket : String ) : Try[List[IRiakObject]] = {
		
		client.fetchBucket(srcBucket).execute match {
				case b :Bucket => b.fetch(srcKey).execute match {
						case k : IRiakObject => client.walk(k).addStep(targetBucket, "_").execute match {
								case null =>  Failure(new RiakException)
								case wr : WalkResult => Success((wr.iterator toList).flatMap{_.map((x)=>x)})
							}
						case null =>  Failure(new RiakException)
					}
				case _ =>  Failure(new RiakException)
		}
	}

	def delete(b : Bucket, k : RiakKey) {
		b.delete(k.id).execute
	}

	def storeImg(bucket : Bucket, key : RiakKey, data : Array[Byte]) : Try[String] = {
		val storeObj = bucket.store(key.id, data).returnBody(false)
		
		var f = future { 
			storeObj.execute
		}
		Await.result(f,1000 millis)

		f value match {
				case Some(Success(_)) =>  Success("")
				case e@_ =>    Failure(new RiakException)
			}
	}

	//implicits
	implicit def fetchBucket(bucket : String ) : Bucket = {
		client.fetchBucket(bucket).execute
	}

	implicit def mapTry(t : Try[IRiakObject]) : Try[String] = {
		t match {
			case Success( r : IRiakObject) => Success("Success")
			case _ => Failure(new RiakException)
		}
	}

}