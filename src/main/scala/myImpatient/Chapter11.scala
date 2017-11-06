package myImpatient

/**
  * Created by zhanghongwei on 16/11/16.
  */
object Identifiers extends App{
  //BK 11.1 Identifiers
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


//BK 11.2 Infix Operators
//Scala的操作符不是特殊的语言语法, 任何方法都可以是操作符
//操作符分为前缀, 中缀, 后缀
//Scala中所有操作符都是方法调用
//你可以把任何方法都当作操作符来标注
//任何方法都可以是操作符
//Scala里的操作符不是特殊的语言语法：任何方法都可以是操作符。使用方法的方式使它成为操作符。
//如果写成s.indexOf('o')，indexOf就不是操作符。不过如果写成，s indexOf 'o'，
//那么indexOf就是操作符了，因为你以操作符标注方式使用它
object InfixOperators2 extends App {
  // infix mean, the operator is between the arguments
  // a identifier b where 
  // identifier denotes a method with two parameters (one implicit, one explicit). 
  // For example, the expression
  1 to 10
  //actuallt this 
  1.to(10) 
  // def to(end: Int): Range.Inclusive = Range.inclusive(self, end), 
  // self is the implicit one and end is the explicit one .
  "x.toLowerCase".toLowerCase
  "x.toLowerCase" toLowerCase
  class Fraction(n: Int, d: Int) {
    private val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d);
    private val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d);

    override def toString = num + "/" + den
    //You can design your own fix identifier, as the scala build in ones
    def *(other: Fraction) = new Fraction(num * other.num, den * other.den)
    def sign(a: Int) = if (a > 0) 1 else if (a < 0) -1 else 0
    def gcd(a: Int, b: Int): Int = if (b == 0) scala.math.abs(a) else gcd(b, a % b)
  }

  val f1 = new Fraction(3, 4)
  val f2 = new Fraction(2, 5)
  println(f1 * f2)

}

//BK 11.3 Unary Operators
//http://developer.51cto.com/art/200907/137999.htm
//本文节选自Martin Odersky，Lex Spoon和Bill Venners所著，Regular翻译的《Programming in Scala》的第五章。
//Scala是一种针对 JVM 将函数和面向对象技术组合在一起的编程语言
//Scala的操作符：任何方法都可以是操作符
object UnaryOperators3 extends App {
  // Infix operators are binary operators—they have two parameters. 
  // An operator with one parameter is called a unary operator. 
  // If it follows the argument, it is a postfix operator. The expression
  // the Operator is following the parameter
  //BK 11.3.1 postfix
//  后缀操作符是不用点或括号调用的不带任何参数的方法
  1 toString()// ??? there need some endpoint. 
    // equals the following
  1.toString()
    // equals the following
   "" toLowerCase

  //BK 11.3.2 perfix
  //可以当作前缀操作符用的标识符只有+，-，!和~
  //4 operators +, -, !, ~ are allowed as prefix operators, appearing before their arguments.
  //They are converted into calls to methods with the name unary_operator.
  //eg2:prefix +,-,!,~
  val a = 42
  -a
  //The same as a.unary_-.
  a.unary_-

}

//BK 11.4 Assignment Operators
object AssignmentOperators4 extends App {
  var a: Any = 3
  a ->= 4 // Same as a = a -> 4
  a

  //  a === 4 // Error; not the same as a = a == 4 because == starts with =
  val b = scala.collection.mutable.Set(1, 2, 3)

  b += 4 // Calls the += method; you couldn't use b = b + 4 with a val
}

//BK 11.5 Precedence
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
  //  1 -> 2 + 3 // Error--can't` apply + to a pair
  1 to 10 toString // Postfix operators have lower precedence than infix
}

//BK 11.6 Associativity
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
  //eg1: normally, you can create upapply and apply for the same Class,
  //apply
  private val fraction: Fraction = Fraction(3, 4)
  //unapply
  var Fraction(a, b) = Fraction(2, 5) * Fraction(3, 4)
  println(a)
  println(b)

  //eg2: extract from String
  // You can define the unapply method just in any object,it can have some input parameters.
  // You can call this upapply method, automatically by this parameters.
  val author = "Zhang Hongwei"
  val author2 = Fraction(2, 5)
  val Name(first, last) = author // Here we difine two parameters. first, last and asing the vaule 
  val Name(first2, last2) = author2
  val Name(first3) = author2
  //  Error: too many patterns for object Name offering (String, String): expected 2, found 3
  // The following has the compile error, will not find the unapply method, when run this method.
  // val Name(a1,a2,a3) = author2
  // apply --> paramters --> object.   eg: val object = Fraction(1,2) --> return the object   
  // upapply --> object --> parameters eg: val Fraction(a,b) = object --> return a and b variable
  //eg3: case class
  case class Currency(value: Double, unit: String)

  val amt = Currency(29.95, "EUR")
  private val value: Any = amt match {
    case Currency(amount, "USD") => println("$" + amount); amount
    case Currency(amount, "EUR") => println("€" + amount); amount
    case _ => println(amt); amt
  }


  class Fraction(n: Int, d: Int) {
    private val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d);
    private val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d);

    //    private val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d);
    override def toString = num + "/" + den

    def sign(a: Int) = if (a > 0) 1
    else if (a < 0) -1 else 0

    def gcd(a: Int, b: Int): Int = if (b == 0) scala.math.abs(a) else gcd(b, a % b)

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
    def unapply(arg: String): Option[(String, String)] = {
      if (arg.isEmpty) {
        None
      } else {
        val split: Array[String] = arg.split(" ")
        Some(split(0), split(1))
      }
    }

    def unapply(f: Fraction): Option[(String, String)] = {
      Some("good","boy")
    }
  }

}

//BK 11.9 Extractors with One or No Arguments
object ExtractorsWithOneOrNoArguments9 extends App {
  //eg1: practice case class the one argument
  case class Dog(name: String)

  val dog2 = Dog("Yanlu")
  val Dog(aadf) = dog2;

  case class Dog2(name: String, age: Int)

  private val dog: Dog2 = Dog2("yanlu", 6)
  val Dog2(abc, def1) = dog


  //eg2:my own one argument

  val Number(n) = "1729s"

  object Number {
    def unapply(input: String): Option[Int] = try {
      Some(Integer.parseInt(input.trim))
    } catch {
      case ex: NumberFormatException => Some(1) // If here return None, will throw exception.
      case ex: RuntimeException => Some(2)
      case ex => Some(3)
    }
  }
  
  println(n)

  //eg3: my own no argument

  val author = "Peter van der Linden"
  author match {
    case Name(first, last) if (last.contains(" ")) => print(last)// The same as following, used boolean -unapply method.
    case Name(first, last@IsCompound()) =>  print(last) // Matches if the author is Peter van der Linden
    case Name(first, last) => 1;print(last)
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