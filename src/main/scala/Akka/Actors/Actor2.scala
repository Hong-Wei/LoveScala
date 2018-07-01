//https://doc.akka.io/docs/akka/current/actors.html#defining-an-actor-class

package Akka.Actors
import akka.actor.{Actor, ActorRef, ActorSystem, PoisonPill, Props}
import com.typesafe.scalalogging.StrictLogging

import language.postfixOps
import scala.concurrent.duration._

case object Ping
case object Pong

class Pinger extends Actor with StrictLogging {
  var countDown = 10

  def receive = {
    case Pong ⇒
      logger.info(s"${self.path} received pong, count down $countDown")

      if (countDown > 0) {
        countDown -= 1
        sender() ! Ping
      } else {
        countDown =10
        sender() ! PoisonPill //kill sender
        self ! PoisonPill //kill itself
      }
  }
}

class Ponger(pinger: ActorRef) extends Actor {
  def receive = {
    case Ping ⇒
      println(s"${self.path} received ping")
      pinger ! Pong
  }
}


object App2 extends App{
  val system = ActorSystem("pingpong")


  
    val pinger = system.actorOf(Props[Pinger], "pinger")

    val ponger = system.actorOf(Props(classOf[Ponger], pinger), "ponger")

    import system.dispatcher
  
    system.scheduler.scheduleOnce(5000 millis) {
      ponger ! Ping
    }
  
}
    
