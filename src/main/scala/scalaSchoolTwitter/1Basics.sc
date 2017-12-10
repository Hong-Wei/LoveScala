//http://twitter.github.io/scala_school/basics.html
class Calculator {

  val brand: String = "HP"
  val model: String = "2B"

  def add(m: Int, n: Int): Int = m + n
}

val calc = new Calculator
calc.add(1, 2)

class CalculatorWithPrimaryCOnstructor(brand: String) {
  /**
    * A constructor.
    */
  val color: String = if (brand == "TI") {
    "blue"
  } else if (brand == "HP") {
    "black"
  } else {
    "white"
  }

  // An instance method.
  def add(m: Int, n: Int): Int = m + n
}

class C {
  var acc = 0

  def aMethod: Unit = {
    acc += 1
  }

  val aFunction: () => Unit = { () => acc += 1 }
}

//http://twitter.github.io/scala_school/basics2.html

// combine the function and object.
//A Function is a set of traits, takes one argument is an instance of a Function1 trait.
// The trait defines the apply() syntactic sugar we learned earlier.
object addOne extends Function1[Int, Int] {
  def apply(m: Int): Int = m + 1
}
addOne(1)

def addOne2(m:Int) = m+1

val times = 3
//Matching on values
times match {
  case i if i == 1 => "one"
  case i if i == 2 => "two"
  case _ => "some other number" //If no this, it will cause the runtime error !!! compile is fine!
}

//Matching on type
def bigger(o: Any): Any = {
  o match {
    case i: Int if i < 0 => i - 1
    case i: Int => i + 1
    case d: Double if d < 0.0 => d - 0.1
    case d: Double => d + 0.1
    case text: String => text + "s"
  }
}

//Matching on class members
def calcType(calc: Calculator) = calc match {
  case _ if calc.brand == "HP" && calc.model == "20B" => "financial"
  case _ if calc.brand == "HP" && calc.model == "48G" => "scientific"
  case _ if calc.brand == "HP" && calc.model == "30B" => "business"
  case _ => "unknown"
}

//Case class, will contains the equal, hash method, and also easy used in patten matching
//Case classes are just normal ones, just help you define the following thing:
//1 each of the constructor parameters becomes a `val` unless it is explicitly declared as a `var`(not good)
//2 `apply` method is provided for the companion object that lets you construct objects without new
//3 `unapply` method is provided that makes pattern matching work.
//4 `toString`,`equals` ,`hashcode` and `copy` are generated unless they are provided explicitly.

case class Calculator2(brand: String, model: String)
def calcType2(calc: Calculator) = calc match {
  case Calculator2("HP", "20B") => "financial"
  case Calculator2("HP", "48G") => "scientific"
  case Calculator2("HP", "30B") => "business"
  case Calculator2(ourBrand, ourModel) => "Calculator: %s %s is of unknown type".format(ourBrand, ourModel)
  //alternatives for that last match
  case Calculator2(_, _) => "Calculator of unknown type"
  //We could simply not specify it is a Calculator at all.
  case abc@_ => s"$abc is unknown type"// use re-bind here, we can get the value from it
  //we could re-bind the matched value with another name--> So we can print or get the info about it.
  case c@Calculator2(_, _) => "Calculator: %s of unknown type".format(c)
}


//Exceptions == try-catch-finally(use pattern mathcing)
try {
  1+1
} catch {
  case e: Exception => "wrong"
} finally {
  "closed"
}
//try is also `expression-oriented, it return some value
val value: Int = try {
  1 + 1
} catch {
  case e: Exception => 2
} finally {
  2
}
