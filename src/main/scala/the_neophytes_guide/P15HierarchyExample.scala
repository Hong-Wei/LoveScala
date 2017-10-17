package the_neophytes_guide

import akka.actor.{ActorRef, ActorSystem, Props}
import com.typesafe.scalalogging.Logger

import scala.concurrent.Future
import scala.util.{Failure, Success}

//BK 15.3 An example hierarchy -- Success case
object P15HierarchyExample extends App {
  val logger = Logger(P15HierarchyExample.getClass)
  import Customer._
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

  import akka.pattern.{ask, pipe}
  import akka.util.Timeout
  import system.dispatcher

  import concurrent.duration._
  implicit val timeout = Timeout(1.seconds)
  val doSth2: Future[Any] = customerJohnny ? CaffeineWithdrawalWarning

  doSth2.onComplete {
    case Success(e) => logger.info("xxxxx1"+e)
    case Failure(e) => logger.info("xxxxx2"+e)
  }

  doSth2.onComplete {
    case Success(e) => logger.info("xxxxx1"+e)
    case Failure(e) => logger.info("xxxxx2"+e)
  }
//  val doSth2: Unit = customerAlina ! CaffeineWithdrawalWarning
  Thread.sleep(2000)
  barista ! the_neophytes_guide.P15HierarchyExample.Barista.ClosingTime
  logger.info(s"12 Totally close system !!!")
  system.terminate()
  
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
    import Barista._
    import Register._
    var revenue = 0
    val prices: Map[Article, Int] = Map[Article, Int](Espresso -> 150, Cappuccino -> 250)
    override def receive: PartialFunction[Any, Unit] = {
      //BK 15.3.3 get the Message, start to process
      case Transaction(article) =>
        log.info(s"7 Register Acator start working : ${self}!")
        val price = prices(article)
        val createReceiptTemp = createReceipt(price)
        Thread.sleep(100)
        log.info(s"7.1 Register Acator send message({}) back!",createReceiptTemp)
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
    import EspressoCup._
    import Register._
    // The following is used for Future and sender back !!!
    import akka.pattern.{ask, pipe}
    import akka.util.Timeout
    import context.dispatcher

    import concurrent.duration._

    implicit val timeout = Timeout(1.seconds)
    //The Regiser Actor is created in its parents: Barista actor !!
    val register = context.actorOf(Props[Register], "Register")
    def receive = {
      //BK 15.3.2 Send the `Transaction(Espresso)` to `Register` Actor 
      case EspressoRequest =>
        log.info(s"6 Barista Acator start working : ${self}!")
        val receipt: Future[Any] = register ? Transaction(Espresso)//Normally, just send Actor a message, here. We need the result back.
        
        log.info("8 Barista Acator get message back from Register, need wait for step7")
        
        val processedReceiptFuture: Future[(EspressoCup, Any)] = receipt.map((EspressoCup(Filled), _))
        //This is Blocking way!!!!
//        val result: (EspressoCup, Receipt) = Await.result(processedReceiptFuture, 3 seconds).asInstanceOf[(EspressoCup, Receipt) ]
//        sender ! result
        
        log.info("9 Barista Acator process message send Back to  from Customer{}",processedReceiptFuture)
        //This is Non-Blocking way!!!
        processedReceiptFuture.pipeTo(sender)
        
      case ClosingTime =>
        log.info("11 context.stop(self)")
        context.stop(self)
    }
  }

  object Customer {
    case object CaffeineWithdrawalWarning
  }
  // Here input the `Barista` actor
  class Customer(coffeeSource: ActorRef) extends Actor with ActorLogging {
    import Barista._
    import Customer._
    import EspressoCup._
    def receive = {
      //BK 15.3.1 Here, send the EspressoRequest to `Barista` Actor
      case CaffeineWithdrawalWarning =>
        log.info("5 Customer Acator start working : ")
        coffeeSource ! EspressoRequest
        Future{"Hongwei1 ! Come on !!!"}.pipeTo(sender)
//        sender ! "Hongwei2 ! Come on !!!"
//        sender ! "Hongwei3 ! Come on !!!"
        
      case (EspressoCup(Filled), Receipt(amount)) =>
        log.info(s"10 Customer Finally get the caffeine for ${self}!")
    }
  }
}


