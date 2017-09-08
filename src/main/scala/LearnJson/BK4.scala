package LearnJson

/**
  * Created by zhanghongwei on 08/09/2017.
  */
object BK4 extends App{

  import net.liftweb.json.Extraction.decompose
  import net.liftweb.json.JsonAST.{JArray, JField, JObject, JString}
  import net.liftweb.json._

  implicit val formats = net.liftweb.json.DefaultFormats

  case class InboundStatusMessage(
                                   source: String,
                                   status: String,
                                   errorCode: String,
                                   text: String
                                 )
  
  case class InboundAccountJune20177(
                                      errorCode: String,
                                      cbsToken: String,
                                      backendMessages: List[InboundStatusMessage],
                                      bankId: String,
                                      branchId: String,
                                      accountId: String,
                                      accountNumber: String,
                                      accountType: String,
                                      balanceAmount: String,
                                      balanceCurrency: String,
                                      owners: List[String],
                                      viewsToGenerate: List[String],
                                      bankRoutingScheme: String,
                                      bankRoutingAddress: String,
                                      branchRoutingScheme: String,
                                      branchRoutingAddress: String,
                                      accountRoutingScheme: String,
                                      accountRoutingAddress: String
                                    )

  val aa: JValue = decompose(InboundAccountJune20177(
    errorCode = "",
    cbsToken ="cbsToken1",
    List(InboundStatusMessage("ESB", "Success", "0", "OK")),
    bankId = "gh.29.uk",
    branchId = "222",
    accountId = "8ca8a7e4-6d02-48e3-a029-0b2bf89de9f0",
    accountNumber = "123",
    accountType = "AC",
    balanceAmount = "50",
    balanceCurrency = "EUR",
    owners = "Susan" :: " Frank" :: Nil,
    viewsToGenerate = "Public" :: "Accountant" :: "Auditor" :: Nil,
    bankRoutingScheme = "iban",
    bankRoutingAddress = "bankRoutingAddress",
    branchRoutingScheme = "branchRoutingScheme",
    branchRoutingAddress = " branchRoutingAddress",
    accountRoutingScheme = "accountRoutingScheme",
    accountRoutingAddress = "accountRoutingAddress"
  )::InboundAccountJune20177(
    errorCode = "",
    cbsToken ="cbsToken1",
    List(InboundStatusMessage("ESB", "Success", "0", "OK")),
    bankId = "gh.29.uk",
    branchId = "222",
    accountId = "8ca8a7e4-6d02-48e3-a029-0b2bf89de9f0",
    accountNumber = "123",
    accountType = "AC",
    balanceAmount = "50",
    balanceCurrency = "EUR",
    owners = "Susan" :: " Frank" :: Nil,
    viewsToGenerate = "Public" :: "Accountant" :: "Auditor" :: Nil,
    bankRoutingScheme = "iban",
    bankRoutingAddress = "bankRoutingAddress",
    branchRoutingScheme = "branchRoutingScheme",
    branchRoutingAddress = " branchRoutingAddress",
    accountRoutingScheme = "accountRoutingScheme",
    accountRoutingAddress = "accountRoutingAddress"
  )::Nil)

  def getErrors(message: String) : List[String] = {
    val json: JValue = parse(message)
    val listOfValues = for {
      JArray(objects) <- json
      JObject(obj) <- objects
      JField("errorCode", JString(fieldName)) <- obj
    } yield fieldName
    listOfValues
  }

  getErrors((compact(render(Extraction.decompose(aa)))))


}


