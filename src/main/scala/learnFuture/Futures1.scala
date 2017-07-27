//https://alvinalexander.com/scala/concurrency-with-scala-futures-tutorials-examples
package learnFuture
import Until._

// 1 - the imports
import scala.concurrent.{Await, Future}
// The code also shows a time duration of 1 second. 
// This is made available by the scala.concurrent.duration._ import. 
// With this library, you can state time durations in several convenient ways, 
// such as 100 nanos, 500 millis, 5 seconds, 1 minute, 1 hour, and 3 days. 
// You can also create a duration as Duration(100, MILLISECONDS), Duration(200, "millis").
import scala.concurrent.duration._
// import statement imports the “default global execution context.” 
// You can think of an execution context as being a thread pool, and this is a simple way to get access to a thread pool.
import scala.concurrent.ExecutionContext.Implicits.global


/**
  * Created by zhanghongwei on 27.05.17.
  */
object Futures1 extends App {
  // used by 'time' method
  implicit val baseTime = System.currentTimeMillis

  // 2 - create a Future
  val f: Future[Int] = Future {
    sleep(500)
    1 + 1
  }

  // 3 - this is blocking (blocking is bad)
  val result = Await.result(f, 600 millisecond)
  val result2 = Await.result(f, 600 millisecond)
  val result3 = Await.result(f, 600 millisecond)
  val result4 = Await.result(f, 600 millisecond)
  println(result)
  println(result2)
  println(result3)
  println(result4)
  sleep(1000)
}
