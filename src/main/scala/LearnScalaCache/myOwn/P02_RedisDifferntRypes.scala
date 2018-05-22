package LearnScalaCache.myOwn

import com.typesafe.scalalogging.StrictLogging
import scalacache.serialization.{Codec, FailedToDecode}

object P02_RedisDifferntRypes extends App with  StrictLogging{

  import scalacache._
  import scalacache.redis._
  import scalacache.serialization.binary._
  import scala.concurrent.duration._
  import scalacache.memoization.memoizeSync
  import scalacache.modes.sync._




  case class Bank(id: Int, name: String)
  case class Account(id: Int, name: String)

  implicit val redisCache: Cache[Bank] = RedisCache("127.0.0.1", 6379)
  implicit val redisCacheList: Cache[List[Bank]] = RedisCache("127.0.0.1", 6379)
  implicit val redisCache2: Cache[Account] = RedisCache("127.0.0.1", 6379)
  implicit val redisCache2List: Cache[List[Account]] = RedisCache("127.0.0.1", 6379)



  
  def getBanks(id: Int): List[Bank] = memoizeSync(Some(10.seconds)) {
    Bank(1,"1")::Bank(2,"2")::Nil
  }

  def getAccounts(id: Int): List[Account] = memoizeSync(Some(10.seconds)) {
    Account(1,"1")::Account(2,"2")::Nil
  }
  
  logger.debug(getBanks(123).toString)
  logger.debug(getBanks(123).toString)
  logger.debug(getAccounts(123).toString)
  logger.debug(getAccounts(123).toString)
  println("Redis Work Well")  
  
}