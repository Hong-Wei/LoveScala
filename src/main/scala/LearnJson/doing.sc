import net.liftweb.json._

import scala.collection.immutable.List

implicit val formats = net.liftweb.json.DefaultFormats

trait links
case class Balances(balances: String) extends links
case class Transactions(trasactions: String) extends links
case class ViewAccount(viewAccount: String) extends links

case class CoreAccountJsonV1(
  id: String,
  iban: String,
  currency: String,
  accountType: String,
  cashAccountType: String,
//  _links: List[links],
  name: String
)

case class CoreAccountsJsonV1(`account-list`: List[CoreAccountJsonV1])

val coreAccountJson_v1 = CoreAccountJsonV1(
  id = "3dc3d5b3-7023-4848-9853-f5400a64e80f",
  iban = "DE2310010010123456789",
  currency = "EUR",
  accountType = "Girokonto",
  cashAccountType = "CurrentAccount",
//  _links = List(
//    Balances("/v1/accounts/3dc3d5b3-7023-4848-9853-f5400a64e80f/balances"),
//    Transactions("/v1/accounts/3dc3d5b3-7023-4848-9853-f5400a64e80f/transactions")),
  name = "Main Account"
)
val coreAccountsJsonV1 = CoreAccountsJsonV1(List(coreAccountJson_v1))

var oneCaseClass = coreAccountsJsonV1
//val twoCaseClass:  = InternalCaseClass("text")


//1case class -->JValue
val classToJValue: JValue = Extraction.decompose(oneCaseClass)

//2 JValue -->String
val jValueToStringCompact: String = compactRender(classToJValue)


//3 String --> JValue
val stringToJValue: JValue = parse(jValueToStringCompact)

//4 JValue --> Case Class
val jvalueToCaseClass = Extraction.extract[CoreAccountJsonV1](stringToJValue)
//val jvalueToCaseClass12: InBoundCaseClass2 = Extraction.extract[InBoundCaseClass2](stringToJValue)
//val jvalueToCaseClass2: InBoundCaseClass = Extraction.extract[InBoundCaseClass](parse("{\n  \"1\":\"2\"\n}"))
//val jvalueToCaseClass3 = Extraction.extractOpt(parse("{\n  \"1\":\"2\"\n}"))//[InBoundCaseClass2]


