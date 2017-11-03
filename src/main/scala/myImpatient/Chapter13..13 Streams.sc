import scala.collection.immutable.Seq

//Stream, #:: is used for create the stream. 
def numsFrom(n: BigInt): Stream[BigInt] = n #:: numsFrom(n + 1)

//It evaluate only when it is called
val tenOrMore: Stream[BigInt] = numsFrom(10)

tenOrMore.tail.tail.tail

val squares: Stream[BigInt] = numsFrom(1).map(x => x * x)

//A stream caches the visited lines, so you can revisit them
val bigInts: Stream[BigInt] = squares.take(5).force
bigInts(0)
bigInts(1)


import scala.io.Source
val words: Stream[String] = Source.fromFile("/usr/share/dict/words").getLines.toStream
words(5)
words
