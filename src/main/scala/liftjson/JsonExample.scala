package liftjson

import net.liftweb.json.JsonAST.{JObject, JValue, render}
import net.liftweb.json.{JValue, Printer, _}
import net.liftweb.json.JsonDSL._

//1  case class --> JValue --> Json String
object JsonExample extends App with Printer{


//2 DSL Rules  https://github.com/lift/lift/tree/master/framework/lift-base/lift-json#dsl-rules
  //Any seq produces JSON array.
  val json1: List[Int] = List(1, 2, 3)
  private val render1 = render(json1)
  private val compact1: String = compact(render1) //[1,2,3]

  //Tuple2[String, A] produces field.
  val json2: (String, String) = ("name" -> "joe")
  private val render2 = render(json2)
  private val compact2: String = compact(render2) // {"name":"joe"}

  //~ operator produces object by combining fields
  val json3: JValue = ("name" -> "joe") ~ ("age" -> 35)
  private val render3 = render(json3)
  private val compact3: String = compact(render3) // {"name":"joe","age":35}

  //Any value can be optional. Field and value is completely removed when it doesn't have a value.
  val json4 = ("name" -> "joe") ~ ("age" -> Some(35))
  compact(render(json4)) // {"name":"joe","age":35}
  val json5 = ("name" -> "joe") ~ ("age" -> (None: Option[Int]))
  compact(render(json5))  //{"name":"joe"}


//3 Example https://github.com/lift/lift/tree/master/framework/lift-base/lift-json#example
  case class Winner(id: Long, numbers: List[Int])
  case class Lotto(id: Long, winningNumbers: List[Int], winners: List[Winner], drawDate: Option[java.util.Date])

  val winners = List(Winner(23, List(2, 45, 34, 23, 3, 5)), Winner(54, List(52, 3, 12, 11, 18, 22)))
  val lotto = Lotto(5, List(2, 45, 34, 23, 7, 5, 3), winners, None)

  val json6: (String, JObject) =
    ("lotto" ->
      ("lotto-id" -> lotto.id) ~
        ("winning-numbers" -> lotto.winningNumbers) ~
        ("draw-date" -> lotto.drawDate.map(_.toString)) ~
        ("winners" ->
          lotto.winners.map { w =>
            (("winner-id" -> w.id) ~
              ("numbers" -> w.numbers))}))

  println(compact(render(json6)))
  println(pretty(render(JsonExample.json6)))




//4 Merging & Diffing  https://github.com/lift/lift/tree/master/framework/lift-base/lift-json#merging--diffing 

  val lotto1: JValue = parse("""{
         "lotto":{
           "lotto-id":5,
           "winning-numbers":[2,45,34,23,7,5,3]
           "winners":[{
             "winner-id":23,
             "numbers":[2,45,34,23,3,5]
           }]
         }
       }""")

  val lotto2: JValue = parse("""{
         "lotto":{ 
           "winners":[{
             "winner-id":54,
             "numbers":[52,3,12,11,18,22]
           }]
         }
       }""")

  val mergedLotto: JValue = lotto1 merge lotto2

  pretty(render(mergedLotto))
//  {
//    "lotto":{
//      "lotto-id":5,
//      "winning-numbers":[2,45,34,23,7,5,3],
//      "winners":[{
//      "winner-id":23,
//      "numbers":[2,45,34,23,3,5]
//    },{
//      "winner-id":54,
//      "numbers":[52,3,12,11,18,22]
//    }]
//    }
//  }

  val Diff(changed, added, deleted) = mergedLotto diff lotto1
//  changed: net.liftweb.json.JsonAST.JValue = JNothing
//  added: net.liftweb.json.JsonAST.JValue = JNothing
//  deleted: net.liftweb.json.JsonAST.JValue = JObject(List(JField(lotto,JObject(List(JField(winners,
//    JArray(List(JObject(List(JField(winner-id,JInt(54)), JField(numbers,JArray(
//      List(JInt(52), JInt(3), JInt(12), JInt(11), JInt(18), JInt(22))))))))))))))



//5 Querying JSON --> https://github.com/lift/lift/tree/master/framework/lift-base/lift-json#querying-json
val json7: JValue = parse(
"""
  { 
   "name": "joe",
   "children": [
     {
       "name": "Mary",
       "age": 5
     },
     {
       "name": "Mazy",
       "age": 3
     }
   ]
  }
"""
)
  private val age = for {JField("age", JInt(age)) <- json7 } yield age //List[BigInt] = List(5, 3)


  private val Nameage = for {
    JObject(child) <- json7
    JField("name", JString(name)) <- child
    JField("age", JInt(age)) <- child
    if age > 4
  } yield (name, age) // List((Mary,5))


//6  XPath + HOFs -->https://github.com/lift/lift/tree/master/framework/lift-base/lift-json#xpath--hofs

//7  Extracting values --> https://github.com/lift/lift/tree/master/framework/lift-base/lift-json#extracting-values
  
  
    val a = 5
}