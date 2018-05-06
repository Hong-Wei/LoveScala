package aBook
//eg2 StackoverFlow
//https://stackoverflow.com/questions/13337338/call-by-name-vs-call-by-value-in-scala-clarification-needed


object stackOverFlow extends App{
  def something(): Int = {
    println("calling something")
    1 // return value
  }

// This is because call-by-value functions compute the passed-in expression's value before calling the function, 
// thus the same value is accessed every time.
  def callByValue(x: Int) = {
    println("x1=" + x)
    println("x2=" + x)
  }

//  However, call-by-name functions recompute the passed-in expression's value every time it is accessed.
  def callByName(x: => Int) = {
    println("x1=" + x)
    println("x2=" + x)
  }

  callByValue(something) 
  println("------------------------------------")  
  callByName(something)

}