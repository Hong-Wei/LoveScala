package learnLogging

import com.typesafe.scalalogging.{LazyLogging, Logger, StrictLogging}
import org.slf4j.LoggerFactory

// Learn --scala-logging
//https://github.com/typesafehub/scala-logging#getting-scala-logging
class MyLogging

object MyLogging extends App {
  val logger = Logger("name")
  //Or, you pass in a SLF4J logger instance:
  val logger2 = Logger(LoggerFactory.getLogger("name"))
  //Or, you pass in a class:
  val logger3 = Logger(classOf[MyLogging])
  //Or, using the runtime class wrapped by the implicit class tag parameter:
  val logger4 = Logger[MyLogging]

  
  //https://github.com/typesafehub/scala-logging#string-interpolation
  val arg1="1"
  val arg2="2"
  val arg3="3"
  logger.error(s"my log message: $arg1 $arg2 $arg3")
  logger.error("my log message: {} {} {}", arg1, arg2, arg3)

 
}


class MyClass extends LazyLogging {
  logger.debug("This is very convenient ;-)")
}

class MyClass2 extends StrictLogging {
  logger.debug("This is very convenient ;-)")
}

object App2 extends App{
  val test = "SomeStringValue"
  val count= test match {
    case "Name" => 1
    case "Age"  => 2
    case "Sex"  => 3
    case default =>pf
  } 
  
//  def pationFunction (A: String)={
//    case "Name" => 1
//    case "Age"  => 2
//  }

  val pf: PartialFunction[(String, Int), String] = { case (word, freq) if freq > 3 && freq < 25 => word }
}


