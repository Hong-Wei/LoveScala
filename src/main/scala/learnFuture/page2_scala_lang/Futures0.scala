//https://docs.scala-lang.org/overviews/core/futures.html#introduction
package learnFuture.page2_scala_lang

import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.Future


object Futures0 extends App with StrictLogging
{
  
  implicit val ec = concurrent.ExecutionContext.Implicits.global
  @volatile var totalA = 0
  
  val text = Future
  {
    logger.info("Computer thread")
    "na" * 16 + "BATMAN!!!"
  }
  
  text onSuccess
    {
      case txt => 
        totalA =totalA + txt.count(_ == 'a')
        logger.info(totalA.toString)
    }
  
  text onSuccess
    {
      
      case txt => 
        txt.count(_ == 'A')
        totalA += txt.count(_ == 'A')
        logger.info(totalA.toString)
    }
  
  
  
  
  
  Future
  {
    logger.info("Computer thread")
    "na" * 16 + "BATMAN!!!"
  }
  Future
  {
    logger.info("Computer thread")
    "na" * 16 + "BATMAN!!!"
  }
  
  
  Future
  {
    logger.info("Computer thread")
    "na" * 16 + "BATMAN!!!"
  }
  Future
  {
    logger.info("Computer thread")
    "na" * 16 + "BATMAN!!!"
  }
  
  Future
  {
    logger.info("Computer thread")
    "na" * 16 + "BATMAN!!!"
  }
  Future
  {
    logger.info("Computer thread")
    "na" * 16 + "BATMAN!!!"
  }
  
  Future
  {
    logger.info("Computer thread")
    "na" * 16 + "BATMAN!!!"
  }
  Future
  {
    logger.info("Computer thread")
    "na" * 16 + "BATMAN!!!"
  }
  
  Future
  {
    logger.info("Computer thread")
    "na" * 16 + "BATMAN!!!"
  }
  Future
  {
    logger.info("Computer thread")
    "na" * 16 + "BATMAN!!!"
  }
  
  
  logger.info("main")
  Thread.sleep(50000)
}
