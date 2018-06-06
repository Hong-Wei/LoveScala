package Akka
//https://doc.akka.io/docs/akka/current/guide/tutorial_1.html
import akka.actor.{ Actor, Props, ActorSystem }
import scala.io.StdIn

class PrintMyActorRefActor extends Actor {
  override def receive: Receive = {
    case "printit" ⇒
      val secondRef = context.actorOf(Props.empty, "second-actor")
      println(s"Second: $secondRef")
  }
}

object ActorHierarchyExperiments extends App {
  val system = ActorSystem("testSystem")

  println(s"System: $system")
  val firstRef = system.actorOf(Props[PrintMyActorRefActor], "first-actor")
  println(s"First: $firstRef")
  firstRef ! "printit"

  system.terminate()
}


//https://doc.akka.io/docs/akka/current/guide/tutorial_1.html#the-actor-lifecycle
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


object Test extends App{
  val system = ActorSystem("testSystem2")
  val first = system.actorOf(Props[StartStopActor1], "first")
  first ! "stop"
  
  system.terminate()

}

//https://doc.akka.io/docs/akka/current/guide/tutorial_1.html#failure-handling
class SupervisingActor extends Actor {
  override def preStart(): Unit = println("SupervisingActor actor started")
  override def postStop(): Unit = println("SupervisingActor actor stopped")
  val child = context.actorOf(Props[SupervisedActor], "supervised-actor")

  override def receive: Receive = {
    case "failChild" ⇒ child ! "fail"
  }
}

class SupervisedActor extends Actor {
  override def preStart(): Unit = println("supervised actor started")
  override def postStop(): Unit = println("supervised actor stopped")

  override def receive: Receive = {
    case "fail" ⇒
      println("supervised actor fails now")
      throw new Exception("I failed!")
  }
}

object testFailure extends App{
  val system = ActorSystem("testSystem3")
  val supervisingActor = system.actorOf(Props[SupervisingActor], "supervising-actor")
  supervisingActor ! "failChild"

}