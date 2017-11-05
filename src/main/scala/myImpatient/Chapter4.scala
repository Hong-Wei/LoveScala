package myImpatient

/**
  * Created by zhanghongwei on 12/11/16.
  */
object Chapter4 {
  val scores = Map("Alice" -> 10, "Bob" -> 3, "Cindy" -> 8)
  val socreMutalbe = scala.collection.mutable.Map("Alice" -> 1, "Bob" -> 2, "Cindy" -> 3)
  //  val socreMutalbe1= scala.collection.mutable.HashMap(String,Int);
  val scores1: Int = if (scores.contains("bob")) scores("Bob1") else 0

//  scores.getOrElse("Alice1",throw new RuntimeException("NO this value"))

  private val maybeInt3: Option[Int] = scores.get("")
  def main(args: Array[String]): Unit = {
    val a = Chapter4
    val orElse: Int = scores.getOrElse("Bob", 0)
    val maybeInt: Option[Int] = scores.get("Bob")
    val scores2: Option[Int] = scores.get("bob")
    val someString: Option[String] = Some("hongwei")
    val none: None.type = None
    val maybeString: Option[String] = Option("Hongwei")
    val someChapter: Some[Chapter4.type] = Some(a)
    val i1: Int = maybeInt.get + 5
    val i: Int = orElse + 5

    val maybeInt1: Option[Int] = scores.get("Alice")
    maybeInt1 match {
      case Some(score) => println(score)
      case None => println("No Score!")
    }

    scores.get("Alice") match {
      case Some(score) => println(score)
      case None => println("No Score!")
    }

    val alicesSocre = scores.get("Alice")
    if (alicesSocre.isEmpty) println("No socore")
    else println(alicesSocre.get)

    val orElse1: Any = alicesSocre.getOrElse("No score")
    val orElse2: Any = scores.getOrElse("Alice", "No score")
    val orElse3: Any = scores.getOrElse("Dog", "No")
    val maybeInt2: Option[Int] = for (score <- scores.get("alice")) yield score
    scores.get("Bob").foreach(println _)
    val map: ((Int) => Nothing) => Option[Nothing] = scores.get("Bob").flatMap(_)
    scores.get("Alicea")
    scores.getOrElse("Bob", 0)

    //4.3
    socreMutalbe("Bob") = 22
    socreMutalbe("Dog") = 4
    socreMutalbe += ("Bob" -> 10, "Fred" -> 7)
    socreMutalbe -= "Alice"
    val newScores = scores + ("Bob" -> 10, "Fred" -> 7)
    val socreMutalbe1 = scores - "Alice"

    //4.4
    //for ((k,v) <- map)   ...
    val keySet: Set[String] = scores.keySet
    for (v <- scores.values) print(v)

    val intToString: Map[Int, String] = for((k,v)<- scores) yield (v,k)

    val scores1 = scala.collection.immutable.SortedMap("A"->1,"B"->2,"C"->3,"D"->4)
    val scores3 = scala.collection.mutable.LinkedHashMap("A"->1,"B"->2,"C"->3,"D"->4)
    val tuple: (Int, Double, String, Chapter1) = (1,3.14,"Fred",new Chapter1)


    //4.8
    val symbols = Array("<","-",">")
    val counts= Array(2,10,2)
    val pairs = symbols.zip(counts)
    for((s,n)<-pairs) Console.print(s*n)

//    val partition: (String, String) = "New York".partition(_.isUpper)
    println(orElse1)

  }
}


















