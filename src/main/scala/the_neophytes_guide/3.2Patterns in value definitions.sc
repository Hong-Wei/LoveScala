//http://danielwestheide.com/blog/2012/12/05/the-neophytes-guide-to-scala-part-3-patterns-everywhere.html

case class Player(name: String, score: Int)


def currentPlayer(): Player = Player("Daniel", 3500)


//normal way, just return object, and get the name from object
val play = currentPlayer()
val name = play.name

//But in scala- use the Patterns in Definitions
val Player(name2,_) = currentPlayer()


// Error handling for `Patterns in value definitions
// take care of the pattern always matches. Otherwise, you will be the witness of an exception at runtime.
def scores: List[Int] = 1::Nil
val best :: rest = scores
//println("The score of our champion is " + best)
//val Player(name3,_) = scores

Nil == List()

//you need to know your type at compile time.



//working with tuples, make your code a lot more readable!
def gameResult(): (String, Int) = ("Daniel", 3500)
val result = gameResult()
println(result._1 + ": " + result._2)

val (name3, score3) = gameResult()
println(name3 + ": " + score3)


