package myImpatient

import java.io.File

/**
  * Created by zhanghongwei on 15/11/16.
  */
//BK 17 Type Parameters
object Chapter17 extends App {

  //BK 17.1 Generic Classes
    class Pair[T, S](val first: T, val second: S) {}

    // variable ,parameter and return value
    // ordinary class
    //infer actual types
    val p: Pair[Int, String] = new Pair(28, "Hongwei")
    // you can specify the types
    val p2: Pair[Any, Any] = new Pair[Any, Any](42, "String")

  //BK 17.2 Generic Functions
    def getMiddle[T](a: Array[T]): T = a(a.length / 2)
    // infer the types
    private val middle: String = getMiddle(Array("Mary","had","a","little","lamb"))
    val f: (Array[String]) => String = getMiddle[String] _

  //BK 17.3 Bounds for Type Variables
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

//bk 17.4 View Bounds
object ViewBounds174 extends App {

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

//bk 17.5 Context Bounds
//T<% V requires the existence of an implicit conversion from T to V.
//T:M requires that there is an “implicit value” of type M[T].
object ContextBounds175 extends App {

  //  class MyPair[T : Ordering] //an implicit value of type Ordering[T]
  class MyPair[T: Ordering](val first: T, val second: T) {
    def smaller(implicit ord: Ordering[T]) = // ord is an implicit value of ordering[T]
      if (ord.compare(first, second) < 0) first
      else second
  }

  //implicit values are more flexible than implicit conversions.
}

//bk 17.6 The Manifest Context Bound
// If you write a generic function that constructs a generic array,
// you need to help it out and pass that manifest object.
// Since it’s an implicit parameter of the constructor, you can use a context bound,
object ManifestContextBounds176 extends App {
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


//bk 17.7 Multiple Bounds 250
//A type variable can have both an upper and a lower bound.
// T >:Lower <:Upper
// Only one upper and lower ,but you can one type have many traits
// you can have many view bounds : T <% Comparable[T] <% String
// can many context bounds: T :Ordering :Manifest

object MultipleBounds177 extends App {

}

//bk 17.8 Type Constraints 250
//Type constraints give you another way of restricting types
// T =:= U --> T equals U
// T <:< U --> T is a subtype of U
// T <%< U --> T is view-convertible to U
// you need “implicit evidence parameter"
object `Type Constraints178` extends App {

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

//bk 17.9 ??? Variance 252
//[+T] The + means that the type is covariant in T—it varies in the same direction.
object ManifestContextBounds179 extends App {

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

//bk 17.10 ??? Co- and Contravariant Positions 253
object ManifestContextBounds1710 extends App {

}

//bk 17.11 ?? Objects Can’t Be Generic 255
object ManifestContextBounds1711 extends App {
}

//bk 17.12 ? Wildcards 256
object ManifestContextBounds1712 extends App {
// def process(people : java.util.List[_ <: Person]){} // scala
//  void processJava(Pair <? extends Person> people){} // java
}







