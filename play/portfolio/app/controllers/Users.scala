package controllers

import play.api._
import play.api.mvc._
import models.User
import models.UserException
import play.api.libs.json._
import scala.util.Success
import scala.util.Failure
import java.util.Calendar
import java.text.SimpleDateFormat



object Users extends Controller {
	
	def list  = Action {
		Ok(Json.toJson(User.list())).as("application/json");
	}

	def create  = Action(BodyParsers.parse.json) { request =>
		val json = request.body
		if(json == null) {
    		BadRequest("Expecting Json data");
  		} else {

  		User.fromJson(json) match {
  				case Some(user) => User.validateNewUser(user) match {
  									case Success(user) => User.saveUser(user) match { 
  															case Success(s) => Ok(Json.toJson(s)).as("application/json");
  															case Failure(e) => BadRequest("Failed to save new user");
  															}
  									case Failure(e : UserException) => BadRequest(Json.toJson(e.id));
  									case _ => BadRequest(Json.toJson(0));
  								}
  				case None => BadRequest("Couldn't convert json to user");
  			}
  			
  		}
	}

	def register = Action {
		Ok(views.html.register())
	}

	def delete(key : String) = TODO
	def get(key : String) = TODO


	def login = Action(BodyParsers.parse.json) { 
		request => val json = request.body
			if(json == null) {
	    		BadRequest(Json.toJson(4));
	  		} else {

	  			User.fromJson(json) match {
		  			case Some(user) => User.checkCredentials(user) match {
		  				case Success(user) => Ok(Json.toJson("success")).withSession("connectedAs" -> Json.stringify(Json.obj("user" ->   user.userName, "time" -> (new java.text.SimpleDateFormat("E HH:mm a")).format(Calendar.getInstance.getTime)))).as("application/json");
		  				case Failure(e)	=> Unauthorized(Json.toJson(0));
		  			}
		  			case None => BadRequest(Json.toJson(4));
	  			}
	  	}
	}

	def logout(key : String) = Action {
		Ok(Json.toJson("success")).withSession("connectedAs" -> "").as("application/json");
	}

}