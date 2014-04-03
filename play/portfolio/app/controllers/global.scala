import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import controllers.Application
import scala.concurrent.Future
import play.api.libs.json._

object Global extends GlobalSettings {

  override def onRouteRequest(request: RequestHeader): Option[Handler] = {
    val jsV = request.session.get("connectedAs") match {
              case Some( s : String) => Json.parse(s)
              case _ => None
            }


  	jsV match {
  		case v : JsValue  => checkWriteAccess(request, v)
  		case _ =>  request.headers.get("referer") match {
  				case Some( s : String) if(s.contains("login") || s.contains("register")) => super.onRouteRequest(request)
  				case _ => request.path match {
  					case ("/login") => super.onRouteRequest(request)
  					case ("/register") => super.onRouteRequest(request)
  					case _ => Some(Action { BadRequest("No User and unrecognised referer") })
          }
  			}

  		}
  }

  def checkWriteAccess(request : RequestHeader, uval : JsValue): Option[Handler] = {
    val isSystem = ((uval \ "role").as[String]).contains("system")
      request.method match {
        case "GET" => super.onRouteRequest(request)
        case _ if(isSystem==true) => Some(Action { Unauthorized("System account cannot be updated. Connect as a different User.") })
        case _  => super.onRouteRequest(request)
      }
  }

}