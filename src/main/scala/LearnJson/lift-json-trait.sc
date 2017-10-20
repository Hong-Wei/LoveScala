import net.liftweb.json._

import scala.text.Document

//implicit val formats = net.liftweb.json.DefaultFormats

trait InternalCaseClassTrait{
  def text: String
}
case class InternalCaseClass(
                              text: String
                            ) extends InternalCaseClassTrait


case class InternalCaseClass2(
                              text: String
                            ) extends InternalCaseClassTrait


case class OutBoundCaseClass(
                              cbsToken: String,
                              aTrait: InternalCaseClassTrait,
                              backendMessages: List[InternalCaseClass],
                              owners: List[String]
                            )


case class InBoundCaseClass(
                             oneMore: Option[String],
                             aTrait: InternalCaseClassTrait,
                             cbsToken: Option[String],
                             backendMessages: List[InternalCaseClass],
                             owners: List[String]
                           )


var oneCaseClass: OutBoundCaseClass = OutBoundCaseClass("cbsToken",InternalCaseClass("trait"), List(InternalCaseClass("text")), List(""))
val twoCaseClass: InternalCaseClass = InternalCaseClass("text")


implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[InternalCaseClass],classOf[InternalCaseClass2])))

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
//val jvalueToCaseClass2: InBoundCaseClass = Extraction.extract[InBoundCaseClass](parse("{\n  \"1\":\"2\"\n}"))


