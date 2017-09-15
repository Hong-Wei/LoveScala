
def response: (Int => Double) = { q => q* 1.2}
val response2: (Int => Double) = { q => q* 1.2}

response(4)

val int1: Int = "234".toInt
response(int1)