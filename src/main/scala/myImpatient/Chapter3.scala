package myImpatient

import scala.collection.immutable.Range.Inclusive
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * Created by zhanghongwei on 11/11/16.
  */
object Chapter3 {
  val nums = new Array[Int](10)
  val stringArr = new Array[String](10)
  val s = Array("Hello", "World")
  s(0) = "Goodbye"


  def main(args: Array[String]): Unit = {

    val b = ArrayBuffer[Int]()
    val b1 = new ArrayBuffer[Int]
    b += 1 //add to the last
    b += (2, -3, 4) //many value using the parentheses
    b ++= Array(2, 3, -44) //add any sets
    b ++= Set(2, 3, 4) //add any sets
    b.trimEnd(5) //delete last five items
    b.insert(2, 6)
    b1.insert(0, 12, 1, 2)
    val toArray: Array[Int] = b.toArray
    val toBuffer: mutable.Buffer[Int] = toArray.toBuffer

    val a1 = 5
    //    print(stringArr)
    val chapter3 = Chapter3
    print(chapter3.s.mkString)
    val a2 = 6
    //3.3
    for (i <- 0 until(b1.length, 2))
      print(i + ": " + b1(i))
    for (i <- (0 until b.length).reverse)
      print(i + ": " + b(i))

    //    for(i<-(qujian))
    for (elem <- b1) Nil
    val until: Range = 0.until(10)
    val inclusive: Inclusive = 0 to 10
    //3.4

    val a34 = ArrayBuffer(2, 3, 4, 5)
    val result = for (elem <- a34) yield 2 * elem // the result contains all the values , each iteral each value
    val result1 = for (elem <- a34 if elem % 2 == 0) yield 2 * elem // the result contains all the values , each iteral each value
    val map: ArrayBuffer[Int] = a34.filter(_ % 2 == 0).map(2 * _)



    var first = true
    var n = b.length
    var i = 0
    while (i < n) {
      if (b(i) >= 0) i += 1
      else {
        if (first) {
          first = false
          i += 1
        }
        else {
          b.remove(i)// waste time
          n -= 1
        }
      }
    }

    //find the right position
    var first1 = true
    var indexes = for (i <- 0 until b.length if first || b(i) >= 0) yield {
      if (b(i) < 0) first = false; i
    }
    // put the items to right position and trim the end
    for (j <- 0 until indexes.length) b(j) = b(indexes(j))
    b.trimEnd(b.length - indexes.length)

    val ints: Array[Int] = Array(1, 7, 2, 9)
    val sorted: Array[Int] = ints.sorted
    val sorted2: Array[Int] = ints.sortWith(_>_)

    val sum: Int = ints.sum
    val sum1: Int = ArrayBuffer(1,7,2,9).sum

    scala.util.Sorting.quickSort(ints)
    println()
    val string: String = ints.mkString(" and ")

    b1.append(1,2,3)

    b1.count(_ > 0) //def count(p: (A) => Boolean): Int
    b1 += 4
    None

  }


}
