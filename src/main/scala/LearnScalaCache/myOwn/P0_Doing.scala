package LearnScalaCache.myOwn

import LearnScalaCache.P3_Synchronous_API.{Cat, ericTheCat}
import com.typesafe.scalalogging.Logger
import net.liftweb.common.{Box, Full}
import net.liftweb.util.Props

object Doing extends App{
  
  //BK I need impliment the basic cache for adapter.
  // set(key)(value)
  // get(key)(value) later.
  
  //1Guava Way

  val logger = Logger("P01_SwitchCacheInProps")


  import scalacache._
  import scalacache.guava._
  import scalacache.modes.sync._
  import scalacache.redis._
  import scalacache.serialization.binary._
  import scala.concurrent.duration._


  final case class Cat(id: Int, name: String, colour: String)
  implicit val guavaCache: GuavaCache[Cat] = GuavaCache[Cat]
  val redisCache: Cache[Cat] = RedisCache("127.0.0.1", 6379)
 
//  val cacheImplementation: Box[String] = Props.get("cache.implementation")

//  implicit val scalaCache: Cache[String] = cacheImplementation match {
//    case Full(value) if value.toLowerCase == "redis" =>
//      redisCache
//    case Full(value) if value.toLowerCase == "in-memory" =>
//      guavaCache
//    case _ =>
//      guavaCache
//  }

  val ericTheCat = Cat(1, "Eric", "tuxedo")
  
  val result: Cat = sync.caching("myKey")(ttl = None) {
    // do stuff...
    ericTheCat
  }
  
  val a: Option[Cat] = sync.get("myKey")

  println(12)
  
  
  
}