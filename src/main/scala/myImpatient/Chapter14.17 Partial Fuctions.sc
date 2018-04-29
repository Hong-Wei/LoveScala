// A set of case clauses enclosed in braces is a partial function
// A function which may not be defined for all inputs.
// 
//It is an instance of a class PartialFunction[A, B]. 
// (A is the parameter type, B the return type.) 
// 
// That class has two methods: apply, which computes the function value from the matching pattern,
// and isDefinedAt, which returns true if the input matches at least one of the patterns.
// 
import scala.collection.immutable.IndexedSeq

//eg1:
private val intToStringToFunction: ((Int) => String) => PartialFunction[Int, String] = PartialFunction[Int, String]
private val println1: Unit = println()


val f: PartialFunction[Char, Int] = {
  case '+' => 1;
  case '-' => -1
}
private val f1: Int = f('-')
private val at1: Boolean = f.isDefinedAt('-')
private val at: Boolean = f.isDefinedAt('*')
//  f('0') // Match Error

//eg2:accept a PartialFunction as a parameter
private val collect: IndexedSeq[Int] = "-3+4".collect { case '+' => 1; case '-' => -1 }
println(collect)
private val collect1: IndexedSeq[Int] = "-3+4".collect {
  f
}

def f(x: => Int): Int = x * x
f(5)

var y = 0

f {
  y += 1; y
}

val a: () => Unit = () => println("I'm an anonymous function")
a.apply()