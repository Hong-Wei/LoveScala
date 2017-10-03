package the_neophytes_guide

import akka.actor.{ActorRef, ActorSystem, Props}

import scala.concurrent.Future


//BK 14.1 The Actor System
object Barista extends App {
  // The Actors are pitiful creatures: they can not live on their own.
  // each and every actor in Akka resides in and is created by an actor system.
  // you can create and lots of functionality from ActorSystem.
  val system: ActorSystem = ActorSystem("Barista")

  //BK 14.2 Defining an actor
  //14.2.1 Design the Actor messages: normally they are case classes, but if there is no parameters.
  //       Just use the case object. 
  // your messages are immutable, or else bad things will happen.
  sealed trait CoffeeRequest

  case object CappuccinoRequest extends CoffeeRequest

  case object EspressoRequest extends CoffeeRequest

  import akka.actor.Actor

  //14.2.2 Extends the Actor trait and Implements the receive method.
  // The actor can do whatever you want it to, just can not return a value.
  // 1 It is always side-effecting.
  // 2 Untyped
  // 3 Asynchronous and non-blocking
  class Barista extends Actor {
    // Actor.Receiver is the alias of `PartialFunction[Any, Unit]`
    // It is just a partition function, only focus on the useful methods, not all the cases.
    override def receive: Actor.Receive = {
      // register some call back functions, when it triggered, than this will be called.
      case CappuccinoRequest => println("I have to prepare a cappuccino!")
      case EspressoRequest => println("Let's prepare an espresso.")
    }
  }


  //BK 14.3 Creating an actor
  // val barista = new Barista // will throw exception
  // you can not new the Actor, you can only ask the actor system for a new instance
  val barista: ActorRef = system.actorOf(Props[Barista], "Barista")

  //BK 14.4 Sending messages
  barista ! CappuccinoRequest
  barista ! EspressoRequest
  println("I ordered a cappuccino and an espresso")
  println()
  println()

  //BK 14.5 Answering to messages
  case class Bill(cents: Int)
  case object ClosingTime

  class Barista2 extends Actor {
    def receive: PartialFunction[Any, Unit] = {
      case CappuccinoRequest =>
        sender ! Bill(250)
        println("I have to prepare a cappuccino!")
      case EspressoRequest =>
        sender ! Bill(200)
        println("Let's prepare an espresso.")
      case ClosingTime => context.system.terminate()
    }
  }

  case object CaffeineWithdrawalWarning

  class Customer(caffeineSource: ActorRef) extends Actor {
    def receive = {
      case CaffeineWithdrawalWarning => caffeineSource ! EspressoRequest
      case Bill(cents) => println(s"I have to pay $cents cents, or else!")
    }
  }

  val barista2 = system.actorOf(Props[Barista2], "Barista2")
  val customer = system.actorOf(Props(classOf[Customer], barista2), "Customer")
  customer ! CaffeineWithdrawalWarning
//  barista2 ! ClosingTime

  //BK 14.6 Asking Questions
  import akka.pattern.ask
  import akka.util.Timeout
  import scala.concurrent.duration._
  implicit val timeout = Timeout(2.second)
  implicit val ec = system.dispatcher
  val f: Future[Any] = barista2 ? CappuccinoRequest
  f.onSuccess {
    case Bill(cents) => println(s"Will pay $cents cents for a cappuccino")
  }
  
  //BK 14.7 Stateful actors
  class Barista3 extends Actor {
    var cappuccinoCount = 0
    var espressoCount = 0
    def receive = {
      case CappuccinoRequest =>
        sender ! Bill(250)
        cappuccinoCount += 1
        println(s"I have to prepare cappuccino #$cappuccinoCount")
      case EspressoRequest =>
        sender ! Bill(200)
        cappuccinoCount -= 1
        println(s"Let's prepare espresso #$cappuccinoCount.")
      case ClosingTime => context.system.terminate()
    }
  }

  val barista3 = system.actorOf(Props[Barista3], "Barista3")
  barista3!CappuccinoRequest
  barista3!CappuccinoRequest
  barista3!CappuccinoRequest
  barista3!CappuccinoRequest
  barista3!EspressoRequest
  barista3!EspressoRequest
  barista3!EspressoRequest
  barista3!EspressoRequest
  barista3!EspressoRequest
  
  system.terminate()
}





