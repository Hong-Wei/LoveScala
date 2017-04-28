package chenChaoVideo.api

/**
  * 1、Scala基础与实践:Scala基础与实践.pdf 陈超 @CrazyJvm
  */

import myImpatient.Chapter1

object Basic {
  //CM 1 define the method, with the default input value
  def hello(name: String = "Scala"): String = {
    "Hello:" + name
  }

  //CM 2 Define the method, without the parameters. the pathnesses can be ommited.
  //def helloScala{
  def helloScala() {
    printf("hello Scala!")
  }

  //CM 3 define the method, with two paramters.
  def add = (x: Int, y: Int) => x + y;

  //CM 4 you can use the curring to define the method.
  def add2(x: Int)(y: Int) = x + y;

  def add3(x: Int)(y: Int)(z: Int) = x + y + z;

  //CM 5 used the multiply input *, mean many input string values.
  // and the * must be in the last one.
  def printEveryChar(a: Int, c: String*) {
    c.foreach(x => println(x))
  }


  def main(args: Array[String]): Unit = {
    println(hello("hongwei"))
    println(hello())
    println(helloScala())
    println(helloScala) //No paramters in the input, the partheness can be omitted

    println(add(1, 2))
    println(add2(1)(2))
    println(add3(1)(2)(3))
    printEveryChar(5, "abc", "bac", "def")

    val x = 1
    val a = if (x > 0) 1 else 0

    //CM 6 define multiply variables at the same time
    var (n, r, stringValue) = (10, 0, "Sting")
    while (n > 0) {
      r = r + n
      n = n - 1
    }
    println(r)

    // 1 to 10 == 1.to(10)
    for (i <- 1 to 10) {
      print(i)
    }
    println()

    for (i <- 1 until (10)) {
      print(i)
    }
    //CM 7 use the filter in for expression
    for (i <- 1 to 10 if i % 2 == 0) {
      print(i + " ")
    }
    val p = new Person
    //    p.name = "jack"
    println(p.name + ":" + p.age)

    //    val p2 = new Person2("Jack", 2)
    //    println(":" + p2.age)
    //    val p3 = new Person2("Jack", 3, "male")
    //    println(": " + p3.age + p3.gender)
    //
    //    val s = new Student("hongwei", 20, "math")
    //    val s1 = new Student("hongwei", 20, "math", "male")
    //    println(s1.toString())
    //    val p1 = new Person1("hongwei", 12)
    //    p1.age
    //    p1.name

    case class Person1(name: String, age: Int) {}

  }
}

import scala.math._

//CM 8 define the class
class Person {
  //CM 8.1 define with var, it will create the get and set method, and  _: place holder, initialise the name to null.
  var name: String = _
  //CM 8.2 define with val only getter
  val age = pow(10, 2)
  
  private[this] val gender = "male"

  // only inside the class, not outside
  def main(args: Array[String]): Unit = {
    val age = sqrt(2)
    val age1 = min(10, 100)
  }
}

//1 主构造器直接跟在类定义里面,
//2 主构造器执行时,会调用所有方法
//3 假设声明时,没有val 和var,相当于:private[this],外部不能访问
class Person2(name: String, val age: Int) {
  println("this is primary constructor :" + name)

  var gender: String = _
  var school = "xiaoxue"

  //1 负数构造器为this
  //2 必须先调用主构造器
  def this(name: String, age: Int, gender: String) {
    this(name, age)
    this.gender = gender
  }
}

class Student(name: String, age: Int, val major: String) extends Person2(name, age) {
  println("this is subclass" + major)

  def this(name: String, age: Int, major: String, gender: String) {
    this(name, age, major)
    println("this is subclass" + gender)
    //    this.gender=gender
  }

  override def toString = "Override toString..."

  var school1 = "xiaoxue"
}















