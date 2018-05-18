//http://cb372.github.io/scalacache/docs/flags.html



package LearnScalaCache

object P5_Flags extends App {

  import scalacache._
  import scalacache.memcached._
  import scalacache.memoization._
  import scalacache.modes.sync._
  import scalacache.serialization.binary._

  final case class Cat(id: Int, name: String, colour: String)

  implicit val catsCache: Cache[Cat] = MemcachedCache("localhost:11211")

  def getCatWithFlags(id: Int)(implicit flags: Flags): Cat = memoizeSync(None) {
    // Do DB lookup here...
    Cat(id, s"cat ${id}", "black")
  }

  def getCatMaybeSkippingCache(id: Int, skipCache: Boolean): Cat = {
    implicit val flags = Flags(readsEnabled = !skipCache)
    getCatWithFlags(id)
  }

}
