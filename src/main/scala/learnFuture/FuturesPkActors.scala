//https://www.chrisstucchio.com/blog/2013/actors_vs_futures.html

import akka.actor.{Actor, ActorSystem, Props}
import com.sun.deploy.net.{HttpRequest, HttpResponse}

import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

sealed trait FooRequest
case object x extends FooRequest
case object y extends FooRequest

//BK 1 Actor-Based- Key feature to note : has no mutable state!! Nothing internal to FooActor is altered when it receives a message. 
// However, FooActor is implemented as an actor, Akka has no choice but to run it in a single theaded manner.
class FooActor extends Actor {
  def receive = {
    case (x:FooRequest) => {
      val x = "select database"//database.runQuery("SELECT * FROM foo WHERE ", x)
      val y = "select redis"//redis.get(x.fookey)
      sender ! (x,y)
    }
  }
}

//BK 2 Future-Based
class FooRequester() {
  def fooResult(x: FooRequest): Future[(String, String)] = Future {
    val x = "select database"
    val y = "select redis"
    (x,y)
  }
}


object mainProces extends App{
  val system: ActorSystem = ActorSystem("Actor")
  val fooActor = system.actorOf(Props[FooActor],"FooActor")
  import akka.pattern.{ask}
  import scala.concurrent.duration._
  implicit val timeout = akka.util.Timeout(400 milliseconds)
  //BK 1.1 run Actor
  val fooResult: Future[Any] = fooActor ? x
  val eventualTuple: Future[(String, String)] = fooResult.mapTo[(String,String)]


  fooResult onComplete {
    case Success(s) => println(s"onComplete = $s")
    case Failure(e) => e.printStackTrace
  }

  eventualTuple onComplete {
    case Success(s) => println(s"onComplete = $s")
    case Failure(e) => e.printStackTrace
  }

  //BK 1.2 run Future
  val myFooRequester = new FooRequester()
  val eventualTuple1: Future[(String, String)] = myFooRequester.fooResult(x)

  eventualTuple1 onComplete {
    case Success(s) => println(s"onComplete = $s")
    case Failure(e) => e.printStackTrace
  }

  //BK 3 Benefits
  // 1 your concurrency situation is drastically improved.
  // compare the following, 3.1 and 3.2 
  /// 1.1 Actor looks like compute separately, but due to the single-threaded nature of the fooActor, the computation is still single threaded.
  ///     But Future is now multithreaded
  //  1.2 second benefit is easy type safety. 
  //BK 3.1 Actor way
  val r1 = fooActor ? x
  val r2 = fooActor ? y
  for {
    result1 <- r1
    result2 <- r2
  } yield (result1, result2)
  //BK 3.2 Future way-concurrently
  val r3 = myFooRequester.fooResult(x)
  val r4 = myFooRequester.fooResult(y)
  for {
    result1 <- r3
    result2 <- r4
  } yield (result1, result2)

  //BK 3.2 Future way- serially  -- equals to map, result2 is waiting for result1.
  val eventualTuple2: Future[((String, String), (String, String))] = for {
    result1 <- myFooRequester.fooResult(x)
    result2 <- myFooRequester.fooResult(y)
  } yield (result1, result2)
//  ====
   val map: Future[((String, String), (String, String))] = myFooRequester.fooResult(x).flatMap(result1 =>
    myFooRequester.fooResult(y).map(result2 => (result1, result2))
  )
  
}



//BK 4 Actors are for state -- This is the proper use of Acotors.
// FooCounter is to mantain the internal state, specifically the `var count`.
// But handling the state in Futures would be difficult- concurrency primivies (java.util.concurrent.AtomicLong) object 
// would need to be used to maintain the state and where would likely be contention if different futures modified the same object.
// 
class FooCounter extends Actor {
  // This look like public, but it is private. FooCounter object is never exposed to ourside world.
  // only an ActorRef object is. 
  var count: Long = 0

  def receive = {
    case x => { count += 1}
    case y => { sender ! count }
  }
}


//BK 5 Futures are composable
// Futures are highly composable.

//def processRequest(request: HttpRequest): Future[HttpResponse] = {
//  val fromRequest: Option[HttpResponse] = getFromCookie(request)
//  fromRequest.map( r => Future { r }).getOrElse({
//    val fromRedis = getFromRedis(request)
//    //getFromRedis will return an unsuccessful future object if redis does not contain anything
//    val fromScratch = getFromScratch(request)
//
//    //On any error in fromRedis, build from scratch
//    val result = fromRedis.recoverWith({ case _ => fromScratch })
//    
//    //Error Handling - log message runs in separate thread
//    result.onFailure({ case e => log.error("OMFG, NOOOO! This error {} occurred", e) })
//    
//    //Warm the cache without making the request wait
//    fromScratch.foreach( value => cache.put(request, value)
//    
//    result
//  })
//}

// BK 6 Actors as a message endpoint -- from Spray Rounting

//val myHttpServer = system.actorOf(Props(new MyHttpServer), "http-server")
//IO(Http) ! Http.Bind(myHttpServer, "localhost", port=8080)

//class MyHttpServer extends Actor with HttpService {
//  val routes = {
//    get {
//      path("foo" / Segment) { fooSlug =>
//        respondWithStatus(StatusCodes.OK)( ctx => {
//          ctx.complete( computeHttpResponse(fooSlug) )
//        })
//      }
//    }
//  }
//}

//BK 7 Conclusion
// Actors are awesome, but not the only abstraction we have.
// Futures are another great abstraction which work very well with purely functional code.
// Actors are great for maintaining state, futures are great for everything else.
