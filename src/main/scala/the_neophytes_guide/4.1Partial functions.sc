val wordFrequencies = ("habitual", 6) :: ("and", 56) :: ("consuetudinary", 2) ::
  ("additionally", 27) :: ("homely", 5) :: ("society", 13) :: Nil

//1 using the syntax for pattern matching anonymous functions
val pf: PartialFunction[(String, Int), String] = {
  case (word, freq) if freq > 3 && freq < 25 => word
}

//2 defined this partial function by explicitly extending the PartialFunction traita
val pf1 = new PartialFunction[(String, Int), String] {
  def apply(wordFrequency: (String, Int)) = wordFrequency match {
    case (word, freq) if freq > 3 && freq < 25 => word
  }
  def isDefinedAt(wordFrequency: (String, Int)) = wordFrequency match {
    case (word, freq) if freq > 3 && freq < 25 => true
    case _ => false
  }
}

//wordFrequencies.map(pf) //scala.MatchError:
//wordFrequencies.map(pf1)  //scala.MatchError:

wordFrequencies.collect(pf)
wordFrequencies.collect(pf1)


//3 difference between normal and Partial functions
// normal - Pattern Matching Anonymous Functions
def wordsWithoutOutliersOld(wordFrequencies: Seq[(String, Int)]): Seq[String] =
  wordFrequencies.filter { case (_, f) => f > 3 && f < 25 } map { case (w, _) => w }


//upgrade this the Partial Functions : performance(Just iterator once, up one twice:Filter + Map) and clear. 
def wordsWithoutOutliersNew(wordFrequencies: Seq[(String, Int)]): Seq[String] =
  wordFrequencies.collect { case (word, freq) if freq > 3 && freq < 25 => word }