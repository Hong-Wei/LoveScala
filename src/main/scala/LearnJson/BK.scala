//
///**
//  *
//  */
////BK 1 when ,how to use None, isEmpty ???who has these feature???
//
////BK 2 P168 ,why use tree structure to store data??
//
////BK day01 Swagger
////  /Users/zhanghongwei/Documents/workspaceScala/LoveScala/src/main/scala/Swagger
//
//
//
//class BK {}
////BK 1 learn sealed 关键字
// //check 14.14 Sealed Classes in Chapter14.scala
//
////BK 2 Learn Lift Json
//
//
////BK 3 Learn Future
//
//
//import argonaut._
//import Argonaut._
//import ArgonautShapeless._
//sealed trait Base
//case class First(i: String) extends Base
//case class Second(s: String) extends Base
//object aa extends App{
//  // encoding
////  val encode = EncodeJson.of[CC]
////
//////  val json = encode(CC(2, "a"))
//////  json.nospaces == """{"i":2,"s":"a"}"""
////
////  // decoding
////  val decode = DecodeJson.of[CC]
////
////  val result = decode.decodeJson(json)
////  result == DecodeResult.ok(CC(2, "a"))
//
//  // encoding
//  val encode: EncodeJson[Base] = EncodeJson.of[Base]
//
//  val json = encode(First("2"))
//  json.nospaces == """{"First":{"i":"2"}}"""
//
//  // decoding
//  val decode = DecodeJson.of[Base]
//
//  val result = decode.decodeJson(json)
//  result == DecodeResult.ok(First("2"))
//
//  import net.liftweb.json.Extraction.decompose
//  import net.liftweb.json.{compact, parse, render,pretty}
//
//
//
//  case class CreateTransaction(
//
//    // fromAccount
//    fromAccountBankId : String,
//    fromAccountId : String,
//
//    // transaction details
//    transactionRequestType: String,
//    transactionChargePolicy: String,
//    transactionRequestCommonBody: Base,
//
//    // toAccount or toCounterparty
//    toCounterpartyId: String,
//    toCounterpartyName: String,
//    toCounterpartyCurrency: String,
//    toCounterpartyRoutingAddress: String,
//    toCounterpartyRoutingScheme: String,
//    toCounterpartyBankRoutingAddress: String,
//    toCounterpartyBankRoutingScheme: String
//
//  )
//
//  val aa = CreateTransaction(
//    // fromAccount
//    fromAccountId = "8ca8a7e4-6d02-48e3-a029-0b2bf89de9f0",
//    fromAccountBankId = "gh.29.uk",
//
//    // transaction details
//    transactionRequestType = "SANDBOX_TAN",
//    transactionChargePolicy = "RECEIVER",
//    transactionRequestCommonBody= First("1"),
//    // toAccount or toCounterparty
//    toCounterpartyId = "1234",
//    toCounterpartyName = "obp",
//    toCounterpartyCurrency = "EUR",
//    toCounterpartyRoutingAddress = "1234",
//    toCounterpartyRoutingScheme = "OBP",
//    toCounterpartyBankRoutingAddress = "12345",
//    toCounterpartyBankRoutingScheme = "OBP"
//  )
////  implicit val formats = net.liftweb.json.DefaultFormats
////  val decompose1 = decompose(aa)
////  val compact1 = compact(render((decompose1)))
//// println(compact1)
////  parse("""{"fromAccountBankId":"gh.29.uk","fromAccountId":"8ca8a7e4-6d02-48e3-a029-0b2bf89de9f0","transactionRequestType":"SANDBOX_TAN","transactionChargePolicy":"RECEIVER","transactionRequestCommonBody":{"i":"1"},"toCounterpartyId":"1234","toCounterpartyName":"obp","toCounterpartyCurrency":"EUR","toCounterpartyRoutingAddress":"1234","toCounterpartyRoutingScheme":"OBP","toCounterpartyBankRoutingAddress":"12345","toCounterpartyBankRoutingScheme":"OBP"}""").extract[CreateTransaction]
////  parse(compact1).extract[CreateTransaction]
//
////  val decode2 = DecodeJson.of[CreateTransaction]
//
////  val result = decode2.decodeJson(compact1)
//
//
//  val encode2: EncodeJson[CreateTransaction] = EncodeJson.of[CreateTransaction]
//
//  val json2 = encode2(aa)
//  println(json2.nospaces) 
//
//  // decoding
//  val decode2 = DecodeJson.of[CreateTransaction]
//
//  val result2 = decode2.decodeJson(json2)
//  println(result2)
//
//
//}
//
