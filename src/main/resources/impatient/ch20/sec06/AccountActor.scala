import scala.actors.Actor

case class Deposit(amount: Double)
case class Withdraw(amount: Double)
case class Balance(amount: Double)


class AccountActor extends Actor {
  val delay = 5000

  private var balance = 0.0
  def act() {
    while (true) {
      receive {
        case Deposit(amount) => { 
          Thread.sleep(delay)
          balance += amount; 
          sender ! Balance(balance) 
        }
          // reply(Balance(balance)) == sender! Balance(balance), same meaning
        case Withdraw(amount) => { balance -= amount; reply(Balance(balance)) }
      }
    }
  }
}

object Main extends App {
  println("Hit Ctrl+C to exit")

  val account = new AccountActor
  account.start

  // !? is a blocking message, it need the result to continue
  val reply = account !? Deposit(1000)
  println("Sent with !?")
  reply match {
    case Balance(bal) => println("Current Balance: " + bal)
  }

  // !! -- instead of waiting for answer, you can opt to receiver a future
  // Future -- an object that will yield a result when it becomes availabe. 
  val replyFuture = account !! Deposit(1000)
  println("Sent with !!")
  println(replyFuture())
}
