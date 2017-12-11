package myImpatient

import java.io.File

/**
  * Created by zhanghongwei on 15/11/16.
  */
//BK 17 Type Parameters
object _1712GenericClass_Functions extends App {

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
    f(Array("Mary", "had", "a", "little", "lamb"))
  
}

object _173BoundsforTypeVariables extends App {
  // place restrictions on type variables
  //BK 17.3.1 upper bound T <: Comparable[T], T must be the sub class of Comparable
  // in the smaller method, we use the compareTo method, it need be implemented in Type 'T'
  // in the class, we need some method, so we can use the <: upper bound.
  class Pair1731[T <: Comparable[T]](val first: T, val second: T) {
    def smaller =
      if (first.compareTo(second) < 0) first
      else second
  }

  //Pair[java.lang.String] is ok, but Pair[java.io.File] is not. 
  //Because, String is a subtype of Comparable[String]
  val p = new Pair1731("Fred", "Brooks")
  println(p.smaller)

  //  new Pair1731(3, 4) --> is still wrong, because Int is not a subtype of Comparable[String]

  //BK 17.3.2 lower bound R >: T
  //1st try
  class Pair1732[T](val first: T, val second: T) { 
    def replaceFirst(newFirst: T) = new Pair1732[T](newFirst, second) 
  }

  val pair1732 = new Pair1732("1","2")
  pair1732.replaceFirst("hongwei")
  
  //2rd, better one
  class Pair17322[T](val first: T, val second: T) {
    // Here, if Pair17322[Student] --> T is student.
    // R --> is Person
    def replaceFirst[R >: T](newFirst: R): Pair17322[R] = new Pair17322[R](newFirst, second)
    //better one: No [R] in the right side.
    def replaceFirst2[R >: T](newFirst: R): Pair17322[R] = new Pair17322(newFirst, second)
    //If omit bound, it will return Pair17322[Any].
    def replaceFirstWrong[R](newFirst: R): Pair17322[Any] = new Pair17322(newFirst, second)
  }

  val pair17322 = new Pair17322("1","2")
  pair17322.replaceFirst("hongwei")
  
  
  //BK 17.3.3 lower bound good Example
  // If it is need Student, we can send it Person, because if it is `Studetn`, it must be a`Person`. 
  //The R is in the parameter part, it can be Super Type of T, Person can be send to the method. And replace the Student.
  // If you omit the lower bound, it will use the Any as the output bound.
  class Pair17323[T](val first: T, val second: T) {
    def replaceFirst[R >: T](newFirst: R) = new Pair17323(newFirst, second)
    override def toString = "(" + first + "," + second + ")"
  }

  class Person(name: String) {
    override def toString = getClass.getName + " " + name
  }

  class Student(name: String) extends Person(name)

  val fred = new Student("Fred")
  val wilma = new Student("Wilma")
  val barney = new Person("Barney")

  val p1 = new Pair17323(fred, wilma)
  p1.replaceFirst(barney) // A Pair17323[Person]

  // Don't omit the upper bound:

  class Pair17324[T](val first: T, val second: T) {
    def replaceFirst[R](newFirst: R) = new Pair17324(newFirst, second)
    override def toString = "(" + first + "," + second + ")"
  }

  val p2 = new Pair17324(fred, wilma)
  p2.replaceFirst(barney) // A Pair[Any]
  

}

//bk 17.4 View Bounds
object _174ViewBounds extends App {

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
object _175ContextBounds extends App {

  class Pair[T : Ordering](val first: T, val second: T) {
    //The implicit value can be used in the methods of the class.
    def smaller(implicit ord: Ordering[T]) =
      if (ord.compare(first, second) < 0) first else second //here, you used the `ord` the implicit value to run its method
    override def toString = "(" + first + "," + second + ")"
  }

  val p = new Pair(4, 2)
  p.smaller

  val q = new Pair("Fred", "Brooks")
  q.smaller

}

//bk 17.6 The Manifest Context Bound
// If you write a generic function that constructs a generic array,
// you need to help it out and pass that manifest object.
// Since it’s an implicit parameter of the constructor, you can use a context bound,
object _176ManifestContextBounds extends App {
  //T:Manifest requires that there is an “implicit value” of type Manifest[T].
  def makePair[T : Manifest](first: T, second: T) = {
    val r = new Array[T](2); r(0) = first; r(1) = second; r
  }

  val a = makePair(4, 2) // An Array[Int]
  a.getClass // In the JVM, [I is an int[] array

  val b = makePair("Fred", "Brooks") // An Array[String]
  b.getClass // In the JVM, [Ljava.lang.String; is a String[] array
}


//bk 17.7 Multiple Bounds 250
object _177MultipleBounds extends App {
  //A type variable can have both an upper and a lower bound.
  // T >:Lower <:Upper
  // Only one upper and lower ,but you can one type have many traits
  // you can have many view bounds : T <% Comparable[T] <% String
  // can many context bounds: T :Ordering :Manifest
}

//bk 17.8 Type Constraints 250
//Type constraints give you another way of restricting types
// T =:= U --> T equals U
// T <:< U --> T is a subtype of U
// T <%< U --> T is view-convertible to U
// you need “implicit evidence parameter"
object _178TypeConstraints extends App {

  //eg 1, just show en example, both are the same. no advantages here.
  class MyPair[T](val first: T, val second: T)(implicit ev: T <:< Ordered[T]) {}
  class MyPair11[T <:Ordered[T]](val first: T, val second: T) {}

  //eg2 :Type constraints let you supply a method in a generic class that can be used only under certain conditions.
  class MyPair2[T](val first: T, val second: T) {
    //The Class level it is totally the same, just for the following method, we used Type Constraints
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
  def firstLast[A, C <: Iterable[A]](it: C) = (it.head, it.last)
//  firstLast(List(1, 2, 3)) // Error

  //But this is correct,
  def firstLast2[A, C](it: C)(implicit ev: C <:< Iterable[A]) =
    (it.head, it.last)
  firstLast2(List(1, 2, 3)) // OK

}

//bk 17.9 Variance 252
//[+T] The + means that the type is covariant in T—it varies in the same direction.
object _179Variance extends App {

  class Student(name: String) extends Person(name)
  
  class Person(name: String) {
    override def toString = getClass.getName + " " + name
  }
  //bk 17.9.1 The `covariant`
  class Pair179[+T](val first: T, val second: T) {
    override def toString = "(" + first + "," + second + ")"
  }

  def makeFriendsWithPersonsSubtype(p: Pair179[Person]) =
    p.first + " and " + p.second + " are now friends."

  val fred = new Student("Fred")
  val wilma = new Student("Wilma")
  val studentPair = new Pair179(fred, wilma)

  makeFriendsWithPersonsSubtype(studentPair) // OK

  //bk 17.9.2 The `contravariant`
  // Student is a subtype of Person, but Friend[Student] is a supertype of Friend[Person].
  trait Friend1[-T] {
    def befriend(someone: T)
  }

  class Person1(name: String) extends Friend1[Person1] {
    override def toString = getClass.getName + " " + name
    def befriend(someone: Person1) {
      this + " and " + someone + " are now friends."
    }

  }

  class Student1(name: String) extends Person1(name)


  //The method here, accept Friend1[Student1], but it also accept the Friend1[Person1]
  // but Friend[Student] is a supertype of Friend[Person]. --> so we can accept  Friend[Person] here.
  def makeFriendWithStudentsSuperType(s: Student1, f: Friend1[Student1]) { f.befriend(s) }

  val student1 = new Student1("Susan")
  val student2 = new Student1("Susan")
  val person1 = new Person1("Fred")
  val person2 = new Person1("Fred")

  makeFriendWithStudentsSuperType(student1, person1) // Ok, person1 is a Friend of any person


  // A unary function has variance Function1[-A, +R]
  // TODO, not so clear what do you mean here?
  def friends(students: Array[Student1], find: Student1 => Person1) = //same as find: Function1[Student1, Person1]
    for (s <- students) yield find(s)

  def findFred(s: Person1) = new Person1("Fred")
  
  // Even the find method, need the Student1 input, Person1 is output. But findFred input is Person1, 
  // So it is also ok here.
  friends(Array(student2, student1), findFred)
}

//bk 17.10  Co- and Contravariant Positions 253
//functions are contravariant in their arguments and covariant in their results
object _1710CoandContravariantPositions extends App {

  // (var first: T) is the contravariant position
//  class Pair[+T](var first: T) // Error --> Error:(284, 9) covariant type T occurs in contravariant position in type T of value first_=

}

//bk 17.11 Objects Can’t Be Generic
object _1711ObjectsCanNotBeGeneric extends App {

  abstract class List[+T] {
    def isEmpty: Boolean

    def head: T

    def tail: List[T]
  }

  class Node[T](val head: T, val tail: List[T]) extends List[T] {
    def isEmpty = false
  }

  object Empty extends List[Nothing] {
    // It can't be object Empty[T] extends List[T] 
    // OK to be class Empty[T] extends List[T] 
    def isEmpty = true

    def head = throw new UnsupportedOperationException

    def tail = throw new UnsupportedOperationException
  }

  def show[T](lst: List[T]) {
    if (!lst.isEmpty) {
      println(lst.head); show(lst.tail)
    }
  }

  // Due to covariance, a List[Nothing] is convertible into a List[Int], and the
  // Node[Int] constructor can be invoked.
  val lst = new Node(42, Empty)
  show(new Node(1729, lst))
}

//bk 17.12 ? Wildcards 256
object _1712Wildcards extends App {
// def process(people : java.util.List[_ <: Person]){} // scala
//  void processJava(Pair <? extends Person> people){} // java
}







