//http://twitter.github.io/scala_school/collections.html

/**
This lesson covers:
  Basic Data Structures
    Arrays
    Lists
    Sets
    Tuple
    Maps
    Option
  Functional Combinators
    map
    foreach
    filter
    zip
    partition
    find
    drop and dropWhile
    foldRight and foldLeft
    flatten
    flatMap
    Generalized functional combinators
    Map?
  */

//1 Arrays- preserve order, can contain duplicates, and are mutable (not length, just content).
val ints = Array(1,1,2)
ints(0)
ints(0)=10
//ints(3)=10, can not change length --> IndexOutOfBoundsException

//2 List- preserve order, can contain duplicated and are immutable
val ints1 = List(1,2,1)
ints1(0)
//ints1(0)=10 //--> compile error
//ints1(3) //--> , can not change length --> IndexOutOfBoundsException

//3 Sets- do not preserve order and have no duplicates
val ints2 = Set(1,1,3)
ints2(0)
//ints2(0)=100  //--> compile error

//4 Tuple- groups together simple logical collections of items without using a class.
val hostPort = ("localhost",80)
//They do not have named accessors, instead they hae accessors that are named by position.(1-based rather than 0-based)
hostPort._1
hostPort._2
// Tuples fit with pattern matching nicely.
hostPort match {
  case ("localhost", port) => 1
  case (host, port) => 2
}

//5 Maps - can hold basic datatypes
Map(1 -> 2)
Map("1" -> "2")
Map("1" -> Map(1 -> 2))   //Map has map inside
def addOne2(m:Int) = m+1
Map("1" -> Map(1 -> addOne2(2))) //Map has function inside

//6 Option-is a container that may or may not hold sth
//eg1: map return Option
val maybeInt = Map("one" -> 1, "two" -> 2).get("one")
maybeInt// Some(1)

//A first instinct might be to do something conditionally based on the isDefined method.
// We want to multiply the number by two, otherwise return 0.
val abc= Option(1)
val result1 = if (abc.isDefined) {
  abc.get * 2
} else {
  0
}
//We would suggest that you use either getOrElse or pattern matching to work with this result.
//getOrElse lets you easily define a default value.
val result2 = abc.getOrElse(0) * 2

//Pattern matching fits naturally with Option.
val result3 = abc match {
  case Some(n) => n * 2
  case None => 0
}



val extensions = Map("steve" -> 100, "bob" -> 101, "joe" -> 201)
extensions.filter((namePhone: (String, Int)) => namePhone._2 < 200)
extensions.filter({case (_, extension) => extension < 200})