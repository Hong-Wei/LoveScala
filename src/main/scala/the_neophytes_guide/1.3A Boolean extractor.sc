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

object premiumCandidate { 
  def unapply(user: FreeUser): Boolean = user.upgradeProbability > 0.75 
}

object premiumCandidate2 {
  def unapply(user: PremiumUser): Boolean = true
}

object premiumCandidate3 {
  def unapply(user: User): Boolean = true
}

val user: User = new FreeUser("Daniel", 2500, 0.8d) 

user match {
    //@ --> bind the value that is matched to a variable,
    // you do not really have the need to extract parameters from a data structure against which you want to match.
    // instead, you just do a simple boolean check.
    // The third and the last of the available `unappply` comes in handy. 
    // allow to bind the value that is matched to a variable, too. -->
    // because of premiumCandidate3.unapply extract the User, so user variable is a User Object
    // because of premiumCandidate2.unapply extract the PremiumUser, so user variable is a PremiumUser Object
    // because of premiumCandidate.unapply extract the FreeUser, so user variable is a FreeUser Object
  case user @ premiumCandidate3() => "initiateSpamProgram(freeUser)"+user
  case freeUser @ premiumCandidate() => "initiateSpamProgram(freeUser)"+freeUser
  case premiumUser @ premiumCandidate2() => "initiateSpamProgram(freeUser)"+premiumUser
  case _ => "sendRegularNewsletter(user)"
}







































































