//http://danielwestheide.com/blog/2013/01/09/the-neophytes-guide-to-scala-part-8-welcome-to-the-future.html

//Writing well-behaved concurrent programs a lot easier than rather low-level concurrency.
//Two cornerstones: Future + Actor. 
// 
// Actors used for maintaining state and providing a messaging endpoint.
// in all other circumstances, it is probably better to use Future. 
// 


import scala.util.Try
// Some type aliases, just for getting more meaningful method signatures:
type CoffeeBeans = String
type GroundCoffee = String
case class Water(temperature: Int)
type Milk = String
type FrothedMilk = String
type Espresso = String
type Cappuccino = String
// dummy implementations of the individual steps:
def grind(beans: CoffeeBeans): GroundCoffee = s"ground coffee of $beans"
def heatWater(water: Water): Water = water.copy(temperature = 85)
def frothMilk(milk: Milk): FrothedMilk = s"frothed $milk"
def brew(coffee: GroundCoffee, heatedWater: Water): Espresso = "espresso"
def combine(espresso: Espresso, frothedMilk: FrothedMilk): Cappuccino = "cappuccino"
// some exceptions for things that might go wrong in the individual steps
// (we'll need some of them later, use the others when experimenting with the code):
case class GrindingException(msg: String) extends Exception(msg)
case class FrothingException(msg: String) extends Exception(msg)
case class WaterBoilingException(msg: String) extends Exception(msg)
case class BrewingException(msg: String) extends Exception(msg)
// going through these steps sequentially:
def prepareCappuccino(): Try[Cappuccino] = for {
  ground <- Try(grind("arabica beans"))
  water <- Try(heatWater(Water(25)))
  espresso <- Try(brew(ground, water))
  foam <- Try(frothMilk("milk"))
} yield combine(espresso, foam)
