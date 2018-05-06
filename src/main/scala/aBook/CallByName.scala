package aBook

//eg1 : simple explaination
//http://zhouchaofei2010.iteye.com/blog/2257201
object CallByName1 extends App {

  // This not use the `call by name` .
  def runInThread(block: () => Unit) {
    new Thread {
      override def run() { block() }
    }.start()
  }
  
  def oneMethod = () => {println("Hi1"); Thread.sleep(1000); println("Bye1")}

//  runInThread { oneMethod } // Here, `oneMethod` will run first, and than into the runInTread. 
  
  // this use the `call by name`, Omit the (),
  def runInThread2(block: => Unit) {
    new Thread {
      override def run() { block }
    }.start()
  }
  def oneMethod2 = {println("Hi2"); Thread.sleep(1000); println("Bye2")}

  runInThread2 { oneMethod2 } // `call by name`, call by the method name, it will not run until in the runInTrhead2 method 
  runInThread2 { oneMethod } 


//  Call by name  ： 传给函数/方法M的参数是另外一个参数函数，该参数函数在函数体内调用时执行
//  call by value ： 传给函数的参数是个值，如果是个表达式或者是另外一个参数函数，则要先计算出表达式的值或者是要先得到参数函数执行后的返回值
}


//《Programming in Scala, 2nd Edition》在 9.5 By-name parameters 一节出给出一个例子解释
object bynameparameters extends App {
  var assertionsEnabled = false
  //Call by name
  def byNameAssert(predicate: => Boolean) =
    if (assertionsEnabled && !predicate)
      throw new AssertionError

  //call by value 
  def boolAssert(predicate: Boolean) =
    if (assertionsEnabled && !predicate)
      throw new AssertionError
//  boolAssert(3 /0 == 0) // java.lang.ArithmeticException: / by zero
  byNameAssert(3 / 0 == 0) //will not yield an exception:
}

