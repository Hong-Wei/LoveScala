//package Akka
//
//import akka.actor.Actor
//import akka.actor.ActorSystem
//import akka.actor.Props
//import akka.actor.Status.Success
//
//import scala.concurrent.Future
//
////CM 1 Define an actor, defining behavior in the special "reveive" method.
//class HelloActor extends Actor {
//  override def receive = {
//    case "hello" => println("hello back at you")
//    case _ => println("huh?")
//  }
//}
//
//class HelloActor2(myName: String) extends Actor {
//  override def receive = {
//    case "2" => println("2")
//    case _ => println("huh?")
//  }
//}
//
//object Main extends App {
//  //The ActorSystem, to get things started, so create one with an arbitrary string. 
//  val system = ActorSystem("HelloSystem")
//  // default Actor constructor, actorof 
//  val helloActor = system.actorOf(Props[HelloActor], name = "helloactor")
//  // if your actor have an argument, using this syntax:
//  val helloActor2 = system.actorOf(Props(new HelloActor2("Fred")), name = "helloactor2")
//  // we have an instance of an actor - helloActor, we send it two message
//  for (i <- 0 to 100) {
//    println(i)
//    helloActor ! "hello"
//  }
//  for (i <- 0 to 100) {
//    println(i)
//    helloActor2 ! "2"
//  }
//
////  import scala.concurrent.ExecutionContext.Implicits.global
////  import scala.concurrent.Future
////  import scala.util.{Success, Failure}
//
//  def computation(): Int = { 25 + 50 }
//  val theFuture: Future[Int] = Future { computation() }
//
////  theFuture.onComplate {
////    case Success(result) => println(result)
////    case Fialure(t) => println(s"Error: %{t.getMessage}")
////  }
//}

import java.io.IOException

import scala.concurrent.Future
import scala.util.Try

object test extends App {


  val name_ : Int = 1

  import scala.concurrent.ExecutionContext.Implicits.global


  val s = "Hello"
  val f: Future[String] = Future {
    s + " future!"
  }
  f onSuccess {
    case msg => println(msg)
  }


  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.Future
  import scala.util.{Success, Failure}

  def computation(): Int = {
    25 + 50
  }

  val theFuture = Future {
    computation()
  }


  None.getOrElse("Hongwei")

  def readfile(): Either[IOException, String] = try {
    Right("羊八井好帅 ^_^！")
  } catch {
    case e: IOException => Left(e)
  }

  println(readfile match {
    case Right(msg) => msg
    case Left(e) => e.getMessage
  })

  val value: Try[Nothing] = Try {
    throw new NullPointerException("null ...")
  }
  value.getOrElse("hongwei")

  "hello"(4)
  "hello".apply(4)
  BigInt("1234567890")
  Array(1, 4, 9, 16.234)

}


object Example_01 extends App{
  import akka.actor.Actor
  import akka.event.Logging
  import akka.actor.ActorSystem
  import akka.actor.Props

  class MyActor extends Actor {
    val log = Logging(context.system, this)

    def receive = {
      case "test" => log.info("received test")
      case _      => log.info("received unknown message")
    }
  }
  //创建ActorSystem对象
  val system = ActorSystem("MyActorSystem")
  //返回ActorSystem的LoggingAdpater
  val systemLog=system.log
  //创建MyActor,指定actor名称为myactor
  val myactor = system.actorOf(Props[MyActor], name = "myactor")
  val myactor2 = system.actorOf(Props[MyActor], name = "myactor2")

  systemLog.info("准备向myactor发送消息")
  //向myactor发送消息
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"
  myactor!"test"

  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor2! 123
  myactor! 123
  myactor! 123
  //关闭ActorSystem，停止程序的运行
    system.terminate()
}