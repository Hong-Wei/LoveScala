def gameResults(): Seq[(String, Int)] =
  ("Daniel", 3500) :: ("Melissa", 13000) :: ("John", 7000) :: Nil

val results: Seq[(String, Int)] = gameResults()

def hallOfFame: Seq[String] = for {
  result <- results
  (name, score) = result
  if (score > 5000)
} yield name

hallOfFame

//more concisely: The left side , generator is also a pattern.
def hallOfFame2 = for {
  (name, score) <- gameResults() if (score > 5000)
} yield name
hallOfFame2


def hallOfFame3 = for {
  name <- gameResults() 
} yield name
hallOfFame3


//3 It is important to know that patterns in the left side of generators 
// can already be used for filtering purposes – if a pattern on the left
// side of a generator does not match, the respective element is filtered out

val lists = List(1, 2, 3) :: List.empty :: List(5, 3) :: Nil

for {
  list @ head :: _ <- lists
} yield list.size