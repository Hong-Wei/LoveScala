//http://danielwestheide.com/blog/2012/11/21/the-neophytes-guide-to-scala-part-1-extractors.html
// Extracting one value
// Extractor is just implicit of the unapply method.
// It will call the default unapply method, and give the value to it.
// constructor: val a = User("hongwei") ==> you create the object from the paramters
// extractor : val User(name) = a; not work, only in pattern matching .



trait User {
  def name: String
}

class FreeUser(val name: String) extends User

class PremiumUser(val name: String) extends User

object FreeUser {
  def unapply(user: FreeUser): Option[String] = Some(user.name)
}

object PremiumUser {
  def unapply(user: PremiumUser): Option[String] = Some(user.name)
}

val unapplyFreeUser: Option[String] = FreeUser.unapply(new FreeUser("Daniel"))

val user: User = new PremiumUser("Daniel")
//This method is accept a User object, and return the filed name from the object.
def ExtractorMethod(user: User): String = user match {
  case FreeUser(name) => "Hello " + name
  case PremiumUser(name) => "Welcome back, dear " + name
}


































































































































































