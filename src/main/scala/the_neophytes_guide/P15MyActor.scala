package the_neophytes_guide

import akka.actor.SupervisorStrategy.{Directive, Resume, Stop}
import akka.actor.{ActorLogging, ActorRef, ActorSystem, OneForOneStrategy, Props, SupervisorStrategy}
import akka.pattern.AskTimeoutException
import akka.stream.Supervision
import com.typesafe.scalalogging.Logger

import scala.concurrent.Future
import scala.util.{Failure, Random, Success}


//BK 15.5 An example-Exception 
object P15MyHierarchyExample extends App {
  val logger = Logger(P15MyHierarchyExample.getClass)
  class PaperJamException(msg: String) extends Exception(msg)
  import Customer._
  val system: ActorSystem = ActorSystem("Coffeehouse")
  val barista: ActorRef = system.actorOf(Props[Barista], "Barista")
  val customerJohnny: ActorRef = system.actorOf(Props(classOf[Customer], barista), "Johnny")
  val customerAlina: ActorRef = system.actorOf(Props(classOf[Customer], barista), "Alina")
  logger.info("user1{}",barista)
  logger.info("user2{}",customerJohnny)
  logger.info("user3{}",customerAlina)
  //BK 15.3.0 start processing

  logger.info("user4 customerJohnny start calling caffe !")
  val doSth1: Unit = customerJohnny ! CaffeineWithdrawalWarning
   customerJohnny ! CaffeineWithdrawalWarning

  // close the system!!!
//  Thread.sleep(100)
//  barista ! the_neophytes_guide.P15MyHierarchyExample.Barista.ClosingTime
//  logger.info("12 Totally close system !!!")
//  system.terminate()
  
  
  //Before do not define the actors, only call some code, here start import akka.actor
  import akka.actor._
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
    import BaristaRegister._
    // The following is used for Future and sender back !!!
    import akka.pattern.{ask, pipe}
    import akka.util.Timeout
    import context.dispatcher
    import concurrent.duration._
    implicit val timeout = Timeout(1.seconds)
    
    //register Actor is from context, not from system. It is from context.
    val register: ActorRef = context.actorOf(Props[BaristaRegister], "Register")

    //TODO 3 change `Resume` in father actor
    val decider: PartialFunction[Throwable, Directive] = {
      case _: PaperJamException => 
        log.info("decider-stop")
        Stop
    }
    override def supervisorStrategy: SupervisorStrategy ={
      log.info("supervisorStrategy-my own supervisor strategy!")
      OneForOneStrategy()(decider.orElse(SupervisorStrategy.defaultStrategy.decider))
    }
    

    def receive = {
      //BK 15.3.2 Send the `Transaction(Espresso)` to `Register` Actor 
      case the_neophytes_guide.P15MyHierarchyExample.Barista.EspressoRequest =>
        log.info(s"user/Barista6 Barista Acator start working : ${self}!")
        val receipt: Future[Any] = register ? Transaction(Espresso)//Normally, just send Actor a message, here. We need the result back.
        log.info("user/Barista8 Barista Acator get message back from Register, need wait for step7")

        val processedReceiptFuture: Future[(EspressoCup, Any)] = receipt.map((EspressoCup(Filled), _))
        log.info("user/Barista9 Barista Acator process message send Back to  from Customer{}",processedReceiptFuture)
        
        processedReceiptFuture.pipeTo(sender)

      case the_neophytes_guide.P15MyHierarchyExample.Barista.ClosingTime =>
        log.info("user/Barista11 context.stop(self)")
        context.stop(self)
    }
  }

  
  // object design some useful message, some static classed, objects....
  object BaristaRegister {
    sealed trait Article
    case object Espresso extends Article
    case object Cappuccino extends Article
    case class Transaction(article: Article)
  }
  //Coffeehouse/user/Barista/BaristaRegister
  class BaristaRegister extends Actor with ActorLogging{
    import Barista._
    import BaristaRegister._
    var revenue = 0
    val prices: Map[Article, Int] = Map[Article, Int](Espresso -> 150, Cappuccino -> 250)
    //TODO 1 if this actor throw exception, this will be called by his parents
    override def postRestart(reason: Throwable) {
      super.postRestart(reason)
      log.info(s"Restarted because of ${reason.getMessage}"
      )
    }

    //TODO 2 supervisor strategy
    import scala.concurrent.duration._ 
    import akka.actor.OneForOneStrategy 
    import akka.actor.SupervisorStrategy.Restart 
    OneForOneStrategy(3, 1.seconds) { 
      case _ => Restart 
    }
    
    
    override def receive: PartialFunction[Any, Unit] = {
      //BK 15.3.3 get the Message, start to process
      case Transaction(article) =>
        log.info(s"user/Barista/BaristaRegister7 Register Acator start working : ${self}!")
        val price = prices(article)
        val createReceiptTemp = createReceipt(price)
        Thread.sleep(100)
        sender ! createReceiptTemp //This actor only work to send the message back, no need to wait for result !!!
        log.info(s"user/Barista/BaristaRegister7.1 Register Acator send message({}) back!",createReceiptTemp)
        revenue += price
        log.info(s"user/Barista/BaristaRegister7.2 Register Acator total revenue: ({}) !",revenue)
    }

    def createReceipt(price: Int): Receipt = {
//      if (Random.nextBoolean()) 
//      Thread.sleep(100)
        throw new PaperJamException("PaperJamException again!")
      Receipt(price)
    }
  }

  
  object Customer {
    case object CaffeineWithdrawalWarning
  }
  //Coffeehouse/user/Customer
  class Customer(coffeeSource: ActorRef) extends Actor with ActorLogging {
    import Barista._
    import Customer._
    import EspressoCup._
    def receive = {
      //BK 15.3.1 Here, send the EspressoRequest to `Barista` Actor
      case CaffeineWithdrawalWarning =>
        log.info("user/Customer5 Customer Acator start working : ")
        coffeeSource ! EspressoRequest

      case (EspressoCup(Filled), Receipt(amount)) =>
        log.info(s"user/Customer10 Customer Finally get the caffeine for ${self}!")

      case e:PaperJamException=>
        log.info(s"user/Customer10.1 This is a PaperJamException!!${e}!")

      case e:Throwable=>
        log.info(s"user/Customer10.2 This is a Throwable!!${e}!")

     //Failure(akka.pattern.AskTimeoutException: Ask timed out on [Actor[akka://Coffeehouse/user/Barista/Register#1159606512]] after [1000 ms]. Sender[Actor[akka://Coffeehouse/user/Barista#1084907993]] sent message of type "the_neophytes_guide.P15MyHierarchyExample$BaristaRegister$Transaction".)!
      case e =>
        log.info(s"user/Customer10.3 This is!!${e}!")
    }
    
  }

}


