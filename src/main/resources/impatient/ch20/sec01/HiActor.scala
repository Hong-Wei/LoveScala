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
  actor1.start()

  actor1 ! "Hi"
 
  // way2,
  val actor2 = actor {
    while (true) {
      receive {
        case "Hi" => println("Hello")
      }
    }
  }

  actor2 ! "Hi"
}
