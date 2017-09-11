//https://alvinalexander.com/scala/concurrency-with-scala-futures-tutorials-examples
package learnFuture.page1_cookbook

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
import learnFuture.page1_cookbook.Cloud._

/**
  * Created by zhanghongwei on 27.05.17.
  */
object Futures1 extends App {
  // used by 'time' method
  implicit val baseTime = System.currentTimeMillis

  // 2 - create a Future
  val f: Future[Int] = Future {
    sleep(100)
    1 + 1
  }

  // 3 - this is blocking (blocking is bad)
  /*
     Await is what is used to ensure proper handling of blocking for Awaitable instances. 
     While occasionally useful, e.g. for testing, 
     it is recommended that you avoid Await when possible 
     1> in favor of callbacks and 
     2> combinators like onComplete and 
     3> use in for comprehensions. 
     
     Await will block the thread on which it runs, and could cause performance and deadlock issues
   */
  val result = Await.result(f, 100 millisecond)
  println(result)
  sleep(1000)
}
