import java.text.SimpleDateFormat
import java.util.{Date, GregorianCalendar, TimeZone}

import net.liftweb.json._

  implicit val formats: Formats = new Formats {

    import java.text.{ParseException, SimpleDateFormat}

    protected def dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZZZZ")
    
    val dateFormat = new DateFormat {
      def parse(s: String) = try {
        Some(formatter.parse(s))
      } catch {
        case e: ParseException => None
      }

      def format(d: Date) = formatter.format(d)

      private def formatter = {
        val f = dateFormatter
        f.setTimeZone(new GregorianCalendar().getTimeZone)
//        f.setTimeZone(TimeZone.getTimeZone("UTC"))
        f
      }
    }

  }

case class InternalCaseClass(
  date: Date
)

val date: Date = new Date()
TimeZone.getDefault

val dateFormatGMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZZZZZZZZ");
dateFormatGMT.setTimeZone(TimeZone.getTimeZone("UTC"));
dateFormatGMT.format(date)




val twoCaseClass: InternalCaseClass = InternalCaseClass(date)


//1case class -->JValue
val classToJValue: JValue = Extraction.decompose(twoCaseClass)(formats)
// This can change the date to TimeZone format.

//2 JValue -->String

//TimeZone.setDefault(new GregorianCalendar().getTimeZone)
val jValueToStringCompact: String = compactRender(classToJValue)