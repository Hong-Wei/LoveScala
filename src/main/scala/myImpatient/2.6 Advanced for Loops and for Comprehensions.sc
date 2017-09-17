import scala.collection.immutable.Seq
//1 similar to Java for loop, can have multiple generators of the form i<- expression
var sum =0 
val a: Unit = for (
  i <- 1 to 3;
  j <- 1 to 3
) sum = sum + 1

sum

//This basic for loop, no yield part. it only has the side affect. 
//it return the Unit 
// if you want to get value from this, you need extra var to set and get values.


//2 Each generator can have a guard, a Boolean condition preceded by if

for (
  i <- 1 to 3 if i != 2; 
  j <- 1 to 3 if i != j //no semicolon before if.
)
  print((10 * i + j) + " ")


//3 you have same any number of definitions. introducing variables that can be used inside the loop:

val Unit1: Unit = for (
  i <- 1 to 3;
  from = 4 - i;
  j <- from to 3
) print((10 * i + j) + " ")
Unit1




//4 yield --> constructs a collection of values, one for each iteration:
// If for loop + yield => for comprehension
val inclusive: Seq[Int] = 1 to 10
val ints: Seq[Int] = for (i <- inclusive) yield i * 2



//5 The for comprehension generated collection is compatible with the first generator.
val inclusive1: Seq[Int] = 0 to 1
//5.1 
val s1: String = for (c <- "Hello"; i <- inclusive1) yield (c + i).toChar

//5.2
val chars: Seq[Char] = for (i <- inclusive1; c <- "Hello") yield (c + i).toChar




//6 you also can encolse generators, guards, definitions in side braces, use newlines instead of semicolons to separate them
val ints1: Seq[Int] = for {
  i <- 1 to 3 if i != 2
  from = 4 - i
  j <- from to 3 if j != 2
} yield i