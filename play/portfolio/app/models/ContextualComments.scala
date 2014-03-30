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

case class ContextualComments(key : String = "", created : String, author : String,  content : String, contextBucket : String, contextKey : String)

class ContextualCommentsException extends Exception

object ContextualComments {
	
	def create( pm : ContextualComments, userKey : String) : Try[String] = {
		val newKey = new RiakKey(userKey + Calendar.getInstance.getTime.getTime)
		val newMessage = ContextualComments(key=newKey.id, author = userKey,content = pm.content, created = (new java.text.SimpleDateFormat("dd/MM/YY HH:mm a")).format(Calendar.getInstance.getTime), contextBucket = pm.contextBucket, contextKey = pm.contextKey)
		//Create a private message thread entry linked to sender and recipient
		RiakClientWrapper.store("ContextualComments", newKey, newMessage) match {
			case Success( r : IRiakObject) =>  RiakClientWrapper.addLinkStep(newMessage.contextBucket,pm.contextKey,"ContextualComments",newKey.id,"comment") match {
							 					case Success( r : IRiakObject) => Success("Success")
							 					case _ => Failure(new RiakException)
											}
			case _ => Failure(new RiakException)
			}
		
	}



	def allForContext(contextBucket : String, contextKey : String) : Option[List[ContextualComments]] = {
		RiakClientWrapper.getLinkStep(contextBucket, contextKey, "ContextualComments") match {
			case Success( l : List[IRiakObject]) => Some(l map {(x)=>Json.parse(x.getValueAsString).validate[ContextualComments].get})
			case _ => None
		}
	}

	/**
	 * Implcitis for loading and saving, currently a little 
	 * pointless as the client recieves as json, and the
	 * db stores as json.
	 **/
	implicit val contextualCommentsReads: Reads[ContextualComments] = (
		(__ \ "key").read[String].orElse(Reads.pure("")) and
  		(__ \ "created").read[String].orElse(Reads.pure("")) and
  		(__ \ "author").read[String].orElse(Reads.pure(""))  and
  		(__ \ "content").read[String] and
  		(__ \ "contextBucket").read[String] and
  		(__ \ "contextKey").read[String]
	)(ContextualComments.apply _)

	implicit val contextualCommentsWrites: Writes[ContextualComments] = (
		(__ \ "key").write[String]  and
  		(__ \ "created").write[String] and
  		(__ \ "author").write[String] and
  		(__ \ "content").write[String] and
  		(__ \ "contextBucket").write[String] and
  		(__ \ "contextKey").write[String]
	)(unlift(ContextualComments.unapply))

	implicit def stringToComments(bs : String) : Option[ContextualComments] = {
		Json.parse(bs).validate[ContextualComments] match {
			case b : JsSuccess[ContextualComments] => Some(b.get)
			case _ => None
		}
	}

	implicit def asJsonString( pm : ContextualComments) : RiakContent = {
		new RiakContent(Json.toJson(pm))
	}

}