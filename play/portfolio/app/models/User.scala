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

case class User(userName : String, email : String, password : String) 


case class UserException(id : Int, smth:String)  extends Exception

//Companion Object for saving loading etc
object User  {


	/**
	 * Create a user from a json description
	 **/
	def fromJson( jsonDesc : JsValue ) : Option[User] = {
		val newUser = jsonDesc.validate[User]
		
		newUser.fold(
			errors => { 
				None
			},
			user => {
				Some(user)
			}
		)
	}

	def checkCredentials(u : User ) : Try[User] = {

		RiakClientWrapper.fetchValue("User", u) match {
			case Some(ruser : IRiakObject) =>  User.fromJson(Json.parse(ruser.getValueAsString)) match {
				case Some(user) =>  if(Crypto.encryptAES(u.password)==user.password) Success(u) else Failure(UserException(0,"Bad credentials"))
				case None => Failure(UserException(0,"Bad Credentials"))
			}
			case None => Failure(UserException(0,"Bad Credentials"))
		}
	}

	/**
	 * Validate a new user, check key doesn't already exist and email is 
	 * valid. Will return a string in the event of a problem, otherwise none
	 **/ 
	def validateNewUser(u : User) : Try[User] = {
		if(RiakClientWrapper.fetchValue("User", u).isDefined==true) {
			Failure(UserException(2,"user name already exists"))
		} else {
			Success(u)
		}
	}

	/**
	 * Send the user an email using the email plugin
	**/
	def sendUserEmail(u : User)  {
		val mail = use[MailerPlugin].email
		mail.setSubject("mailer")
		mail.addRecipient(u.email)
		//or use a list
		mail.addFrom("stuart.mailme@gmail.com")
		//sends text/text
		mail.send( "test email send" )
	}

	/**
	 * Push a user into Riak
	 **/
	def newUser( u : User ) : Try[String] = {
		var newUser = u.copy(password = Crypto.encryptAES(u.password))
		val riakFut = RiakClientWrapper.store("User",u,u) 
		
		riakFut  match {
			case Success(_) => Success("Saved successfully")
			case e@Failure(_) => e
		}
		UserProfile.newProfile(u.userName);
	}


	/**
	 * Get a list of all users
	 **/
	def list() : List[User] = {
		
		val futureUserValues = RiakClientWrapper.fetchAllForBucket("User")

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
 					x=>Json.parse(x.getValueAsString).validate[User] match 
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
	implicit val userReads: Reads[User] = (
  		(__ \ "name").read[String] and
  		(__ \  "email").read[String] and
  		(__ \  "password").read[String] 
	)(User.apply _)

	implicit val userWrites: Writes[User] = (
  		(JsPath \ "name").write[String] and
  		(JsPath \ "email").write[String] and
  		(JsPath \ "password").write[String] 
	)(unlift(User.unapply))
	
	implicit def toKey( u : User) : RiakKey = {
		new RiakKey(u.userName)
	}

	implicit def asJsonString( u : User) : RiakContent = {
		new RiakContent(Json.toJson(u))
	}

}