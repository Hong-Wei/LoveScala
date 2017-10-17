package the_neophytes_guide

import akka.actor.SupervisorStrategy.{Decider, Stop}
import akka.actor.{ActorRef, ActorSystem, Props}
import com.typesafe.scalalogging.Logger

import scala.concurrent.Future


object P15HierarchyExampleSuperviser extends App {
  val logger = Logger(P15HierarchyExampleSuperviser.getClass)
  class PaperJamException(msg: String) extends Exception(msg)
  import Customer._
  val system: ActorSystem = ActorSystem("Coffeehouse")
  val barista: ActorRef = system.actorOf(Props[Barista], "Barista")
  val customerJohnny: ActorRef = system.actorOf(Props(classOf[Customer], barista), "Johnny")
  val customerAlina: ActorRef = system.actorOf(Props(classOf[Customer], barista), "Alina")
  logger.info("user1{}",barista)
  logger.info("user2{}",customerJohnny)
  logger.info("user3{}",customerAlina)

  logger.info("user4 customerJohnny start calling caffe !")
  for(i <- 1 to 100){
    customerJohnny ! CaffeineWithdrawalWarning
    
  }

  // close the system!!!
  Thread.sleep(2000)
  barista ! the_neophytes_guide.P15HierarchyExampleSuperviser.Barista.ClosingTime
  logger.info("12 Totally close system !!!")
  system.terminate()
  
  
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
    import BaristaRegister._
    import EspressoCup._
    // The following is used for Future and sender back !!!
    import akka.pattern.{ask, pipe}
    import akka.util.Timeout
    import context.dispatcher

    import concurrent.duration._
    implicit val timeout = Timeout(1.seconds)
    
    //register Actor is from context, not from system. It is from context.
    val register: ActorRef = context.actorOf(Props[BaristaRegister], "Register")

  

    //TODO 3.2 my own oneForStragegy
    import akka.actor.OneForOneStrategy
    import akka.actor.SupervisorStrategy.Restart

    import scala.concurrent.duration._

    private val oneForOneStrategy: OneForOneStrategy = OneForOneStrategy(10, 1.seconds, true) {
      case _ => Restart
    }
    //TODO 4.1 This handle children's exceptions.
    // Each actor defines its own supervisor strategy,
    // which tells Akka how to deal with certain types of 
    // errors occurring in your children.
//    override def supervisorStrategy: SupervisorStrategy ={
//      log.info("supervisorStrategy-my own supervisor strategy!")
//      oneForOneStrategy
//    }

    //TODO 2 Directive
    // type Decider = PartialFunction[Throwable, Directive]
    // this allows you to match against
    // certain subtypes of Throwable and decide for each of them
    // what’s supposed to happen to your problematic child actor
    // Directive --> Stop, Restart, Resume, allow you work on your child actor!
    val decider: Decider = {
      case _: PaperJamException =>
        log.info("decider-stop")
        Stop
    }
    
    //TODO 3.1  override def supervisorStrategy
    //The child actor exception is handled in supervisorStrategy.
    //Not in the receiver method:
    //This method will affect Actor behaviour.
    //It retrun the SupervisorStrategy. there are three kinds:
    //AllForOneStrategy
    //OneForOneStrategy--> Here we overwrite the OneForOneStrategy()
     override def supervisorStrategy: SupervisorStrategy ={
      log.info("supervisorStrategy-my own supervisor strategy!")
      OneForOneStrategy()(decider.orElse(SupervisorStrategy.defaultStrategy.decider))
    }
    
    //TODO 4.2 This handle normal messages 
    def receive = {
      case the_neophytes_guide.P15HierarchyExampleSuperviser.Barista.EspressoRequest =>
        log.info(s"user/Barista6 Barista Acator start working : ${self}!")
        val receipt: Future[Any] = register ? Transaction(Espresso)//Normally, just send Actor a message, here. We need the result back.
        log.info("user/Barista8 Barista Acator get message back from Register, need wait for step7")

        val processedReceiptFuture: Future[(EspressoCup, Any)] = receipt.map((EspressoCup(Filled), _))
        log.info("user/Barista9 Barista Acator process message send Back to  from Customer{}",processedReceiptFuture)
        
        processedReceiptFuture.pipeTo(sender)

      case the_neophytes_guide.P15HierarchyExampleSuperviser.Barista.ClosingTime =>
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
    //TODO 1 postResart hook !!!
    override def postRestart(reason: Throwable) {
      super.postRestart(reason)
      log.info(s"Restarted because of ${reason.getMessage}"
      )
    }
    
    override def receive: PartialFunction[Any, Unit] = {
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
//        new java.net.URL("")
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


