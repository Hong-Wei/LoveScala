//https://alvinalexander.com/scala/concurrency-with-scala-futures-tutorials-examples
package learnFuture.page1_cookbook

import learnFuture.page1_cookbook.Cloud._

import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

object Futures4 extends App {
  println("starting futures")
  //Bk 1. The failure process - fallbackTo
  val result = Cloud.runException(10) fallbackTo Cloud.runAlgorithm(10)

  result onComplete {
    case Success(s) => println(s"onComplete = $result")
    case Failure(e) => e.printStackTrace
  }


  println("run andThen !!!!")

  result onComplete {
    case Success(s) => println(s"onComplete = $result")
    case Failure(e) => e.printStackTrace
  }
  println("before sleep at the end")
  sleep(200) // important: keep the jvm alive
}