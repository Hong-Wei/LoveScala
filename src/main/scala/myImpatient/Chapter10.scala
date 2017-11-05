package myImpatient.chapter10

import java.io.{IOException, PrintStream, PrintWriter}

/**
  * Created by zhanghongwei on 13/11/16.
  */
class Chapter10 {
//BK 10.1 Why No Multiple Inheritance?
  
}

object Chapter10 extends App {

  new UnhappyException
  //  val f = new JFrame with LoggedException13
  val acct = new SavingsAccount with ConsoleLogger
  acct.withdraw(100.0)
  val acct0 = new SavingsAccount with FileLogger

  trait FileLogger

  val acct1 = new SavingsAccount with ConsoleLogger with TimestampLogger with ShortLogger
  acct1.withdraw(10)
  val acct2 = new SavingsAccount with ConsoleLogger with ShortLogger with TimestampLogger
  acct2.withdraw(122)

  val acct3 = new SavingsAccount with FileLogger {
    val filename = "map.log"
  }

}

//BK 10.2 Traits as Interfaces -only have the abstract methods
trait Logger {
   def log(msg: String){}

  def info(msg: String) {
    log("INFO +" + msg)
  }

  def warn(msg: String) {
    log("warn +" + msg)
  }

  def severe(msg: String) {
    log("severe +" + msg)
  } 
}

//BK 10.3 Traits with Concrete Implementations
trait ConsoleLogger extends Logger with Cloneable with Serializable{
  override def log(msg: String) {
    println(msg)
  }
}

class Account {
  protected var balance = 0.0
}

class SavingsAccount extends Account with Logger {
  def withdraw(amount: Double): Unit = {
    if (amount > balance) log("Insufficient funds")
    else balance -= amount

  }
}

//BK 10.4 Objects with Traits
trait ConsoleLogger2 extends Logger with Cloneable with Serializable {
  override def log(msg: String) {
    println(msg)
  }
}

class SavingsAccount2 extends Account {
  def withdraw(amount: Double): Unit = {
    if (amount > balance) 1
    else balance -= amount

  }
}

object objectWithTraite extends App {
  val acct = new SavingsAccount with ConsoleLogger // Because SavingsAccount default Logger , have no implements, 
                                                   // so we mixed in `ConsleLogger`, have the concrete implement.
  val acct2 = new SavingsAccount2 with ConsoleLogger2
}


//BK 10.5 Layered Traits
trait TimestampLogger extends Logger {
  override def log(msg: String) {
    super.log(new java.util.Date() + " " + msg)
  }
}

trait ShortLogger extends Logger {
  val maxLength = 15
  override def log(msg: String): Unit = {
    if (msg.length <= maxLength) println(msg)
    else println(msg.substring(0, maxLength - 3) + "...")
  }
}

// Layer Traits -- mixed many traits :
// 1 construct from left --> right
// 2 call method from right --> left
// 3 when call super.log in trait, it is not the same as class. it depends on the order of traits.
object layeredTraits extends App {
  val acct1 = new SavingsAccount with ConsoleLogger with TimestampLogger with ShortLogger 
  val acct2 = new SavingsAccount with ConsoleLogger with ShortLogger with TimestampLogger
  acct1.log("hongwei1")
  acct2.log("hongwei2")
}

//BK 10.6 Overriding Abstract Methods in Traits
trait Logger6 { def log(msg: String)}

trait TimestampLogger6 extends Logger {
  //If you override the abstract method, you also need add `abstract` before the method. It is still abstract 
  abstract override def log(msg: String) {
    super.log(new java.util.Date() + " " + msg)
  }
}

//Bk 10.7 Traits for Rich Interfaces
// see trait Logger, we add three concrete method in the trait. 
// So we have 1 abstract + 3 concrete methods there.

// in the class, you can call abstract and implement your own. nad you can call the concrete one directly.
class SavingsAccount107 extends Account with Logger {
  def withdraw(amount: Double) = severe("Insufficient funds")

  override def log(msg: String) {
    println(msg);
  }

} 

//BK 10.8 Concrete Fields in Traits
// The field in the trait with value, mean the concrete fields. 
trait ShortLogger108 extends Logger { 
  val maxLength = 15 // A concrete field ...
}
//You can think of concrete trait fields as “assembly instructions” for the classes that use the trait. 
//Any such fields become fields of the class.
// Because of JVM only support one super class. So you can not get the filed from trait. 
// The concrete fields will be the normal fields in the subclasses.


//BK 10.9 Abstract Fields in Traits
// The field in the trait without value, mean the abstract fields. 
// The sub class must override the field and set value there.
trait ShortLogger109 extends Logger {
  val maxLength :Int
  val a = println(maxLength)
  override def log(msg: String){ println(msg + maxLength)}
}

class Some {
//  override val maxLength: Int = 10
}

object Try109 extends App{
//  println(new Some().maxLength)

  val acct = new Some with ShortLogger109 {override val maxLength = 20 }

  acct.log("hongewi")
}


//BK 10.10 Trait Construction Order
//Constructors execute in the following order:
  //1 The superclass constructor is called first.
  //2 Trait constructors are executed after the superclass constructor but before the class constructor.
  //3 Traits are constructed left-to-right.
  //4 Within each trait, the parents get constructed first.
  //5 If multiple traits share a common parent, and that parent has already been constructed, it is not constructed again.
  //6 After all traits are constructed, the subclass is constructed.
trait FileLogger extends Logger{
  val out = new PrintWriter("App.log")
  out.println("#" + new java.util.Date().toString)
  override def log(msg: String): Unit = {
    out.print(msg)
    out.flush()
  }
}

class SavingsAccount10 extends Account with FileLogger with ShortLogger
//1 Account - 2Logger - 3FileLogger - 4ShortLogger - 5SavingsAccount10

//This order decide the super method call sequence. 
//calling super in a ShortLogger invokes the FileLogger method
//calling super in a FileLogger invokes the Logger method.

//The constructor ordering is the reverse of the linearization of the class.
//lin(SavingsAccount) = SavingsAccount » lin(ShortLogger)       » lin(FileLogger)       » lin(Account)
//                    = SavingsAccount » (ShortLogger » Logger) » (FileLogger » Logger) » lin(Account) 
//                    = SavingsAccount » ShortLogger » FileLogger » Logger » Account.



//BK 10.11 Initializing Trait Fields
//The absence of constructor parameters is the only technical difference between traits and classes. 
//Otherwise, traits can have all the features of classes, such as concrete and abstract fields and superclasses.
// limitation--> following is not possible, you can not mix a trait with parameters.
//val acct = new SavingsAccount with FileLogger("myapp.log") // Error: Can't have constructor parameters for traits

//right way: you can have the abstract field `filename` in the trait.  
trait FileLogger2 extends Logger { 
  val filename: String 
  val out = new PrintStream(filename)
  override def log(msg: String) { out.println(msg); out.flush()}
}
object try11 extends App {
//  val acct = new SavingsAccount with FileLogger2 { val filename = "myapp.log" } 
  //  up sentence does not work --> Exception in thread "main" java.lang.NullPointerException, in contructing FileLogger2.out
  //  because of the contract sequence.
  //  even though, filename defined in up sentence, but FileLogger2 construct first.
  //  when construct FileLogger2, there is no value for filename, it is null. 
  // 

  // Early definition block after new
  val acct = new {  val filename = "myapp.log" } with SavingsAccount with FileLogger2
  acct.log("Hongwei try11")


  // Early definition block after extends
  class SavingsAccount2 extends { val filename = "savings.log" } with Account with FileLogger2 
  
  val acct2 = new SavingsAccount2 
  acct2.log("hongwei")
    
}
  

//BK 10.12 Traits Extending Classes
//Trait extends Exception --> all other class extends this trait will be the subclasses of Exception.
// If you want to use this trait, you will be a subclass of exception 
trait LoggerException extends Exception with Logger  {
  def log(): Unit = {
    log(getMessage)
  }
}

class UnhappyException extends IOException with LoggerException {
  override def getMessage: String = "arggh!"
}

//BK 10.13 Self Types
//When a trait extends a class, there is a guarantee that the superclass is present in any class mixing in the trait.
//Scala has an alternate mechanism for guaranteeing this: self types.

//When a trait starts out with
//
//this: Type =>
//
//then it can only be mixed into a subclass of the given type.


// trait extends the class
trait LoggedException131 extends Exception  {
  def log() {
    getMessage
  }
}

// trait use self type
trait LoggedException132   {
  this: Exception =>
  def log() {
    getMessage
  }
}

// Any no super class can `extends` LoggedException131
// But only subclass of Exception can extend LoggedException132
class Some1 extends LoggedException131
class Some2 extends Exception with LoggedException132


// self type, can used in `structural types`
trait LoggedException133 extends Logger  {
  this: {def getMessage(): String} =>
  def log() {
    log(getMessage())
  }
}

//BK 10.14 What Happens under the Hood