//class Person {
//  var age = 0
//}

import net.liftweb.json.JsonAST.JValue
import net.liftweb.json.parse

case class Address(street: String, city: Option[String])
case class Person(name: String, address: Address)


object Person1 extends App{
  implicit val formats = net.liftweb.json.DefaultFormats
  val  fred = parse("""
{ "name": "joe",
  "address": {
    "street": "Bulevard",
    "city": "london"}
  }
""").extract[Person]

  print(fred.name)
}