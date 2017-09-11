import scala.collection.immutable.Stream.cons

/**
  * Created by zhanghongwei on 11/09/2017.
  */


//1 https://alvinalexander.com/scala/how-to-use-stream-class-lazy-list-scala-cookbook
val stream = 1 #:: 2 #:: 3 #:: Stream.empty
val stream2: Stream[Int] = (1 to 10000).toStream

//result is Stream(1, ?), Just return the first value. The second has not be evaluated!!


val head = stream2.head  // head is returned immediately. 
val last = stream2.last
val take: Int = stream2.take(2).head
stream.max
stream.size
stream.sum

val list: Stream[Int] = (1 to 100000000).toStream
list(100)


//How to create a Stream with 3 numbers using Stream.cons
//2 http://allaboutscala.com/tutorials/chapter-6-beginner-tutorial-using-scala-immutable-collection/scala-tutorial-learn-use-immutable-stream/
val stream21: Stream[Int] = cons(1, cons(2, cons(3, Stream.empty) ) )

val take21: Stream[Int] = stream21.take(3)
val print1 = take21.print()
val head21: Int = take21.head
val tail211: Int = take21.tail.head
val tail212: Stream[Int] = take21.tail.tail


//3 How to define an infinite Stream of numbers using Stream.cons
def inifiniteNumberStream(number: Int): Stream[Int] = Stream.cons(number, inifiniteNumberStream(number + 1))

inifiniteNumberStream(10).take(20).print()

//4 How to define an infinite stream of numbers using Stream.from
Stream.from(1).take(20).print()

//5 How to initialize an empty Stream
val emptyStream: Stream[Int] = Stream.empty[Int]


// take some careful study of this subject unitl you feel familiar with the tools and techniques.

