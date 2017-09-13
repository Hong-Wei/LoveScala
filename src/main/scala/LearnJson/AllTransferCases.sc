import net.liftweb.json._

import scala.text.Document

implicit val formats = net.liftweb.json.DefaultFormats

case class InternalCaseClass(
                                 text: String
                               )


case class OutBoundCaseClass(
                              cbsToken: String,
                              backendMessages: List[InternalCaseClass],
                              owners: List[String]
                            )


case class InBoundCaseClass(
                             oneMore: Option[String], 
                             cbsToken: Option[String], 
                             backendMessages: List[InternalCaseClass], 
                             owners: List[String]
                            )


val oneCaseClass: OutBoundCaseClass = OutBoundCaseClass("cbsToken", List(InternalCaseClass("text")), List(""))


//1case class -->JValue
val classToJValue: JValue = Extraction.decompose(oneCaseClass)

//2 JValue -->String
//2.1 JValue -->Document first.
val jValueToDocument: Document = render(classToJValue)
//2.2 Document-->String
val documnetToStringCompact: String = compact(jValueToDocument)
val documnetToStringPretty: String = pretty(jValueToDocument)

//2.1 and 2.2 one way:
val jValueToStringCompact: String = compactRender(classToJValue)




//3.0 String --> JValue
val stringToJValue: JValue = parse(jValueToStringCompact)

//4.0 JValue --> Case Class
val jvalueToCaseClass: InBoundCaseClass = Extraction.extract[InBoundCaseClass](stringToJValue)


