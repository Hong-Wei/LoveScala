//The sample explanations here: 

import java.text.SimpleDateFormat
import java.util.TimeZone

//1 this date is from Leumi `Asia/Jerusalem` TimeZone
val leumiDate = "20170524"
val leumiTime = "130040"

val leumiTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss")
leumiTimeFormat.setTimeZone(TimeZone.getTimeZone("Asia/Jerusalem"))


val dateWithIsraelTime = leumiTimeFormat.parse(leumiDate + leumiTime)

val utcFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
val dateUtcAsString: String = utcFormat.format(dateWithIsraelTime)






