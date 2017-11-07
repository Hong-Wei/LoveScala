abstract class Item
case class Article(description: String, price: Double) extends Item
case class Bundle(description: String, discount: Double, items: Item*) extends Item

val special = Bundle(
  "Father's day special", 
  20.0,
  Article("Scala for the Impatient", 39.95),
  Bundle(
    "Anchor Distillery Sampler", 
    10.0,
    Article("Old Potrero Straight Rye Whiskey", 79.95),
    Article("Junípero Gin", 32.95)
  )
)
//1 binds descr to the description of the first article in a bundle.
special match {
  case Bundle(_, _, Article(descr, _), _*) => descr
}

//2 You can bind a nested value to a variable with the @ notation:
//Now art is the first article in a bundle and rest is the sequence of the other items.
//Note that the _* is required in this example.
special match {
  case Bundle(_, _, art @ Article(_, _), rest @ _*) => (art, rest)
}


//3 match a bundle with an article and exactly one additional item, bound to rest.
special match {
  case Bundle(_, _, art @ Article(_, _), rest) => (art, rest)
}

def price(it: Item): Double = it match {
  case Article(_, p) => p
  case Bundle(_, disc, its @ _*) => its.map(price _).sum - disc
}

price(special)

