package controllers


import play.api._
import play.api.mvc._
import play.api.libs.json._
import models.ContextualComments
import play.api.libs.functional.syntax._
import scala.util.{Success, Failure}

object ContextualCommentsController extends Controller {
	
	def create = Action(BodyParsers.parse.json)  {
		request =>
		request.body.validate[ContextualComments] match {
			case s : JsSuccess[ContextualComments] =>  ContextualComments.create(s.get, (Json.parse(request.session.get("connectedAs").get) \ "user").as[String]) match {
				case Success(_) => Created(Json.obj("result" ->   "success"))
				case Failure(_) => BadRequest(Json.stringify(Json.obj("result" ->   "Unable to save message")));
			}
			case _ => BadRequest(Json.stringify(Json.obj("result" ->   "Unable to parse message")));
		}
	}

	def get(bucket : String, key : String) = Action {
		request =>
		ContextualComments.allForContext(bucket, key) match {
			case Some( l : List[ContextualComments]) => Ok(Json.toJson(l)).as("application/json")
			case _ => NoContent
		}
		
	}



}

