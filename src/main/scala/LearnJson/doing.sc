import java.text.SimpleDateFormat
import java.util.{Date, TimeZone}

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
//OBPDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))

val date1 = OBPDateFormat.parse("1990-08-10T00:00:00.000Z")


case class OutBoundCaseClass(
  date: Date,
  dateString: String
)

case class InBoundCaseClass(
  date: Date,
  dateString: String
)

var outBoundCaseClass = OutBoundCaseClass(
  date = date1,
  dateString = ""
)

//1case class -->JValue
val classToJValue: JValue = Extraction.decompose(outBoundCaseClass)(formats)

//2 JValue -->String
val jValueToString: String = compactRender(classToJValue)

//3 String --> JValue
val stringToJValue: JValue = parse(jValueToString)

//4 JValue --> Case Class
val jvalueToCaseClass: InBoundCaseClass = Extraction.extract[InBoundCaseClass](stringToJValue)

//5 Case Class --> App
val classToJValue51: JValue = Extraction.decompose(outBoundCaseClass)(formats)
val jValueToStringCompact52: String = compactRender(classToJValue)



