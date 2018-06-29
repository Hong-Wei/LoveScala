package LearnScalaTest

//http://www.scalatest.org/user_guide/using_assertions 

import org.scalatest._

import scala.collection.mutable.Stack

/*
assert for general assertions;
assertResult to differentiate expected from actual values;
assertThrows to ensure a bit of code throws an expected exception.
fail to fail a test unconditionally;
cancel to cancel a test unconditionally;
succeed to make a test succeed unconditionally;
intercept to ensure a bit of code throws an expected exception and then make assertions about the exception;
assertDoesNotCompile to ensure a bit of code does not compile;
assertCompiles to ensure a bit of code does compile;
assertTypeError to ensure a bit of code does not compile because of a type (not parse) error;
withClue to add more information about a failure.
*/
@Ignore
class assertions extends FlatSpec with Matchers {

  "The assert macro: " should "2 did not equal 1" in {
    val left = 2
    val right = 1
    assert(left == right)  //have message: 2 did not equal 1, ScalaTestFailureLocation
    
  }

  it should "1 did not equal 2, and 3 was not greater than or equal to 4" in {
    val a = 1
    val b = 2
    val c = 3
    val d = 4

    assert(a == b || c >= d) // Use the Logic ||
    // Error message: 1 did not equal 2, and 3 was not greater than or equal to 4
  }
  
  it should "List(1, 2, 3) did not contain 4" in {
    val a = 1
    val b = 2
    val c = 3
    val xs = List(a, b, c)

    assert(xs.exists(_ == 4)) 
    // Error message: List(1, 2, 3) did not contain 4

  }
  
  it should """"hello" started with "h", but "goodbye" did not end with "y" """ in {
   
    assert("hello".startsWith("h") && "goodbye".endsWith("y")) // logic && and string boolean.
    // Error message: "hello" started with "h", but "goodbye" did not end with "y"
  }
  
  it should "1.0 was not instance of scala.Int" in {
    
    val num: Double = 1.0
    assert(num.isInstanceOf[Int]) // isInstanceOf[Int]
    // Error message: 1.0 was not instance of scala.Int

  }
  it should "Some(2) was not empty" in {

    assert(Some(2).isEmpty)
    // Error message: Some(2) was not empty
  }

  it should "For expressions that are not recognized1" in {
    assert(None.isDefined)
    // Error message: scala.None.isDefined was false
     }

  it should "For expressions that are not recognized2" in {
    val xs = List(1, 2, 3)
    assert(xs.exists(i => i > 10))
    // Error message: xs.exists(((i: Int) => i.>(10))) was false
  }

  it should "can augment the standard error message by providing a String as a second argument to assert" in {
    val attempted = 2
    assert(attempted == 1, "Execution was attempted " + attempted + " times instead of 1 time")
  }

  // Although the assert macro provides a natural, readable extension to Scala's assert mechanism that provides good error messages, 
  // 1> as the operands become lengthy, the code becomes less readable. 
  // 2> The error messages generated for == and === comparisons don't distinguish between actual and expected values. 
  //    The operands are just called left and right, because if one were named expected and the other actual, 
  //     it would be difficult for people to remember which was which.
  // So --> To help with these limitations of assertions, 
  //        Suite includes a method called assertResult that can be used as an alternative to assert.
  
  "Expected results" should "(expected){}" in {
    val a = 5
    val b = 2
    assertResult(2) {
      a - b
    }
  }

  "Forcing failures" should "failures" in {
    fail("I've got a bad feeling about this")
  }

  "Achieving success" should "success" in {
    //TODO 1, not so clear what is this for ?? async
    // In async style tests, you must end your test body with either Future[Assertion] or Assertion. 
    // ScalaTest's assertions (including matcher expressions) have result type Assertion, 
    // so ending with an assertion will satisfy the compiler. 
    // If a test body or function body passed to Future.map does not end with type Assertion, 
    // however, you can fix the type error by placing succeed at the end of the test or function body:
    succeed
  }
  
  "assertThrows- Expected exceptions" should "wrong exceptions " in {
    val s = "hi"
    assertThrows[NullPointerException] { // Result type: Expected exception java.lang.NullPointerException to be thrown, but java.lang.StringIndexOutOfBoundsException was thrown
      s.charAt(-1)
    }
  }

  it should "right exceptions " in {
    val s = "hi"
    assertThrows[IndexOutOfBoundsException] { // Result type: Expected exception java.lang.NullPointerException to be thrown, but java.lang.StringIndexOutOfBoundsException was thrown
      s.charAt(-1)
    }
  }

  "intercept- exceptions" should "wrong exceptions " in {
    val s = "hi"
    val caught =
      intercept[NullPointerException] { // Result type: Expected exception java.lang.NullPointerException to be thrown, but java.lang.StringIndexOutOfBoundsException was thrown
        s.charAt(-1)
      }
    assert(caught.getMessage.indexOf("-1") != -1)
  }
  
  it should "right exceptions " in {
    val s = "hi"
    val caught =
      intercept[IndexOutOfBoundsException] { // Result type: Expected exception java.lang.NullPointerException to be thrown, but java.lang.StringIndexOutOfBoundsException was thrown
        s.charAt(-1)
      }
    assert(caught.getMessage.indexOf("-1") != -1)
  }

  "Checking that a snippet of code does or does not compile" should "compile" in {
    assertDoesNotCompile("val a: String = 1")
  }

  it should "compile- assertTypeError" in {
    assertTypeError("val a: String = 1")
  }

  it should "compile2" in {
    assertCompiles("val a: Int = 1")
  }


  "Assumptions" should "database is working" in {
//    assume(database.isAvailable, "The database was down again")
//    assume(database.getAllUsers.count === 9)
  }


  "Forcing cancelations" should "cancelations" in {
    cancel("Can't run the test because no internet connection was found")
  }

  "Getting a clue" should "clue" in {
    assert(1 + 1 === 3, "this is a clue")
  }

  it should "clue same as first" in {
    assertResult(3, "this is a clue") { 1 + 1 }
  }

  it should "clue in exception" in {
    withClue("this is a clue") {
      assertThrows[IndexOutOfBoundsException] {
        "hi".charAt(-1)
      }
    }
  }

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    stack.pop() should be(2)
    stack.pop() should be(1)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new Stack[Int]
    a[NoSuchElementException] should be thrownBy {
      emptyStack.pop()
    }
  }
}