import net.liftweb.json.JsonAST.JValue
import net.liftweb.json._

val json = parse(
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

for {JField("age", JInt(age)) <- json} yield age

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


val value = for {JField("counterparty_id", JString(age)) <- myJson} yield age

//val values = value.toString

for {
  JField("counterparty_id", JString(counterparty_id)) <- myJson
  JField("is_beneficiary", JBool(is_beneficiary)) <- myJson
  JField("key", JString(key)) <- myJson
} yield (counterparty_id,is_beneficiary,key)


//TODO why the following code do not work??
///Users/zhanghongwei/Documents/GitHub-Tower/OBP-API_Leumi/src/test/scala/code/api/v2_2_0/CreateCounterpartyTest.scala

//for {
//  JField("counterparty_id", JString(counterparty_id)) <- responsePost.body
//} yield (counterparty_id)
