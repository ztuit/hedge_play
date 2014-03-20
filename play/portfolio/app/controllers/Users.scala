package controllers

import play.api._
import play.api.mvc._
import models.User
import models.UserException
import play.api.libs.json._
import scala.util.Success
import scala.util.Failure


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
	def login = TODO


}