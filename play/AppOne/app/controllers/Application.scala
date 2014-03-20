package controllers

import play.api._
import play.api.mvc._
import models.Task
import play.api.data._
import play.api.data.Forms._
import com.scalapenos.riak._
import play.api.libs.json._
import play.api.libs.functional.syntax._

object Application extends Controller {

  def index = Action {
    
      val obj = Json.obj("status" ->"OK", "message" -> "x" ); 
      println(obj); 
      Ok(obj).as("application/json");
    
  }
  /*Action(parse.json) {
    val t = List()
    Ok(Json.toJson(t))
  }*/

  val taskForm = Form(
  "label" -> nonEmptyText
)

	def tasks = Action {
  		Ok(views.html.task(Task.all(), taskForm))
	}
 
  def newTask = Action { 
  
    implicit request =>  
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.task(Task.all(), errors)),
      label => {
        Task.create(label)
        Redirect(routes.Application.tasks)
      }
    )

  }

 
  
  def deleteTask(id: String) = TODO

}
