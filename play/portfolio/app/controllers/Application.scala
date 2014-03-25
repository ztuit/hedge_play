package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def login = Action {
  	 Ok(views.html.login("login"))
  }

  def mainpage = Action {
  	Ok(views.html.mainpage())
  }

  def connected(id : String) = Action {
  	request =>
	  request.session.get("connectedAs").map { user =>
	    Ok(user).as("application/json")
	  }.getOrElse {
	    Unauthorized("Oops, you are not connected")
	  }
  }



}