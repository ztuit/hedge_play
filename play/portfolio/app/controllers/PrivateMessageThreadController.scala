package controllers


import play.api._
import play.api.mvc._
import play.api.libs.json._

object PrivateMessageThreadController extends Controller {
	
	def create = Action {
		Ok(Json.toJson("success")).withNewSession.as("application/json");
	}

	def all = Action {
		Ok(Json.toJson("success")).withNewSession.as("application/json");
	}

}