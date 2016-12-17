package myImpatient

import java.io.File

//BK 16 XML Processing 18=227-245

//BK 16.1 XML Literals
object  C1 extends App {

  val doc = <html><head><title>Fred's Memoirs</title></head><body>...</body></html>
  val items = <li>Fred</li><li>Wilma</li>;

  print(123)

}
object Chapter16 extends App {

  //BK 16.2 XML Nodes
  //  def getMiddle[T](a: Array[T]): T = a(a.length / 2)
  //  // infer the types
  //  getMiddle(Array("Mary","had","a","little","lamb"))
  //  val f = getMiddle[String] _

  //BK 16.3 Element Attributes
  //  //  place restrictions on type variables
  //  //   T <: Comparable is the upper bound, T must be the sub class of Comparable
  //  class Pair[T <: Comparable[T]](val first: T, val second: T) {
  //    def smaller =
  //      if (first.compareTo(second) < 0) first
  //      else second
  //  }
  //
  //  val p = new Pair("Fred", "Brooks")
  //  println(p.smaller)
  //
  //  new Pair(3, 4)
  //
  //  // wrong !!!
  //
  //  class Pair[T](val first: T, val second: T) {
  // R>:T , R has the low bound ,it is the super class of T
  //    def replaceFirst[R >: T](newFirst: R): Pair[R] = new Pair[R](newFirst, second)
  //
  //    def replaceFirst1[R](newFirst: R): Pair[Any] = new Pair(newFirst, second)
  //  }

}

//bk 16.4 Embedded Expressions
object C4 extends App {

  // <% means T can be converted to a Comparable[T] through an implicit conversion.
  //  class Pair[T <% Comparable[T]]

  class MyPair[T <% Ordered[T]](val first: T, val second: T) {
    def smaller = if (first < second) first
    else second
  }

  private val pair = new MyPair(32, 45)
  print(pair.smaller)
  private val pairString = new MyPair("Hong", "Wei")
  print(pairString.smaller)

}

//bk 16.5 Expressions in Attributes
//T<% V requires the existence of an implicit conversion from T to V.
//T:M requires that there is an “implicit value” of type M[T].
object C5 extends App {

  //  class MyPair[T : Ordering] //an implicit value of type Ordering[T]
  class MyPair[T: Ordering](val first: T, val second: T) {
    def smaller(implicit ord: Ordering[T]) = // ord is an implicit value of ordering[T]
      if (ord.compare(first, second) < 0) first
      else second
  }

  //implicit values are more flexible than implicit conversions.
}

//bk 16.6 Uncommon Node Types
// If you write a generic function that constructs a generic array,
// you need to help it out and pass that manifest object.
// Since it’s an implicit parameter of the constructor, you can use a context bound,
object C6 extends App {
  //T:Manifest requires that there is an “implicit value” of type Manifest[T].
  def makePair[T: Manifest](first: T, second: T): Unit = {
    val r = new Array[T](2);
    r(0) = first
    r(1) = second
    r
  }

  makePair(4, 9) //-> new Array(2)(IntManifest)-->int[2]
  // why ? because in JVM ,all the type information are erased.
  // only a single makePair method that needs to work for all types T.
}


//bk 16.7 XPath-like Expressions
//A type variable can have both an upper and a lower bound.
// T >:Lower <:Upper
// Only one upper and lower ,but you can one type have many traits
// you can have many view bounds : T <% Comparable[T] <% String
// can many context bounds: T :Ordering :Manifest

object C7 extends App {

}

//bk 16.8 Pattern Matching
//Type constraints give you another way of restricting types
// T =:= U --> T equals U
// T <:< U --> T is a subtype of U
// T <%< U --> T is view-convertible to U
// you need “implicit evidence parameter"
object C8 extends App {

  //eg 1
  class MyPair[T](val first: T, val second: T)(implicit ev: T <:< Ordered[T]) {}

  //eg2 :Type constraints let you supply a method in a generic class that
  // can be used only under certain conditions.
  class MyPair2[T](val first: T, val second: T) {
    def smaller(implicit ev: T <:< Ordered[T]) =
      if (first < second) first
      else second
  }

  // you can not make
  //  new MyPair[File](new File(""),new File(""))
  // but you can
  private val pair: MyPair2[File] = new MyPair2[File](new File(""), new File(""))
  // you still can not use the method ,but you can create the class
  //  pair.smaller

  //eg3
  val friends = Map("Fred" -> "Barney")
  val friendOpt = friends.get("Wilma")
  //Option[String]
  val friendOrNull = friendOpt.orNull // String or null

  //eg4
  def firstLast[A, C <: Iterable[A]](it: C): (A, A) = (it.head, it.last)

  //  def firstLast[A, C](it: C)(implicit ev: C <:< Iterable[A]) = (it.head, it.last)
  //  firstLast((List(1,2,3)))

  //eg5
  //  def corresponds[B](that :Seq[B]) (match: (A, B) => Boolean): Boolean

}

//bk 16.9 Modifying Elements and Attributes
//[+T] The + means that the type is covariant in T—it varies in the same direction.
object C9 extends App {

  // problem1:
  //  def makeFriends(p: Pair[Person])
  //  Pair[Student] can not use into makeFriends. altought Student :< Person.
  // because Pair[Student]  no relationship with Pair[Person]
  //  you need :
  class MyPair[+T](val first: T, val second: T)

  //problem2:
  trait Friend[-T] {
    def beFriend(someone: T)
  }

  // functions are contravariant in their arguments and
  // covariant in their results.
}

//bk 16.10 Transforming XML —
object C10 extends App {

}

//bk 16.11 Loading and Saving
object C11 extends App {
}

//bk 16.12 Namespaces
object C12 extends App {
// def process(people : java.util.List[_ <: Person]){} // scala
//  void processJava(Pair <? extends Person> people){} // java
}







