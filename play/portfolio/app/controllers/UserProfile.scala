package controllers

import play.api._
import play.api.mvc._
import models.UserProfile
import models.UserException
import play.api.libs.json._
import scala.util.Success
import scala.util.Failure
import java.util.Calendar
import java.text.SimpleDateFormat
import java.io.File
import java.io.FileOutputStream
import javax.imageio.ImageIO
import org.imgscalr.Scalr

import play.api.libs.Files

object UserProfileController extends Controller {


	def save(key : String) = Action(BodyParsers.parse.json) { request =>
		request.body.validate[UserProfile] match {
			case s : JsSuccess[UserProfile] => UserProfile.save(s.get) match {
				case Success(_) => Created(Json.obj("result" ->   "success"))
				case _ => BadRequest(Json.stringify(Json.obj("result" ->   "Unable to save user")));
			}
			case _ => BadRequest(Json.stringify(Json.obj("result" ->   "Unable to parse user")));
		}
	}

	def get(key : String) = Action {
		UserProfile.get(key) match {
			case Some(s : String) => Ok(s).as("application/json")
			case _ => BadRequest(Json.stringify(Json.obj("reason" ->   "Unable to load user")));
		}
	}

	def all = Action {
		Ok(Json.toJson(UserProfile.all())).as("application/json");
	}

	def uploadProfilePhoto = Action(parse.multipartFormData) { request =>
		var tmpFile = File.createTempFile("profile","playpic");
  		request.body.file("userfile").map { picture => picture.ref.moveTo(tmpFile, true)}

  		UserProfile.updateUserPhoto(tmpFile, (Json.parse(request.session.get("connectedAs").get) \ "user").as[String]) match {
  			case Success(_) => tmpFile.delete(); Created("File uploaded")
  			case _ => BadRequest(Json.stringify(Json.obj("reason" ->   "Unable to update user")));
  		}
	}

	def profileImage = Action {
		request =>
		UserProfile.profileImage((Json.parse(request.session.get("connectedAs").get) \ "user").as[String]) match {
			case Success(s : Array[Byte]) =>  Ok(s).as("image/png")
			case _ => BadRequest(Json.stringify(Json.obj("reason" ->   "Unable to load user image")));
		}
	}

	def profileThumbImage = Action {
		request =>
		UserProfile.profileThumbImage((Json.parse(request.session.get("connectedAs").get) \ "user").as[String]) match {
			case Success(s : Array[Byte]) =>  Ok(s).as("image/png")
			case _ => BadRequest(Json.stringify(Json.obj("reason" ->   "Unable to load user image")));
		}
	}

	def profileImageKey(key : String) = Action {
		request =>
		UserProfile.profileImage(key) match {
			case Success(s : Array[Byte]) =>  Ok(s).as("image/png")
			case _ => BadRequest(Json.stringify(Json.obj("reason" ->   "Unable to load user image")));
		}
	}

	def profileThumbImageKey(key : String) = Action {
		request =>
		UserProfile.profileThumbImage(key) match {
			case Success(s : Array[Byte]) =>  Ok(s).as("image/png")
			case _ => BadRequest(Json.stringify(Json.obj("reason" ->   "Unable to load user image")));
		}
	}

}