//http://danielwestheide.com/blog/2012/11/28/the-neophytes-guide-to-scala-part-2-extracting-sequences.html

//1 an arbitrary number of extracted parameters.
val xs = 3 :: 6 :: 12 :: Nil
xs match {
  case List(a, b) => a * b
  case List(a, b, c) => a + b + c
  case _ => 0
}

//2 want to match lists the exact length of which you don’t care about, you can use a wildcard operator, _*
val xs2 = 3 :: 6 :: 12 :: 24 :: Nil
xs2 match {
  case List(a, b, _*) => a * b
  case _ => 0
}