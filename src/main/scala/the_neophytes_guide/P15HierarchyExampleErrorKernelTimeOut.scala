package the_neophytes_guide

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.AskTimeoutException
import akka.util.Timeout
import com.typesafe.scalalogging.Logger
import the_neophytes_guide.P15HierarchyExampleErrorKernelTimeOut.Barista.Receipt
import the_neophytes_guide.P15HierarchyExampleErrorKernelTimeOut.ReceiptPrinter.{PaperJamException, PrintJob}
import the_neophytes_guide.P15HierarchyExampleErrorKernelTimeOut.Register.{Article, Cappuccino, Espresso, Transaction}

import scala.concurrent.Future
import scala.util.Random


object P15HierarchyExampleErrorKernelTimeOut extends App {
  
  val log = Logger(P15HierarchyExampleSuperviser.getClass)

  import Customer._
  val system: ActorSystem = ActorSystem("Coffeehouse")
  val barista: ActorRef = system.actorOf(Props[Barista], "Barista")
  val customerJohnny: ActorRef = system.actorOf(Props(classOf[Customer], barista), "Johnny")
  log.info("user1{}",barista)
  log.info("user2{}",customerJohnny)

  log.info("user4 customerJohnny start calling caffe !")
  for(i <- 1 to 10){
    customerJohnny ! CaffeineWithdrawalWarning

  }

  // close the system!!!
//  Thread.sleep(2000)
//  barista ! ClosingTime
//  log.info("12 Totally close system !!!")
//  system.terminate()


  //Before do not define the actors, only call some code, here start import akka.actor
  import akka.actor._
  object Customer {
    case object CaffeineWithdrawalWarning
    case object ComebackLater
  }
  //Coffeehouse/user/Customer
  class Customer(coffeeSource: ActorRef) extends Actor with ActorLogging {
    import Barista._
    import Customer._
    import EspressoCup._
    def receive = {
      case CaffeineWithdrawalWarning =>
        log.info("user/Customer5 Customer Acator start working : ")
        coffeeSource ! EspressoRequest

      case (EspressoCup(Filled), Receipt(amount)) =>
        log.info(s"user/Customer10 Customer Finally get the caffeine for ${self}!")

      case ComebackLater=>
        log.info(s"user/Customer10.1 ComebackLater soon!!!")

      case e:Throwable=>
        log.info(s"user/Customer10.2 This is a Throwable!!${e}!")

      //Failure(akka.pattern.AskTimeoutException: Ask timed out on [Actor[akka://Coffeehouse/user/Barista/Register#1159606512]] after [1000 ms]. Sender[Actor[akka://Coffeehouse/user/Barista#1084907993]] sent message of type "the_neophytes_guide.P15MyHierarchyExample$BaristaRegister$Transaction".)!
      case e =>
        log.info(s"user/Customer10.3 This is!!${e}!")
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
    import Barista._
    import EspressoCup._
    // The following is used for Future and sender back !!!
    import akka.pattern.{ask, pipe}
    import akka.util.Timeout
    import context.dispatcher

    import concurrent.duration._
    implicit val timeout = Timeout(1.seconds)

    //register Actor is from context, not from system. It is from context.
    val register: ActorRef = context.actorOf(Props[Register], "Register")

    def receive = {
      case EspressoRequest =>
        log.info(s"user/Barista6 Barista Acator start working : ${self}!")
        val receipt: Future[Any] = register ? Transaction(Espresso)//Normally, just send Actor a message, here. We need the result back.
        log.info("user/Barista8 Barista Acator get message back from Register, need wait for step7")

        val processedReceiptFuture = receipt
          .map((EspressoCup(Filled), _))
          .recover {  //BK 1 Timeout, just add the recover for the future. It will not return the AskTimeoutException!!
            case _: AskTimeoutException => ComebackLater
          }
        
        log.info("user/Barista9 Barista Acator process message send Back to  from Customer")

        processedReceiptFuture.pipeTo(sender)

      case ClosingTime =>
        log.info("user/Barista11 context.stop(self)")
        context.stop(self)
    }
  }
  // object design some useful message, some static classed, objects....
  object Register {
    sealed trait Article
    case object Espresso extends Article
    case object Cappuccino extends Article
    case class Transaction(article: Article)
  }
  //Coffeehouse/user/Barista/BaristaRegister
  class Register extends Actor with ActorLogging {
    import akka.pattern.{ask, pipe}
    import context.dispatcher

    import concurrent.duration._
    implicit val timeout = Timeout(4.seconds)
    var revenue = 0
    val prices = Map[Article, Int](Espresso -> 150, Cappuccino -> 250)
    val printer = context.actorOf(Props[ReceiptPrinter], "Printer")
    override def postRestart(reason: Throwable) {
      super.postRestart(reason)
      log.info(s"Restarted, and revenue is $revenue cents")
    }
    def receive = {
      case Transaction(article) =>
        val price = prices(article)
        val requester = sender
        (printer ? PrintJob(price)).map((requester, _)).pipeTo(self)
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
  class ReceiptPrinter extends Actor with ActorLogging {
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
      if (paperJam) throw new PaperJamException("OMG, not again!")
      Receipt(price)
    }
  }


  

}


