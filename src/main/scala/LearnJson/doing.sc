


import net.liftweb.json._
import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
val object1: JValue = parse(""" { "numbers" : [1, 2, 3, 4] } """)


val json = List(1, 2, 3)

//compactRender(json)


case class Child (a:String, b:Int, c: Option[String])
import net.liftweb.json._
import net.liftweb.json.Serialization.{read, write}
implicit val formats = Serialization.formats(NoTypeHints)
val ser = write(Child("Mary", 5, None))
read[Child](ser)


val dateTime = "11/15/2013 08:00:00";
// Format for input
val dtf = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
// Parsing the date
val jodatime: DateTime = dtf.parseDateTime(dateTime);
// Format for output
val dtfOut = DateTimeFormat.forPattern("MM/dd/yyyy");
// Printing the date
dtfOut.print(jodatime)