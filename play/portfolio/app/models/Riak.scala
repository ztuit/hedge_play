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

class RiakException extends Exception

class RiakKey( s : String ) {
	val id = s;
}

class RiakContent( v : JsValue) {
	val content = Json.stringify(v)
}

object RiakClientWrapper {
	
	private var client = RiakFactory.httpClient("http://localhost:8098/riak")

	def store(bucket : String, key : RiakKey, data : RiakContent) : Try[IRiakObject] = {
		val storeObj = client.fetchBucket(bucket).execute.store(key.id, data.content).returnBody(true)
		
		var f = future { 
			storeObj.execute
		}
		Await.result(f,500 millis)

		f value match {
				case Some(Success(a : IRiakObject)) =>  Success(a)
				case e@_ =>    Failure(new RiakException)
			}
	}


	def fetchAllForBucket(bucket : String ) : Future[List[IRiakObject]] = {
		val strArray  = (client.fetchBucket(bucket).execute.keys.getAll map {_.toString}).toArray
		future {
			client.fetchBucket(bucket).execute.multiFetch(strArray).execute map { _.get} toList
		}
	}

	def fetchValue(bucket : String, key : RiakKey) : Option[IRiakObject] = {
		client.fetchBucket(bucket).execute.fetch(key.id).execute match {
			case v : IRiakObject => Some(v)
			case _ => None
		}
	}

	def store(bucket : String, r : IRiakObject) : Try[String] = {
		client.fetchBucket(bucket).execute.store(r).returnBody(true).execute match {
			case a : IRiakObject =>   Success("stored")
			case e@_ =>  Failure(new RiakException)
		}
	}

	def addLinkStep(srcBucket : String, srcKey : String, targetBucket : String, targetKey : String, linkName : String) : Try[IRiakObject] = {
		val bucket = client.fetchBucket(srcBucket).execute
		bucket.store(bucket.fetch(srcKey).execute.addLink(new RiakLink(targetBucket,targetKey, linkName))).returnBody(true).execute match {
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


}