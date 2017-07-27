//https://alvinalexander.com/scala/concurrency-with-scala-futures-tutorials-examples
package learnFuture

import scala.concurrent.{Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scala.util.Random
import Until._

object Example1 extends App {
  println("starting calculation ...")
  val future: Future[Int] = Future {
    sleep(Random.nextInt(500))
    42
  }
  println("before onComplete")
// Because the Future is off running concurrently somewhere, 
// and you don’t know exactly when the result will be computed, 
// the output from this code is nondeterministic, but it can look like this:
  private val complete: Unit = future.onComplete {
    case Success(value) => println(s"Got the callback, meaning = $value")
    case Failure(e) => e.printStackTrace
  }
  complete
  // do the rest of your work
  println("A ..."); sleep(100)
  println("B ..."); sleep(100)
  println("C ..."); sleep(100)
  println("D ..."); sleep(100)
  println("E ..."); sleep(100)
  println("F ..."); sleep(100)
  sleep(2000)
}
