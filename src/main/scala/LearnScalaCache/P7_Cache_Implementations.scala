package LearnScalaCache

import com.typesafe.scalalogging.Logger

object P7_Cache_Implementations extends App {

  // 1 Guava Cache:  http://cb372.github.io/scalacache/docs/cache-implementations.html#google-guava
  import scalacache._
  import scalacache.guava._

  implicit val guavaCache: Cache[String] = GuavaCache[String]

  //If you want Customize, you can set it 
  import com.google.common.cache.CacheBuilder
  val underlyingGuavaCache = CacheBuilder.newBuilder().maximumSize(10000L).build[String, Entry[String]]
  implicit val guavaCacheCustomize: Cache[String] = GuavaCache(underlyingGuavaCache)
  

  //2  Memcached
  import scalacache._
  import scalacache.memcached._
  import scalacache.serialization.binary._

  implicit val memcachedCache: Cache[String] = MemcachedCache("localhost:11211")

  import scalacache._
  import scalacache.memcached._
  import scalacache.serialization.binary._
  import net.spy.memcached._

  val memcachedClient = new MemcachedClient(
    new BinaryConnectionFactory(),
    AddrUtil.getAddresses("localhost:11211")
  )
  implicit val customisedMemcachedCache: Cache[String] = MemcachedCache(memcachedClient)

  //3 Ehcache
  import scalacache._
  import scalacache.ehcache._
  import net.sf.ehcache.{Cache => UnderlyingCache, _}

  // We assume you've already taken care of Ehcache config, 
  // and you have an initialized Ehcache cache.
  val cacheManager = new CacheManager
  val underlying: UnderlyingCache = cacheManager.getCache("myCache")

  implicit val ehcacheCache: Cache[String] = EhcacheCache(underlying)
  
  //4 Redis
  import scalacache._
  import scalacache.redis._
  import scalacache.serialization.binary._

  implicit val redisCache: Cache[String] = RedisCache("host1", 6379)

  // or Jedis client
  import scalacache._
  import scalacache.redis._
  import scalacache.serialization.binary._
  import _root_.redis.clients.jedis._

  val jedisPool = new JedisPool("localhost", 6379)
  implicit val customisedRedisCache: Cache[String] = RedisCache(jedisPool)


  
  //5 caffeine
  import scalacache._
  import scalacache.caffeine._

  implicit val caffeineCache: Cache[String] = CaffeineCache[String]


  import scalacache._
  import scalacache.caffeine._
  import com.github.benmanes.caffeine.cache.Caffeine

  val underlyingCaffeineCache = Caffeine.newBuilder().maximumSize(10000L).build[String, Entry[String]]
  implicit val customisedCaffeineCache: Cache[String] = CaffeineCache(underlyingCaffeineCache)

  
  //6 cache2k
  import java.util.concurrent.TimeUnit
  import scalacache._
  import scalacache.cache2k._
  import org.cache2k.Cache2kBuilder

  // You have to configure the cache with a ExpiryPolicy or Cache2kBuilder.expireAfterWrite
  // if you want to set expiry on individual values.
  val underlyingCache2kCache = new Cache2kBuilder[String, String]() {}.expireAfterWrite(1L, TimeUnit.MINUTES).build
  implicit val customisedCache2kCache: Cache[String] = Cache2kCache(underlyingCache2kCache)

  
  //7 OHC
  import scalacache._
  import scalacache.ohc._
  import org.caffinitas.ohc.CacheSerializer

  implicit val valueSerializer: CacheSerializer[String] = OhcCache.stringSerializer
  implicit val ohcCache: Cache[String] = OhcCache[String]


  import scalacache._
  import scalacache.ohc._
  import org.caffinitas.ohc.OHCacheBuilder

  // You have to configure the cache with OHCacheBuilder.timeouts(true)
  // if you want to set expiry on individual values.
  val underlyingOhcCache =
  OHCacheBuilder.
    newBuilder().
    keySerializer(OhcCache.stringSerializer).
    valueSerializer(OhcCache.stringSerializer).
    timeouts(true).
    build()
  implicit val customisedOhcCache: Cache[String] = OhcCache(underlyingOhcCache)


  println(123)

  
}







object P0_Guava extends App {
  val logger = Logger("P2_Modes")

  //BK 1 switch cache with the props between GuavaCache and Redis

  //1 make GuavaCache work

  import scalacache._
  import scalacache.guava._
  import scalacache.modes.sync._
  import scala.concurrent.duration._
  import scalacache.memoization.memoizeSync

  implicit val guavaCache= GuavaCache[String]
  def getCatSync(id: Int): String = memoizeSync(Some(10.seconds)) {
    "hongwei"
  }
  logger.debug(getCatSync(123).toString)
  logger.debug(getCatSync(123).toString)

  println("GuavaCache work well")



}


object P1_RedisCache extends App {
  val logger = Logger("P1_Doing")
  import scalacache._
  import scalacache.redis._
  import scalacache.serialization.binary._
  import scala.concurrent.duration._
  import scalacache.memoization.memoizeSync
  import scalacache.modes.sync._

  implicit val redisCache: Cache[String] = RedisCache("127.0.0.1", 6379)

  def getCatSync2(id: Int): String = memoizeSync(Some(10.seconds)) {
    "hongwei"
  }
  logger.debug(getCatSync2(123).toString)
  logger.debug(getCatSync2(123).toString)
  println("Redis Work Well")

}

object P2_MemeoryCache extends App {
  //  $ lunchy start memcached 
  // $ lunchy stop memcached
  val logger = Logger("P1_Doing")
  import scalacache._
  import scalacache.memcached.MemcachedCache
  import scalacache.serialization.binary._
  import scala.concurrent.duration._
  import scalacache.memoization.memoizeSync
  import scalacache.modes.sync._
  
  implicit val catsCache: Cache[String] = MemcachedCache("localhost:11211")

  def getCatSync2(id: Int): String = memoizeSync(Some(10.seconds)) {
    "hongwei"
  }
  logger.debug(getCatSync2(123).toString)
  logger.debug(getCatSync2(123).toString)
  println("Redis Work Well")
}