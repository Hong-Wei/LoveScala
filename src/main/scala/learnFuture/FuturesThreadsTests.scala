//http://josephguan.github.io/2016/06/26/thread-in-future/
package learnFuture

import scala.concurrent.Future
import scala.util.{Failure, Success}

object FuturesThreadsTests extends App {

  import java.util.concurrent.Executors

  import scala.concurrent._
  implicit val ec2 = new ExecutionContext {
    val threadPool = Executors.newFixedThreadPool(4); // 设置你期望的线程数大小
    def execute(runnable: Runnable) {
      threadPool.submit(runnable)
    }
    def reportFailure(t: Throwable) {}
  }
  
  def testFuture(i: Int) = Future {
    println(s"${Thread.currentThread()}, i = $i");
    Thread.sleep(30000);
    i * 2
  }

  (1 to 5).foreach{i =>
    testFuture(i)
    println(s"run testFuture $i")
  }
  println("all future started")


  Thread.sleep(300000);

  (6 to 10).foreach{i =>
    testFuture(i)
    println(s"run testFuture $i")
  }


  import scala.concurrent.Future

  val opt: Future[String] = Future("123")
  //The for will create the new thread for println
  val Unit1= for (o <- opt) println("xxxxx:"+o)
  
}


