// 1 in Scala, a function is a "first-class citizen",just like a number.
//You can store a function in variable:
val num: Double = 3.14
val fun: (Double) => Double = scala.math.ceil _ 
//1 The code sets num to 3.14 and fun to ceil function.
// The _ behind the ceil function, indicates that you really meant the function, and you didn't just forget to supply the arguments.
// Technically, _ turns the ceil method into a function. In scala, you cannot manipulate methods, only functions. 

def increment(n: Int): Int = n + 1
val fun1: (Int) => Int = increment _ // change the methods to functions

//2, what can you do with a function ? 
//1  Call it 
//2  pass it around, by storing it in a variable or giving it to a function as a parameter.
fun(num) //1 call it, the same as following, just a new
scala.math.ceil(num) 
List(1, 2, 3).map(fun1) 

//eg3: implicitly changed method to function
List(1, 2, 3).map(increment _) //change methods to functions automatically
List(1, 2, 3).map(increment)  
