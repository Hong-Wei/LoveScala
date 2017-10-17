package the_neophytes_guide

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.AskTimeoutException
import com.typesafe.scalalogging.Logger

import scala.concurrent.Future


//BK 15.4 An example-Exception 
object P15HierarchyExampleErrorKernel extends App {
  val logger = Logger(P15HierarchyExample.getClass)
  import Customer._
  val system: ActorSystem = ActorSystem("Coffeehouse")
  val barista: ActorRef = system.actorOf(Props[Barista], "Barista")
  val customerJohnny: ActorRef = system.actorOf(Props(classOf[Customer], barista), "Johnny")
  logger.info("1{}",barista)
  logger.info("2{}",customerJohnny)

//  import scala.concurrent.duration._
//  import akka.actor.OneForOneStrategy
//  import akka.actor.SupervisorStrategy.Restart
//  OneForOneStrategy(2, 10.seconds) {
//    case _ => Restart
//  }
//
//  val decider: Supervision.Decider = {
//    case e: Throwable =>
//      logger.error("Exception occurred, stopping...", e)
//      Supervision.Restart
//    case _ =>
//      logger.error("Unknown problem, stopping...")
//      Supervision.Restart
//  }
  
  //BK 15.4.0 start processing
  logger.info("4 customerJohnny start calling caffe !")
//    barista ! ClosingTime
  customerJohnny ! CaffeineWithdrawalWarning
//  customerJohnny ! CaffeineWithdrawalWarning
//  customerJohnny ! CaffeineWithdrawalWarning
//  customerJohnny ! CaffeineWithdrawalWarning
//  customerJohnny ! CaffeineWithdrawalWarning
//    system.terminate()
//  customerJohnny ! CaffeineWithdrawalWarning
//  customerJohnny ! CaffeineWithdrawalWarning
//  customerJohnny ! CaffeineWithdrawalWarning

  import akka.actor._
  // object design some useful message, some static classed, objects....
  object Register {
    sealed trait Article
    case object Espresso extends Article
    case object Cappuccino extends Article
    case class Transaction(article: Article)
    class PaperJamException(msg: String) extends Exception(msg)
  }
  // Design the `Register` Actor, just overide the `receive` method. Design the `Partial Function`, just process some cases, not all the cases 
  //Coffeehouse/user/Barista/register
  class Register extends Actor with ActorLogging{
    log.info(s"0 Register Acator set up working : ${self}!")
    import Barista._
    import Register._
    import akka.pattern.{ask, pipe}
    import akka.util.Timeout
    import context.dispatcher

    import concurrent.duration._ // sender is in the context

    implicit val timeout = Timeout(4.seconds)
    var revenue = 0
    val prices = Map[Article, Int](Espresso -> 150, Cappuccino -> 250)
    val printer = context.actorOf(Props[ReceiptPrinter], "Printer")
    override def postRestart(reason: Throwable) {
      super.postRestart(reason)
      log.info(s"Restarted, and revenue is $revenue cents")
    }
//    import scala.concurrent.duration._
//    import akka.actor.OneForOneStrategy
//    import akka.actor.SupervisorStrategy.Restart
//    OneForOneStrategy(2, 10.seconds) {
//      case _ => Restart
//    }

    def receive = {
      case Transaction(article) =>
        val price = prices(article)
        val requester = sender
        (printer ? ReceiptPrinter.PrintJob(price)).map((requester, _)).pipeTo(self)
      case (requester: ActorRef, receipt: Receipt) =>
        revenue += receipt.amount
        log.info(s"revenue is $revenue cents")
        requester ! receipt
    }

  }

  object ReceiptPrinter {
    case class PrintJob(amount: Int)
    class PaperJamException(msg: String) extends Exception(msg)
  }
  //Coffeehouse/user/Barista/register/ReceiptPrinter
  class ReceiptPrinter extends Actor with ActorLogging {
    import ReceiptPrinter._
    import the_neophytes_guide.P15HierarchyExampleErrorKernel.Barista.Receipt
    var paperJam = false
    override def postRestart(reason: Throwable) {
      super.postRestart(reason)
      log.info(s"Restarted, paper jam == $paperJam")
    }
    def receive = {
      case PrintJob(amount) => sender ! createReceipt(amount)
    }
    def createReceipt(price: Int): Receipt = {
//      if (Random.nextBoolean()) paperJam = true
//      if (paperJam) 
        throw new PaperJamException("OMG, not again!")
//      Receipt(price)
    }
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
  //Coffeehouse/user/Barista
  class Barista extends Actor with ActorLogging{
    log.info(s"0 Barista Acator set up working : ${self}!")
    import Barista._
    import EspressoCup._
    import Register._
    
    // The following is used for Future and sender back !!!
    import akka.pattern.{ask, pipe}
    import akka.util.Timeout
    import context.dispatcher
    import the_neophytes_guide.P15HierarchyExampleErrorKernel.Barista.ClosingTime

    import concurrent.duration._

//    val decider: Supervision.Decider = {
//      case e: Throwable =>
//        logger.error("Exception occurred, stopping...", e)
//        Supervision.stop
//      case _ =>
//        logger.error("Unknown problem, stopping...")
//        Supervision.stop
//    }
    
    implicit val timeout = Timeout(1.seconds)
    //The Regiser Actor is created in its parents: Barista actor !!
    val register = context.actorOf(Props[Register], "Register")
    def receive = {
      //BK 15.4.2 Send the `Transaction(Espresso)` to `Register` Actor 
      case EspressoRequest =>
        log.info(s"6 Barista Acator start working : ${self}!")
        val receipt: Future[Any] = register ? Transaction(Espresso)//Normally, just send Actor a message, here. We need the result back.
        log.info("8 Barista Acator get message back from Register, No need wair for step7!")

        val processedReceiptFuture = receipt.map((EspressoCup(Filled), _))
          .recover {
          case _: AskTimeoutException => "ComebackLater"
        } 
        //This is Blocking way!!!!
//        val result: (EspressoCup, Receipt) = Await.result(processedReceiptFuture, 10 seconds).asInstanceOf[(EspressoCup, Receipt)]
//        sender ! result

        log.info("9 Barista Acator process message send Back to  from Customer {}",processedReceiptFuture)
        //This is Non-Blocking way!!!
        processedReceiptFuture.pipeTo(sender)

      case ClosingTime =>
        log.info("9.1 Barista Acator is stop!!!")
        context.stop(self)
    }
  }

  object Customer {
    case object CaffeineWithdrawalWarning
  }
  // Here input the `Barista` actor
  ////Coffeehouse/user/Customer
  class Customer(coffeeSource: ActorRef) extends Actor with ActorLogging {
    log.info(s"0 Customer Acator set up working : ${self}!")
    import Barista._
    import Customer._
    import EspressoCup._
    import context.dispatcher

    context.watch(coffeeSource)

    import akka.actor.OneForOneStrategy
    import akka.actor.SupervisorStrategy.Restart

    import scala.concurrent.duration._
    OneForOneStrategy(2, 10.seconds) {
      case _ => Restart
    }
    
    def receive = {
      //BK 15.4.1 Here, send the EspressoRequest to `Barista` Actor
      case CaffeineWithdrawalWarning =>
        log.info("5 Customer Acator start working : ")
        coffeeSource ! EspressoRequest
      case (EspressoCup(Filled), Receipt(amount)) =>
        log.info(s"10 Customer Finally get the caffeine for ${self}!")
//      case e: Throwable =>
//        log.error("Exception occurred, stopping...", e)
//      case _ =>
//        log.error("Exception occurred, stopping...")
      case "ComebackLater" =>
        log.info("grumble, grumble")
        context.system.scheduler.scheduleOnce(300.millis) {
        coffeeSource ! EspressoRequest
      }
      case Terminated(barista) =>
      log.info("Oh well, let's find another coffeehouse...")
    }
  }

}


