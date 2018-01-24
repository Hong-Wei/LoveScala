import net.liftweb.json.Serialization._
import net.liftweb.json._

class Interval(start: Long, end: Long) {
  val startTime = start
  val endTime = end
}


class IntervalSerializer extends Serializer[Interval] {
  private val IntervalClass = classOf[Interval]

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), Interval] = {
    case (TypeInfo(IntervalClass, _), json) => json match {
      case JObject(JField("start", JInt(s)) :: JField("end", JInt(e)) :: Nil) =>
        new Interval(s.longValue, e.longValue)
      case x => throw new MappingException("Can't convert " + x + " to Interval")
    }
  }

  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case x: Interval =>
      JObject(JField("start", JInt(BigInt(x.startTime))) ::
        JField("end",   JInt(BigInt(x.endTime))) :: Nil)
  }
}

implicit val formats = Serialization.formats(NoTypeHints) + new IntervalSerializer


val ser: String = writePretty(new Interval(1,2))
println(ser)

val read1: Interval = read(ser)(formats,(manifest[Interval]))
read1.endTime
read1.startTime
