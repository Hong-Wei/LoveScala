package Akka

import net.liftweb.json.JValue
import net.liftweb.json.JsonAST.compactRender
import net.liftweb.json.JsonDSL._

//For akka-http
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.util.{Failure, Success}


object getItFromLeumi_adapter extends App {
  def getJoniMfHttp(username: String): Future[HttpResponse] = {

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()

    val json: JValue = ("JONI_0_000" -> ("NtdriveCommonHeader" -> ("AuthArguments" -> ("UserName" -> username))))
    val data: String = compactRender(json)
    var contentType: ContentType = ContentType(MediaType.applicationWithFixedCharset("json;charset=utf-8", HttpCharsets.`UTF-8`))

    Http().singleRequest(HttpRequest(
      method = HttpMethods.POST,
      uri = "http://localhost:1080/ESBLeumiDigitalBank/PAPI/V1.0/JONI/0/000/01.01",
      entity = HttpEntity.apply(contentType, data.getBytes())
    ))

  }

  getJoniMfHttp("asdf") onComplete {
    case Success(e) => println(e)
    case Failure(e) => println(e)
  }

}