package controllers


import play.api._
import play.api.mvc._
import play.api.libs.json._
import models.PrivateMessageThread
import play.api.libs.functional.syntax._
import scala.util.{Success, Failure}

object PrivateMessageThreadController extends Controller {
	
	def create = Action(BodyParsers.parse.json)  {
		request =>
		request.body.validate[PrivateMessageThread] match {
			case s : JsSuccess[PrivateMessageThread] => PrivateMessageThread.create(s.get, request.session.get("connectedAs").get) match {
				case Success(_) => Ok(Json.obj("result" ->   "success"))
				case _ => BadRequest(Json.stringify(Json.obj("result" ->   "Unable to save user")));
			}
			case _ => BadRequest(Json.stringify(Json.obj("result" ->   "Unable to parse user")));
		}
	}

	def all = Action {
		Ok(Json.toJson("success")).withNewSession.as("application/json");
	}

}