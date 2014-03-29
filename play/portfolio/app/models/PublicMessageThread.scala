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

case class PublicMessageThread(key : String, sender : String,  subject : String, content : String, sent : String, previous : String)

object PublicMessageThread {
	
	def create( pm : PublicMessageThread, s : String) : Try[String] = {
		val newKey = PublicMessageThread.toKey(pm)
		val newMessage = PublicMessageThread(key=newKey.id, sender = s, subject = pm.subject,content = pm.content, sent = (new java.text.SimpleDateFormat("dd/MM/YY HH:mm a")).format(Calendar.getInstance.getTime), previous = pm.previous)
		//Create a private message thread entry linked to sender and recipient
		RiakClientWrapper.storeWithIndex("PublicMessageThread", newKey, newMessage, "depth", newMessage.previous.length.toString) match {
			case Success( r : IRiakObject) => newMessage.previous.length match {
					case 0 => Success("Success")
					case _ => RiakClientWrapper.addLinkStep("PublicMessageThread",pm.previous,"PublicMessageThread",newKey.id,"next") match {
								case Success(_) => Success("success") 
								case _ => Failure(new RiakException)
							}
				}
			case Failure(_) => Failure(new RiakException)
		}

	}

	/**
	 * Load all the private messages where the user key is linked to
	 **/
	def all() : Try[List[List[PublicMessageThread]]] = {
		allSubThreads ( RiakClientWrapper.fetchForIndex("PublicMessageThread", "depth", "0") match {
			case Success( rl : List[IRiakObject]) =>  Success(rl map {(x : IRiakObject) => Json.parse(x.getValueAsString).validate[PublicMessageThread] get })
			case Failure(_) => Failure(new RiakException)
		})
	}

	def allSubThreads( l : Try[List[PublicMessageThread]])  : Try[List[List[PublicMessageThread]]] = {

		Success(l.get map {(x) => subThreads(x)})
	}

	def subThreads( pm : PublicMessageThread) : List[PublicMessageThread] = {
		if(pm==null) return List();

		pm :: subThreads(RiakClientWrapper.getLinkStep("PublicMessageThread", pm.key, "PublicMessageThread") match {
			case Success( h :: _ ) => Json.parse(h.getValueAsString).validate[PublicMessageThread] get
			case _ => null
		})
	}
	/**
	 * Implcitis for loading and saving, currently a little 
	 * pointless as the client recieves as json, and the
	 * db stores as json.
	 **/
	implicit val privateMessageThreadReads: Reads[PublicMessageThread] = (
		(__ \ "key").read[String] and
  		(__ \ "sender").read[String] and
  		(__ \ "subject").read[String] and
  		(__ \ "content").read[String] and
  		(__ \ "sent").read[String] and
  		(__ \ "previous").read[String]
	)(PublicMessageThread.apply _)

	implicit val privateMessageThreadWrites: Writes[PublicMessageThread] = (
		(JsPath \ "key").write[String] and
  		(JsPath \ "sender").write[String] and
  		(JsPath \ "subject").write[String] and
  		(JsPath \ "content").write[String] and
    	(JsPath \ "sent").write[String] and
  		(JsPath \ "previous").write[String]
	)(unlift(PublicMessageThread.unapply))

	implicit def toKey( pm : PublicMessageThread) : RiakKey = {
		new RiakKey(pm.sender + Calendar.getInstance.getTime.getTime)
	}

	implicit def asJsonString( pm : PublicMessageThread) : RiakContent = {
		new RiakContent(Json.toJson(pm))
	}

}