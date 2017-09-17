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
object Closures6 extends App {
  def mulBy(factor: Double) = (x: Double) => factor * x

  // each of the returned functions has its own setting for factor : Closures consists of code together with variables
  val triple = mulBy(3)
  // implemented as objects of a class ,with an instance variable factor and apply method
  val half = mulBy(0.5)
  // can access nonlocal variables: each functions has its own setting for factor
  println(triple(14) + " " + half(14))
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
//use currying for a function parameter so that the type inferencer has more information.
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
  //  print(corresponds)
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

  indexOf("Hello", 'l')

  indexOf("Hello", '!')

  val a=5

}












