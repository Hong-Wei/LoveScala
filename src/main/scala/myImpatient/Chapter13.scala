package myImpatient

import java.awt.Color

import scala.List
import scala.collection.immutable.List
import scala.collection.mutable.ArrayBuffer
import scala.collection.{Map, SortedSet, mutable}
import scala.collection.immutable.List

/**
  * Created by zhanghongwei on 14/11/16.
  */


//BK 13.1 The Main Collections Traits
object MainCollectionsTraits1 extends App {

  //eg1: normal iterator process
  val coll = Array(1, 7, 2, 9)
  // some Iterable
  val iter = coll.iterator
  while (iter.hasNext)
    println(iter.next())

  // Seq is an ordered sequence of values: Array or List. IndexSeq allows fast random access through an integer index. ArrayBuffer is indexed ,but linked list is not .
  // Set is an unordered collection of values.
  // Map is a set of (key,value) pairs.


  // uniform creation principle
  // eg2: each Scala collection contain a companion object with aplly method, to create the instance!
  private val ints: Iterable[Int] = Iterable(0xFF, 0xFF00, 0xFF0000)
  private val colors: Set[Color] = Set(Color.RED, Color.GREEN, Color.BLUE)
  private val colorToInt: Map[Color, Int] = Map(Color.RED -> 0xFF, Color.GREEN -> 0xFF00, Color.BLUE -> 0xFF0000)
  private val strings: SortedSet[String] = SortedSet("Hello", "World")
}


//BK 13.2 Mutable and Immutable Collections
object MutableAndImmutalbeCollections2 extends App {

  val immutableMap: Map[String, Int] = Map("Hello" -> 42) // Nobody can change it

  val mutableMap = new collection.mutable.HashMap[String, Int]

  val map: collection.Map[String, Int] = mutableMap

  //  immutableMap.put("Fred", 29) // Error
  mutableMap.put("Wilma", 17)
  //  map.put("Fred", 29) // Error

  import scala.collection.mutable

  val immutableMap1 = Map("Hello" -> 42)
  val mutableMap2 = new mutable.HashMap[String, Int]

  def digits(n: Int): Set[Int] =
    if (n < 0) {
      digits(-n)
    } else if (n < 10) {
      Set(n)
    } else
      digits(n / 10) + (n % 10)

  private val digits1: Set[Int] = digits(1729777)
  print(digits1)
}

//bk 13.3 Sequences
object Sequences3 extends App {
  //Vector Range
  val vec = (1 to 1000000) map (_ % 100) // map transforms a Range into a Vector

  val lst = vec.toList // Vector -->List

  def time[T](block: => T) = {
    val start = System.nanoTime
    val result = block
    val elapsed = System.nanoTime - start
    println(elapsed + " nanoseconds")
    result
  }

  time(vec(500000))

  time(lst(500000))

}

//BK 13.4 Lists
object List4 extends App {
  //eg1: create new List
  import scala.List

  val digits = scala.List(4, 2)
  9 :: digits
  9 :: 4 :: 2 :: Nil
  9 :: (4 :: (2 :: Nil))

  //eg2: Iterable List
  def sum(lst: List[Int]): Int =
  if (lst == Nil) 0 else lst.head + sum(lst.tail)

  sum(scala.List(9, 4, 2))

  //  def sum2(lst: List[Int]): Int = lst match {
  //    case Nil => 0
  //    case h :: t => h + sum(t) // h is lst.head, t is lst.tail // decountructor the List to Head and Tail.
  //  }

  sum(scala.List(9, 4, 2))

  //eg3: before you recucive , scala has been sum method
  scala.List(9, 4, 2).sum
}


//BK 13.5 Mutable Lists
object MutableLists5 extends App {

  val lst1 = scala.collection.mutable.LinkedList(1, -7, 2, -9)
  var cur1 = lst1
  while (cur1 != Nil) {
    if (cur1.elem < 0)
      cur1.elem = 0
    cur1 = cur1.next
  }

  var cur2 = lst1
  while (cur2 != Nil && cur2.next != Nil) {
    cur2.next = cur2.next.next
    cur2 = cur2.next
  }

  // Remove everything starting from the first negative number

  import scala.collection.mutable.LinkedList

  val lst = LinkedList(1, 7, -2, 9)

  var cur3 = lst
  while (cur3 != Nil && cur3.next != Nil) {
    if (cur3.next.elem < 0)
      cur3.next = LinkedList.empty // DO NOT set to null
    cur3 = cur3.next
  }
}

//BK 13.6 Sets
object Sets6 extends App {

  import scala.collection.mutable._

  Set(2, 0, 1) + 1
  private val strings1: LinkedHashSet[String] = LinkedHashSet("Mo", "Tu", "We", "Th", "Fr")
  collection.immutable.SortedSet(1, 2, 3, 4, 5, 6)

  private val b1: Boolean = strings1 contains "hongwei"
  private val b: Boolean = LinkedHashSet("Mo", "Tu") subsetOf strings1

  val primes = Set(2, 4, 5, 7)
  val digits = Set(1, 7, 2, 9)

  digits union primes //(1, 2, 3, 5, 7, 9)
  digits ++ primes

  digits & primes //(2, 7)
  digits intersect primes

  digits -- primes //(1, 9)
  digits diff primes
}
//BK 13.7 Operators for Adding or Removing Elements
object OperatorsforAddingorRemovingElements7 extends App{
  import scala.collection.mutable.ArrayBuffer

  val digits = ArrayBuffer(1, 7, 2, 9)
  val sets = Set(100,101)
  val sets1 = Set(103,104)

  digits :+ 100
  101 +: digits
  digits :+ sets

  sets +1
  sets +(1,2,3)
  sets - 1

  sets ++ sets1
  sets ++: sets1
  //For List
  val ints: scala.List[Int] = scala.List(1,2,3)
  val ints1: scala.List[Int] = 3 :: ints
  val list: scala.List[Any] = ints :: ints1
  val list1: scala.List[Any] = ints ::: ints1
  val list2: scala.List[Any] = ints ++: ints1

  val strings: mutable.LinkedHashSet[String] = mutable.LinkedHashSet("Mo", "Tu", "We", "Th", "Fr")
  val strings1: mutable.LinkedHashSet[String] = mutable.LinkedHashSet("1","2")
  strings += "hongwei"
  strings += ("hongwei","yanlu")
  strings ++= strings1
  strings -= ("Mo")
  strings --= strings1

  val ints2: ArrayBuffer[Int] = ArrayBuffer(1, 2, 3)
  ints2

  4 +=: ints2
  ints2 ++=: ints2

  Vector(1,2,3) :+ 5 // left collect --no change
  5 +:Vector(1,2,3) //right collect

  ArrayBuffer(1,2,3) += 4 //changed

}

//BK 13.8 Common Methods
object CommonMethods8 extends App{
  val coll = 1 to 10

  coll.head
  coll.last
  coll.headOption
  scala.List().headOption
  coll.lastOption

  coll.length
  coll.isEmpty

  coll.sum
  coll.product
  coll.max
  coll.min

  coll.count(_ % 2 == 0)
  coll.forall(_ % 2 == 0)
  coll.exists(_ % 2 == 0)

  coll.filter(_ % 2 == 0)
  coll.filterNot(_ % 2 == 0)
  coll.partition(_ % 2 == 0)

  coll.takeWhile(_ < 3)
  coll.dropWhile(_ < 3)
  coll.span(_ < 3)

  coll.take(4)
  coll.drop(4)
  coll.splitAt(4)

  coll.takeRight(4)
  coll.dropRight(4)

  coll.slice(2, 8)

  coll.grouped(3).toArray
  coll.sliding(3).toArray

  coll.mkString("<", "|", ">")


  coll.toIterable
  coll.toSeq
  coll.toIndexedSeq
  coll.toArray
  coll.toList
  coll.toSet

  // Seq methods

  coll.indexWhere(_ % 3 == 0)

  coll.prefixLength(_ % 4 != 0)
  coll.segmentLength(_ % 4 != 0, 4)
  coll.padTo(20, 0)

  val a = Seq(1, 1, 2, 3, 1, 1, 1)
  val b = Seq(1, 2, 3, 2, 1)

  a intersect b
  a diff b

  val words = "Mary had a little lamb".split(" ")

  words.reverse

  words.sorted
  words.sortWith(_.length < _.length)
  words.sortBy(_.length)

  words.permutations.toArray
  words.combinations(3).toArray
}
//BK 13.9 Mapping a Function
object MappingAFunction9 extends App{
  import scala.collection.immutable.List
  val names = List("Peter", "Paul", "Mary")

  names.map(_.toUpperCase) // List("PETER", "PAUL", "MARY")

  for (n <- names) yield n.toUpperCase

  def ulcase(s: String) = Vector(s.toUpperCase(), s.toLowerCase())

  names.map(ulcase)
  names.flatMap(ulcase)

  "-3+4".collect { case '+' => 1 ; case '-' => -1 }

  names.foreach(println)
}


//BK 13.10 Reducing, Folding, and Scanning
object ReducingFoldingAndScanning10 extends App{
  import scala.collection.immutable.List
  scala.List(1, 7, 2, 9).reduceLeft(_ - _) // 1-7-2-9

  List(1, 7, 2, 9).reduceRight(_ - _) //1- (7-(2-9))

  List(1, 7, 2, 9).foldLeft(0)(_ - _) //0-1-7-2-9

  (0 /: List(1, 7, 2, 9)) (_ - _) // 0-1-7-2-9

  //TODO 13.10 replace for ??
  val freq = scala.collection.mutable.Map[Char, Int]()
  for (c <- "Mississippi") {
    freq(c) = freq.getOrElse(c, 0) + 1
  }
  freq.mkString(",")

  (Map[Char, Int]() /: "Mississippi") {
    (m, c) => m + (c -> (m.getOrElse(c, 0) + 1))
  }
  (1 to 10).scanLeft(0)(_ + _)

}

//  //BK 13.11 Zipping
object Zipping extends App{
  import scala.collection.immutable.List
  val prices = List(5.0, 20.0, 9.95)
  val quantities = List(10, 2, 1)

  prices zip quantities

  (prices zip quantities) map { p => p._1 * p._2 }

  ((prices zip quantities) map { p => p._1 * p._2 }) sum

  List(5.0, 20.0, 9.95) zip List(10, 2)

  List(5.0, 20.0, 9.95).zipAll(List(1011, 2), 3000, 4)

  "Scala".zipWithIndex

  "Scala".zipWithIndex.max

  "Scala".zipWithIndex.max._2

}

//
//
//  //bk 13.12 Iterators
object Iterators extends App{
  val iter1 = (1 to 10).sliding(3)

  while (iter1.hasNext)
    println(iter1.next())

  val iter2 = (1 to 10).sliding(3)

  for (elem <- iter2)
    println(elem)

  val iter3 = (1 to 10).sliding(3)

  println(iter3.length)

  println(iter3.hasNext) // The iterator is now consumed

  val iter4 = (1 to 10).sliding(3)

  iter4.toArray

  iter4.toIterable
}
//
//  //bk 13.13 Streams
object Streams extends App{
  def numsFrom(n: BigInt): Stream[BigInt] = n #:: numsFrom(n + 1)

  val tenOrMore = numsFrom(10)

  tenOrMore.tail.tail.tail

  val squares = numsFrom(1).map(x => x * x)

  squares.take(5).force

  import scala.io.Source

  val words = Source.fromFile("/usr/share/dict/words").getLines.toStream
  words
  words(5)
  words
}
//bk 13.14 Lazy Views
object LazyView extends App{
//  import scala.math._
//
//  val powers = (0 until 1000).view.map(pow(10, _))
//
//  powers(100)
//
//  val powers = (0 until 1000).view.map(n => { println(n) ; pow(10, n) })
//
//  powers(100) // Prints 100 in the method call
//  powers(100) // Prints 100 again; the method is called twice
//
//  // Contrast with streams
//
//  def powers(n: Int): Stream[Double] = { println(n) ; pow(10, n) } #:: powers(n + 1)
//
//  val powerStream = powers(0) // Calls method with 0
//  powerStream(100) // Calls method with 1 to 100
//  powerStream(100) // Doesn't call the method again
//
//  (0 to 1000).map(pow(10, _)).map(1 / _)
//
//  (0 to 1000).view.map(pow(10, _)).map(1 / _).force
//
//  (0 to 1000).map(x => pow(10, -x))
}
//  val powers = (0 until 1000).view.map(scala.math.pow(10, _))
//  powers(1000)
//  (0 to 1000).map(scala.math.pow(10, _)).map(1 / _)


object Chapter13 extends App {

}






























