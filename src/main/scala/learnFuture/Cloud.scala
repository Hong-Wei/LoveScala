package learnFuture

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by zhanghongwei on 28/07/2017.
  */
object Cloud {
  def sleep(time: Long) { Thread.sleep(time) }
  
  def runAlgorithm (number : Int)= Future {
    sleep(100)
    println(number)
    number
  }
  def runException (number : Int)= Future {
    sleep(100)
    println(1/0)
    number
  }
}
