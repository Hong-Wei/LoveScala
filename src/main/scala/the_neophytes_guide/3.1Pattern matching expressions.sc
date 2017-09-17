case class Player(name: String, score: Int)

def printMessage(player: Player) : Unit = player match {
  case Player(_, score) if score > 100000 => println("Get a job, dude!")
  case Player(name, _) => println("Hey " + name + ", nice to see you again!")
}


def message(player: Player): String = player match {
  case Player(_, score) if score > 100000 => "Get a job, dude!"
  case Player(name, _) => "Hey " + name + ", nice to see you again!"
}
def printMessage2(player: Player): Unit = println(message(player))