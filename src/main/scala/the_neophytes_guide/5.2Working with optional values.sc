import java.io.Serializable

import scala.collection.immutable.Seq
case class User(
                 id: Int,
                 firstName: String,
                 lastName: String,
                 age: Int,
                 gender: Option[String])

object UserRepository {
  private val users = Map(
    1 -> User(1, "John",    "Doe", 32, Some("male")),
    2 -> User(2, "Johanna", "Doe", 30, None)
  )

  def findById(id: Int): Option[User] = users.get(id)

  def findAll: Iterable[User] = users.values
}


//1  Guava library -Bad
val user1 = UserRepository.findById(1)
if (user1.isDefined) {
  println(user1.get.firstName)
} // will print "John"

//2 Providing a default value

val user2 = User(2, "Johanna", "Doe", 30, None)
println("Gender: " + user2.gender.getOrElse("not specified")) // will print "not specified"
val user21 = Some( None)
val orElse: Serializable = user21.getOrElse("asfd")
val orElse1 = user21 orElse Some("1231")

//3 Pattern matching - not good
val user3 = User(2, "Johanna", "Doe", 30, None)
user3.gender match {
  case Some(gender) => println("Gender: " + gender)
  case None => println("Gender: not specified")
}

//4 Options can be viewed as collections
//4.1 Performing a side-effect if a value is present
UserRepository.findById(2).foreach(user => println(user.firstName)) // prints "Johanna"

//4.2 Mapping an option
val age = UserRepository.findById(1).map(_.age) // age is Some(32)
val gender: Option[Option[String]] = UserRepository.findById(1).map(_.gender) // gender is an Option[Option[String]]
val gender1 = UserRepository.findById(1).flatMap(_.gender) // gender is Some("male")

//4.3 example for flatMap 
val names = List(List(List(List("John", "Johanna", "Daniel"), List(), List("Doe", "Westheide"))))
val map: Seq[List[List[List[String]]]] = names.map(_.map(_.map(_.map(_.toUpperCase))))
// results in List(List("JOHN", "JOHANNA", "DANIEL"), List(), List("DOE", "WESTHEIDE"))
val map1: List[String] = names.flatMap(_.flatMap(_.flatMap(_.map(_.toUpperCase))))
// results in List("JOHN", "JOHANNA", "DANIEL", "DOE", "WESTHEIDE")


//4.4 Filtering an option
UserRepository.findById(1).filter(_.age > 30) // Some(user), because age is > 30
UserRepository.findById(2).filter(_.age > 30) // None, because age is <= 30
UserRepository.findById(3).filter(_.age > 30) // None, because user is already None


//4.5 For comprehensions
for {
  user <- UserRepository.findById(1)
  gender <- user.gender
} yield gender // results in Some("male")


for {
  user <- UserRepository.findAll
  gender <- user.gender
} yield gender


//4.6 Usage in the left side of a generator
for {
  User(_, _, _, _, Some(gender)) <- UserRepository.findAll
} yield gender
