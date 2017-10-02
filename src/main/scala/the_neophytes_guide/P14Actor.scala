package the_neophytes_guide
import akka.actor.{Props,ActorRef, ActorSystem}

object Barista extends App {
  val system = ActorSystem("Barista")
  //A2 Creating an actor
//  val barista = new Barista // will throw exception
  // you can not new the Actor, you can only ask the actor system for a new instance
  val barista: ActorRef = system.actorOf(Props[Barista], "Barista")
  
  //A3 Sending messages
//  private val Unit1: Unit = barista ! CappuccinoRequest
//  barista ! EspressoRequest
//  println("I ordered a cappuccino and an espresso")
//  system.terminate()
  
  //A4 Answering to messages
  val barista2 = system.actorOf(Props[Barista2], "Barista2")
  val customer = system.actorOf(Props(classOf[Customer], barista2), "Customer")
  private val Unit1: Unit = customer ! CaffeineWithdrawalWarning
  private val Unit2: Unit = barista2 ! ClosingTime
  
}

sealed trait CoffeeRequest
case object CappuccinoRequest extends CoffeeRequest
case object EspressoRequest extends CoffeeRequest

import akka.actor.Actor
//A1 Defining an actor
// The actor can do whatever you want it to, just can not return a value.
// 1 It is always side-effecting.
// 2 Untyped
// 3 Asynchronous and non-blocking
class Barista extends Actor {
  
  // Actor.Receiver is the alias of `PartialFunction[Any, Unit]`
  override def receive: Actor.Receive = {
    // register some call back functions, when it triggered, than this will be called.
    case CappuccinoRequest => println("I have to prepare a cappuccino!")
    case EspressoRequest => println("Let's prepare an espresso.")
  }


}

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
