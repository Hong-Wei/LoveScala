import net.liftweb.json._

implicit val formats = net.liftweb.json.DefaultFormats

case class InternalCaseClass(
                                 text: String
                               )


case class OutBoundCaseClass(
                              cbsToken: String,
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
                             oneMore: String,
                             cbsToken: Option[String],
                             backendMessages: List[InternalCaseClass],
                             owners: List[String]
                           )


var oneCaseClass: OutBoundCaseClass = OutBoundCaseClass("cbsToken", List(InternalCaseClass("text")), List(""),BigDecimal(1))
val twoCaseClass: InternalCaseClass = InternalCaseClass("text")


//1case class -->JValue
val classToJValue: JValue = Extraction.decompose(twoCaseClass)

//2 JValue -->String
//2.1 JValue -->Document first.
val jValueToDocument: scala.text.Document = render(classToJValue)
//2.2 Document-->String
val documnetToStringCompact: String = compact(jValueToDocument)
val documnetToStringPretty: String = pretty(jValueToDocument)

//2.1 and 2.2 one way:
val jValueToStringCompact: String = compactRender(classToJValue)




//3.0 String --> JValue
val stringToJValue: JValue = parse(jValueToStringCompact)

//4.0 JValue --> Case Class
val jvalueToCaseClass: InBoundCaseClass = Extraction.extract[InBoundCaseClass](stringToJValue)
val jvalueToCaseClass2: InBoundCaseClass = Extraction.extract[InBoundCaseClass](parse("{\n  \"1\":\"2\"\n}"))
val jvalueToCaseClass3 = Extraction.extractOpt(parse("{\n  \"1\":\"2\"\n}"))//[InBoundCaseClass2]


