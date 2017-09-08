package learnScalaUtil

/**
  * Created by zhanghongwei on 08/09/2017.
  */
object tryExample extends App{
  import scala.io.StdIn
  import scala.util.{Try, Success, Failure}

  def divide: Try[Int] = {
    val dividend: Try[Int] = Try(StdIn.readLine("Enter an Int that you'd like to divide:\n").toInt)
    val divisor: Try[Int] = Try(StdIn.readLine("Enter an Int that you'd like to divide by:\n").toInt)
    val problem: Try[Int] = dividend.flatMap(x => divisor.map(y => x/y))
    val res: Try[Int] = problem match {
      case Success(v) =>
        println("Result of " + dividend.get + "/"+ divisor.get +" is: " + v)
        Success(v)
      case Failure(e) =>
        println("You must've divided by zero or entered something that's not an Int. Try again!")
        println("Info from the exception: " + e.getMessage)
        divide
    }
    res
  }
  divide
}