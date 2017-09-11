//https://alvinalexander.com/scala/concurrency-with-scala-futures-tutorials-examples
package learnFuture.page1_cookbook

// 1 - the imports
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}
import learnFuture.page1_cookbook.Cloud._

//Creating a method to return a Future[T]
object Futures2 extends App {
  implicit val baseTime = System.currentTimeMillis

  def longRunningComputation(i: Int): Future[Int] = Future {
    sleep(1000)
    i + 1
  }


  private val computation: Future[Int] = longRunningComputation(1100)
  
  computation onSuccess {
    case result => println(s"onSuccess: $result")
  }

  computation onFailure {
    case result => println(s"onFailure: $result")
  }
  
  // this does not block
  computation onComplete {
    case Success(result) => println(s"onComplete = $result")
    case Failure(e) => e.printStackTrace
  }

  // important: keep the jvm from shutting down. if there is no this, the program will be dead.
  sleep(10000)
}
