package the_neophytes_guide

import akka.actor.{ActorLogging, ActorRef, ActorSystem, Props}
import com.typesafe.scalalogging.Logger


object P15ActorBasic extends App {

  val logger = Logger(P15ActorBasic.getClass)
  
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
    
    val logger = Logger(P15ActorBasic.getClass)
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

