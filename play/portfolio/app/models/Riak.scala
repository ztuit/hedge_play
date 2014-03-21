package models

import com.basho.riak.client.RiakFactory
import com.basho.riak.client.bucket.WriteBucket
import com.basho.riak.client.IRiakObject
import scala.concurrent._
import play.api.libs.concurrent.Execution.Implicits._
import scala.collection.JavaConversions._

object RiakClientWrapper {
	
	private var client = RiakFactory.httpClient("http://localhost:8098/riak")

	def store(bucket : String, key : String, data : String) : Future[IRiakObject] = {
		val storeObj = client.fetchBucket(bucket).execute.store(key, data)
		future {
			storeObj.execute
		}
	}

	def fetchAllForBucket(bucket : String ) : Future[List[IRiakObject]] = {
		val strArray  = (client.fetchBucket(bucket).execute.keys.getAll map {_.toString}).toArray
		future {
			client.fetchBucket(bucket).execute.multiFetch(strArray).execute map { _.get} toList
		}
	}

	def fetchValue(bucket : String, key : String) : Option[IRiakObject] = {
		client.fetchBucket(bucket).execute.fetch(key).execute match {
			case v : IRiakObject => Some(v)
			case _ => None
		}
	}



}