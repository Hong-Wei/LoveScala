// normally, e(p1, p2)--> p1 and p2 can be extracted from a given data structure
// also support infix, p1 e p2 -->
// following example: head #::tail could also be written as #::(head, tail)


val xs = 58 #:: 43 #:: 93 #:: Stream.empty
xs match {
  case first #:: second #:: _ => first - second
  case _ => -1
}
//--> A closer look at the Stream extractor
// Totally the same as before
xs match {
  case #::(first, #::(second, _)) => first - second
  case _ => -1
}
// will run following extractor twice, #::
//This is from scala library
object #:: {
  def unapply[A](xs: Stream[A]): Option[(A, Stream[A])] =
    if (xs.isEmpty) None
    else Some((xs.head, xs.tail))
}