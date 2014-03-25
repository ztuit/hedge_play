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



object UserProfileController extends Controller {


	def save(key : String) = Action(BodyParsers.parse.json) { request =>
		request.body.validate[UserProfile] match {
			case s : JsSuccess[UserProfile] => UserProfile.save(s.get) match {
				case Success(_) => Ok(Json.obj("result" ->   "success"))
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

}