//https://doc.akka.io/docs/akka-http/current/scala/http/introduction.html#routing-dsl-for-http-servers

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.io.StdIn
import scala.util.{Failure, Success}

object WebServer {
  def main(args: Array[String]) {

    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    val route =
      path("hello") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8084)

    println(s"Server online at http://localhost:8084/hello")
    println(s"Press RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

}

object client extends App {

  import akka.actor.ActorSystem
  import akka.http.scaladsl.Http
  import akka.http.scaladsl.model._
  import akka.stream.ActorMaterializer
  import akka.http.scaladsl.model.headers.RawHeader

  import scala.concurrent.Future

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val responseFuture: Future[HttpResponse] =
    Http().singleRequest(HttpRequest(
      uri = "http://localhost:8084/hello")
      .withHeaders(RawHeader("X-CSRF-TOKEN", "")
      )
    )


  import scala.concurrent.ExecutionContext.Implicits.global

  responseFuture.onComplete {
    case Success(e) => print(e)
    case Failure(e) => print(e)
  }
}