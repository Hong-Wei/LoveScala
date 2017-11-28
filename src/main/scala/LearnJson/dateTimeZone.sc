//I think it is important to share our knowledge about the `date` for now.
//  Each time we transfer `String`<--> `Date`, we alway use the timeZone. We transfer many times for all the data flow: from Adapter -->kafka-->connector --> api level.
//Each transfer used the `timeZone` (I mean the liftweb json).


import java.text.SimpleDateFormat
import java.util.TimeZone


val mydate= "19901128"
val defaultFilterFormat: SimpleDateFormat = new SimpleDateFormat("yyyyMMdd")

val simpleTransactionDateFormat = new SimpleDateFormat("yyyyMMdd")
simpleTransactionDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))


val local= defaultFilterFormat.parse(mydate)
val utc = simpleTransactionDateFormat.parse(mydate)

defaultFilterFormat.getTimeZone
simpleTransactionDateFormat.getTimeZone
