import net.liftweb.json._

implicit val formats = net.liftweb.json.DefaultFormats

case class InternalCaseClass(
                                 text: String
                               )


case class OutBoundCaseClass(
                              cbsToken: String,
                              jValue : JValue,
                              backendMessages: List[InternalCaseClass],
                              owners: List[String],
                              Number: BigDecimal
                            )


case class InBoundCaseClass(
                             oneMore: Option[String], 
                             cbsToken: Option[String], 
                             backendMessages: List[InternalCaseClass], 
                             owners: List[String]
                            )

case class InBoundCaseClass2(
                             oneMore: Option[String],
                             jValue : JValue,
                             cbsToken: String,
                             backendMessages: List[InternalCaseClass],
                             owners: List[String]
                           )


var oneCaseClass: OutBoundCaseClass = OutBoundCaseClass(null, null,Nil, List(""),BigDecimal(1))
val twoCaseClass: InternalCaseClass = InternalCaseClass("text")


//1case class -->JValue
val classToJValue: JValue = Extraction.decompose(oneCaseClass)

//2 JValue -->String
val jValueToStringCompact: String = compactRender(classToJValue)


//3 String --> JValue
val stringToJValue: JValue = parse(jValueToStringCompact)

//4 JValue --> Case Class
val jvalueToCaseClass: InBoundCaseClass = Extraction.extract[InBoundCaseClass](stringToJValue)
val jvalueToCaseClass12: InBoundCaseClass2 = Extraction.extract[InBoundCaseClass2](stringToJValue)
val jvalueToCaseClass2: InBoundCaseClass = Extraction.extract[InBoundCaseClass](parse("{\n  \"1\":\"2\"\n}"))
val jvalueToCaseClass3 = Extraction.extractOpt(parse("{\n  \"1\":\"2\"\n}"))//[InBoundCaseClass2]


