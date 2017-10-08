//https://alvinalexander.com/scala/concurrency-with-scala-futures-tutorials-examples
package learnFuture.page1_cookbook

import learnFuture.page1_cookbook.Cloud._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object Futures5 extends App {
  def method1: Future[Int] = Future {throw new RuntimeException("123")}
  def method2: Future[Int] = Cloud.runAlgorithm(1)
  def method3: Future[Int] = method2.flatMap(Cloud.runAlgorithm)
  

  val result: Future[Int] = for {
    r1 <- method1 //recover { case e: RuntimeException => 1}
    r2 <- method2
    r3 <- method3
  } yield (r2 + r3)

  result onSuccess {
    case result => println(s"onSuccess = $result")
  }

  result onFailure  {
    case result => println(s"onFailure = ${result.printStackTrace()}")
  }

  
  println("before sleep at the end")
  sleep(1000) // important: keep the jvm alive
}