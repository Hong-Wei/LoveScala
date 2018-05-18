//http://cb372.github.io/scalacache/docs/memoization.html
package LearnScalaCache

import LearnScalaCache.P4_Memozation.{Cat, logger}
import com.typesafe.scalalogging.Logger
import scalacache.memoization.memoizeSync

object P4_Memozation extends App {
  import scalacache._
  import scalacache.memcached._
  import scalacache.memoization._
  import scalacache.serialization.binary._
  import scalacache.modes.try_._
  import scala.concurrent.duration._
  import scala.util.Try
  final case class Cat(id: Int, name: String, colour: String)
  implicit val catsCache: Cache[Cat] = MemcachedCache("localhost:11211")

  val logger = Logger("P4_Memozation")
  // You wouldn't normally need to specify the type params for memoize.
  // This is an artifact of the way this README is generated using tut.
  def getCat(id: Int): Try[Cat] = memoize(None){
    // Retrieve data from a remote API here ...
     Cat(id, s"cat ${id}", "black")
  }

  logger.debug(getCat(123).toString)
  logger.debug(getCat(123).toString)
  logger.debug(getCat(123).toString)

//   If the result of your block is wrapped in an effect container, use memoizeF:
  def getCatF(id: Int): Try[Cat] = memoizeF(Some(10.seconds)) {
    Try {
      // Retrieve data from a remote API here ...
      Cat(id, s"cat ${id}", "black")
    }
  }
  logger.debug(getCatF(123).toString)


  
}

object P4_1 extends App{
  //  http://cb372.github.io/scalacache/docs/memoization.html#synchronous-memoization-api
  import scalacache._
  import scalacache.guava._
  val logger = Logger("P4_1")
  implicit val guavaCache: Cache[String] = GuavaCache[String]
  import scalacache.modes.sync._
  import scala.concurrent.duration._
  
  def getCatSync(id: Int): String = memoizeSync(Some(10.seconds)) {
    "hongwei"
  }
  logger.debug("1")
  logger.debug(getCatSync(123).toString)
  logger.debug(getCatSync(123).toString)
}