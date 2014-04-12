package controllers


import play.api._
import play.api.mvc._
import play.api.libs.json._
import models.Sandcastle
import models.Sandcastle.stringToCastle
import play.api.libs.functional.syntax._
import scala.util.{Success, Failure}

object SandboxController extends Controller {
	
	def all = Action {
		request => 
		Sandcastle.all  match {
				case Some( l : List[Sandcastle]) => Ok(Json.toJson(l)) 
				case _ => Ok(Json.obj("result" ->   "none available"))
			}
	}

	def get(k : String) = Action {
		request => 
		Sandcastle.get(k) match {
				case Some( l : Sandcastle) => Ok(Json.toJson(l)) 
				case _ => Ok(Json.obj("result" ->   "none available"))
			}
	}

	def update(key : String) = Action(BodyParsers.parse.json) {
		request => request.body.validate[Sandcastle] match {

			case s : JsSuccess[Sandcastle] => Sandcastle.createOrUpdate(s.get) match {
					case Success( t : String) => Ok(Json.obj("result" ->   "success", "edited" -> t))
					case _ => BadRequest(Json.obj("result" ->   "Unable to update blog"));
				}
			case _ => BadRequest(Json.obj("result" ->   "Unable to parse blog json"));
		}
	}

	def delete(k : String) = Action {
		request => Sandcastle.delete(k) match {
				case Success(_) => NoContent
				case _ => Ok(Json.obj("result" ->   "Failed to delete"))
			}
	}

	def create = Action(BodyParsers.parse.json) {
		request => request.body.validate[Sandcastle] match {

			case s : JsSuccess[Sandcastle] => Sandcastle.createOrUpdate(s.get) match {
					case Success(_) => Created(Json.obj("result" ->   "success"))
					case _ => BadRequest(Json.obj("result" ->   "Unable to save blog"));
				}
			case _ => BadRequest(Json.obj("result" ->   "Unable to parse blog json"));
		}
	}


}

