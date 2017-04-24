import scala.actors.Actor
import scala.actors.Actor._

class HiActor extends Actor {
  def act() {
    while (true) {
      receive {
        case "Hi" => println("Hello")
      }
    }
  }
}

object Main extends App {
  println("Hit Ctrl+C to exit")

  // way1, you define your own Class HiActor and extends Actor
  // and new the new object and start the Actor
  val actor1 = new HiActor
  // the start is similar as run in Java, you need start the method
  actor1.start()
  // start method, and just send message to the object, it will call recerive method in it own
  actor1 ! "Hi"
 
  // way2, actor has its own companion object, and you can call its method actor, you can create the Actor,
  // no need to create the class.
  val actor2 = Actor.actor {
    while (true) {
      receive {
        case "Hi" => println("Hello")
      }
    }
  }
  // if define actor in this way, no need to start, it will start automaticlly. 
  actor2 ! "Hi"
  
  // note:  an anonymous actor needs to send another actor a reference to itself. It is available as the self property.
  
}
