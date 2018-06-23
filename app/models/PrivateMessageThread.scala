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

case class PrivateMessageThread(key : String, sender : String, recipient : String, subject : String, content : String, sent : String, previous : String)

object PrivateMessageThread {
	
	def create( pm : PrivateMessageThread, s : String) : Try[String] = {
		val newKey = PrivateMessageThread.toKey(pm)
		val newMessage = PrivateMessageThread(key=newKey.id, sender = s, subject = pm.subject,recipient = pm.recipient, content = pm.content, sent = (new java.text.SimpleDateFormat("dd/MM/YY HH:mm a")).format(Calendar.getInstance.getTime), previous = pm.previous)
		//Create a private message thread entry linked to sender and recipient
		RiakClientWrapper.store("PrivateMessageThread", newKey, newMessage) match {
			case Success( r : IRiakObject) => newMessage.previous.length match {
					case 0 => RiakClientWrapper.addLinkStep("User",s,"PrivateMessageThread", newKey.id, "sender") match {
							case Success(_) => RiakClientWrapper.addLinkStep("User",pm.recipient,"PrivateMessageThread", newKey.id, "reciever") match {
								case Success(_) => Success("success") 
								case _ => Failure(new RiakException)
							}
							case Failure(_) => Failure(new RiakException)
					}
					case _ => RiakClientWrapper.addLinkStep("PrivateMessageThread",pm.previous,"PrivateMessageThread",newKey.id,"next") match {
								case Success(_) => Success("success") 
								case _ => Failure(new RiakException)
							}
				}
			case Failure(_) => Failure(new RiakException)
		}

	}

	def get(threadKey : String) : Try[List[PrivateMessageThread]] = {
		//Inverse of create
		Success(List())
	}

	/**
	 * Load all the private messages where the user key is linked to
	 **/
	def all( userKey : String) : Try[List[List[PrivateMessageThread]]] = {
		allSubThreads ( RiakClientWrapper.getLinkStep("User", userKey, "PrivateMessageThread") match {
			case Success( rl : List[IRiakObject]) =>  Success(rl map {(x : IRiakObject) => Json.parse(x.getValueAsString).validate[PrivateMessageThread] get })
			case Failure(_) => Failure(new RiakException)
		})
	}

	def allSubThreads( l : Try[List[PrivateMessageThread]])  : Try[List[List[PrivateMessageThread]]] = {
		
		Success((l.get distinct) map {(x) => subThreads(x)})
		
	}

	def subThreads( pm : PrivateMessageThread) : List[PrivateMessageThread] = {
		if(pm==null) return List();

		pm :: subThreads(RiakClientWrapper.getLinkStep("PrivateMessageThread", pm.key, "PrivateMessageThread") match {
			case Success( h :: _ )=> Json.parse(h.getValueAsString).validate[PrivateMessageThread] get
			case _ => null
		})
	}
	/**
	 * Implcitis for loading and saving, currently a little 
	 * pointless as the client recieves as json, and the
		(JsPath \ "key").write[String] and
  		(JsPath \ "sender").write[String] and
  		(JsPath \ "subject").write[String] and
  		(JsPath \ "content").write[String] and
    	(JsPath \ "sent").write[String] and
  		(JsPath \ "previous").write[String]	 * db stores as json.
	 **/
	implicit val privateMessageThreadReads: Reads[PrivateMessageThread] = (
		(__ \ "key").read[String] and
  		(__ \ "sender").read[String] and
  		(__ \ "recipient").read[String] and
  		(__ \ "subject").read[String] and
  		(__ \ "content").read[String] and
  		(__ \ "sent").read[String] and
  		(__ \ "previous").read[String]
	)(PrivateMessageThread.apply _)



	implicit val privateMessageThreadWrites: Writes[PrivateMessageThread] = (
		(JsPath \ "key").write[String] and
  		(JsPath \ "sender").write[String] and
  		(JsPath \ "recipient").write[String] and
  		(JsPath \ "subject").write[String] and
  		(JsPath \ "content").write[String] and
    	(JsPath \ "sent").write[String] and
  		(JsPath \ "previous").write[String]
	)(unlift(PrivateMessageThread.unapply))

	implicit def toKey( pm : PrivateMessageThread) : RiakKey = {
		new RiakKey(pm.sender + Calendar.getInstance.getTime.getTime)
	}

	implicit def asJsonString( pm : PrivateMessageThread) : RiakContent = {
		new RiakContent(Json.toJson(pm))
	}

}