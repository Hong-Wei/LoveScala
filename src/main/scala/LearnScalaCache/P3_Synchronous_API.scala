//http://cb372.github.io/scalacache/docs/modes.html
package LearnScalaCache

object P3_Synchronous_API extends App {
  import scalacache._
  import scalacache.memcached._
  import scalacache.modes.sync._
  import scalacache.serialization.binary._
  final case class Cat(id: Int, name: String, colour: String)
  implicit val catsCache: Cache[Cat] = MemcachedCache("localhost:11211")
  val myValue: Option[Cat] = sync.get("eric")
  val ericTheCat = Cat(1, "Eric", "tuxedo")


  val result = sync.caching("myKey")(ttl = None) {
     // do stuff...
     ericTheCat
  }
  val myValue2: Option[Cat] = sync.get("myKey")
  println(2)
}
