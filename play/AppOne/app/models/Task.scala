package models

import com.scalapenos.riak._
import play.api.libs.concurrent.Execution.Implicits._
import scala.util.{Success, Failure}
import scala.concurrent.Await
import scala.concurrent.duration._
import play.api.libs.json._
import play.api.libs.functional.syntax._



case class Task(id: String , label: String, description : String) {
	
}

object Task {
  
	val bucket = RiakClient("localhost", 8098).bucket("task");
	val rindex = RiakIndex("task",1)

	implicit object indexer extends RiakIndexer[String] { def index(s:String) = Set(rindex);}

  def all(): List[Task] = {
 
  	val futureTaskValues = bucket.fetch(rindex) 
  	
  	futureTaskValues onComplete { 
  		case Success(rvalues) => println("Success")
  		case Failure(f) => print("Failure " + f)
  	}
  	Await.result(futureTaskValues,500 millis)
 	(((futureTaskValues value) get) get) map (x=>taskReads.reads(Json.parse(x.data)) get)
  	//Nil
  }
  
  def create(label: String) {
  	bucket.store(label, "{\"name\":\"" + label + "\",\"description\":\"\"}")
  }
  
  def delete(id: String) {}

 

 implicit val taskReads: Reads[Task] = (
  	(JsPath \ "name").read[String] and
  	(JsPath \ "name").read[String] and
  	(JsPath \ "description").read[String]
		)(Task.apply _)
  
}
