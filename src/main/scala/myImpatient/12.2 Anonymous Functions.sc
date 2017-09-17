//In scala, you don't have to give a name to each function, just like you don't
//have to give a name to each unmber.
(x: Double) => 3 * x
// store it in a variable. 
val tripleVarible: (Double) => Double = (x: Double) => 3 * x
// or define a function
def tripleFunction(x: Double): Double = 3 * x // def


//You do not have to name the function, can just pass it to another function:
Array(3.14, 1.42, 2.0).map((x: Double) => 3 * x)
Array(3.14, 1.42, 2.0).map(tripleVarible)
Array(3.14, 1.42, 2.0).map(tripleFunction)
//you can use braces instead of parentheses
Array(3.14, 1.42, 2.0).map {tripleFunction _}
Array(3.14, 1.42, 2.0) map {tripleFunction}