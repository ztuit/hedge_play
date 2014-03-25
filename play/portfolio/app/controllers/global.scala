import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import controllers.Application
import scala.concurrent.Future

object Global extends GlobalSettings {

  override def onRouteRequest(request: RequestHeader): Option[Handler] = {

  	request.session.get("connectedAs") match {
  		case Some( s : String) if(s.length!=0) => super.onRouteRequest(request) 
  		case _ =>  request.headers.get("referer") match {
  				case Some( s : String) if(s.contains("login") || s.contains("register")) => super.onRouteRequest(request)
  				case _ => request.path match {
  					case ("/login") => super.onRouteRequest(request)
  					case ("/register") => super.onRouteRequest(request)
  					case _ => Some(Application.index)
  				}
  			}

  		}
  }


}