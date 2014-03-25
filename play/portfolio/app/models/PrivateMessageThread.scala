package models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.util.{Success, Failure}
import scala.util.Try

case class PrivateMessageThread(sender : String, recipient : String, content : String)

object PrivateMessageThread {
	
	def create( pm : PrivateMessageThread, sender : String) = Try[String] {
		//Create a private message thread entry linked to sender and recipient
		//set the content as sender recipient, sender and date
		//create a message key with content as message content and link to thread
	}

	def get(threadKey : String) : Try[PrivateMessageThread] = {
		//Inverse of create
	}

	/**
	 * Implcitis for loading and saving, currently a little 
	 * pointless as the client recieves as json, and the
	 * db stores as json.
	 **/
	implicit val privateMessageThreadReads: Reads[PrivateMessageThread] = (
  		(__ \ "sender").read[String] and
  		(__ \ "recipient").read[String] and
  		(__ \ "content").read[String] 
	)(PrivateMessageThread.apply _)

	implicit val privateMessageThreadWrites: Writes[PrivateMessageThread] = (
  		(JsPath \ "sender").write[String] and
  		(JsPath \ "recipient").write[String] and
  		(JsPath \ "content").write[String] 
	)(unlift(PrivateMessageThread.unapply))
}