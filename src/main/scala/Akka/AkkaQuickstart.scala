package Akka

//https://developer.lightbend.com/guides/akka-quickstart-scala/full-example.html
import akka.actor.{ Actor, ActorLogging, ActorRef, ActorSystem, Props }

object Greeter {
  def props(message: String, printerActor: ActorRef): Props = Props(new Greeter(message, printerActor))
  final case class WhoToGreet(who: String)
  case object Greet
}

class Greeter(message: String, printerActor: ActorRef) extends Actor {
  import Greeter._
  import Printer._

  var greeting = ""

  def receive = {
    case WhoToGreet(who) =>
      greeting = message + ", " + who
    case Greet           =>
      printerActor ! Greeting(greeting)
  }
}

object Printer {
  def props: Props = Props[Printer]
  final case class Greeting(greeting: String)
}

class Printer extends Actor with ActorLogging {
  import Printer._

  def receive = {
    case Greeting(greeting) =>
      log.info("Greeting received (from " + sender() + "): " + greeting)
  }
}

object AkkaQuickstart extends App {
  import Greeter._

  // Create the 'helloAkka' actor system
  val system: ActorSystem = ActorSystem("helloAkka")

  // Create the printer actor
  val printer: ActorRef = system.actorOf(Printer.props, "printerActor")

  // Create the 'greeter' actors
  val howdyGreeter: ActorRef =
    system.actorOf(Greeter.props("Howdy", printer), "howdyGreeter")
  val helloGreeter: ActorRef =
    system.actorOf(Greeter.props("Hello", printer), "helloGreeter")
  val goodDayGreeter: ActorRef =
    system.actorOf(Greeter.props("Good day", printer), "goodDayGreeter")

  howdyGreeter ! WhoToGreet("Akka")
  howdyGreeter ! Greet

  howdyGreeter ! WhoToGreet("Lightbend")
  howdyGreeter ! Greet

  
  howdyGreeter ! WhoToGreet("1")
  howdyGreeter ! Greet
  
  howdyGreeter ! WhoToGreet("2")
  howdyGreeter ! Greet
  
  howdyGreeter ! WhoToGreet("3")
  howdyGreeter ! Greet
  
  howdyGreeter ! WhoToGreet("4")
  howdyGreeter ! Greet
  
  howdyGreeter ! WhoToGreet("5")
  howdyGreeter ! Greet
  
  howdyGreeter ! WhoToGreet("6")
  howdyGreeter ! Greet
  
  howdyGreeter ! WhoToGreet("7")
  howdyGreeter ! Greet
  
  howdyGreeter ! WhoToGreet("8") // in one actor it has the order !!
  howdyGreeter ! Greet
  
  helloGreeter ! WhoToGreet("Scala")
  helloGreeter ! Greet

  goodDayGreeter ! WhoToGreet("Play")
  goodDayGreeter ! Greet
}