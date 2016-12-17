package chenChaoVideo.api

/**
  * Created by zhanghongwei on 13/11/16.
  */
class day04ATAGURU {
}

object day04ATAGURU extends App {
  //BK 4.1 fanction as the parameter
  def sum(f: Int => Int, a: Int, b: Int): Int =
  if (a > b) 0
  else f(a) + sum(f, a + 1, b)

  def f1(a: Int) = a

  def sum1(a: Int, b: Int): Int = sum(f1, a, b)

  def f2(a: Int) = a * 2

  def sum2(a: Int, b: Int): Int = sum(f2, a, b)

  //BK 4.2 Anonymous Function
  (x: Int) => x * 2

  // () all the parameters
  // => all the results with parameters
  //  (x1:T1,x2:T2,...) => E
  def sum3(a: Int, b: Int): Int = sum((x: Int) => x, a, b)

  def sum4(a: Int, b: Int): Int = sum((x: Int) => x * 2, a, b)


  //  val sum5 = sum4 _

  val f22 = (a: Int) => a * 2 // set the Anonymous function to a val.
  //  def f2(a: Int) = a * 2
  //  f2(10)

  // BK 4.3 High Order Function
  val f3 = (a: Int) => a
  val f4 = (a: Int) => a * 2
  val f5 = (a: Int) => a * 3

  def mul1(a: Int): (Int) => Int = ((b: Int) => b * a)

  // use function to create new function !
  val f6 = mul1(1)
  val f7 = mul1(2)

  private val u: day04ATAGURU = new day04ATAGURU
  private val f8: Int = f1(2)
  private val f9: Int = f22(2)
  val a = 5

  def mul2(a: Int): (Int) => Int = (b: Int) => b * a

  val f10 = mul2(1)
  val f11 = mul2(2)


  //  def sum4(f: Int => Int) = (a:Int, b:Int) => Int = {
  //    def sum5(a:Int,b:Int) =sum(f,a,b);
  //    sum5
  //  }
  //BK 4.4 Currying
  def mul44(a: Int, b: Int): Int = a * b

  def mul45(a: Int)(b: Int) = a * b
  // Curring divide into two steps
  // def first(a:Int) = (b:Int) => a*b // input Int, output is function
  // def second =first(a) //
  // second: (Int)=>Int

}

