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
			case s : JsSuccess[PrivateMessageThread] =>  PrivateMessageThread.create(s.get, (Json.parse(request.session.get("connectedAs").get) \ "user").as[String]) match {
				case Success(_) => Created(Json.obj("result" ->   "success"))
				case Failure(_) => BadRequest(Json.stringify(Json.obj("result" ->   "Unable to save message")));
			}
			case _ => BadRequest(Json.stringify(Json.obj("result" ->   "Unable to parse user")));
		}
	}

	def all = Action {
		request =>
		PrivateMessageThread.all((Json.parse(request.session.get("connectedAs").get) \ "user").as[String]) match {
			case Success( x@_) => Ok(Json.toJson(x)).as("application/json")
			case Failure(_) => BadRequest(Json.stringify(Json.obj("result" ->   "Unable to load messages")));
		}
		
	}



}