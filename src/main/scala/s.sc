import java.text.SimpleDateFormat
import java.util.TimeZone

val OBPDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
println(OBPDateFormat.getTimeZone)
OBPDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Tel_Aviv"))
println(OBPDateFormat.getTimeZone)