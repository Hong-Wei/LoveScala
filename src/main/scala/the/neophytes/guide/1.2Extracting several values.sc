//http://danielwestheide.com/blog/2012/11/21/the-neophytes-guide-to-scala-part-1-extractors.html
// Extracting several values
trait User {
  def name: String
  def score: Int
}

class FreeUser(val name: String, val score: Int, val upgradeProbability: Double) extends User

class PremiumUser(val name: String, val score: Int) extends User

object FreeUser {
  def unapply(user: FreeUser): Option[(String, Int, Double)] =
    Some((user.name, user.score, user.upgradeProbability))
}
object PremiumUser {
  def unapply(user: PremiumUser): Option[(String, Int)] = Some((user.name, user.score))
}

val user: User = new FreeUser("Daniel", 3000, 0.7d)
user match {
  case FreeUser(name, _, p) =>
    if (p > 0.75) name + ", what can we do for you today?" else "Hello " + name
  case PremiumUser(name, _) => "Welcome back, dear " + name
}