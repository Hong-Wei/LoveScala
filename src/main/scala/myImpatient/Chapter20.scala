package myImpatient

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

class HelloActor extends Actor {
  def receive = {
    case "hello" => println("您好！")
    case _       => println("您是?")
  }
}

object Main extends App {
  val system = ActorSystem("HelloSystem")
  // 缺省的Actor构造函数
  val helloActor = system.actorOf(Props[HelloActor], name = "helloactor")
  helloActor ! "hello"
  helloActor ! "喂"
}

 

import akka.actor._

case object PingMessage
case object PongMessage
case object StartMessage
case object StopMessage

class Ping(pong: ActorRef) extends Actor {
  var count = 0
  def incrementAndPrint { count += 1; println("ping") }
  def receive = {
    case StartMessage =>
      incrementAndPrint
      pong ! PingMessage
    case PongMessage =>
      if (count > 9) {
        sender ! StopMessage
        println("ping stopped")
        context.stop(self)
      } else {
        incrementAndPrint
        sender ! PingMessage
      }
  }
}

class Pong extends Actor {
  def receive = {
    case PingMessage =>
      println("  pong")
      sender ! PongMessage
    case StopMessage =>
      println("pong stopped")
      context.stop(self)
//      context.system.shutdown()
  }
}

object PingPongTest extends App {
  val system = ActorSystem("PingPongSystem")
  val pong = system.actorOf(Props[Pong], name = "pong")
  val ping = system.actorOf(Props(new Ping(pong)), name = "ping")
  // start them going
  ping ! StartMessage
}


case class ProcessStringMsg(string: String)
case class StringProcessedMsg(words: Integer)
class StringCounterActor extends Actor {
  def receive = {
    case ProcessStringMsg(string) => {
      val wordsInLine = string.split(" ").length
      sender ! StringProcessedMsg(wordsInLine)
    }
    case _ => println("Error: message not recognized")
  }
}

case class StartProcessFileMsg()

class WordCounterActor(filename: String) extends Actor {

  private var running = false
  private var totalLines = 0
  private var linesProcessed = 0
  private var result = 0
  private var fileSender: Option[ActorRef] = None

  def receive = {
    case StartProcessFileMsg() => {
      if (running) {
        // println just used for example purposes;
        // Akka logger should be used instead
        println("Warning: duplicate start message received")
      } else {
        running = true
        fileSender = Some(sender) // save reference to process invoker
        import scala.io.Source._
        fromFile(filename).getLines.foreach { line =>
          context.actorOf(Props[StringCounterActor]) ! ProcessStringMsg(line)
          totalLines += 1
        }
      }
    }
    case StringProcessedMsg(words) => {
      result += words
      linesProcessed += 1
      if (linesProcessed == totalLines) {
        fileSender.map(_ ! result)  // provide result to process invoker
      }
    }
    case _ => println("message not recognized!")
  }
}