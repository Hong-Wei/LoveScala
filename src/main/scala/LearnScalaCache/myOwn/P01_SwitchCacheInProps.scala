package LearnScalaCache.myOwn

import com.typesafe.scalalogging.Logger
import net.liftweb.common.{Box, Full}
import net.liftweb.util.Props


//BK 1 switch cache with the props between GuavaCache and Redis
object P01_SwitchCacheInProps extends App {
  val logger = Logger("P01_SwitchCacheInProps")

  
  import scalacache._
  import scalacache.guava._
  import scalacache.memoization.memoizeSync
  import scalacache.modes.sync._
  import scalacache.redis._
  import scalacache.serialization.binary._
  import scala.concurrent.duration._
  
  
  val guavaCache= GuavaCache[String]
  val redisCache: Cache[String] = RedisCache("127.0.0.1", 6379)
  
  val cacheImplementation: Box[String] = Props.get("cache.implementation")
  
  implicit val scalaCache: Cache[String] = cacheImplementation match {
    case Full(value) if value.toLowerCase == "redis" =>
      redisCache 
    case Full(value) if value.toLowerCase == "in-memory" =>
      guavaCache
    case _ =>
      guavaCache
  }
  def getCatSync2(id: Int): String = memoizeSync(Some(10.seconds)) {
    "hongwei"
  }
  

  logger.info(cacheImplementation.toString)
  logger.info(scalaCache.toString)
  logger.debug(getCatSync2(123).toString)
  logger.debug(getCatSync2(123).toString)
  println("Redis Work Well")
  
}