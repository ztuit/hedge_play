package models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.util.{Success, Failure}
import scala.util.Try
import java.util.Calendar
import java.text.SimpleDateFormat
import com.basho.riak.client.IRiakObject
import scala.util.{Success, Failure}
import com.basho.riak.client.RiakLink
import RiakClientWrapper.fetchBucket
import RiakClientWrapper.mapTry

case class Blog(key : String = "", created : String, edited : String,  content : String)

class BlogException extends Exception

object Blog {
	
	def create( b : Blog, userKey : String) : Try[String] = {
		val key = new RiakKey(userKey + Calendar.getInstance.getTime.getTime)
		val currentTime = (new java.text.SimpleDateFormat("dd:MM:Y HH:mm a")).format(Calendar.getInstance.getTime);
		val newB = Blog(key.id, created = currentTime, edited = currentTime, content = b.content)
		RiakClientWrapper.store("Blog",key,new RiakContent(Json.toJson(newB))) match {
			case Success(_) => RiakClientWrapper.addLinkStep("User", userKey, "Blog", key.id, "has")
			case Failure(_) => Failure(new BlogException)
		}
	}

	def update( b : Blog) : Try[String] = {
		val currentTime = (new java.text.SimpleDateFormat("dd:MM:Y HH:mm a")).format(Calendar.getInstance.getTime);
		val newB = Blog(b.key, created = b.created, edited = currentTime, content = b.content)
		RiakClientWrapper.store("Blog",new RiakKey(newB.key),new RiakContent(Json.toJson(newB))) match {
			case Success(_) => Success(newB.edited)
			case _ => Failure(new RiakException)
		}
	}

	def delete( s : String) : Try[String] = {
		RiakClientWrapper.delete("Blog",new RiakKey(s));
		Success("");
	}

	def allForUser(userKey : String) : Option[List[Blog]] = {
		RiakClientWrapper.getLinkStep("User", userKey, "Blog") match {
			case Success( l : List[IRiakObject]) => Some(l map {(x)=>Json.parse(x.getValueAsString).validate[Blog].get})
			case _ => None
		}
	}

	/**
	 * Implcitis for loading and saving, currently a little 
	 * pointless as the client recieves as json, and the
	 * db stores as json.
	 **/
	implicit val privateMessageThreadReads: Reads[Blog] = (
		(__ \ "key").read[String] orElse Reads.pure("") and
  		(__ \ "created").read[String] and
  		(__ \ "edited").read[String] and
  		(__ \ "content").read[String]
	)(Blog.apply _)

	implicit val privateMessageThreadWrites: Writes[Blog] = (
		(JsPath \ "key").write[String] and
  		(JsPath \ "created").write[String] and
  		(JsPath \ "edited").write[String] and
  		(JsPath \ "content").write[String]
	)(unlift(Blog.unapply))

	implicit def stringToBlog(bs : String) : Option[Blog] = {
		Json.parse(bs).validate[Blog] match {
			case b : JsSuccess[Blog] => Some(b.get)
			case _ => None
		}
	}

	implicit def asJsonString( pm : Blog) : RiakContent = {
		new RiakContent(Json.toJson(pm))
	}

}