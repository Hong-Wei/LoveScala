//https://docs.scala-lang.org/overviews/core/futures.html#introduction
package learnFuture.page2_scala_lang

import scala.concurrent.Future
import scala.util.{Failure, Success}

object Futures1 extends App {
  //A typical future 
  val inverseFuture1: Future[String] = Future {
    "123"
  }(concurrent.ExecutionContext.Implicits.global)

  // more idiomatic future
  // used the implicit parameters 
  implicit val ec = concurrent.ExecutionContext.Implicits.global
  val inverseFuture2 : Future[String] = Future {
    "123"
  } 
  
  //Exception in Future
  val myExceptionFuture = Future{
//    throw new RuntimeException("This is an exception!")
    "good"
  }
   val exceptionFuture = myExceptionFuture

  exceptionFuture recover{
    case _ => "recover --> good!"
  } onComplete {
    case Success(s) =>println(s)
    case Failure(f) =>println(f)
  }
  
  // many callback functions can be registered, 
  // 
  // not sure the order
  exceptionFuture recover{
    case _ => "recover --> good!"
  } onComplete {
    case Success(s) =>println("1"+s)
    case Failure(f) =>println(f)
  }
  
  exceptionFuture recover{
    case _ => "recover --> good!"
  } onComplete {
    case Success(s) =>println("2"+s)
    case Failure(f) =>println(f)
  }

  
  println("end")
}
