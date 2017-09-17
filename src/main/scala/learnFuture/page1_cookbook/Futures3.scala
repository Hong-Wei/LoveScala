//https://alvinalexander.com/scala/concurrency-with-scala-futures-tutorials-examples
package learnFuture.page1_cookbook

import learnFuture.page1_cookbook.Cloud._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object Futures3 extends App {
  println("starting futures")
  val result1 = Cloud.runAlgorithm(10)
  val result2 = Cloud.runAlgorithm(20)
  val result3 = Cloud.runAlgorithm(30)
//  val result3 = Cloud.runException(30)

  println("before for-comprehension")
  val result: Future[Int] = for {
    r1 <- result1
    r2 <- result2
    r3 <- result3
  } yield (r1 + r2 + r3)

  println("before onSuccess")
  result onSuccess {
    case result => println(s"total = $result")
  }

  result onFailure  {
    case result => println(s"total = ${result.printStackTrace()}")
  }

  result onComplete {
    case Success(s) => println(s"onComplete = $result")
    case Failure(e) => e.printStackTrace
  }
  
  println("before sleep at the end")
  sleep(200) // important: keep the jvm alive
}