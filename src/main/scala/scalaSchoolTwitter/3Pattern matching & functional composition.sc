//http://twitter.github.io/scala_school/pattern-matching-and-functional-composition.html

//1 Function Composition
def f(s: String) = "f(" + s + ")"
def g(s: String) = "g(" + s + ")"
val fComposeG = f _ compose g _ //-->f(g(x)), g is first
val fAndThenG = f _ andThen g _ //-->g(f(x)), f is first



//2Currying vs Partial Application
//2.1 case statement--> it is a subclass of function called a PartialFunction
//2.2 a collection of multiple case statements--> multiple PartialFunctions compose together.

//normal function need take care of all the int, not only 1
def normalFunction= (n: Int)  => if (n ==1 )"normal Function" else ""
// partial function only take case `case 1`, all others forget them, it has the build-in method `isDefinedAt` to check all cases.
def oneIntPartialFunction: PartialFunction[Int, String] = { case 1 => "oneIntPartialFunction" }

oneIntPartialFunction.isDefinedAt(1)
oneIntPartialFunction.isDefinedAt(2)


normalFunction(1)
oneIntPartialFunction(1)


// PartialFunctions can be composed with something new, called orElse, that reflects whether the PartialFunction is defined over the supplied argument.
val two: PartialFunction[Int, String] = { case 2 => "two" }
val three: PartialFunction[Int, String] = { case 3 => "three" }
val wildcard: PartialFunction[Int, String] = { case _ => "something else" }

val partial: PartialFunction[Int, String] = two orElse three orElse wildcard
partial(5)

///The mystery of case.
case class PhoneExt(name: String, ext: Int)

val extensions = List(PhoneExt("steve", 100), PhoneExt("robey", 200))

//filter takes a function. In this case a predicate function of (PhoneExt) => Boolean.
extensions.filter { case PhoneExt(name, extension) => extension < 200 }
