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

case class Sandcastle(appname : String , shortdesc : String, longdesc : String,  img : String, caption : String)



object Sandcastle {
	
	def createOrUpdate( b : Sandcastle) : Try[String] = {
		
		RiakClientWrapper.store("Sandcastle",new RiakKey(b.appname),new RiakContent(Json.toJson(b))) match {
			case Success(_) => Success("created")
			case Failure(_) => Failure(new RiakException)
		}
	}

	def get( k : String) : Option[Sandcastle] = {
		RiakClientWrapper.fetchValue("Sandcastle", new RiakKey(k)) match {
			case Some( l : IRiakObject) => Some(Json.parse(l.getValueAsString).validate[Sandcastle].get)
			case _ => None
		}
	}

	def delete( s : String) : Try[String] = {
		RiakClientWrapper.delete("Sandcastle",new RiakKey(s));
		Success("");
	}

	def all : Option[List[Sandcastle]] = {
		(RiakClientWrapper.fetchAllForBucket("Sandcastle") value) match {
			case Some(Success( l : List[IRiakObject])) => Some(l map {(x)=>Json.parse(x.getValueAsString).validate[Sandcastle].get})
			case _ => None
		}
	}

	/**
	 * Implcitis for loading and saving, currently a little 
	 * pointless as the client recieves as json, and the
	 * db stores as json.
	 **/
	implicit val privateMessageThreadReads: Reads[Sandcastle] = (
		(__ \ "appname").read[String] and
  		(__ \ "shortdesc").read[String] and
  		(__ \ "longdesc").read[String] and
  		(__ \ "img").read[String] and
  		(__ \ "caption").read[String]
	)(Sandcastle.apply _)

	implicit val privateMessageThreadWrites: Writes[Sandcastle] = (
		(__ \ "appname").write[String] and
  		(__ \ "shortdesc").write[String] and
  		(__ \ "longdesc").write[String] and
  		(__ \ "img").write[String] and
  		(__ \ "caption").write[String]
	)(unlift(Sandcastle.unapply))

	implicit def stringToCastle(bs : String) : Option[Sandcastle] = {
		Json.parse(bs).validate[Sandcastle] match {
			case b : JsSuccess[Sandcastle] => Some(b.get)
			case _ => None
		}
	}

	implicit def asJsonString( pm : Sandcastle) : RiakContent = {
		new RiakContent(Json.toJson(pm))
	}

}