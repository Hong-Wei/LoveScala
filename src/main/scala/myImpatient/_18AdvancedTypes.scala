package myImpatient

import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JComponent

import scala.collection.mutable.{ArrayBuffer, HashMap}
import scala.io.Source

/**
  * Created by zhanghongwei on 15/11/16.
  */

//bk 18. Advanced Types
object _181SingletonTypes extends App {

  val article: Document = new Document
  article.setTitle("Love you ").setAuthor("Hongwei")

  //  v.type v null
  // eg1: return this, can connect together
  class Document {
    def setTitle(title: String): this.type = {
      print("Set Title : " + title);
      this
    }

    def setAuthor(author: String): this.type = {
      print("Set Author :" + author);
      this
    }
  }

  //eg2: if you have subclass ,the problem
  class Book extends Document {
    def addChapter(chapter: String) = {
      print("add chapter =" + chapter);
      this
    }
  }

  val book = new Book()
  book.addChapter("add ").setTitle("Scala for the Impatient") // right
  book.setTitle("Scala for the Impatient").addChapter("add ") // Wrong !!!
  //solution: use this.type. it return the book.type


  //eg3:accept boject instance as parameter, you also can use singleton type


}

object _182TypeProjections extends App{

  import scala.collection.mutable.ArrayBuffer

  class Network {

    class Member(val name: String) {
      val contacts = new ArrayBuffer[Network#Member] //a type projection:  “a Member of any Network.
    }

    private val members = new ArrayBuffer[Member]

    def join(name: String) = {
      val m = new Member(name)
      members += m
      m
    }
  }

  val chatter = new Network
  val myFace = new Network

  val fred = chatter.join("Fred")
  val wilma = chatter.join("Wilma")
  fred.contacts += wilma // OK
  val barney = myFace.join("Barney")
  fred.contacts += barney // Also OK, here use `Network#Member` 

  println("chatter=" + chatter)
  println("myFace=" + myFace)

}

object _183Paths3 {

  //Following is from WangJialin.-->
  class Outer {
    private val x = 10

    class Inner {
      private val y = x + 10
    }

  }

  val outer = new Outer
  val inner = new outer.Inner
  val inner2: outer.Inner = new outer.Inner

  val o1 = new Outer
  val o2 = new Outer
  //Outer#Inner is the Type Projection,
  //In scala, 内部类一般由外部类的实例产生的. 可是也可以通过 projection, 它是所有内部类的父类.
  val i: Outer#Inner = new o1.Inner



  //  com.hotstmann.impatient.chatter.Member
  //  com.hotstmann.impatient.Network.Member : accoumpany object
  //Such an expression is called a path.
  //package ,object ,val ,this,
}

object _184TypeAliases extends App{
  //eg1: You can create a simple alias for a complicated type with the `type` keyword,
  class Document {
    import scala.collection.mutable._
    type Index = HashMap[String, (Int, Int)]
  }

  class Book extends Document {
    val tables = new Index //Same as new HashMap[String, (Int, Int)], just have a simple way!!
    val tables2 = new HashMap[String, (Int, Int)] //Same as new HashMap[String, (Int, Int)]
    val figures = new Index

    def addTableRef(title: String, chapter: Int, section: Int) {
      tables += title -> (chapter, section)
    }

    def addFigureRef(caption: String, chapter: Int, section: Int) {
      figures += caption -> (chapter, section)
    }
  }

  // you can use Book.Index == scala.collection.mutable.HashMap[String ,(Int,Int)]

  //eg2: The type keyword is also used for abstract types that are made concrete in a subclass
  abstract class Reader {
    type Contents

    def read(fileName: String): Contents
  }

}

//bk 18.5 Structural Types
// A “structural type” is a specification of abstract methods, fields, and types that a conforming type should possess.
object _185StructuralTypes extends App {
  //For example, this method has a structural type parameter
  //You can call the `appendLines` method with an instance of any class that has an `append` method
  //This is more flexible than defining a Appendable trait, because you might not always be able to add that trait to the classes you are using.
  def appendLines(target: {def append(str: String): Any}, lines: Iterable[String]) {
    for (l <- lines) {
      target.append(l); 
      target.append("\n")
    }
  }

  val lines = Array("Mary", "had", "a", "little", "lamb")

  //Because StringBuilder has the method `append`, so it can be the `target` parameter.
  val builder: StringBuilder = new StringBuilder
  appendLines(builder, lines)
  println(builder)

  import javax.swing._

  val area = new JTextArea(20, 20)
  appendLines(area, lines)

  val frame = new JFrame
  frame.add(area)
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  frame.pack()
  frame.setVisible(true)

}

// form: T1 with T2 with T3
// T1 ,T2 ,T3 is type, if want to be instance , it need to meet all the requirements.
// IT is an intersection type.
object _186CompoundTypes {
  //eg1
  val image = new ArrayBuffer[java.awt.Shape with java.io.Serializable]

  val rect = new Rectangle(5, 10, 20, 30)
  image += rect // OK == Rectangle is both shape nad serialibable
  //  image += new Area(rect) // Error --> Area is Shape ,but not serializable

  //eg2 mix structural Type and compound type
  //  class myShape with Serializable {def contains (p:Point):Boolean}
  //  it both the sub class of Serializable and Shape , and contains one method having Point parameter


  //eg3:
  //  {def append(str:String):Any}
  //  is short for
  //  AnyRef { def append(str :String):Any}

  //  Shape with Serializable
  //  is short for
  //  Shape with Serializable{}


}

//An infix type is a type with two type parameters, written in “infix” syntax,
//with the type name between the type parameters.
object _187InfixTypes {
  //eg1: Map[String, Int] === String Map Int, put the Map between two parameters
  val scores: String Map Int = Map("Fred" -> 42)
  val scores2: Map[String, Int] = Map("Fred" -> 42)

  //eg2: You can define your own `infix` type
  type ×[A, B] = (A, B)

  val pair: String × Int = ("Fred", 42)
  val pair2: (String, Int) = ("Fred", 42)

  val triple: String × Int × Int = (("Fred", 42), 1729)
  val triple2: ((String, Int), Int) = (("Fred", 42), 1729)

}

object _188ExistentialTypes extends App{
  //Existential types were added to Scala for compatibility with Java wildcards.
  //An existential type is a type expression followed by forSome { ... },
  //where the braces contain type and val declarations.
  //new Array[T] forSome {type T <:JComponent}
  // ==
  //new Array[_ <: JComponent](1)

  //eg2 scala wildcards are  syntactic sugar
  //  Array[_] == Array[T] forSome {type T}
  // Map[_,_] == Map[T,U] forSome{type T; type U}


  import scala.collection.mutable. ArrayBuffer

  class Network {
    class Member(val name: String) {
      val contacts = new ArrayBuffer[Network#Member]
    }

    private val members = new ArrayBuffer[Member]

    def join(name: String) = {
      val m = new Member(name)
      members += m
      m
    }
  }
    def process[M <: n.Member forSome { val n: Network }](m1: M, m2: M) = (m1, m2)

    val chatter: Network = new Network
    val myFace: Network = new Network
    val fred: _root_.myImpatient._188ExistentialTypes.chatter.Member = chatter.join("Fred")
    val wilma: _root_.myImpatient._188ExistentialTypes.chatter.Member = chatter.join("Wilma")
    val barney: _root_.myImpatient._188ExistentialTypes.myFace.Member = myFace.join("Barney")
    process(fred, wilma) // Ok
//    process(fred, barney) // Error

}

// 10 types
// 1 Class or Trait
// 2 Tuple type
// 3 Function Type
// 4 Annotated Type
// 5 Parametrised Type
// 6 Singleton
// 7 Type Projection 
// 8 Compound Type
// 9 Infix Type
// 10 Existential Type
object _189TypeSystem extends App {
  // A method type is denoted by (T 1 , ..., T n )T without a =>.
  def square(x: Int): Int = x * x

  //Turned into a function
  //You can turn a method into a function by appending a _.
  def methodToFunction: (Int) => Int = square _

  // A function
  val functionDirectly: (Int) => Int = (x: Int) => x * x

}

//how a trait can require that it is mixed into a class that extends another type
//this: Type
object _1810SelfTypes extends App{

  //eg1 : LoggedException can only mixed a class that extends Exception
  trait Logged {
    def log(msg: String)
  }

  trait LoggerException extends Logged {
//    The class LoggerException extends Logged { self => syntax makes the variable outer refer to Network.this. You can choose any name for this variable. The name self is common, but perhaps confusing when used with  nested classes.
    // this, self, outer, you can have any name for it, 
    self: Exception =>
    def log() {
      log(getMessage) // OK to call getMessage because this is an Exception
    }
  }

  // Error: JFrame isn't a subtype of Exception,
  // the self type of LoggedException
  // val f = new JFrame with LoggerException

  //eg2 : require multiple types, use a compound type
//  this: T with U with ... =>
  
  
  //eg3:  Self type doesn't inherit automatically 
//  trait ManagedExceptionWrong extends LoggerException { // Error
//  // Self type doesn't inherit
//    def print() { println(getMessage()) }
//  }

  trait ManagedExceptionRight extends LoggerException {
    this: Exception => // Must repeat self type
    def print() { println(getMessage()) }
  }


}

object _1811DependencyInjection extends App{
//Java has libraries for `Dependency Injection`
//Scala In Scala, you can achieve a simple form of dependency injection with traits and self types.

  import java.io._

  trait LoggerComponent {
    trait Logger { def log(msg: String) }

    val logger: Logger

    class ConsoleLogger extends Logger {
      def log(msg: String) { println(msg); }
    }

    class FileLogger(file: String) extends Logger {
      val out = new PrintWriter(file)
      def log(msg: String) { out.println(msg); out.flush() }
    }
  }

  trait AuthComponent {
    this: LoggerComponent => // Gives access to logger    

    trait Auth {
      def login(id: String, password: String) = {
        if (check(id, password)) true
        else {
          logger.log("login failure for " + id)
          false
        }
      }
      def check(id: String, password: String): Boolean
    }

    val auth: Auth

    class MockAuth(file: String) extends Auth {
      val props = new java.util.Properties()
      props.load(new FileReader(file))
      def check(id: String, password: String) = props.getProperty(id) == password
    }
  }

  //Now the component configuration can happen in one central place:
  object AppComponents extends LoggerComponent with AuthComponent {
    val logger = new FileLogger("test.log")
    val auth = new MockAuth("/Users/zhanghongwei/Documents/workspaceScala/LoveScala/src/main/scala/myImpatient/users.txt")
  }

    import AppComponents._
    if (auth.login("cay", "secret"))
      logger.log("cay is logged in")
    println("Look inside test.log")
}

object _1812AbstractTypes {

  //  A class or trait can define an abstract type that is made concrete in a subclass.
  //eg1:
  trait Reader {
    type Contents

    // is abstract
    def read(fileName: String): Contents
  }

  class StringReader extends Reader {
    type Contents = String

    def read(fileName: String) = Source.fromFile(fileName, "UTF-8").mkString
  }

  class ImageReader extends Reader {
    type Contents = BufferedImage

    // is abstract
    def read(fileName: String): BufferedImage = ImageIO.read(new File(fileName))
  }

  //eg2 : same logic using Type parameter:

//  trait Reader[C] {
//
//    def read(fileName: String): C
//  }
//
//  class StringReader extends Reader[String] {
//
//    def read(fileName: String) = Source.fromFile(fileName, "UTF-8").mkString
//  }
//
//  class ImageReader extends Reader[BufferedImage] {
//
//    def read(fileName: String) = ImageIO.read(new File(fileName))
//  }

}

object _1813FamilyPolymorphism {

}

object _1814HigherKindedTypes {

}
