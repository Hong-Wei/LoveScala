package myImpatient

import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.JButton

import scala.collection.immutable.IndexedSeq
import scala.math.{ceil, sqrt}

//BK 12.4 Parameter Inference
object ParameterInference extends App {
  def valueAtOneQuarter(f: (Double) => Double) = f(0.25)

  //eg1:
  valueAtOneQuarter((x: Double) => 3 * x) // you can write type, but Scala inference it
  valueAtOneQuarter((x) => 3 * x) // no type
  valueAtOneQuarter(x => 3 * x) // not parentheses
  valueAtOneQuarter(3 * _)
  // one parameter on the right-hand side , can omit ()-- special bonus

  //eg2: use cases
  //  val fun1 = 3 * _ // Error: Can’t infer types
  val fun2 = 3 * (_: Double)
  // OK
  val fun3: (Double) => Double = 3 * _ // OK
}

//BK 12.5 Useful Higher-Order Functions
object UsefulHighOrderFunctions extends App {
  //1 map
  //principle : if you want a sequence of values ,see if you can transform if from a simpler one !!!
  private val doubles: IndexedSeq[Double] = (1 to 9) map (0.1 * _)

  //2 foreach
  //foreach simply apply the fucntion to each argument // map return a value
  private val Unit1: Unit = (1 to 9) map ("*" * _) foreach (println _)

  //3 filter
  // filter : yields all elements that match a particular condition
  private val ints: IndexedSeq[Int] = (1 to 9) filter (_ % 2 == 0)

  //4 reduceLeft: a binary function
  private val i: Int = (1 to 9) reduceLeft (_ * _) //1*2*3*4....*9

  //5 sortWith :a binary function
  private val sortWith: Array[String] = "Mary has a little lamb".split(" ").sortWith(_.length < _.length)
}

//BK 12.6 Closures
//“An object is data with functions. 
// A closure is a function with data.” — John D. Cook
//已知一个函数  f( x ) = x + i  ，让你求  f（3） = 3+i。
//分析：要得到最终的函数值，你必须知道i的值。i称作开放项（“开”着的，对应闭包的“闭”），若上文中定义了“ inti = 1”，
//则可以得到f（3） = 3+1 =4, 即函数值若想被创建必须捕获i的值，这一过程可以被理解为做对函数执行“关闭”操作，所以叫闭包。
//
//链接：https://www.zhihu.com/question/28849447/answer/42339956

//闭包出现是因为lexical scope，闭包是由函数和环境组成，Scala应该支持函数作为参数或返回值，这时如果没有闭包，那么函数的free 变量就会出错


//https://zhuanlan.zhihu.com/p/21346046
//Closures are functions that refer to independent (free) variables (variables that are used locally, 
// but defined in an enclosing scope). In other words, 
// the function defined in the closure 'remembers' the environment in which it was created.

object Closures6 extends App {
  def mulBy(factor: Double) = (x: Double) => factor * x

  // each of the returned functions has its own setting for factor : Closures consists of code together with variables
  val triple = mulBy(3)
  //3*x
  // implemented as objects of a class ,with an instance variable factor and apply method
  val half = mulBy(0.5) //0.5*x
  // can access nonlocal variables: each functions has its own setting for factor
  // when call triple(14)--> in mulBy factory = 3, this is only inside the method, but here we store the data for this function.
  // when call half(14)--> in mulBy factory = 0.5, this is only inside the method, but here we store the data for this function.
  println(triple(14) + " " + half(14))

  // Such a function is called a closure. 
  // A closure consists of code together with the definitions of any nonlocal variables that the code uses.
  //These functions are actually implemented as objects of a class, with an instance variable factor and an apply method that contains the body of the function
  // val triple = mulBy(3) --> 
  //  object triple {
  //    val factor = 3
  //    apply(a: Int)={
  //      3*a
  //    }
  //  }
  // The function can save some data 
}

//BK 12.7 SAM Conversions
object SAMConversion7 extends App {
  // interfaces have a single abstract method-SAM in java
  //eg1: Java-SAM
  var counter = 0
  val button = new JButton("Increment")

  // java way
  button.addActionListener(new ActionListener {
    override def actionPerformed(event: ActionEvent): Unit = {
      counter += 1
    }
  })

  // add implicit : add a function parameter : to an actionListenner instance!
  implicit def makeAction(action: (ActionEvent) => Unit) = new ActionListener {
    override def actionPerformed(event: ActionEvent) {
      action(event)
    }
  }

  //scala simply it :you can pass any (ActionEvent) => Unit function where an ActionListener object is expected
  button.addActionListener((event: ActionEvent) => counter += 1)

}

//bk 12.8 Currying
//use currying for a function parameter so that the type inference has more information.
object Curring8 extends App {
  //eg1: process -> turning a function two arguments into a function takes one argument
  def mul(x: Int, y: Int) = x * y

  def mulOneAtTime(x: Int) = (y: Int) => x * y

  //--> shortcut
  def mulOneAtTimeShort(x: Int)(y: Int) = x * y

  mul(6, 7)
  mulOneAtTime(6)(7)


  //eg2:
  val a = Array("Hello", "World")
  val b = Array("hello", "world")
  private val corresponds: Boolean = a.corresponds(b)((s: String, s1: String) => s.equalsIgnoreCase(s1))
  // because you can get the String Type from `a.corresponds(b)`--> 
  // So there is no need to write the up sentences. 
  a.corresponds(b)(_.equalsIgnoreCase(_))
  
}

//BK 12.9 Control Abstractions
// model a sequence of statements as function with no parameters or return value.
object ControlAbstraction9 extends App{
  //eg1: run some code in thread
  // function type: () => Unit
  def runInThread(block: () => Unit) {
    new Thread {
      override def run() { block() }
    }.start()
  }
  //when you use ,you must supply :() =>
  runInThread { () => println("Hi") ; Thread.sleep(100); println("Bye") }

  //eg2: call by name notation :Omit ().
  def runInThread1(block: => Unit) {
    new Thread {
      override def run() { block }
    }.start()
  }
  runInThread1 { println("Hi") ; Thread.sleep(1000); println("Bye") }

  //eg3: while ,but inverted condition
  def until(condition: => Boolean)(block: => Unit) {
    if (!condition) {
      block
      until(condition)(block)
    }
  }

  var x = 10
  until (x == 0) {
    x -= 1
    println(x)
  }

  Thread.sleep(10000)
}


//BK 12.10 The return Expression
object ReturnExpresseion10 extends App{

  def until(condition: => Boolean)(block: => Unit) {
    if (!condition) {
      block
      until(condition)(block)
    }
  }

  def indexOf1(str: String, ch: Char): Int = {
    var i = 0
    until (i == str.length) {
      //block: => Unit ,but it has return , this will throw an exception!!!
      if (str(i) == ch) return i ;i += 1
    }
    -1
  }

  indexOf1("Hello", 'l')

  indexOf1("Hello", '!')

  def indexOf(str: String, ch: Char): Int = {
    var i = 0
    try {
      until (i == str.length) {
        if (str(i) == ch) return i
        i += 1
      }
    } catch {
      case t: Throwable => println(t)
    }
    -1
  }

  println(indexOf("Hello", 'l'))

  println(indexOf("Hello", '!'))

  val a=5

}












