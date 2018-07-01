
object intsets{
  println("Good boy")
}

abstract class IntSet {
  def incl (x: Int) : IntSet
  def contains (x: Int) : Boolean
}

val z: Int = null
