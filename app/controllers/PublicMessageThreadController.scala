package controllers


import play.api._
import play.api.mvc._
import play.api.libs.json._
import models.PublicMessageThread
import play.api.libs.functional.syntax._
import scala.util.{Success, Failure}

object PublicMessageThreadController extends Controller {
	
	def create = Action(BodyParsers.parse.json)  {
		request =>
		request.body.validate[PublicMessageThread] match {
			case s : JsSuccess[PublicMessageThread] =>  PublicMessageThread.create(s.get, (Json.parse(request.session.get("connectedAs").get) \ "user").as[String]) match {
				case Success(_) => Created(Json.obj("result" ->   "success"))
				case Failure(_) => BadRequest(Json.stringify(Json.obj("result" ->   "Unable to save message")));
			}
			case _ => BadRequest(Json.stringify(Json.obj("result" ->   "Unable to parse message")));
		}
	}

	def all = Action {
		request =>
		PublicMessageThread.all match {
			case Success( x@_) => Ok(Json.toJson(x)).as("application/json")
			case Failure(_) => BadRequest(Json.stringify(Json.obj("result" ->   "Unable to load messages")));
		}
		
	}



}

