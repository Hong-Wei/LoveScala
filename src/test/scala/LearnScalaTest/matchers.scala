//http://www.scalatest.org/user_guide/using_matchers 
package LearnScalaTest

import org.scalatest._

import scala.List
import scala.collection.mutable.Stack

/*
Here is a table of contents for this page:

Checking equality with matchers
Checking size and length
Checking strings
Greater and less than
Checking Boolean properties with be
Using custom BeMatchers
Checking object identity
Checking an object's class
Checking numbers against a range
Checking for emptiness
Working with "containers"
Working with "aggregations"
Working with "sequences"
Working with "sortables"
Working with iterators
Inspector shorthands
Single-element collections
Java collections and maps
Strings and Arrays as collections
Be as an equality comparison
Being negative
Checking that a snippet of code does not compile
Logical expressions with and and or
Working with Options
Checking arbitrary properties with have
Using length and size with HavePropertyMatchers
Checking that an expression matches a pattern
Using custom matchers
Creating dynamic matchers
Creating matchers using logical operators
Composing matchers
Checking for expected exceptions
Those pesky parens
*/

class matchers extends FlatSpec with Matchers {

  val result: Int = 3
  val result2: List[Int] = List(3)
  val map = Map("two"->3)

  "Matchers : " should "use should equal" in {
    result should equal (3)
//    result should not equal (3)
  }

  it should "Checking equality with matchers - 5 kinds" in {
    result should equal(3) // can customize equality
    result should ===(3) // can customize equality and enforce type constraints
    result should be(3) // cannot customize equality, so fastest to compile
    result shouldEqual 3 // can customize equality, no parentheses required
    result shouldBe 3 // cannot customize equality, so fastest to compile, no parentheses required
  }

  it should "Checking equality with matchers - 5 Array" in {
    // By default, an implicit Equality[T] instance is available for any type T, in which equality is implemented 
    // by simply invoking == on the left value, passing in the right value, with special treatment for arrays. 
    // If either left or right is an array, deep will be invoked on it before comparing with ==. 
    // Thus, the following expression will yield false, because Array's equals method compares object identity:
    // --> Array(1, 2) == Array(1, 2) // yields false
    //  The next expression will by default not result in a TestFailedException, because default Equality[Array[Int]] 
    // compares the two arrays structurally, taking into consideration the equality of the array's contents:
    // --> Array(1, 2) should equal (Array(1, 2)) // succeeds (i.e., does not throw TestFailedException)


    Array(1, 2) should equal(Array(1, 2)) // succeeds (i.e., does not throw TestFailedException)
    Array(1, 2) should ===(Array(1, 2)) // succeeds (i.e., does not throw TestFailedException)
    Array(1, 2) should be(Array(1, 2)) // succeeds (i.e., does not throw TestFailedException)
    Array(1, 2) shouldEqual (Array(1, 2)) // succeeds (i.e., does not throw TestFailedException)
    Array(1, 2) shouldBe (Array(1, 2)) // succeeds (i.e., does not throw TestFailedException)

    assertResult(Array(1, 2))(Array(1, 2)) // succeeds (i.e., does not throw TestFailedException)
    assert(Array(1, 2) === Array(1, 2)) // succeeds (i.e., does not throw TestFailedException)
//    assert(Array(1, 2) == Array(1, 2)) //  throw TestFailedException,  ==, in scala compares object identity
  }

  it should "supply implicit parameters explicitly" in {
    "Hi" should equal("hi")(org.scalactic.Explicitly.after being org.scalactic.StringNormalizations.lowerCased)
  }


  it should "Checking size and length" in {
    // The length syntax can be used with String, Array, any scala.collection.GenSeq, 
    //    any java.util.List, and any type T for which an implicit Length[T] type class is available in scope. 
    // The size syntax can be used with Array, any scala.collection.GenTraversable, any java.util.Collection, 
    //    any java.util.Map, and any type T for which an implicit Size[T] type class is available in scope.

    //In addition, the length syntax can be used with any object that has a field or method named length 
    // or a method named getLength. Similarly, the size syntax can be used with any object that has a field 
    // or method named size or a method named getSize. The type of a length or size field, 
    // or return type of a method, must be either Int or Long. Any such method must take no parameters.
    val result = List(1, 2, 3)
    result should have length 3
    result should have size 3
  }

  it should "Checking strings" in {
    val string = "Hello seven world"
    string should startWith("Hello")
    string should endWith("world")
    string should include("seven")

    // regular expression
    string should startWith regex "Hel*o"
    string should endWith regex "wo.ld"
    string should include regex "wo.ld"
    "abbccxxx" should startWith regex ("a(b*)(c*)" withGroups("bb", "cc"))
    "xxxabbcc" should endWith regex ("a(b*)(c*)" withGroups("bb", "cc"))
    "xxxabbccxxx" should include regex ("a(b*)(c*)" withGroups("bb", "cc"))
//    "abbcc" should fullyMatch regex ("a(b*)(c*)" withGroups("bb", "cc"))
//    string should fullyMatch regex """(-)?(\d+)(\.\d*)?"""
  }

  it should "Greater and less than" in {
    val one = 5
    one should be < 7
    one should be > 0
    one should be <= 7
    one should be >= 0
  }

  //TODO not so clear ??
  it should "Checking Boolean properties with be" in {
    //    iter shouldBe 'traversableAgain
    //    temp should be a 'file
    //    keyEvent should be an 'actionKey
  }
  //TODO not so clear ??
  it should "Using custom BeMatchers" in {
    //    num shouldBe odd
    //    num should not be evene: scala.None.isDefined was false
  }

  it should "Checking object identity" in {
    val a = new Integer(1)
    val b = new Integer(1)
    a should not be theSameInstanceAs (b)
  }

  it should "Checking an object's class" in {
    result shouldBe a[Integer]
    result should not be an[Double]

    // Because type parameters are erased on the JVM, we recommend you insert an underscore for any type parameters 
    // when using this syntax. Both of the following test only that the result is an instance of List[_], 
    // because at runtime the type parameter has been erased:

    result2 shouldBe a[List[_]] // recommended
    result2 shouldBe a[List[Integer]] // discouraged
  }


  it should "Checking numbers against a range" in {
    val sevenDotOh = 7.1
    sevenDotOh should equal(6.9 +- 0.2)
    sevenDotOh should ===(6.9 +- 0.2)
    sevenDotOh should be(6.9 +- 0.2)
    sevenDotOh shouldEqual 6.9 +- 0.2
    sevenDotOh shouldBe 6.9 +- 0.2

    val seven = 7
    seven should equal(6 +- 2)
    seven should ===(6 +- 2)
    seven should be(6 +- 2)
    seven shouldEqual 6 +- 2
    seven shouldBe 6 +- 2
  }

  it should "Checking for emptiness" in {
    val traversable = List()
    val javaMap = Map(1 -> 2)
    traversable shouldBe empty
    javaMap should not be empty

    // The empty token can be used with any type L for which an implicit Emptiness[L] exists. 
    // The Emptiness companion object provides implicits for GenTraversable[E], java.util.Collection[E], java.util.Map[K, V], String, Array[E], and Option[E]. 
    // In addition, the Emptiness companion object provides structural implicits for types that declare an isEmpty method that returns a Boolean. 
    // Here are some examples:

    List.empty shouldBe empty
    None shouldBe empty
    Some(1) should not be empty

    "" shouldBe empty

    new java.util.HashMap[Int, Int] shouldBe empty

    new {
      def isEmpty = true
    } shouldBe empty

    Array(1, 2, 3) should not be empty

  }

  it should "Working with `containers`" in {
    // The contain syntax shown above can be used with any type C that has a "containing" nature, 
    // evidenced by an implicit org.scalatest.enablers.Containing[L], 
    // where L is left-hand type on which should is invoked. 
    // In the Containing companion object, implicits are provided for types GenTraversable[E], java.util.Collection[E], java.util.Map[K, V], String, Array[E], and Option[E]. 
    // Here are some examples:
    Array(1, 2, 3) should contain(1)
    Map('a' -> 1, 'b' -> 2, 'c' -> 3) should contain('b' -> 2)
    Set(1, 2, 3) should contain(2)
    List(1, 2, 3) should contain(2)
    "123" should contain('2')
    Some(2) should contain(2)


    (List("Hi", "Di", "Ho") should contain("ho")) (org.scalactic.Explicitly.after being org.scalactic.StringNormalizations.lowerCased)


    List(1, 2, 3, 4, 5) should contain oneOf(5, 7, 9)
    Some(7) should contain oneOf(5, 7, 9)
    "howdy" should contain oneOf('a', 'b', 'c', 'd')


    // atLeastOneOf--   Note that if multiple specified elements appear in the containing object, oneOf will fail: use atLeastOneOf 
    List(1, 2, 3, 4, 5) should contain atLeastOneOf(2, 3, 4)

    //noneOf
    List(1, 2, 3, 4, 5) should contain noneOf(7, 8, 9)
    Some(0) should contain noneOf(7, 8, 9)
    "12345" should contain noneOf('7', '8', '9')
  }

  it should "Working with `aggregations`" in {
    List(1, 2, 3) should contain atLeastOneOf (2, 3, 4)
    Array(1, 2, 3) should contain atLeastOneOf (3, 4, 5)
    "abc" should contain atLeastOneOf ('c', 'a', 't')
    List(1, 2, 3, 4, 5) should contain atMostOneOf (5, 6, 7)
    List(1, 2, 3, 4, 5) should contain allOf (2, 3, 5)
    List(1, 2, 3, 2, 1) should contain only (1, 2, 3)
    List(1, 2, 2, 3, 3, 3) should contain theSameElementsAs Vector(3, 2, 3, 1, 2, 3)
  }
  
  it should "Checking arbitrary properties with have" in {

    case class Book(
                     title: String,
                     author: List[String],
                     pubYear: Int
                   )
    
    val book = Book("Programming in Scala",Nil,0)
    book should have (
      'pubYear (0)
    )

  }



  it should "Logical expressions with and and or" in {
    map should (contain key ("two") and not contain value (7))
  }

  it should "Checking for expected exceptions" in {
    an [IndexOutOfBoundsException] should be thrownBy "hi".charAt(-1)
    val thrown = the [IndexOutOfBoundsException] thrownBy "hi".charAt(-1)
    thrown.getMessage should equal ("String index out of range: -1")

    the [ArithmeticException] thrownBy 1 / 0 should have message "/ by zero"
    the [IndexOutOfBoundsException] thrownBy {
      "hI".charAt(-1)
    } should have message "String index out of range: -1"

  }

  
}