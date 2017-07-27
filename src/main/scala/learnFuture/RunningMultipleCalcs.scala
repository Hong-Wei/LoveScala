//https://alvinalexander.com/scala/concurrency-with-scala-futures-tutorials-examples
package learnFuture
import Until._

import scala.concurrent.{Future, future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scala.util.Random

object RunningMultipleCalcs extends App {
  println("starting futures")
  val result1 = Until.runAlgorithm(10)
  val result2 = Until.runAlgorithm(20)
  val result3 = Until.runAlgorithm(30)

  println("before for-comprehension")
  
  val result: Future[Int] = for {
    r1 <- result1
    r2 <- result2
    r3 <- result3
  } yield (r1 + r2 + r3)

  println("before onSuccess")
  result onSuccess {
    case futureValue => println(s"total = $futureValue")
  }

  println("before sleep at the end")
  sleep(2000)  // important: keep the jvm alive
}