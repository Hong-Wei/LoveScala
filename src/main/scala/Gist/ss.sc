trait TransactionRequestCommonBodyJSON1 {
  val value : String
  val description: String
}

// the common parts of four types
// note: there is TransactionRequestCommonBodyJSON trait, so this case class call TransactionRequestBodyCommonJSON
case class TransactionRequestBodyCommonJSON(
  value: String,
  description: String,
  description2: String
) extends TransactionRequestCommonBodyJSON1

TransactionRequestBodyCommonJSON("1","2","3")