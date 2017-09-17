//http://danielwestheide.com/blog/2012/12/12/the-neophytes-guide-to-scala-part-4-pattern-matching-anonymous-functions.html


//1 define an anonymous function that you pass to the map method
val songTitles = List("The White Hare", "Childe the Hunter", "Take no Rogues")
songTitles.map(t => t.toLowerCase)
songTitles.map(_.toLowerCase)


//2 ugly example
val wordFrequencies = ("habitual", 6) :: ("and", 56) :: ("consuetudinary", 2) ::
  ("additionally", 27) :: ("homely", 5) :: ("society", 13) :: Nil
def wordsWithoutOutliers(wordFrequencies: Seq[(String, Int)]): Seq[String] =
  wordFrequencies.filter(wf => wf._2 > 3 && wf._2 < 25).map(_._1)
wordsWithoutOutliers(wordFrequencies) // List("habitual", "homely", "society")


//3 pattern matching anonymous functions
// a block , consisting of a sequence of cases, surrounded s usual by curly braces.
// but without a `match` keyword before the block
def wordsWithoutOutliersNew(wordFrequencies: Seq[(String, Int)]): Seq[String] =
  wordFrequencies.filter { case (_, f) => f > 3 && f < 25 } map { case (w, _) => w }

val predicate: (String, Int) => Boolean = { case (_, f) => f > 3 && f < 25 }
val transformFn: (String, Int) => String = { case (w, _) => w }


