import java.util.{Date, TimeZone}

//JVM has the default TimeZone, you need set it properly.

//Just date.now, different timezones have different values:
TimeZone.setDefault(TimeZone.getTimeZone("GMT")) //GMT +00
new Date() // GMT       -- Thu Apr 26 20:36:44 GMT 2018
new Date().getTime // GMT       -- 1524775004351


TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"))// Shanghai GMT+08
new Date() // Shanghai  -- Fri Apr 27 04:36:29 CST 2018
new Date().getTime // Shanghai  -- 1524775017873




//result:
//even if the same Date long: 1524775017873, with different timezones will have different values.


TimeZone.setDefault(TimeZone.getTimeZone("GMT")) //GMT +00
val dateGMT= new Date(1524775017873L)          //Thu Apr 26 20:36:57 GMT 2018
val a24 = dateGMT.toString
val a25 = dateGMT.toLocaleString
val a26 = dateGMT.toGMTString
val a27 = dateGMT.toInstant
TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"))// Shanghai GMT+08
val dateShanghai = new Date(1524775017873L)     //Fri Apr 27 04:36:57 CST 2018
val a28 = dateShanghai.toString
val a29 = dateShanghai.toLocaleString
val a30 = dateShanghai.toGMTString
val a31 = dateShanghai.toInstant

