package the_neophytes_guide

import akka.actor.{ActorLogging, ActorRef, ActorSelection, ActorSystem, Props}
import com.typesafe.scalalogging.Logger

import scala.concurrent.{Await, Future}



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

 
  class Barista extends Actor with ActorLogging {
    log.info("This is actor-logging info ! ")
    log.debug("This is actor-logging debug! ")
    
    val logger = Logger(BaristaP15.getClass)
    logger.info("This is scala-logging  !")
    logger.debug("This is scala-logging  debug!")
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
  val logger = Logger(P15HierarchyExample.getClass)
  import Customer._
  import the_neophytes_guide.P15HierarchyExample.Barista.ClosingTime
  val system: ActorSystem = ActorSystem("Coffeehouse")
  val barista: ActorRef = system.actorOf(Props[Barista], "Barista")
  val customerJohnny: ActorRef = system.actorOf(Props(classOf[Customer], barista), "Johnny")
  val customerAlina: ActorRef = system.actorOf(Props(classOf[Customer], barista), "Alina")
  logger.info("1{}",barista)
  logger.info("2{}",customerJohnny)
  logger.info("3{}",customerAlina)
  //BK 15.3.0 start processing

  logger.info("4 customerJohnny start calling caffe !")
  val doSth1: Unit = customerJohnny ! CaffeineWithdrawalWarning
//  val doSth2: Unit = customerAlina ! CaffeineWithdrawalWarning
//  barista ! ClosingTime
//  system.terminate()
  
  import akka.actor._
  // object design some useful message, some static classed, objects....
  object Register {
    sealed trait Article
    case object Espresso extends Article
    case object Cappuccino extends Article
    case class Transaction(article: Article)
  }
  // Design the `Register` Actor, just overide the `receive` method. Design the `Partial Function`, just process some cases, not all the cases 
  class Register extends Actor with ActorLogging{
    import Register._
    import Barista._
    var revenue = 0
    val prices: Map[Article, Int] = Map[Article, Int](Espresso -> 150, Cappuccino -> 250)
    override def receive: PartialFunction[Any, Unit] = {
      //BK 15.3.3 get the Message, start to process
      case Transaction(article) =>
        log.info(s"7 Register Acotr start working : ${self}!")
        val price = prices(article)
        val createReceiptTemp = createReceipt(price)
        Thread.sleep(100)
        sender ! createReceiptTemp //This actor only work to send the message back, no need to wait for result !!!
        revenue += price
    }
    
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
  class Barista extends Actor with ActorLogging{
    import Barista._
    import Register._
    import EspressoCup._
    // The following is used for Future and sender back !!!
    import context.dispatcher // sender is in the context
    import akka.util.Timeout
    import akka.pattern.ask
    import akka.pattern.pipe
    import concurrent.duration._

    implicit val timeout = Timeout(1.seconds)
    //The Regiser Actor is created in its parents: Barista actor !!
    val register = context.actorOf(Props[Register], "Register")
    def receive = {
      //BK 15.3.2 Send the `Transaction(Espresso)` to `Register` Actor 
      case EspressoRequest =>
        log.info(s"6 Barista Acotr start working : ${self}!")
        val receipt: Future[Any] = register ? Transaction(Espresso)//Normally, just send Actor a message, here. We need the result back.
        log.info("8 Barista Acotr get message back from Register{}",receipt)
        
        val processedReceiptFuture: Future[(EspressoCup, Any)] = receipt.map((EspressoCup(Filled), _))
        //This is Blocking way!!!!
//        val result: (EspressoCup, Receipt) = Await.result(processedReceiptFuture, 3 seconds).asInstanceOf[(EspressoCup, Receipt) ]
//        sender ! result
        
        log.info("9 Barista Acotr process message send Back to  from Customer{}",processedReceiptFuture)
        //This is Non-Blocking way!!!
        processedReceiptFuture.pipeTo(sender)
        
      case ClosingTime => 
        context.stop(self)
    }
  }

  object Customer {
    case object CaffeineWithdrawalWarning
  }
  // Here input the `Barista` actor
  class Customer(coffeeSource: ActorRef) extends Actor with ActorLogging {
    import Customer._
    import Barista._
    import EspressoCup._
    def receive = {
      //BK 15.3.1 Here, send the EspressoRequest to `Barista` Actor
      case CaffeineWithdrawalWarning =>
        log.info("5 Customer Acotr start working : ")
        coffeeSource ! EspressoRequest
      case (EspressoCup(Filled), Receipt(amount)) =>
        log.info(s"10 Customer Finally get the caffeine for ${self}!")
    }
  }

}




