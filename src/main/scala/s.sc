def numsFrom(n: BigInt): Stream[BigInt] = n #:: numsFrom(n + 1)

val tenOrMore = numsFrom(10)

tenOrMore.tail.tail.tail

val squares = numsFrom(1).map(x => x * x)

squares.take(5) take 5