//https://alvinalexander.com/scala/concurrency-with-scala-futures-tutorials-examples
package learnFuture
import Until._

// 1 - the imports
import scala.concurrent.{Await, Future, future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Futures2 extends App {
  implicit val baseTime = System.currentTimeMillis

  def longRunningComputation(i: Int): Future[Int] = Future {
    sleep(100)
    i + 1
  }


  private val computation = longRunningComputation(11)
  computation onSuccess {
    case result => println(s"onSuccess: $result")
  }
  // this does not block
  computation onComplete {
    case Success(result) => println(s"onComplete = $result")
    case Failure(e) => e.printStackTrace
  }

  // important: keep the jvm from shutting down
  sleep(1000)
}
