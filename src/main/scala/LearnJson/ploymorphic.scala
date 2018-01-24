package LearnJson

import net.liftweb.json.{Serialization, ShortTypeHints}
import net.liftweb.json.Serialization.write
import scala.collection.immutable.List
trait Animal

case class Dog(name: String) extends Animal

case class Fish(weight: Double) extends Animal

case class Animals(animals: List[Animal])
/**
  * Created by zhanghongwei on 08/09/2017.
  */
object ploymorphic extends App {

  implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[Dog], classOf[Fish])))
  val ser: String = write(Animals(Dog("pluto") :: Fish(1.2) :: Nil))
  println(ser)

}


