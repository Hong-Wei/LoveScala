//trait TransactionRequestCommonBodyJSON {
//  val description: String
//}
//
//object TransactionRequestCommonBodyJSON extends TransactionRequestCommonBodyJSON {
//  override val description: String = "hongwei"
//}
//
//// the data from endpoint, extract as valid JSON
//case class TransactionRequestBodyCounterpartyJSON(
//                                                   description: String,
//                                                   hongwei: String
//                                                 ) extends TransactionRequestCommonBodyJSON
//
//case class CreateTransaction(
//                              transactionRequestCommonBody: TransactionRequestCommonBodyJSON,
//                              toCounterpartyId: String
//                            )
//
//object testJsonTrait extends App {
//
//  val transactionRequestBodyCounterpartyJSON = TransactionRequestBodyCounterpartyJSON(
//    "A description for the transaction to the counterparty",
//    "hongwei"
//  )
//
//  import net.liftweb.json.Extraction.decompose
//  import net.liftweb.json.{compact, parse, render, pretty}
//
//
//  val createTransaction = CreateTransaction(
//    transactionRequestCommonBody = transactionRequestBodyCounterpartyJSON,
//    toCounterpartyId = "1234"
//
//  )
//  implicit val formats = net.liftweb.json.DefaultFormats
//  val decompose1 = decompose(createTransaction)
//  val compact1 = compact(render((decompose1)))
//  private val toCounterpartyId = parse(compact1).extract[CreateTransaction].toCounterpartyId
//
//}
//
