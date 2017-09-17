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
    //CM 8 Class - examples.
    val p = new Person
    // name is var so it can call the set method.
    p.name = "jack"
    // the age is val, so there only get method there.
    // p.age = 10
    println(p.name + ":" + p.age)

    val p2 = new Person2("Jack", 2)
    println(":" + p2.age)
    val p3 = new Person2("Jack", 3, "male")
    println(": " + p3.age + p3.gender)

    val s = new Student("hongwei", 20, "math")
    val s1 = new Student("hongwei", 20, "math", "male")
    println(s1.toString())
    val p1 = new Person1("hongwei", 12)
    p1.age
    p1.name

    //CM 8.6 Trait-examples
    val trait1 = new MyAccout
    trait1.save

    // the object can have specific trait, not only the class
    val trait2 = new MyAccout with MessageLogger
    trait2.save

    //CM 8.6 apply-examples
    ApplyTest.static
    import ApplyTest._
    static
    val applyT1 = ApplyTest
    applyT1.static
    val applyT2 = ApplyTest()
    applyT2.test
    val applyT3 = new ApplyTest()
    applyT3.test
    applyT3.apply()

    //CM 8.6 单例对象证明,call the method many times and check the varible in the object.
    for (i <- 1 to 10) ApplyTest.incr
    println(ApplyTest.count)


    //CM 10 模式匹配-example, 不用 break,一旦匹配立即执行,然后终止.
    val mathingValue = Book("hongwei2","hongwei111")
    val result = mathingValue match {
//      case 1 => "this is normal Int"
//      case string: String => "String Type matching"
//      case day01: Long => " Long Type matching"
//      case int: Int if int > 10 => " Int Type matching and with filtering "
      case Book("hongwei",author) => println(author)
      case Book(name,author) => println(name+ author)
    }
    println("the result is : " + result)

    //CM 10 高阶函数-example - useful collectors
    val list10 = List(1,2,3,4,5,6,7,8,9,10)
    val newList1= list10.map((x:Int) => 2* x)
    val newList2= list10.map((x) => 2* x)
    val newList3= list10.map(x => 2* x)
    val newList4= list10.map(2 * _)
    val set10 = Set(1,2,3,4,5,6,7,8,9,10)
    val stringToIntMap: Map[String, Int] = Map("hongwei" -> 23)
    val tuple: (String, Int, Int) = ("hongwei", 23, 1099)
    //CM 10 高阶函数-example - foreach - no reponse -> Unit
    list10.foreach(print(_))
    
    //CM 10 高阶函数-example - filter
    println(list10.filter(x => x%2 ==0))
    println(list10.filter( _ % 2 ==0))

    //CM 10 高阶函数-example - zip
    println(list10 zip list10)

    //CM 10 高阶函数-example - partition, devide one to two list....
    println(list10.partition(_%2 ==0))

    //CM 10 高阶函数-example - flatten, flat two list into flat one
    println(List(List(1,2), List(2,3,4)).flatten)


    //CM 10 高阶函数-example - flatten, flat two list into flat one
    println(List(List(1,2), List(2,3,4)).flatMap(x =>x.map(_ * 2)))


    //CM 13 隐式转化
    implicit def a2RichA(a:A) = new RichA(a)
    val implictA = new A
    implictA.rich

    //CM 13 隐式参数, if you do not input,it has default value
    def testParam(implicit name : String): Unit = {
      println(name)
    }
    implicit val  name = "Hongwei! come on, this from implicit parameter  "
    testParam
    testParam("xxx")

    //CM 13 隐式类. add is not the memeber for Int, but it will check the implicit class to map the correct pattern
    implicit class Calculator(x : Int){
      def add(a : Int) :Int = a +1
    }
    // you need import xx.xx to make sure you have the function
    println(1.add(1))
    Option
    
    println(A.name)
    println(new A().name)
    
    
  }

  case class Person1(name: String, age: Int) {}

}

import scala.math._

//CM 8 Class
class Person {
  //CM 8.1 fields-1 define with var, it will create the get and set method, and  _: place holder, initialise the name to null.
  var name: String = _
  //CM 8.1 fields-2 define with val only getter
  val age = pow(10, 2)
  //CM 8.1 fields-3 define wtih private[This], the gender is only can be used inside the class.
  private[this] var gender = "male"

  // only inside the class, not outside
  def main(args: Array[String]): Unit = {
    val age = sqrt(2)
    val age1 = min(10, 100)
    gender = "Body"
  }
}

//CM 8.2 Constructor 
//CM 8.2 Constructor 1 主构造器直接跟在类定义里面, finial the paramters will be compiled into fields for the class.
//CM 8.2 Constructor 3 假设声明时,没有val 和var,相当于:private[this],外部不能访问
class Person2(name: String, val age: Int) {
  // CM 8.2 Constructor 2 主构造器执行时,会调用所有方法.
  //The following method will be called when create the instance!!
  println("This is primary constructor :" + name)

  var gender: String = _
  val school = "school"

  //CM 8.2 Constructor 3 附属造器, the name must be this and 必须显示先调用主构造器或者别的附属
  def this(name: String, age: Int, gender: String) {
    // 第一行,必须先调用主构造器
    this(name, age)
    this.gender = gender
  }
}

//CM 8.3 Inheritance
class Student(name: String, age: Int, major: String) extends Person2(name, age) {
  println("this is subclass" + major)

  def this(name: String, age: Int, major: String, gender: String) {
    this(name, age, major)
    println("this is subclass" + gender)
    this.gender = gender
  }

  //CM 8.4 Override -field
  override val school = "University"

  //CM 8.4 Override -method
  override def toString = "Override toString..."
}


//CM 8.5 Abstract Class
/** 1 类的一个或多个方法没有完整定义
  * 2 声明抽象方法不需要加 abstract 关键字,只需要不写方法体
  * 3 子类复习父类抽象方法时,不需要override 方法
  * 4 父类可以声明抽象字段,不用初始化都可以
  * 5 子类复写父类字段时不用家 override
  * 
  */
abstract class simpleAbstractClass(name: String) {}

//CM 8.6 Trait - 带有具体实现方法的 interface, with 关键字混入, 从左到右被构造,

trait Logger {
  def log(msg: String): Unit = {
    println("log" + msg)
  }
}

trait ConcoleLogger {
  def log(msg: String): Unit = {
    println("save money : " + msg)
  }
}

trait MessageLogger extends ConcoleLogger {
  override def log(msg: String): Unit = {
    println("save money to bank: " + msg)
  }
}

abstract class Account {
  def save
}

class MyAccout extends Account with ConcoleLogger {
  def save: Unit = {
    log("100")
  }
}

//CM 8.7 apply and 单例对象. 类名() --> object里面的 apply方法, 对象()->class 里面的apply方法.
class ApplyTest {
  def apply() = println("apply in class")

  def test: Unit = {
    println("test")
  }
}

//放在object 里面的方法,可以看成 class 的静态方法, scala 没有 static 关键字,但是伴生对象里面的方法都是, static 的
object ApplyTest {
  //99% apply ,used for initialised the class
  def apply(): ApplyTest = new ApplyTest()

  def static: Unit = {
    println("I am a static method!!! ")
  }

  var count = 0

  def incr = {
    count = count + 1
  }
}

//CM 9 Package-1 可串联
/**
  * package com.tesoble{
  * // 这里的代码访问不了 spark 你们的东西.
  * package spark{
  * // 可以访问上层的东西
  * }
  * //CM 9 Package-2 顶部标记
  * package com.tesosbe
  * // 这里的代码访问不了 spark 你们的东西.
  * package spark
  *
  * //CM 9 Package-3 包可以再任何地方引入,
  * //CM 9 Package-4 包可以重命名 和类的隐藏
  * import com.tesobe.{HashMap => JavaHashMap} --重命名
  * import com.tesobe.{HashMap => _} --HashMap隐藏了.
  */


//CM 10 模式匹配 
// 1 可以加 guard: 可以用 if....
// 2 可以匹配类型: Int, String...

//CM 11 Case Class 样本类, 90% 用在了模式匹配,因为构造器中每个类型都是val(不建议 var), 一旦构造好就不改变了.不用 new 就可以.
case class Book(name : String, author : String)

//CM 12 高阶函数 -函数式编程
// 1 匿名函数: val double = (x: Int) => 2*x
// 2 函数作为参数
// 3 参数类型推断
// 4 常用高阶函数: map, filter ,reduce.....

//CM 13 隐式转换, 隐式函数, 隐式类
//1 位于源目标类型的伴生对象中的隐私函数
//2 位于当前域可以单个标识符自带的隐私函数


class  A {
  val name : String = "class"
}
class RichA(a: A){
  def rich: Unit ={
    println("rich")
  }
}


object A{
  val name: String = "object"
}








































