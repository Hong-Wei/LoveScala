//https://alvinalexander.com/scala/concurrency-with-scala-futures-tutorials-examples
package learnFuture.page1_cookbook

import akka.actor.{ActorRef, ActorSystem, Props}
import learnFuture.page1_cookbook.Cloud._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object Futures6 extends App {
  //  import scala.concurrent.ExecutionContext.Implicits.global
  //  class HasGatewayHeader extends Actor{
  //    override def receive: Receive = {
  //      case HasGatewayHeader.Hongwei => true
  //    }
  //  }
  //  object HasGatewayHeader {
  //    case object Hongwei
  //  }
  //
  //  val system: ActorSystem = ActorSystem("Myown")
  //  val customer: ActorRef = system.actorOf(Props[HasGatewayHeader], "HasGatewayHeader")
  //  println("1111111111111111"+customer)
  //
  //  import akka.pattern.{ask}
  //  import scala.concurrent.duration._
  //  implicit val timeout = akka.util.Timeout(4000 milliseconds)
  //  val future: Future[Any] = customer ? HasGatewayHeader.Hongwei
  //
  //  future onComplete {
  //    case Success(s) => println(s"onComplete = $future")
  //    case Failure(e) => e.printStackTrace
  //  }
  //  //  val b: Boolean = Await.result(future.mapTo[Boolean], 40000 milliseconds)
  //  println("xxxxxxxxxxxxxxxx"+future)
  val system: ActorSystem = ActorSystem("Barista")

  //BK 14.2 Defining an actor
  //14.2.1 Design the Actor messages: normally they are case classes, but if there is no parameters.
  //       Just use the case object. 
  // your messages are immutable, or else bad things will happen.
  sealed trait CoffeeRequest

  case object CappuccinoRequest extends CoffeeRequest

  import akka.actor.Actor

  class Barista extends Actor {
    override def receive: Actor.Receive = {
      case CappuccinoRequest => println("I have to prepare a cappuccino!"); sender() ! true
    }
  }

  val barista: ActorRef = system.actorOf(Props[Barista], "Barista")

  barista ! CappuccinoRequest

  import akka.pattern.{ask}
  import scala.concurrent.duration._
  implicit val timeout = akka.util.Timeout(400 milliseconds)
  barista ? CappuccinoRequest onComplete {
    case Success(s) => println(s"onComplete = $s")
    case Failure(e) => e.printStackTrace
  }
}