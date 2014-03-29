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
		Ok("")
	}

	def update = Action(BodyParsers.parse.json) {
		request =>
		Ok("")
	}

	def create = Action(BodyParsers.parse.json) {
		request => request.body.validate[Blog] match {

			case s : JsSuccess[Blog] => Blog.create(s.get, (Json.parse(request.session.get("connectedAs").get) \ "user").as[String]) match {
					case Success(_) => Ok(Json.obj("result" ->   "success"))
					case _ => BadRequest(Json.obj("result" ->   "Unable to save blog"));
				}
			case _ => BadRequest(Json.obj("result" ->   "Unable to parse blog json"));
		}
	}


}

