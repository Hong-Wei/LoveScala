package LearnJson


/**
  *
  */
//BK 1 when ,how to use None, isEmpty ???who has these feature???

//BK 2 P168 ,why use tree structure to store data??

//BK day01 Swagger
//  /Users/zhanghongwei/Documents/workspaceScala/LoveScala/src/main/scala/Swagger



class BK2 {}
//BK 1 learn sealed 关键字
 //check 14.14 Sealed Classes in Chapter14.scala

//BK 2 Learn Lift Json


//BK 3 Learn Future


import argonaut.Argonaut._
import argonaut._


sealed trait TransactionRequestCommonBodyJSON {
  val value : AmountOfMoneyJsonV121
  val description: String
}
case class AmountOfMoneyJsonV121(
                                  currency : String,
                                  amount : String
                                )
// the data from endpoint, extract as valid JSON
case class TransactionRequestBodyCounterpartyJSON(
                                                   value: AmountOfMoneyJsonV121,
                                                   description: String,
                                                   charge_policy: String,
                                                   hongwei: String
                                                 ) extends TransactionRequestCommonBodyJSON



case class CreateTransaction(

                              // fromAccount
                              fromAccountBankId : String,
                              fromAccountId : String,

                              // transaction details
                              transactionRequestType: String,
                              transactionChargePolicy: String,
                              transactionRequestCommonBody: TransactionRequestCommonBodyJSON,

                              // toAccount or toCounterparty
                              toCounterpartyId: String,
                              toCounterpartyName: String,
                              toCounterpartyCurrency: String,
                              toCounterpartyRoutingAddress: String,
                              toCounterpartyRoutingScheme: String,
                              toCounterpartyBankRoutingAddress: String,
                              toCounterpartyBankRoutingScheme: String

                            )

object a1 extends App{

  val bodyValue = AmountOfMoneyJsonV121("EUR", "1000")

  val transactionRequestBodyCounterpartyJSON = TransactionRequestBodyCounterpartyJSON(
    bodyValue,
    "A description for the transaction to the counterparty",
    "SHARED", 
    "hongwei"
  )
  // encoding
//  val encode = EncodeJson.of[CC]
//
////  val json = encode(CC(2, "a"))
////  json.nospaces == """{"i":2,"s":"a"}"""
//
//  // decoding
//  val decode = DecodeJson.of[CC]
//
//  val result = decode.decodeJson(json)
//  result == DecodeResult.ok(CC(2, "a"))

  // encoding
//  val encode: EncodeJson[TransactionRequestCommonBodyJSON] = EncodeJson.of[TransactionRequestCommonBodyJSON]
//
//  val json = encode(transactionRequestBodyCounterpartyJSON)
//
//  // decoding
//  val decode = DecodeJson.of[TransactionRequestCommonBodyJSON]
//
//  val result = decode.decodeJson(json)


  val aa = CreateTransaction(
    // fromAccount
    fromAccountId = "8ca8a7e4-6d02-48e3-a029-0b2bf89de9f0",
    fromAccountBankId = "gh.29.uk",

    // transaction details
    transactionRequestType = "SANDBOX_TAN",
    transactionChargePolicy = "RECEIVER",
    transactionRequestCommonBody= transactionRequestBodyCounterpartyJSON,
    // toAccount or toCounterparty
    toCounterpartyId = "1234",
    toCounterpartyName = "obp",
    toCounterpartyCurrency = "EUR",
    toCounterpartyRoutingAddress = "1234",
    toCounterpartyRoutingScheme = "OBP",
    toCounterpartyBankRoutingAddress = "12345",
    toCounterpartyBankRoutingScheme = "OBP"
  )
//  implicit val formats = net.liftweb.json.DefaultFormats
//  val decompose1 = decompose(aa)
//  val compact1 = compact(render((decompose1)))

//  parse("""{"fromAccountBankId":"gh.29.uk","fromAccountId":"8ca8a7e4-6d02-48e3-a029-0b2bf89de9f0","transactionRequestType":"SANDBOX_TAN","transactionChargePolicy":"RECEIVER","transactionRequestCommonBody":{"i":"1"},"toCounterpartyId":"1234","toCounterpartyName":"obp","toCounterpartyCurrency":"EUR","toCounterpartyRoutingAddress":"1234","toCounterpartyRoutingScheme":"OBP","toCounterpartyBankRoutingAddress":"12345","toCounterpartyBankRoutingScheme":"OBP"}""").extract[CreateTransaction]
//  parse(compact1).extract[CreateTransaction]

//encoding
//  val encode2: EncodeJson[CreateTransaction] = EncodeJson.of[CreateTransaction]
//
//  val json2: Json = encode2(aa)
//  private val nospacesString = json2.nospaces
//  println(nospacesString)
  
  
  
  private val x: CreateTransaction = """{"transactionRequestType":"SANDBOX_TAN","toCounterpartyRoutingAddress":"1234","toCounterpartyBankRoutingScheme":"OBP","fromAccountId":"8ca8a7e4-6d02-48e3-a029-0b2bf89de9f0","transactionChargePolicy":"RECEIVER","toCounterpartyId":"1234","toCounterpartyRoutingScheme":"OBP","toCounterpartyBankRoutingAddress":"12345","toCounterpartyName":"obp","toCounterpartyCurrency":"EUR","transactionRequestCommonBody":{"TransactionRequestBodyCounterpartyJSON":{"hongwei":"hongwei","charge_policy":"SHARED","description":"A description for the transaction to the counterparty","value":{"amount":"1000","currency":"EUR"}}},"fromAccountBankId":"gh.29.uk"}""".decodeOption[CreateTransaction].get
  println(x)


// decoding
//  val decode2 = DecodeJson.of[CreateTransaction]
//
//  val result2 = decode2.decodeJson(json2)
//  private val b = result2.result.right.get.transactionRequestCommonBody.isInstanceOf[TransactionRequestBodyCounterpartyJSON]
//  println(result2)
//  println(result2.result.right.get.transactionRequestCommonBody.asInstanceOf[TransactionRequestBodyCounterpartyJSON].charge_policy)
//  println(result2.result.right.get.transactionRequestCommonBody.asInstanceOf[TransactionRequestBodyCounterpartyJSON].hongwei)

}

