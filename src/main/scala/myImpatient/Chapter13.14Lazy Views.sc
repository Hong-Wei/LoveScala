//bk 13.14 Lazy Views
import scala.collection.SeqView
import scala.collection.immutable.{IndexedSeq}
import scala.math._


val range: IndexedSeq[Int] = 0 until 101
val view: SeqView[Int, IndexedSeq[Int]] = range.view
//This is SeqView, it will calculate only when it is called. 
val powersFromView: SeqView[Double, Seq[_]] = view.map(pow(10, _))
//This is Seq, it will calculate when it is defined 
val powersFromList: IndexedSeq[Double] = range.map(pow(10, _))

//Unlike streams, these views do not cache any values.
powersFromView(100)
powersFromList(100)

  val powers = (0 until 1000).view.map(n => { println(n) ; pow(10, n) })
  powers(100) // Prints 100 in the method call
  powers(100) // Prints 100 again; the method is called twice


  // Contrast with streams
  def powers2(n: Int): Stream[Double] = { println(n) ; pow(10, n) } #:: powers2(n + 1)
//
  val powerStream = powers2(0) // Calls method with 0
  powerStream(100) // Calls method with 1 to 100
  powerStream(100) // Doesn't call the method again

  (0 to 1000).map(pow(10, _)).map(1 / _)

  (0 to 1000).view.map(pow(10, _)).map(1 / _).force

  (0 to 1000).map(x => pow(10, -x))


  val powers3 = (0 until 1000).view.map(scala.math.pow(10, _))
  powers3(1000)
  (0 to 1000).map(scala.math.pow(10, _)).map(1 / _)
