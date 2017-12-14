//https://www.implicitdef.com/2015/11/19/comparing-scala-http-client-libraries.html

package LearnMockedServer

object _1Dispatch extends App {
//  Diagnostic : abandonware with a cryptic DSL. Don’t use it.
  import dispatch._
  import dispatch.Defaults._

  // Instantiation of the client
  // In a real-life application, you would instantiate it once, share it everywhere,
  // and call h.shutdown() when you're done
  val requestWithHandler =
  // Defining the request
    url("http://jsonplaceholder.typicode.com/comments/1")
      .<<?(Seq("some_parameter" -> "some_value", "some_other_parameter" -> "some_other_value"))
      .<:<(Seq("Cache-Control" -> "no-cache"))
      // Requires a 2xx status code
      .OK { response =>
            // Defines a handler
            println(s"OK, received ${response.getResponseBody}")
            println(s"The response header Content-Length was ${response.getHeader("Content-Length")}")
          }
  // Executes it
  private val future: Future[Unit] = Http.default(requestWithHandler)

}

object _2Newman extends App {
//  Last commits in 2014, and Scala 2.11 isn’t supported.
//  Diagnostic : abandonware. Don’t use it.

}

object _3scalaj_http extends App {
  //However it is synchronous ! No Futures there. Each HTTP request will block the thread
}

object _4spray_client extends App {
// Now we are attacking the big players. Spray is a solid HTTP framework, split in multiple modules. One of them is spray-client.
  import akka.actor._
  import spray.http._
  import spray.client.pipelining._

  // Start an Akka Actor System
  // In a real-life webapp, you would use only one, share it everywhere,
  // and call actorSystem.shutdown() when you're done
  implicit val actorSystem = ActorSystem()
  import actorSystem.dispatcher

  val pipeline = sendReceive
  pipeline(
    // Building the request
    Get(
      Uri(
        "http://jsonplaceholder.typicode.com/comments/1"
      ).withQuery("some_parameter" -> "some_value", "some_other_parameter" -> "some_other_value")
    )
      .withHeaders(HttpHeaders.`Cache-Control`(CacheDirectives.`no-cache`))
  )
    .map { response =>
      // Treating the response
      if (response.status.isFailure) {
        sys.error(s"Received unexpected status ${response.status} : ${response.entity.asString(HttpCharsets.`UTF-8`)}")
      }
      println(s"OK, received ${response.entity.asString(HttpCharsets.`UTF-8`)}")
      println(s"The response header Content-Length was ${response.header[HttpHeaders.`Content-Length`]}")
    }
}

object _5Play_WS extends App {
  import play.api.libs.ws.ning.NingWSClient
  import scala.concurrent.ExecutionContext.Implicits.global

  // Instantiation of the client
  // In a real-life application, you would instantiate one, share it everywhere,
  // and call wsClient.close() when you're done
  val wsClient = NingWSClient()
  wsClient
    .url("http://jsonplaceholder.typicode.com/comments/1")
    .withQueryString("some_parameter" -> "some_value", "some_other_parameter" -> "some_other_value")
    .withHeaders("Cache-Control" -> "no-cache")
    .get()
    .map { wsResponse =>
      if (! (200 to 299).contains(wsResponse.status)) {
        sys.error(s"Received unexpected status ${wsResponse.status} : ${wsResponse.body}")
      }
      println(s"OK, received ${wsResponse.body}")
      println(s"The response header Content-Length was ${wsResponse.header("Content-Length")}")
    }
  
}
