//Scala 初学指南 - v1.0.pdf
//http://wiki.jikexueyuan.com/project/guides-to-scala-book/
// 
//example1: case class User(firstName: String, lastName: String, score: Int)
//def advance(xs: List[User]) = xs match {
//
//  case User(_, _, score1) :: User(_, _, score2) :: _ => score1 - score2
//
//  case _ => 0
//}
//advance( List(User("","",100),User("","",90)))

//构造器 --> 从给定的参数列表创建一个对象
//提取器 --> 从传递给它的对象中提取出构造该对象的参数
//scala standard contains some extractors.
//eg1: case class --> scala will create Companion object = apply + unapply + single object

trait User {
  def name: String
}

class FreeUser(val name: String) extends User

object FreeUser {
  def unapply(user: FreeUser): Option[String] = Some(user.name)
}

class PremiumUser(val name: String) extends User

object PremiumUser {
  def unapply(user: PremiumUser): Option[String] = Some(user.name)
}


FreeUser.unapply(new FreeUser("Daniel"))

val user: User = new PremiumUser("Daniel")

user match {
  case FreeUser(name) => "Hello" + name
  case PremiumUser(name) => "Welcome back, dear" + name
}