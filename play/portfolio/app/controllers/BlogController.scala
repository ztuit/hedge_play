package controllers


import play.api._
import play.api.mvc._
import play.api.libs.json._
import models.Blog
import models.Blog.stringToBlog
import play.api.libs.functional.syntax._
import scala.util.{Success, Failure}

object BlogController extends Controller {
	
	def current = Action {
		request => 
		Blog.allForUser((Json.parse(request.session.get("connectedAs").get) \ "user").as[String]) match {
				case Some( l : List[Blog]) => Ok(Json.toJson(l)) 
				case _ => Ok(Json.obj("result" ->   "none available"))
			}
	}

	def get(k : String) = Action {
		request => 
		Blog.allForUser(k) match {
				case Some( l : List[Blog]) => Ok(Json.toJson(l)) 
				case _ => Ok(Json.obj("result" ->   "none available"))
			}
	}

	def update(key : String) = Action(BodyParsers.parse.json) {
		request => request.body.validate[Blog] match {

			case s : JsSuccess[Blog] => Blog.update(s.get) match {
					case Success( t : String) => Ok(Json.obj("result" ->   "success", "edited" -> t))
					case _ => BadRequest(Json.obj("result" ->   "Unable to update blog"));
				}
			case _ => BadRequest(Json.obj("result" ->   "Unable to parse blog json"));
		}
	}

	def delete(k : String) = Action {
		request => Blog.delete(k) match {
				case Success(_) => NoContent
				case _ => Ok(Json.obj("result" ->   "Failed to delete"))
			}
	}

	def create = Action(BodyParsers.parse.json) {
		request => request.body.validate[Blog] match {

			case s : JsSuccess[Blog] => Blog.create(s.get, (Json.parse(request.session.get("connectedAs").get) \ "user").as[String]) match {
					case Success(_) => Created(Json.obj("result" ->   "success"))
					case _ => BadRequest(Json.obj("result" ->   "Unable to save blog"));
				}
			case _ => BadRequest(Json.obj("result" ->   "Unable to parse blog json"));
		}
	}


}

