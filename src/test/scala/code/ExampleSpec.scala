


import org.scalatest.FunSuite

import scala.xml.XML

class SetFunSuite extends FunSuite {

  test("An empty Set should have size 0") {
    assert(Set.empty.size == 0)
  }

  test("Invoking head on an empty Set should produce NoSuchElementException") {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
  }
}

import org.scalatest.FlatSpec

class SetFlatSpec extends FlatSpec {

  "An empty Set" should "have size 0" in {
    assert(Set.empty.size == 0)
  }

  it should "produce NoSuchElementException when head is invoked" in {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
  }
}

import org.scalatest.FunSpec

class SetFunSpec extends FunSpec {

  describe("A Set") {
    describe("when empty") {
      it("should have size 0") {
        assert(Set.empty.size == 0)
      }

      it("should produce NoSuchElementException when head is invoked") {
        assertThrows[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }
}

import org.scalatest.WordSpec

class SetWordSpec extends WordSpec {

  "A Set" when {
    "empty" should {
      "have size 0" in {
        assert(Set.empty.size == 0)
      }

      "produce NoSuchElementException when head is invoked" in {
        assertThrows[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }
}

import org.scalatest.FreeSpec

class SetFreeSpec extends FreeSpec {

  "A Set" - {
    "when empty" - {
      "should have size 0" in {
        assert(Set.empty.size == 0)
      }

      "should produce NoSuchElementException when head is invoked" in {
        assertThrows[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }
}

import org.scalatest._
import prop._
import scala.collection.immutable._

class SetPropSpec extends PropSpec with TableDrivenPropertyChecks with Matchers {

  val examples =
    Table(
      "set",
      BitSet.empty,
      HashSet.empty[Int],
      TreeSet.empty[Int]
    )

  property("an empty Set should have size 0") {
    forAll(examples) { set =>
      set.size should be (0)
    }
  }

  property("invoking head on an empty set should produce NoSuchElementException") {
    forAll(examples) { set =>
      a [NoSuchElementException] should be thrownBy { set.head }
    }
  }
}

import org.scalatest._

class TVSet {
  private var on: Boolean = false
  def isOn: Boolean = on
  def pressPowerButton() {
    on = !on
  }
}

class SetFeatureSpec extends FeatureSpec with GivenWhenThen {

  info("As a TV set owner")
  info("I want to be able to turn the TV on and off")
  info("So I can watch TV when I want")
  info("And save energy when I'm not watching TV")

  feature("TV power button") {
    scenario("User presses power button when TV is off") {

      Given("a TV set that is switched off")
      val tv = new TVSet
      assert(!tv.isOn)

      When("the power button is pressed")
      tv.pressPowerButton()

      Then("the TV should switch on")
      assert(tv.isOn)
    }

    scenario("User presses power button when TV is on") {

      Given("a TV set that is switched on")
      val tv = new TVSet
      tv.pressPowerButton()
      assert(tv.isOn)

      When("the power button is pressed")
      tv.pressPowerButton()

      Then("the TV should switch off")
      assert(!tv.isOn)
    }
  }
}

import org.scalatest.refspec.RefSpec

class SetRefSpec extends RefSpec {

  object `A Set` {
    object `when empty` {
      def `should have size 0` {
        assert(Set.empty.size == 0)
      }

      def `should produce NoSuchElementException when head is invoked` {
        assertThrows[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }
}



import org.scalatest._

abstract class UnitSpec extends FlatSpec with Matchers with
  OptionValues with Inside with Inspectors


import org.scalatest._

class MySpec extends UnitSpec {
  // Your tests here
}


import collection.mutable.Stack
import org.scalatest._

class StackSpec extends FlatSpec {

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    assert(stack.pop() === 2)
    assert(stack.pop() === 1)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new Stack[String]
    assertThrows[NoSuchElementException] {
      emptyStack.pop()
    }
  }
//BK 1 The assert macro
//  val a = 1
//  val b = 2
//  val c=   3
//  val d = 4
//  val xs=  List(a, b, c)
//  val num = 1.0
//  assert(a == b || c >= d)
//  // Error message: 1 did not equal 2, and 3 was not greater than or equal to 4
//
//  assert(xs.exists(_ == 4))
//  // Error message: List(1, 2, 3) did not contain 4
//
//  assert("hello".startsWith("h") && "goodbye".endsWith("y"))
//  // Error message: "hello" started with "h", but "goodbye" did not end with "y"
//
//  assert(num.isInstanceOf[Int])
//  // Error message: 1.0 was not instance of scala.Int
//
//  assert(Some(2).isEmpty)
//  // Error message: Some(2) was not empty
//
//  assert(None.isDefined)
//  // Error message: scala.None.isDefined was false
//
//  assert(xs.exists(i => i > 10))
//  // Error message: xs.exists(((i: Int) => i.>(10))) was false
//  val attempted = 2
//  assert(attempted == 1, "Execution was attempted " + 2 + " times instead of 1 time")


  //BK 2 Expected results
  val a = 4
  val b = 2
  assertResult(2) {
    a - b
  }

  //BK 3 Forcing failures
//  fail()
//  fail("I've got a bad feeling about this")

//BK 4 Expected exceptions
  val s = "hi"
  try {
    s.charAt(-1)
    fail()
  }
  catch {
    case _: IndexOutOfBoundsException => // Expected, so continue
  }

  assertThrows[IndexOutOfBoundsException] { // Result type: Assertion
    s.charAt(-1)
  }
//
//  assume(database.isAvailable, "The database was down again")
//  assume(database.getAllUsers.count === 9)

//  cancel("Can't run the test because no internet connection was found")

//  assert(1 + 1 === 3, "this is a clue")
  assertResult(3, "this is a clue") { 1 + 1 }
}

import org.scalatest.FlatSpec
import scala.collection.mutable.Stack

class StackSpec1 extends FlatSpec {

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    assert(stack.pop() === 2)
    assert(stack.pop() === 1)
  }

  ignore should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new Stack[String]
    intercept[NoSuchElementException] {
      emptyStack.pop()
    }
  }
}


import org.scalatest.Tag

object DbTest extends Tag("com.mycompany.tags.DbTest")

import org.scalatest.FlatSpec
import org.scalatest.tagobjects.Slow

class ExampleSpec extends FlatSpec {

  "The Scala language" must "add correctly" taggedAs(Slow) in {
    val sum = 1 +   1
    assert(sum === 2)
  }

  it must "subtract correctly" taggedAs(Slow, DbTest) in {
    val diff = 4 - 1
    assert(diff === 3)
  }
}


import org.scalatest.FlatSpec
import collection.mutable.ListBuffer

class FixturesSpec extends FlatSpec {

  def fixture =
    new {
      val builder = new StringBuilder("ScalaTest is ")
      val buffer = new ListBuffer[String]
    }

  "Testing" should "be easy" in {
    val f = fixture
    f.builder.append("easy!")
    assert(f.builder.toString === "ScalaTest is easy!")
    assert(f.buffer.isEmpty)
    f.buffer += "sweet"
  }

  it should "be fun" in {
    val f = fixture
    f.builder.append("fun!")
    assert(f.builder.toString === "ScalaTest is fun!")
    assert(f.buffer.isEmpty)
  }
}

import collection.mutable.ListBuffer
import org.scalatest.FlatSpec

class FixtureSpec1 extends FlatSpec {

  trait Builder {
    val builder = new StringBuilder("ScalaTest is ")
  }

  trait Buffer {
    val buffer = ListBuffer("ScalaTest", "is")
  }

  // This test needs the StringBuilder fixture
  "Testing" should "be productive" in new Builder {
    builder.append("productive!")
    assert(builder.toString === "ScalaTest is productive!")
  }

  // This test needs the ListBuffer[String] fixture
  "Test code" should "be readable" in new Buffer {
    buffer += ("readable!")
    assert(buffer === List("ScalaTest", "is", "readable!"))
  }

  // This test needs both the StringBuilder and ListBuffer
  it should "be clear and concise" in new Builder with Buffer {
    builder.append("clear!")
    buffer += ("concise!")
    assert(builder.toString === "ScalaTest is clear!")
    assert(buffer === List("ScalaTest", "is", "concise!"))
  }
}


import java.io.File
import org.scalatest._

class FixtureSpec2 extends FlatSpec {

  override def withFixture(test: NoArgTest) = {

    super.withFixture(test) match {
      case failed: Failed =>
        val currDir = new File(".")
        val fileNames = currDir.list()
        info("Dir snapshot: " + fileNames.mkString(", "))
        failed
      case other => other
    }
  }

  "This test" should "succeed" in {
    assert(1 + 1 === 2)
  }

  it should "fail" in {
//    assert(1 + 1 === 3)
  }
}


import java.util.concurrent.ConcurrentHashMap

object DbServer {
  // Simulating a database server
  type Db = StringBuffer
  private val databases = new ConcurrentHashMap[String, Db]

  def createDb(name: String): Db = {
    val db = new StringBuffer
    databases.put(name, db)
    db
  }

  def removeDb(name: String) {
    databases.remove(name)
  }
}

import org.scalatest.FlatSpec
import DbServer._
import java.util.UUID.randomUUID
import java.io._

class FixtureSpec3 extends FlatSpec {

  def withDatabase(testCode: Db => Any) {
    val dbName = randomUUID.toString
    val db = createDb(dbName) // create the fixture
    try {
      db.append("ScalaTest is ") // perform setup
      testCode(db) // "loan" the fixture to the test
    }
    finally removeDb(dbName) // clean up the fixture
  }

  def withFile(testCode: (File, FileWriter) => Any) {
    val file = File.createTempFile("hello", "world") // create the fixture
    val writer = new FileWriter(file)
    try {
      writer.write("ScalaTest is ") // set up the fixture
      testCode(file, writer) // "loan" the fixture to the test
    }
    finally writer.close() // clean up the fixture
  }

  // This test needs the file fixture
  "Testing" should "be productive" in withFile { (file, writer) =>
    writer.write("productive!")
    writer.flush()
    assert(file.length === 24)
  }

  // This test needs the database fixture
  "Test code" should "be readable" in withDatabase { db =>
    db.append("readable!")
    assert(db.toString === "ScalaTest is readable!")
  }

  // This test needs both the file and the database
  it should "be clear and concise" in withDatabase { db =>
    withFile { (file, writer) => // loan-fixture methods compose
      db.append("clear!")
      writer.write("concise!")
      writer.flush()
      assert(db.toString === "ScalaTest is clear!")
      assert(file.length === 21)
    }
  }
}

import org.scalatest.fixture
import java.io._

class FixtureSpec4 extends fixture.FlatSpec {

  case class FixtureParam(file: File, writer: FileWriter)

  def withFixture(test: OneArgTest) = {
    val file = File.createTempFile("hello", "world") // create the fixture
    val writer = new FileWriter(file)
    val theFixture = FixtureParam(file, writer)

    try {
      writer.write("ScalaTest is ") // set up the fixture
      withFixture(test.toNoArgTest(theFixture)) // "loan" the fixture to the test
    }
    finally writer.close() // clean up the fixture
  }

  "Testing" should "be easy" in { f =>
    f.writer.write("easy!")
    f.writer.flush()
    assert(f.file.length === 18)
  }

  it should "be fun" in { f =>
    f.writer.write("fun!")
    f.writer.flush()
    assert(f.file.length === 17)
  }
}


import org.scalatest._
import collection.mutable.ListBuffer

class FixtureSpec5 extends FlatSpec with BeforeAndAfter {

  val builder = new StringBuilder
  val buffer = new ListBuffer[String]

  before {
    builder.append("ScalaTest is ")
  }

  after {
    builder.clear()
    buffer.clear()
  }

  "Testing" should "be easy" in {
    builder.append("easy!")
    assert(builder.toString === "ScalaTest is easy!")
    assert(buffer.isEmpty)
    buffer += "sweet"
  }

  it should "be fun" in {
    builder.append("fun!")
    assert(builder.toString === "ScalaTest is fun!")
    assert(buffer.isEmpty)
  }
}

import org.scalatest._

class MatcherSpec extends FlatSpec with Matchers {
  val result =3
//  result should equal (3)
//  result should equal (3) // can customize equality
//  result should === (3)   // can customize equality and enforce type constraints
//  result should be (3)    // cannot customize equality, so fastest to compile
//  result shouldEqual 3    // can customize equality, no parentheses required
//  result shouldBe 3       // cannot customize equality, so fastest to compile, no parentheses required
//
//
//  Array(1, 2) == Array(1, 2) // yields false
//  Array(1, 2) should equal (Array(1, 2)) // succeeds (i.e., does not throw TestFailedException)
////  Some(2) should === (2)
//
//  result should have length 3
}

//import org.scalatest.FlatSpec
//import org.scalamock.scalatest.MockFactory
//
//class MockSpec extends FlatSpec with MockFactory{
//  val m = mockFunction[Int, String]
//
//}
//
//object ABC extends App{
//  val xml = XML.loadFile("/src/main/resources/ISOCurrencyCode.xml")
//  val toList = xml.toList
//  //      val currencies = Currency.getAvailableCurrencies();
//  //      for {
//  //        currency <- currencies if (currency.getNumericCode() == numericCode)
//  //      }
//  //    }
//  //    if(currency.length!= 3) return false
//  //    Charset.forName(CharEncoding.ISO_8859_1).newEncoder().canEncode(currency)
//  true
//}