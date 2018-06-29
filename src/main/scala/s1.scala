package hongwei.good.boy

import scala.concurrent.Future
import akka.util.Timeout
import net.liftweb.common._
import net.liftweb.util.Props

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


object s1 extends App {

// val a: String = List(1, 2, 3).asInstanceOf[String]

 val TIMEOUT = (2 seconds)
 def extractFuture[T](f: Future[Any]): T = {
//    val r: Future[Any] = f.map{
//      case s: Set[T] => s
//      case l: List[T] => l
//      case t: T => t
//      case _ => Empty ~> ""
//    }
    val a = Await.result(f, TIMEOUT)
    val b = a.asInstanceOf[T] //TODO what is here????
    val c = a.asInstanceOf[String] //TODO what is here????
    
  println(b)
  b
  }
 
 
// def get: String =  extractFuture[String](Future{1})
// get
 
// null.asInstanceOf[Int]
// val abc: Int = null: Int
 
 List(1).asInstanceOf[List[String]]
 List("Hongwei").asInstanceOf[List[Int]]
}