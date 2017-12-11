package myImpatient

import java.io.File
import scala.io.Source

/**
  * Created by zhanghongwei on 15/11/16.
  */
//Implicit conversions and implicit parameters are Scala’s power tools that do useful work behind the scenes
object _1ImplicitConversions extends App {

  //eg1: convert integers n to fractions n / 1 implicit
  import scala.math._

  class Fraction(n: Int, d: Int) {
    private val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d);
    private val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d);

    override def toString = num + "/" + den

    def sign(a: Int) = if (a > 0) 1 else if (a < 0) -1 else 0

    def gcd(a: Int, b: Int): Int = if (b == 0) abs(a) else gcd(b, a % b)

    def *(other: Fraction) = new Fraction(num * other.num, den * other.den)
  }

  object Fraction {
    def apply(n: Int, d: Int) = new Fraction(n, d)
  }

  implicit def int2Fraction(n: Int) = Fraction(n, 1) // convert integers n to fractions n / 1.

  val result = 3 * Fraction(4, 5) // Calls int2Fraction(3)--> Fraction(3, 1) * Fraction(4, 5)
  println(result)
  println(3)

}

//1 create the new class and implement what function you need
//2 implicit the older class into new class
object _2UsingImplicitsforEnrichingExistingLibraries extends App {
  //1 you can define an enriched type that provides what you want:
  class RichFIle(val from: File) {
    def read = Source.fromFile(from.getPath).mkString
  }
  //2 Then, provide an implicit conversion to that type:
  implicit def file2RichFile(from: File) = new RichFIle(from)

  //3 than you can call it. change File <---> RichFile
  println(new File("src/main/scala/myImpatient/_21Implicits.scala").read)

}

//bk 21.3 Importing Implicits
//Scala will consider the following implicit conversion functions:
//1. Implicit functions in the companion object of the source or target type
//2. Implicit functions that are in scope as a single identifier
object _3ImportingImplicits extends App {

  import scala.math._

  class Fraction(n: Int, d: Int) {
    val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d);
    val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d);

    override def toString = num + "/" + den

    def sign(a: Int) = if (a > 0) 1 else if (a < 0) -1 else 0

    def gcd(a: Int, b: Int): Int = if (b == 0) abs(a) else gcd(b, a % b)

    def *(other: Fraction) = new Fraction(num * other.num, den * other.den)
  }

  object Fraction {
    def apply(n: Int, d: Int) = new Fraction(n, d)
  }

  //    object FractionConversions {
  implicit def int2Fraction(n: Int) = Fraction(n, 1)

  implicit def fraction2Double(f: Fraction) = f.num * 1.0 / f.den

  val result = 3 * Fraction(4, 5) // Calls int2Fraction(3)
  println(result)

}

object _4RulesforImplicitConversions extends App {
  //1 Implicit conversions are considered in three distinct situations:
    //1) If the type of an expression differs from the expected type:
    //2) If an object accesses a nonexistent member:
    //3) If an object invokes a method whose parameters don’t match the given arguments:

  //2 On the other hand, there are three situations when an implicit conversion is not attempted:
    // 1) No implicit conversion is used if the code compiles without it.
    // For example, if a * b compiles, the compiler won’t try a * convert(b) or convert(a) * b.
    //
    // 2) The compiler will never attempt multiple conversions,
    // such as convert1(convert2(a)) * b.
    //
    // 3) Ambiguous conversions are an error.
    // For example, if both convert1(a) * b and convert2(a) * b are valid, the compiler will report an error.
}

object _5ImplicitParameters extends App {
  //The compiler looks for such an object in two places:
    // 1) Among all val and def of the desired type that are in scope as a single identifier.
    // 2) In the companion object of a type that is associated with the desired type.
    //    Associated types include the desired type itself, and, if it is a parametrised type, its type parameters.

  case class Delimiters(left: String, right: String)

  def quote(what: String)(implicit delims: Delimiters) = delims.left + what + delims.right

  println(quote("Bonjour le monde")(Delimiters("«", "»")))

  object FrenchPunctuation {
    implicit val quoteDelimiters = Delimiters("«", "»")
  }

  import FrenchPunctuation._

  println(quote("Bonjour le monde"))

}

object _6ImplicitConversionswithImplicitParameters extends App {
  def smaller[T](a: T, b: T)(implicit order: T => Ordered[T]) = if (order(a) < b) a else b

  smaller(40, 2)

  smaller("Hello", "World")

  def smaller2[T](a: T, b: T)(implicit order: T => Ordered[T]) = if (a < b) a else b // Can omit call to order

  smaller2(40, 2)

  smaller2("Hello", "World")
}

object _7ContextBounds extends App {

  class Pair1[T : Ordering](val first: T, val second: T) {
    def smaller(implicit ord: Ordering[T]) =
      if (ord.compare(first, second) < 0) first else second
  }

  new Pair1(40, 2).smaller

  class Pair2[T : Ordering](val first: T, val second: T) {
    def smaller =
      if (implicitly[Ordering[T]].compare(first, second) < 0) { first
      } else second
  }

  new Pair2(40, 2).smaller

  class Pair3[T : Ordering](val first: T, val second: T) {
    def smaller = {
      import Ordered._;
      if (first < second) first else second
    }
  }

  new Pair3(40, 2).smaller

  import java.awt.Point

  // No ordering available
//  new Pair3(new Point(3, 4), new Point(2, 5)).smaller

  implicit object PointOrdering extends Ordering[Point] {
    def compare(a: Point, b: Point) =
      a.x * a.x + a.y * a.y - b.x * b.x + b.y * b.y
  }

  // Now there is an ordering available

  new Pair3(new Point(3, 4), new Point(2, 5)).smaller

  // Namely this one

  implicitly[Ordering[Point]]

}

//bk 21.8 Evidence
object _8Evidence extends App {

  def firstLast[A, C](it: C)(implicit ev: C <:< Iterable[A]) =
    (it.head, it.last)

  firstLast(List(1, 7, 2, 9).map(_*2))

//  firstLast("Fred")

//  implicitly[String <:< Iterable[_]]
//
//  implicitly[String <:< AnyRef]
//
//  implicitly[AnyRef <:< String]


}

object _9TheimplicitNotFoundAnnotation extends App{
//  import scala.annotation._
//
//  @implicitNotFound(msg = "I am baffled why you give me ${From} when I want ${To}.")
//  abstract class <:<[-From, +To] extends Function1[From, To]
//
//  object <:< {
//    implicit def conforms[A] = new (A <:< A) { def apply(x: A) = x }
//  }
//
//  def firstLast[A, C](it: C)(implicit ev: C <:< Iterable[A]) =
//    (it.head, it.last)
//
//  firstLast("Fred")
}

object _10CanBuildFromDemystified extends App{
  import scala.math._

  trait Iterator[E] {
    def next(): E
    def hasNext: Boolean
  }

  trait Builder[-E, +To] {
    def +=(e: E): Unit
    def result(): To
  }

  trait CanBuildFrom[-From, -E, +To] {
    def apply(): Builder[E, To]
  }

  trait Iterable[A, Repr] {
    def iterator(): Iterator[A]

    def map[B, That](f : (A) => B)(implicit bf: CanBuildFrom[Repr, B, That]): That = {
      val builder: Builder[B, That] = bf()
      val iter: Iterator[A] = iterator()
      while (iter.hasNext) builder += f(iter.next())
      val result: That = builder.result
      result
    }
  }

  class Buffer[E : Manifest] extends Iterable[E, Buffer[E]]
    with Builder[E, Buffer[E]] {
    private var capacity = 10
    private var length = 0
    private var elems = new Array[E](capacity)
    def iterator() = new Iterator[E] {
      private var i = 0
      def hasNext = i < length
      def next() = { i += 1; elems(i - 1) }
    }
    def +=(e: E) {
      if (length == capacity) {
        capacity = 2 * capacity
        val nelems = new Array[E](capacity)
        for (i <- 0 until length) nelems(i) = elems(i)
        elems = nelems
      }
      elems(length) = e
      length += 1
    }
    def result() = this
  }

  object Buffer {
    implicit def canBuildFrom[E : Manifest] = new CanBuildFrom[Buffer[_], E, Buffer[E]] {
      def apply() = new Buffer[E]
    }
  }

  class Range(val low: Int, val high: Int) extends Iterable[Int, Range] {
    def iterator() = new Iterator[Int] {
      private var i = low
      def hasNext = i <= high
      def next() = { i += 1; i - 1 }
    }
  }

  object Range {
    implicit def canBuildFrom[E : Manifest] = new CanBuildFrom[Range, E, Buffer[E]] {
      def apply() = new Buffer[E]
    }
  }

  val names = new Buffer[String]
  names += "Fred"
  names += "Linda"
  val lengths: Buffer[Int] = names.map(_.length)
  lengths.map(println(_))

  val res: Buffer[Double] = new Range(1, 10).map(sqrt(_))
  res.map(println(_))

  //As you just saw, the implicit `CanBuildFrom[Repr, B, That]` parameter locates a factory object 
  //that can produce a builder for the target collection.  
  //The builder factory is defined as implicit in the companion object of Repr.

}