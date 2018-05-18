package LearnScalaCache

import scalacache.Cache

import scala.util.Try

object P1_Getting_Started extends App {

//  http://cb372.github.io/scalacache/docs/index.html#create-a-cache
  import scalacache.memcached._

  // We'll use the binary serialization codec - more on that later
  import scalacache.serialization.binary._

  final case class Cat(id: Int, name: String, colour: String)

  implicit val catsCache: Cache[Cat] = MemcachedCache("localhost:11211")

  println(1)

//  http://cb372.github.io/scalacache/docs/index.html#basic-cache-operations
  val ericTheCat = Cat(1, "Eric", "tuxedo")
  val doraemon = Cat(99, "Doraemon", "blue")
  import scalacache.modes.try_._
  private val value1: Try[Any] = catsCache.put("eric")(ericTheCat)
  import scala.concurrent.duration._
  private val value2: Try[Any] = catsCache.put("doraemon")(doraemon, ttl = Some(10.seconds))

  private val triedMaybeCat1: Try[Option[Cat]] = catsCache.get("eric")
  println(triedMaybeCat1)
  private val triedMaybeCat2: Try[Option[Cat]] = catsCache.get("doraemon")
  println(triedMaybeCat2)

  // Remove it from the cache
  private val value: Try[Any] = catsCache.remove("doraemon")

  // Flush the cache 
  //TODO, this throw error????
  //catsCache.removeAll[Cat]()



  // Wrap any block with caching: if the key is not present in the cache,
  // the block will be executed and the value will be cached and returned
  val result: Try[Cat] = catsCache.caching("benjamin")(ttl = None) {
     // e.g. call an external API ...
     new RuntimeException("Hongwei")
     Cat(1, "Benjamin", "ginger")
   }


  // If the result of the block is wrapped in an effect, use cachingF
  val result2: Try[Cat] = catsCache.cachingF("benjamin2")(ttl = None) {
  	import scala.util.Try
    Try {
      // e.g. call an external API ...
      new RuntimeException("Hongwei")
      Cat(2, "Benjamin2", "ginger2")
    }
  }

  // You can also pass multiple parts to be combined into one key
  private val value3: Try[Any] = catsCache.put("foo", 123, "bar")(ericTheCat)
  println(value3) // Will be cached with key "foo:123:bar"
  
}