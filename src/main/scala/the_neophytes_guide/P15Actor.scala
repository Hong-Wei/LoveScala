package the_neophytes_guide

import akka.actor.{ActorRef, ActorSelection, ActorSystem, Props}
import com.typesafe.scalalogging.Logger

import scala.concurrent.Future



object BaristaP15 extends App {

  val logger = Logger(BaristaP15.getClass)
  
  //BK 15.1 The Guardian Actor
  val system = ActorSystem("Coffeehouse")
  val barista: ActorRef = system.actorOf(Props[Barista], "Barista")
  val customer = system.actorOf(Props(classOf[Customer], barista), "Customer")
  
  //BK 15.2 Actor paths: 
  logger.info(barista.path.toString) //akka://Coffeehouse/user/Barista
  logger.info(customer.path.toString)//akka://Coffeehouse/user/Customer
  val barista2 = system.actorSelection("/user/Barista") // akka://Coffeehouse/user/Barista
 
  

  sealed trait CoffeeRequest

  case object CappuccinoRequest extends CoffeeRequest

  case object EspressoRequest extends CoffeeRequest

  import akka.actor.Actor

 
  class Barista extends Actor {
    val barista3 = context.actorSelection("../Barista") // akka://Coffeehouse/user/Barista
    // Actor.Receiver is the alias of `PartialFunction[Any, Unit]`
    // It is just a partition function, only focus on the useful methods, not all the cases.
    override def receive: Actor.Receive = {
      // register some call back functions, when it triggered, than this will be called.
      case CappuccinoRequest => println("I have to prepare a cappuccino!")
      case EspressoRequest => println("Let's prepare an espresso.")
    }
  }


  

  barista ! CappuccinoRequest
  barista ! EspressoRequest
  barista2 ! EspressoRequest
  println("I ordered a cappuccino and an espresso")

  case class Bill(cents: Int)
  case object ClosingTime

  case object CaffeineWithdrawalWarning

  class Customer(caffeineSource: ActorRef) extends Actor {
    def receive = {
      case CaffeineWithdrawalWarning => caffeineSource ! EspressoRequest
      case Bill(cents) => println(s"I have to pay $cents cents, or else!")
    }
  }

  
  
  
  system.terminate()
}
//BK 15.3 An example hierarchy
object P15HierarchyExample extends App {

  import Customer._
  import the_neophytes_guide.P15HierarchyExample.Barista.ClosingTime
  val system: ActorSystem = ActorSystem("Coffeehouse")
  val barista: ActorRef = system.actorOf(Props[Barista], "Barista")
  val customerJohnny: ActorRef = system.actorOf(Props(classOf[Customer], barista), "Johnny")
  val customerAlina: ActorRef = system.actorOf(Props(classOf[Customer], barista), "Alina")
  val doSth1: Unit = customerJohnny ! CaffeineWithdrawalWarning
  val doSth2: Unit = customerAlina ! CaffeineWithdrawalWarning
//  barista ! ClosingTime
//  system.terminate()
  
  import akka.actor._
  object Register {
    sealed trait Article
    case object Espresso extends Article
    case object Cappuccino extends Article
    case class Transaction(article: Article)
  }
  class Register extends Actor {
    import Register._
    import Barista._
    var revenue = 0
    val prices = Map[Article, Int](Espresso -> 150, Cappuccino -> 250)
    val partialFunction:PartialFunction[Any, Unit] = {
      case Transaction(article) =>
        val price = prices(article)
        val createReceiptTemp = createReceipt(price)
        sender ! createReceiptTemp
        revenue += price
    }
    override def receive: PartialFunction[Any, Unit] = partialFunction
    def createReceipt(price: Int): Receipt = Receipt(price)
  }


  object Barista {
    case object EspressoRequest
    case object ClosingTime
    case class EspressoCup(state: EspressoCup.State)
    object EspressoCup {
      sealed trait State
      case object Clean extends State
      case object Filled extends State
      case object Dirty extends State
    }
    case class Receipt(amount: Int)
  }
  class Barista extends Actor {
    import Barista._
    import Register._
    import EspressoCup._
    import context.dispatcher
    import akka.util.Timeout
    import akka.pattern.ask
    import akka.pattern.pipe
    import concurrent.duration._

    implicit val timeout = Timeout(4.seconds)
    val register = context.actorOf(Props[Register], "Register")
    println(register.path)
    def receive = {
      case EspressoRequest =>
        val receipt: Future[Any] = register ? Transaction(Espresso)
        val processedReceiptFuture: Future[(EspressoCup, Any)] = receipt.map((EspressoCup(Filled), _))
        val sendFutureBack: Future[(EspressoCup, Any)] = processedReceiptFuture.pipeTo(sender)
      case ClosingTime => 
        context.stop(self)
    }
  }

  object Customer {
    case object CaffeineWithdrawalWarning
  }
  class Customer(coffeeSource: ActorRef) extends Actor with ActorLogging {
    import Customer._
    import Barista._
    import EspressoCup._
    def receive = {
      case CaffeineWithdrawalWarning => coffeeSource ! EspressoRequest
      case (EspressoCup(Filled), Receipt(amount)) =>
        log.info(s"yay, caffeine for ${self}!")
    }
  }

}




