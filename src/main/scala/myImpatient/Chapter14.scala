package myImpatient

//import impatient.ch14.sec09._
//import myImpatient.CaseClasses.Currency

import scala.collection.immutable.IndexedSeq

/**
  * Created by zhanghongwei on 14/11/16. I just want to test it . I add something in develop branch !
  */
object Chapter14 extends App {
  //bk 14.1 A Better Switch
  //1 C style
  var honwei = "Hongwei"
  var sign = 0
  var ch = '*'
  private val Unit1: Unit = ch match {
    case '+' => sign = 1
    case '-' => sign = -1
    case _ => sign = 0 // if no ,scala.MatchError
  }


//  for {
//    1 <- 1 to 10
//    name <- ch match {
//      case '+' => Option(1)
//    }
//    a = 5


//  } yield 1





  //2 match is an expression, not a statement.
  private val i: Int = ch match {
    case '+' => 1
    case '-' => -1
    case _ => 0 // if no ,scala.MatchError
  }
  //3 you can use any types in match ,not only numbers
  //  color match {
  //    case Color.RED =>
  //    case Color.RED =>
  //  }
  //BK 14.2 Guards
  // Guard is any Boolean conditions
  var digit = Character.digit('1', 10)
  ch match {
    case '+' => 1
    case '-' => -1
    case _ if Character.isDigit(ch) => digit = Character.digit(ch, 10) //
    case _ => 0 // if no ,scala.MatchError
  }
  //BK 14.3 Variables in Patterns
  ch = '1'
  var ch143 = '3'
  var intValue = 4
  print(ch match {
    //    case '+' => 1
    case '-' => -1
    case ch if Character.isDigit(ch) => Character.digit(ch, 10) // add gurd
    case abc => print(abc) // will assign the value to abc
  })

  import scala.math._

  var x = Pi
  x match {
    case Pi => print(Pi)
    case x => print(2)
  }
  println()
  //BK14.4 Type Patterns
  //  much better than the following code:
  //  for (obj <- Array(42, "42", BigInt(42), BigInt, 42.0, Chapter14, Map("Hongwei" -> 28), Map(12 -> 12))) {
  //    val result = obj match {
  //      case x: Int => x
  //      case s: String => Integer.parseInt(s)
  //      case _: BigInt => Int.MaxValue
  //      case BigInt => -1
  //      case x: Double => x
  //      case Chapter14 => Chapter14.honwei = "500"; Chapter14.honwei
  //      case m: Map[_, _] => m // use a generic map
  //      case m: Map[String, Int] => m
  //      case m: Map[Int, Int] => m // generic types are eraed , not useful!!!
  //      case _ => 0
  //    }
  //    println(result)
  //  }

  //  if (p.isInstanceOf[Employee]) {
  //    val s = p.asInstanceOf[Employee] // s has type Employee
  //    s.salary = 50000

  //2 Matches occur at runtime, and generic types are erased
  //  case m :Map[String, Int]=>
  // you can match a generic map:
  //  case m:Map[]=>   ...

  //bk 14.5 Matching Arrays, Lists, and Tuples
  //  for (arr <- Array(Array(0), Array(1, 0),Array(1, 0,0), Array(0, 1, 0),Array(0, 1, 1,1,11,1), Array(1, 1, 0))) {
  //
  //    val result = arr match {
  //      case Array(0) => arr.mkString
  //      case Array(x, y) => arr.mkString
  //      case Array(0, _*) => arr.mkString
  //      case Array(1,1, _*) => arr.mkString
  //      case _ => "something else"
  //    }
  //
  //    println(result)
  //  }

  //  for (lst <- Array(List(0), List(1, 0), List(0, 0, 0), List(1, 0, 0))) {
  //
  //    val result = lst match {
  //      case 0 :: Nil => "0"
  //      case x :: y :: Nil => x + " " + y
  //      case 0 :: tail => "0 ..."
  //      case _ => "something else"
  //    }
  //
  //    println(result)
  //  }
  //
  //  for (pair <- Array((0, 1,1,1), (1, 0), (1, 1),(0,0))) {
  //    val result = pair match {
  //      case (0, _) => "0 ..."
  //      case (y, 0) => y + " 0"
  //      case _ => "neither is 0"
  //    }
  //
  //    println(result)
  //  }

  //BK 14.6 Extractors
  //1 unapply -> numbers of objects
  //2 unapplySeq-> a sequence
  val arr = Array(0, 1)

  arr match {
    case Array(0, x) => x
  }

  Array.unapplySeq(arr)

  val pattern = "([0-9]+) ([a-z]+)".r

  "99 bottles" match {
    case pattern(num, item) => (num.toInt, item)
  }

  private val seq: Option[List[String]] = pattern.unapplySeq("99 bottles")


  //BK  14.7 Patterns in Variable Declarations
  val (x147, y147) = (1, 2)

  val (q, r) = BigInt(10) /% 3

  val arr1 = Array(1, 7, 2, 9)

  val Array(first, second, _*) = arr1

  //BK 14.8 Patterns in for Expressions
  //  import scala.collection.JavaConversions.propertiesAsScalaMap
  //  // Converts Java Properties to a Scala map—just to get an interesting example
  //  for ((k, v) <- System.getProperties())
  //    println(k + " -> " + v)
  //
  //  for ((k, "") <- System.getProperties())// match failures are silently ignored
  //    println(k)
  //
  //  for ((k, v) <- System.getProperties() if v == "") // can also use a guard
  //    println(k)



}

//BK 14.9 Case Classes

//  1 Each of the constructor parameters becomes a val unless
// it is explicitly declared as a var (which is not recommended).
//
//  2 An apply method is provided for the companion object that lets you
// construct objects without new, such as Dollar(29.95) or Currency(29.95, "EUR").
//
//  3 An unapply method is provided that makes pattern matching work—see Chapter 11 for
// the details. (You don’t really need to know these details to use case classes
// for pattern matching.)

//  4 Methods toString, equals, hashCode, and copy are generated unless they are explicitly provided.
object CaseClasses extends App{
  abstract class Amount

  class MyMoney(value: Double) extends Amount

  object MyMoney extends Amount {
    def apply(value: Double): MyMoney = new MyMoney(value)

    def unapply(arg: MyMoney): Option[Double] = Option(2.4)
  }

  case class Dollar(value: Double) extends Amount{
  }

  case class Currency(value: Double, unit: String) extends Amount

  case object Nothing extends Amount

  for (amt <- Array(Dollar(1000.0), Currency(1000.0, "EUR"), MyMoney(1000.0), Nothing)) {
    val result = amt match {
      case Dollar(v) => "$" + v
      case Currency(_, u) => "Oh noes, I got " + u
      case MyMoney(v) => "My money" + v
      case Nothing => ""
    }
    // Note that amt is printed nicely, thanks to the generated toString
    println(amt + ": " + result)
  }
  var currency = Currency(1.2,"hognwe")
  private val copy: Currency = currency.copy(currency.value)
  val a = 5
}
//bk 14.11 Infix Notation in case Clauses
object InfixNotationInCaseClauses extends App {

}
//bk 14.12 Matching Nested Structures
object MatchingNestedStructures extends App{

}

//bk 14.14 Sealed Classes
//All subclasses of a sealed class must be defined in the same file as the class itself.
//It is a good idea for all case classes to extend a sealed class or trait.
//如果子类都明确的情况下，为了防止继承滥用，为抽象类添加sealed。
//sealed 关键字主要有2个作用：
//
//其修饰的trait，class只能在当前文件里面被继承
//用sealed修饰这样做的目的是告诉scala编译器在检查模式匹配的时候，让scala知道这些case的所有情况，scala就能够在编译的时候进行检查，看你写的代码是否有没有漏掉什么没case到，减少编程的错误。


sealed abstract class Amount 
case class Dollar(value: Double) extends Amount 
case class Currency(value: Double, unit: String) extends Amount

object Test14 extends App{
  abstract class People
  case object American extends People
  case object Japanese extends People
  case object Chinese extends People
  case object Russia extends People

  def people(p: People) = p match {
    case American ⇒ println("American person")
    case Japanese ⇒ println("Japanese person")
    case Chinese ⇒ println("Chinese person")
  }

  // Exiting paste mode, now interpreting.

  
}


//bk 14.16 The Option Type
object Option16 extends App {
  //http://lift.la/blog/scala-option-lift-box-and-how-to-make-your-co

  for {x <- Some(5); y <- Some(4)} yield x * y

  val yOpt: Option[Int] = None

  for {x <- Some(3); y <- yOpt} yield x * y

  (for {x <- Some(3); y <- yOpt} yield x * y) getOrElse -1

  (for {x <- Some(3); y <- Some(4)} yield x * y) getOrElse -1

  //Lift has an analogous construct called Box.
  //Box Full or Not
  //Non-Full Box can be Empty Sigleton or a Failure, it will carry around why Box contains no value

  //Failure : can display an error ,Http response code ,a message , what you have
  //1 methods that return request parameters return Box[String]
  //2 finder methods on models (not find all, just the ones that return a single instance) return Box[Model]
  //3 any method that would have returned a null if I was writing in Java returns a Box[T] in Lift

  //eg1:normal method
  //for {
  //  id <- S.param("id") ?~ "id param missing"
  //  u <- getUser(id) ?~ "User not found"
  //} yield u.toXml

  //eg2 : Rest Handler
  //serve {
  //  case "user" :: "info" :: _ XmlGet _ =>
  //    for {
  //      id <- S.param("id") ?~ "id param missing" ~> 401
  //      u <- User.find(id) ?~ "User not found"
  //    } yield u.toXml
  //}

  //2https://app.assembla.com/wiki/show/liftweb/Box
  //Option has two values: Some and None
  //Box has three values: Full, Empty and Failure

  //  1Using the box
  //  val s = Full(“Thing”)
  //  val e: Box[String] = Empty


  //  2Opening the box
  //  val myString: String = s openOr “(none)”
  //  val fail = s ?~ “Opening the box failed”
  //  s match { case Full(myS) => println(myS); case _ => println(“not found”) }


}

//bk 14.17 Partial Functions
// A set of case clauses enclosed in braces is a partial function
// A function which may not be defined for all inputs.
object PartialFunctions17 extends App {

  //eg1:
  private val intToStringToFunction: ((Int) => String) => PartialFunction[Int, String] = PartialFunction[Int, String]
  private val println1: Unit = println()

  val f: PartialFunction[Char, Int] = {
    case '+' => 1;
    case '-' => -1
  }
  private val f1: Int = f('-')
  private val at1: Boolean = f.isDefinedAt('-')
  private val at: Boolean = f.isDefinedAt('*')
  //  f('0') // Match Error

  //eg2:accept a PartialFunction as a parameter
  private val collect: IndexedSeq[Int] = "-3+4".collect { case '+' => 1; case '-' => -1 }
  println(collect)
  private val collect1: IndexedSeq[Int] = "-3+4".collect {
    f
  }
  println(collect1)

}


