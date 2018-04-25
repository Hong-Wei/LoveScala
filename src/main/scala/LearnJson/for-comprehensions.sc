import net.liftweb.common.Full
import net.liftweb.json.JsonAST.{JString, JValue}
import net.liftweb.json.{JValue, parse, _}

import scala.collection.mutable.Buffer

val json: JValue = parse(
  s"""
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
  """.stripMargin
)

for {
  JObject(age) <- json
  JField("name", JString(joe)) <- age
} yield joe

for {
  JObject(child) <- json
  JField("name", JString(name)) <- child
  JField("age", JInt(age)) <- child
} yield (name, age)



// my own test.
val myJson: JValue = parse(
  s"""
  {
    "name":"CounterpartyName",
    "description":"My landlord",
    "created_by_user_id":"49e1e147-64c1-4823-ad9f-89efcd02a9fa",
    "this_bank_id":"gh.29.uk",
    "this_account_id":"8ca8a7e4-6d02-48e3-a029-0b2bf89de9f0",
    "this_view_id":"owner",
    "counterparty_id":"1d65db7c-a7b2-4839-af41-958276ab7790",
    "other_bank_routing_scheme":"bankCode",
    "other_bank_routing_address":"bankCode",
    "other_branch_routing_scheme":"bankCode",
    "other_branch_routing_address":"bankCode",
    "other_account_routing_scheme":"bankCode",
    "other_account_routing_address":"bankCode",
    "other_account_secondary_routing_scheme":"bankCode",
    "other_account_secondary_routing_address":"bankCode",
    "is_beneficiary":true,
    "bespoke":[{
    "key":"englishName",
    "value":"english Name"
    },{
    "key":"englishDescription",
    "value":"englishDescription"
    }]
   }
  """.stripMargin
)


val value: List[String] = for {
  JObject(child) <- myJson
  JField("counterparty_id", JString(counterparty_id)) <- child
  counterparty_id1<- counterparty_id
} 
  yield 
    counterparty_id


//for {
//  JField("counterparty_id", JString(counterparty_id)) <- myJson
//  JField("is_beneficiary", JBool(is_beneficiary)) <- myJson
//  JField("key", JString(key)) <- myJson
//} yield (counterparty_id,is_beneficiary,key)


//TODO why the following code do not work??
///Users/zhanghongwei/Documents/GitHub-Tower/OBP-API_Leumi/src/test/scala/code/api/v2_2_0/CreateCounterpartyTest.scala

myJson.replace(List("counterparty_id"),JString("counterparty_id"))



myJson findField {
  case JField(n, v) =>
    n.contains("_id")
}


myJson filterField {
  case JField(n,v) if n.contains("_id") =>
    true
  case _ =>
    false
}

def renameCurrencyToCcy(json: JValue): JValue = json transformField {
  case JField("counterparty_id", x) => JField("COUNTERPARTYID", x)
}

def renameCcyToCurrency(json: JValue): JValue = json transformField {
  case JField("ccy", x) => JField("currency", x)
}




renameCurrencyToCcy(myJson)