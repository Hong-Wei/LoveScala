package aBook

//eg3 official
//https://docs.scala-lang.org/tour/by-name-parameters.html


object official extends App{
//  By-name parameters are only evaluated when used. 
  // They are in contrast to by-value parameters. 
  // To make a parameter called by-name, simply prepend => to its type.

  //it does not care, how many parameters in there !!
  def calculate(input: => Int) = input * 37
  
  println(calculate(3))
  def method1(a: Int) = a+2
  def method2(a: Int,a1: Int) = a+1+a1
  println(calculate(method1(1)))
  println(calculate(method2(1,1)))

  // By-name parameters have the advantage that they are not evaluated if they aren’t used in the function body.
  // On the other hand, by-value parameters have the advantage that they are evaluated only once.

  def whileLoop(condition: => Boolean)(body: => Unit): Unit =
    if (condition) {
      body
      whileLoop(condition)(body)
    }

  var i = 3

  whileLoop (i > 0) {
    println(i)
    i -= 1
  }  // prints 2 1


//  This ability to delay evaluation of a parameter until it is used can help performance 
  // if the parameter is computationally intensive to evaluate or a longer-running block of code such as fetching a URL.
  
  
  
  


}