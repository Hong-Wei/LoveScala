package myImpatient

/**
  * Created by zhanghongwei on 16/11/16.
  */
object Identifiers extends App{
  //BK 11.1 Identifiers — page 131 •
  //scala support more characters.
  // all the three are not support
  val `()[]{}` = 8
  val `.,;'` =9 
  val `'""`= 10
  //1 so these support
  val !#%&*+-/:<=>?@\^|~ = 7
  //2 this support 
  val √ = scala.math.sqrt _
  //3 you can include just about any sequence of characters in backquotes ``
  val `val` = 42
  val `main` =50
  val `I Like this game` = 100
  // yield is reserved word, so you need backquotes to call java method. 
  Thread.`yield`()
}


//BK 11.2 Infix Operators — page 132 •
object InfixOperators2 extends App {
  // a identifier b where 
  // identifier denotes a method with two parameters (one implicit, one explicit). 
  // For example, the expression
  1 to 10
  //actuallt this 

  import scala.collection.immutable.Range.Inclusive
  import scala.math._

  class Fraction(n: Int, d: Int) {
    private val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d);
    private val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d);

    override def toString = num + "/" + den

    def sign(a: Int) = if (a > 0) 1 else if (a < 0) -1 else 0

    def gcd(a: Int, b: Int): Int = if (b == 0) abs(a) else gcd(b, a % b)

    def *(other: Fraction) = new Fraction(num * other.num, den * other.den)
  }

  val f1 = new Fraction(3, 4)
  val f2 = new Fraction(2, 5)
  println(f1 * f2)

  private val tuple: (Int, Int) = 1 -> 10
  private val inclusive: Inclusive = 1 to 10
  private val to: Inclusive = 1.to(10)

}

//BK 11.3 Unary Operators — page 133 •
object UnaryOperators3 extends App {
  //eg1 : postfix
  1 toString

  1.toString()

  //eg2:prefix +,-,!,~
  val a = 42
  -a
  a.unary_-

}

//BK 11.4 Assignment Operators — page 133 •
object AssignmentOperators4 extends App {
  var a: Any = 3
  a ->= 4 // Same as a = a -> 4
  a

  //  a === 4 // Error; not the same as a = a == 4 because == starts with =
  val b = scala.collection.mutable.Set(1, 2, 3)

  b += 4 // Calls the += method; you couldn't use b = b + 4 with a val
}

//BK 11.5 Precedence — page 134 •
object Precedence5 extends App {
  1 + 2 * 3 // * has higher precedence than +
  1 + (2 * 3)
  (1 + 2) * 3

  1 + 4 | 9 // | has lower precedence than +
  (1 + 4) | 9
  1 + (4 | 9)

  1 + 2 to 10 // to has lower precedence than +
  1 -> 2 * 3 // * has higher precedence than ->
  1 + 2 -> 3 // + has the same precedence as ->
  //  1 -> 2 + 3 // Error--can't apply + to a pair
  1 to 10 toString // Postfix operators have lower precedence than infix
}

//BK 11.6 Associativity 143
object Associativity6 extends App {
  17 - 2 - 9 // - is left associative
  (17 - 2) - 9
  17 - (2 - 9)

  1 :: 2 :: Nil // :: is right associative
  1 :: (2 :: Nil)
  //  (1 :: 2) :: Nil // Error since :: can't be applied to 2
  var a: Any = 3
  a = a = 4 // = is right associative
  a = (a = 4)
  //  (a = a) = 4// Error since = can't be applied to (), a=a return empty(), 4 cannot be applied to empty()
  var l = List(1, 2, 3)

  4 :: l // :: is a member of the operand to the right since it ends in a colon
  l.::(4)

  l ::= 4 // ::=  is a member of the operand to the left since it doesn't end in a colon
}


//BK 11.7 The apply and update Methods
// let you extend the use of f(arg1,arg2...) , even if f is not a funciton.
// it will  call f.apply(arg1,arg2...) implicit method
// or f(arg1,arg2...) = value, it wil lcall
// f.update(arg1,arg2...,value)
object ApplyUpapply7 extends App {
  //eg1: use in Array and Map
  val scores = new scala.collection.mutable.HashMap[String, Int]
  scores("Bob") = 100
  //scores.update("Bob",100)
  val bobsScore = scores("Bob")

  // scores.apply("Bob")= scores.get("Bob")-->Option
  //eg2: apply use in companion object, use for creating new object instead of "new"
  class Fraction(n: Int, d: Int) {}

  object Fraction {
    def apply(n: Int, d: Int) = new Fraction(n, d)
  }

  Fraction(3, 4)


}

//BK 11.8 Extractors
//An extractor is an object with an unapply method
object Extractors8 extends App {

  //eg1: opposite from apply
  //apply
  private val fraction: Fraction = Fraction(3, 4)
  //unapply
  var Fraction(a, b) = Fraction(2, 5) * Fraction(3, 4)
  println(a)
  println(b)

  //eg2: extract from String
  val author = "Zhang Hongwei"
  val Name(first, last) = author

  //eg3: case class
  case class Currency(value: Double, unit: String)

  val amt = Currency(29.95, "EUR")
  private val value: Any = amt match {
    case Currency(amount, "USD") => println("$" + amount); amount
    case Currency(amount, "EUR") => println("€" + amount); amount
    case _ => println(amt); amt
  }


  import scala.math._

  /**
    *
    * @param n numerator fenzi
    * @param d denominator fenmu
    */
  class Fraction(n: Int, d: Int) {
    private val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d);
    private val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d);

    //    private val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d);
    override def toString = num + "/" + den

    def sign(a: Int) = if (a > 0) 1
    else if (a < 0) -1 else 0

    def gcd(a: Int, b: Int): Int = if (b == 0) abs(a) else gcd(b, a % b)

    def *(other: Fraction) = new Fraction(num * other.num, den * other.den)
  }

  object Fraction {
    def apply(n: Int, d: Int): Fraction = new Fraction(n, d)

    def unapply(arg: Fraction): Option[(Int, Int)] =
      if (arg.den == 0)
        None
      else
        Some(arg.num, arg.den)
  }

  object Name {
    //    def unapply(input: String): Option[(String, String)] = {
    //      val pos = input.indexOf(" ")
    //      if (pos == -1) None else Some((input.substring(0, pos), input.substring(pos + 1)))
    //    }
    def unapply(arg: String): Option[(String, String)] = {
      if (arg.isEmpty) {
        None
      } else {
        val split: Array[String] = arg.split(" ")
        Some(split(0), split(1))
      }
    }
  }

}

//BK 11.9 Extractors with One or No Arguments •
object ExtractorsWithOneOrNoArguments9 extends App {


  //eg1: practice case class the one argument
  case class Dog(name: String)

  val dog2 = Dog("Yanlu")
  val Dog(aadf) = dog2;

  case class Dog2(name: String, age: Int)

  private val dog: Dog2 = Dog2("yanlu", 6)
  val Dog2(abc, def1) = dog


  //eg2:my own one argument

  val Number(n) = "1729"

  object Number {
    def unapply(input: String): Option[Int] = try {
      Some(Integer.parseInt(input.trim))
    } catch {
      case ex: NumberFormatException => None
    }
  }

  //eg3: my own no argument

  val author = "Peter van der Linden"
  author match {
    case Name(first, last@IsCompound()) =>  print(last.split("\\s+").length) // Matches if the author is Peter van der Linden
//    case Name(first, last) => 1;print(last)
  }


  object Name {
    def unapply(input: String): Option[(String, String)] = {
      val pos = input.indexOf(" ")
      if (pos == -1) None
      else Some((input.substring(0, pos), input.substring(pos + 1)))
    }
  }

  object IsCompound {
    def unapply(input: String): Boolean = input.contains(" ")
  }

}

//BK 11.10 The unapplySeq Method
object UnapplySeq extends App {
  val author = "Peter van der Linden"

  author match {
    case Name(first, last) => print(author)
    case Name(first, middle, last) => print(first + " " + last)
    case Name(first, middle, _,_,_) => print(first + " ")
    case Name(first, "van", "der", last) => print("Hello Peter!")
  }

  object Name {
    def unapplySeq(input: String): Option[Seq[String]] = if (input.trim == "") None else Some(input.trim.split("\\s+"))
  }


}