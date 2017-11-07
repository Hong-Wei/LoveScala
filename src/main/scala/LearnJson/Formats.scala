package LearnJson

import java.text.SimpleDateFormat
import java.util.{Date, GregorianCalendar, TimeZone}

import com.typesafe.scalalogging.Logger

/**
  * Created by zhanghongwei on 08/09/2017.
  */
object Formats extends App {
  val logger = Logger("my")

  
  import net.liftweb.json._
  implicit val formats = net.liftweb.json.DefaultFormats
//  implicit val formats = new DefaultFormats {
//    override def dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//    dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"))
//    dateFormatter.setTimeZone(new GregorianCalendar().getTimeZone())
//  }
//  formats.setTimeZone(new GregorianCalendar().getTimeZone)
//  formats.setTimeZone(new GregorianCalendar().getTimeZone)

//
//  val formats: SimpleDateFormat = {
//    val simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
//    simpleDateFormat
//  }

//  implicit val formats: Formats = new Formats {
//
//    import java.text.{ParseException, SimpleDateFormat}
//
//    val dateFormat = new DateFormat {
//      def parse(s: String) = try {
//        Some(formatter.parse(s))
//      } catch {
//        case e: ParseException => None
//      }
//
//      def format(d: Date) = formatter.format(d)
//
//      private def formatter = {
//        val f = dateFormatter
////        f.setTimeZone(new GregorianCalendar().getTimeZone)
//        f.setTimeZone(TimeZone.getTimeZone("UTC"))
//        f
//      }
//    }
//
//    protected def dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//  }
  val OBPDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  println(OBPDateFormat.getTimeZone)
  OBPDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
  println(OBPDateFormat.getTimeZone)
  val MfDateFormat = new SimpleDateFormat("yyyyMMdd")
  val MfDateFormat2 = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss.SSS'Z'")

  case class InternalCaseClass(
                                text: String
                              )

  case class OutBoundCaseClass(
                                cbsToken: String,
                                backendMessages: List[InternalCaseClass],
                                owners: List[String],
                                number: Int, // This must have some value. if BigDecimal("") --> Extraction java.lang.NumberFormatException
                                date: Date // This must have some value. if BigDecimal("") --> Extraction java.lang.NumberFormatException
                              )


  case class InBoundCaseClass(
                               oneMore: Option[String],
                               cbsToken: Option[String],
                               backendMessages: List[InternalCaseClass],
                               owners: List[String],
                               number: Int, // This must have some value. if BigDecimal("") --> Extraction java.lang.NumberFormatException
                               date: Date // This must have some value. if BigDecimal("") --> Extraction java.lang.NumberFormatException

                             )


  var outCaseClass: OutBoundCaseClass = OutBoundCaseClass(
    cbsToken= "cbsToken",
    backendMessages= List(InternalCaseClass("text")),
    owners= List(""),
    number = 1,
    date = OBPDateFormat.parse("1990-08-10T00:00:00.000Z")
  )
  logger.info("1"+outCaseClass.toString)

  //1case class -->JValue
  val classToJValue: JValue = Extraction.decompose(outCaseClass)(formats)

//  //2 JValue -->String
  //2.1 JValue -->Document first.
  val jValueToDocument: scala.text.Document = render(classToJValue)
  //2.2 Document-->String
//  val documnetToStringCompact: String = compact(jValueToDocument)
//  val documnetToStringPretty: String = pretty(jValueToDocument)
//
//  //2.1 and 2.2 one way:
  val jValueToStringCompact: String = compactRender(classToJValue)
  logger.info("2"+jValueToStringCompact)
//
//
  //3.0 String --> JValue
  val stringToJValue: JValue = parse(jValueToStringCompact)
  logger.info("3"+stringToJValue.toString)

  //4.0 JValue --> Case Class
  val jvalueToCaseClass: InBoundCaseClass = Extraction.extract[InBoundCaseClass](stringToJValue)//(formats)
  logger.info("4"+jvalueToCaseClass.toString)
//  val jvalueToCaseClass2: InBoundCaseClass = Extraction.extract[InBoundCaseClass](parse("{\n  \"1\":\"2\"\n}"))
//  val jvalueToCaseClass3 = Extraction.extractOpt(parse("{\n  \"1\":\"2\"\n}"))//[InBoundCaseClass2]
//

  //5.0 Case Class --> APp
  val classToJValue51: JValue = Extraction.decompose(outCaseClass)(formats)
  val jValueToStringCompact52: String = compactRender(classToJValue)
  logger.info("5"+jValueToStringCompact52)


  val outputString = "1990-08-10T00:00:00.000Z"
  val inputString = "19900810"
  val date3: Date =MfDateFormat.parse(inputString)
  val date2: Date =MfDateFormat2.parse(inputString+"T00:00:00.000Z")
  logger.info("6.2"+date2)
  logger.info("6.1"+date3)
  logger.info("7"+jValueToStringCompact52)

}


