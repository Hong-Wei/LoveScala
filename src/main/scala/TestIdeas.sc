class Animal { val sound = "rustle" }
class Bird extends Animal { override val sound = "call" }
class Chicken extends Bird { override val sound = "cluck" }
val getTweet: (Bird => String) = ((a: Animal) => a.sound )
val hatch: (() => Bird) = (() => new Chicken )
def count(l: List[T forSome { type T }]) = l.size
//import akka.actor.Actor
//import akka.event.Logging
//
////通过扩展Actor并实现receive方法来定义Actor
//class MyActor extends Actor {
//  //获取LoggingAdapter，用于日志输出
//  val log = Logging(context.system, this)
//
//  //实现receive方法，定义Actor的行为逻辑，返回的是一个偏函数
//  def receive = {
//    case "test" => log.info("received test")
//    case _      => log.info("received unknown message")
//  }
//}
//
//val numbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
//def m2 (n: Int) = n *2
//
//numbers.foldRight(List[Int]())((x: Int, xs: List[Int]) => m2(x) :: xs)
//
//11 :: List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10) // :: is from right to left
//List(11,22) ::: List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)// :: is from right to left
//
//def ourMap(numbers: List[Int], fn: Int => Int): List[Int] = {
//  numbers.foldRight(List[Int]()) { (x: Int, xs: List[Int]) =>
//    fn(x) :: xs
//  }
//}
//
//def f(s: String) = "f(" + s + ")"
//def g(s: String) = "g(" + s + ")"
//val fComposeG = f _ compose g _
//val fAndThenG = f _ andThen g _
//fComposeG("HHH")
//fAndThenG("HHH")
//
//def one: PartialFunction[Int, String] = { case 1 => "one" }
//def two: PartialFunction[Int, String] = { case 2 => "two" }
//one.isDefinedAt(1)
//one(1)
//one.isDefinedAt(2)
//
//val partial = one orElse two

//def foo[A, B](f: A => List[A], b: B) = f(b)
//def foo[A](f: A => List[A], i: Int): List[Any] = f(i)

class Covariant[-A]
//val cv1: Covariant[AnyRef] = new Covariant[String]
val cv2: Covariant[String] = new Covariant[AnyRef]
val cv3: Covariant[String] = new Covariant[String]


