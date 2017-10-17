package the_neophytes_guide

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.AskTimeoutException
import com.typesafe.scalalogging.Logger

import scala.concurrent.Future
import scala.util.Random


//BK 15.4 An example-Exception 
object P15HierarchyExampleErrorKernel_back extends App {
  val logger = Logger(P15HierarchyExample.getClass)
  import Customer._
  val system: ActorSystem = ActorSystem("Coffeehouse")
  val barista: ActorRef = system.actorOf(Props[Barista], "Barista")
  val customerJohnny: ActorRef = system.actorOf(Props(classOf[Customer], barista), "Johnny")
  logger.info("1{}",barista)
  logger.info("2{}",customerJohnny)

  //BK 15.4.0 start processing
  logger.info("4 customerJohnny start calling caffe !")
  for(i<- 1 to 10) customerJohnny ! CaffeineWithdrawalWarning

//    barista ! ClosingTime
//    system.terminate()
  
  import akka.actor._

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
    OneForOneStrategy(2, 1.seconds) {
      case _ => Restart
    }

    def receive = {
      //BK 15.4.1 Here, send the EspressoRequest to `Barista` Actor
      case CaffeineWithdrawalWarning =>
        log.info("5 Customer Acator start working : ")
        coffeeSource ! EspressoRequest
      case (EspressoCup(Filled), Receipt(amount)) =>
        log.info(s"10 Customer Finally get the caffeine for ${self}!")
      case "ComebackLater" =>
        log.info("grumble, grumble")
        context.system.scheduler.scheduleOnce(300.millis) {
          coffeeSource ! EspressoRequest
        }
      case Terminated(barista) =>
        log.info("Oh well, let's find another coffeehouse...")
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
    import BaristaRegister._
    import EspressoCup._

    // The following is used for Future and sender back !!!
    import P15HierarchyExampleErrorKernel_back.Barista.ClosingTime
    import akka.pattern.{ask, pipe}
    import akka.util.Timeout
    import context.dispatcher

    import concurrent.duration._

    implicit val timeout = Timeout(1.seconds)
    //The Regiser Actor is created in its parents: Barista actor !!
    val register = context.actorOf(Props[BaristaRegister], "Register")
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
        log.info("9 Barista Acator process message send Back to  from Customer {}",processedReceiptFuture)
        //This is Non-Blocking way!!!
        processedReceiptFuture.pipeTo(sender)

      case ClosingTime =>
        log.info("9.1 Barista Acator is stop!!!")
        context.stop(self)
    }
  }
  
  // object design some useful message, some static classed, objects....
  object BaristaRegister {
    sealed trait Article
    case object Espresso extends Article
    case object Cappuccino extends Article
    case class Transaction(article: Article)
    class PaperJamException(msg: String) extends Exception(msg)
  }
  // Design the `Register` Actor, just overide the `receive` method. Design the `Partial Function`, just process some cases, not all the cases 
  //Coffeehouse/user/Barista/register
  class BaristaRegister extends Actor with ActorLogging{
    log.info(s"0 Register Acator set up working : ${self}!")
    import Barista._
    import BaristaRegister._
    import akka.pattern.{ask, pipe}
    import akka.util.Timeout
    import context.dispatcher

    import concurrent.duration._ // sender is in the context

    implicit val timeout = Timeout(4.seconds)
    var revenue = 0
    val prices = Map[Article, Int](Espresso -> 150, Cappuccino -> 250)
    val printer = context.actorOf(Props[BaristaRegisterPrinter], "Printer")
    override def postRestart(reason: Throwable) {
      super.postRestart(reason)
      log.info(s"Restarted, and revenue is $revenue cents")
    }

    def receive = {
      case Transaction(article) =>
        val price = prices(article)
        val requester = sender
        (printer ? BaristaRegisterPrinter.PrintJob(price)).map((requester, _)).pipeTo(self)
      case (requester: ActorRef, receipt: Receipt) =>
        revenue += receipt.amount
        log.info(s"revenue is $revenue cents")
        requester ! receipt
    }

  }

  object BaristaRegisterPrinter {
    case class PrintJob(amount: Int)
    class PaperJamException(msg: String) extends Exception(msg)
  }
  //Coffeehouse/user/Barista/register/ReceiptPrinter
  class BaristaRegisterPrinter extends Actor with ActorLogging {
    import BaristaRegisterPrinter._
    import P15HierarchyExampleErrorKernel_back.Barista.Receipt
    var paperJam = false
    override def postRestart(reason: Throwable) {
      super.postRestart(reason)
      log.info(s"Restarted, paper jam == $paperJam")
    }
    def receive = {
      case PrintJob(amount) => sender ! createReceipt(amount)
    }
    def createReceipt(price: Int): Receipt = {
      if (Random.nextBoolean()) paperJam = true
      if (paperJam) 
        throw new PaperJamException("OMG, not again!")
      Receipt(price)
    }
  }
}


