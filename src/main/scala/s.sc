var factor = 3
def multiplier: (Int) => Int = (i:Int) => i * factor

multiplier(1)


def mulBy(factor: Double): (Double) => Double = (x: Double) => factor * x

// each of the returned functions has its own setting for factor : Closures consists of code together with variables
val triple = mulBy(3)
triple(3)


object Demo {
  def main(args: Array[String]) {
    println( "multiplier(1) value = " +  multiplier(1) )
    println( "multiplier(2) value = " +  multiplier(2) )
  }
  var factor = 3
  val multiplier = (i:Int) => i * factor
}
