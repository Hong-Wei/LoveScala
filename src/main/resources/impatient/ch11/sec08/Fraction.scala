package impatient.ch11.sec08

import scala.math._

class Fraction(n: Int, d: Int) {
  private val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d);
  private val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d);

  override def toString = num + "/" + den

  def sign(a: Int) = if (a > 0) 1 else if (a < 0) -1 else 0

  def gcd(a: Int, b: Int): Int = if (b == 0) abs(a) else gcd(b, a % b)

  def *(other: Fraction) = new Fraction(num * other.num, den * other.den)
}

object Fraction {
  def apply(n: Int, d: Int): Fraction = new Fraction(n, d)

  def unapply(input: Fraction): Option[(Int, Int)] =
    if (input.den == 0) None
    else Some((input.num, input.den))
}

object Main extends App {
  private val fraction1: Fraction = Fraction(3, 4)
  //apply
  private val fraction2: Fraction = Fraction(2, 5)
  private val result: Fraction = fraction1 * fraction2
  var Fraction(a, b) = result //unapply
  println(a)
  println(b)

  val author = "Zhang Hongwei"
  val Name(first, last) = author
  val abc = 5
}

object Name {
  def unapply(input: String): Option[(String, String)] = {
    val pos = input.indexOf(" ")
    if (pos == -1) None
    else Some((input.substring(0, pos), input.substring(pos + 1)))
  }
}
