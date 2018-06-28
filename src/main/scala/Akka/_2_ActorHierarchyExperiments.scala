//https://doc.akka.io/docs/akka/current/guide/tutorial_1.html

package com.lightbend.akka.sample

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.typesafe.scalalogging.Logger
import wangjialin.scala.oop.Logger

import scala.io.StdIn

class PrintMyActorRefActor extends Actor {
  override def receive: Receive = {
    case "printit" ⇒
      val secondRef = context.actorOf(Props.empty, "second-actor")
      println(s"Second: $secondRef") // Second: Actor[akka://testSystem/user/first-actor/second-actor#-554178854]
      context.stop(self)
  }
}

//https://doc.akka.io/docs/akka/current/guide/tutorial_1.html#the-akka-actor-hierarchy
object ActorHierarchyExperiments extends App {
  val system = ActorSystem("testSystem")
  println(s"System: $system")  // System: akka://testSystem

  val firstRef = system.actorOf(Props[PrintMyActorRefActor], "first-actor")
  println(s"First: $firstRef") // First: Actor[akka://testSystem/user/first-actor#1167937603] -->  `#1167937603`is a unique identifier that you can ignore in most cases.
  firstRef ! "printit"
  system.stop(firstRef)
  firstRef ! "printit" //Message [java.lang.String] without sender to Actor[akka://testSystem/user/first-actor#1860201781] was not delivered. [1] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
  firstRef ! "printit"

  println(">>> Press ENTER to exit <<<")
  try StdIn.readLine()
  finally system.terminate()
}


class StartStopActor1 extends Actor {
  override def preStart(): Unit = {
    println("first started")
    context.actorOf(Props[StartStopActor2], "second")
  }
  override def postStop(): Unit = println("first stopped")

  override def receive: Receive = {
    case "stop" ⇒ context.stop(self)
  }
}

class StartStopActor2 extends Actor {
  override def preStart(): Unit = println("second started")
  override def postStop(): Unit = println("second stopped")

  // Actor.emptyBehavior is a useful placeholder when we don't
  // want to handle any messages in the actor.
  override def receive: Receive = Actor.emptyBehavior
}

//https://doc.akka.io/docs/akka/current/guide/tutorial_1.html#the-actor-lifecycle
object myApp extends App{
  val system = ActorSystem("testLiftCycle")
  val first = system.actorOf(Props[StartStopActor1], "first")
  first ! "stop"
}



class SupervisingActor extends Actor {
  val child = context.actorOf(Props[SupervisedActor], "supervised-actor")

  override def receive: Receive = {
    case "failChild" ⇒ child ! "fail"
  }
}

class SupervisedActor extends Actor with ActorLogging{
  override def preStart(): Unit = log.info("supervised actor started")
  override def postStop(): Unit = log.info("supervised actor stopped")
  
  override def receive: Receive = {
    case "fail" ⇒
      log.info("supervised actor fails now !!!!!!!!")
      throw new Exception("Here we throw the exception ! This will stop current actor!!!")
  }
}

//https://doc.akka.io/docs/akka/current/guide/tutorial_1.html#failure-handling
object myApp2 extends App {
  val logger = Logger("my")
  val system = ActorSystem("testLiftCycle")
  logger.info("This is start system actor !")
  val supervisingActor = system.actorOf(Props[SupervisingActor], "supervising-actor")
  supervisingActor ! "failChild"

}