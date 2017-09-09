//https://alvinalexander.com/scala/concurrency-with-scala-futures-tutorials-examples
package learnFuture
import Cloud._
import scala.concurrent.{Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random

object OnSuccessAndFailure extends App {
  val future: Future[Int] = Future {
    sleep(Random.nextInt(500))
    if (Random.nextInt(500) > 250) 
      throw new Exception("Yikes!") else 42
  }
  future onSuccess {
    case result => println(s"onSuccess: $result")
  }
  
  future onFailure {
    case t => println(s"onFailure: ${t.getMessage}")
  }

  future onComplete  {
    case t => println(s"onComplete: ${t}")
  }

  // do the rest of your work
  println("A ..."); sleep(100)
  println("B ..."); sleep(100)
  println("C ..."); sleep(100)
  println("D ..."); sleep(100)
  println("E ..."); sleep(100)
  println("F ..."); sleep(100)
  sleep(2000)
}