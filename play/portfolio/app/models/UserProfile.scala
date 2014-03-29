package models

import com.scalapenos.riak._
import play.api.libs.concurrent.Execution.Implicits._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.util.{Success, Failure}
import scala.concurrent.Await
import scala.concurrent.duration._
import play.api.data.validation.ValidationError
import com.basho.riak.client.IRiakObject
import scala.util.Try
import com.typesafe.plugin._
import play.api.Play.current
import play.api.libs.Crypto

import java.util.Calendar
import java.text.SimpleDateFormat

import RiakClientWrapper.fetchBucket

case class UserProfile(username : String = "", created: String = "", fullname : String = "", role : String = "", description : String = "new description", img : String = "") 


case class UserProfileException(id : Int, smth:String)  extends Exception

//Companion Object for saving loading etc
object UserProfile  {

	def save( u : UserProfile) : Try[String] = {
		val riakFut = RiakClientWrapper.store("UserProfile",u,u) 
		
		riakFut  match {
			case Success(_) => Success("Saved successfully")
			case Failure(e) => Failure(UserProfileException(3,"User Profile save failed"))
		}
	}

	def get(key : String) : Option[String] = {
		RiakClientWrapper.fetchValue("UserProfile", new RiakKey(key)) match {
			case Some(ro : IRiakObject) => Some(ro.getValueAsString)
			case _ => Some(Json.stringify(Json.toJson(UserProfile(username = key))))
		}
	}

	def newProfile(key : String) : Try[String] = {
			val profile = UserProfile(username = key, created = (new java.text.SimpleDateFormat("dd:MM:Y HH:mm a")).format(Calendar.getInstance.getTime))
			var fut = RiakClientWrapper.store("UserProfile",profile, profile)

			
			fut  match {
			case Success(_) => Success("Saved successfully")
			case Failure(e) => Failure(UserProfileException(3,"User Profile create failed"))
		}
	}

	def all() : List[UserProfile] = {
		val futureUserValues = RiakClientWrapper.fetchAllForBucket("UserProfile")

		futureUserValues onComplete { 
  			case Success(rvalues) => println("Success")
  			case Failure(f) => print("Failure " + f)
  		}
  	
  		Await.result(futureUserValues,500 millis)
  		
 		((futureUserValues value) match 
 			{ 	
 				case Some(Success(l : List[IRiakObject])) => l; 
 				case _ => List();
 			})  map (
 					x=>Json.parse(x.getValueAsString).validate[UserProfile] match 
 						{
 			 				case JsSuccess(user, _) =>  user
      						case err@JsError(_) => null
      					}
      			)
	}

	/**
	 * Implcitis for loading and saving, currently a little 
	 * pointless as the client recieves as json, and the
	 * db stores as json.
	 **/
	implicit val userProfileReads: Reads[UserProfile] = (
  		(__ \ "username").read[String] and
  		(__ \ "created").read[String] and
  		(__ \ "fullname").read[String] and 
  		(__ \ "role").read[String] and
  		(__ \ "description").read[String] and 
  		(__ \ "img").read[String]
	)(UserProfile.apply _)

	implicit val userProfileWrites: Writes[UserProfile] = (
  		(JsPath \ "username").write[String] and
  		(JsPath \ "created").write[String] and
  		(JsPath \ "fullname").write[String] and 
  		(JsPath \ "role").write[String] and
  		(JsPath \ "description").write[String] and 
  		(JsPath \ "img").write[String]
	)(unlift(UserProfile.unapply))

	implicit def toKey( u : UserProfile) : RiakKey = {
		new RiakKey(u.username)
	}

	implicit def asJsonString( u : UserProfile) : RiakContent = {
		new RiakContent(Json.toJson(u))
	}

}