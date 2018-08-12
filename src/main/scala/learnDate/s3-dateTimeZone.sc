//I think it is important to share our knowledge about the `date` for now.
//  Each time we transfer `String`<--> `Date`, we alway use the timeZone. 
// We transfer many times for all the data flow: 
// from Adapter -->kafka-->connector --> api level.
//Each transfer used the `timeZone` (I mean the liftweb json).

import java.text.{DateFormat, SimpleDateFormat}
import java.time.format.DateTimeFormatter
import java.util.{Date, TimeZone}
//TimeZone.setDefault(TimeZone.getTimeZone("UTC"))


//1 dateFormat 
val myDateString = "20160201"
val myDateObject = new Date() 

val myOwnDateFormat = new SimpleDateFormat("yyyyMMdd")

//1.1 format : Date --> String
DateFormat.getDateInstance.format(myDateObject)
DateFormat.getTimeInstance.format(myDateObject)
DateFormat.getDateTimeInstance.format(myDateObject)

//all the sampleDateFormat patterns.
val p1 = "yyyy.MM.dd G 'at' HH:mm:ss z"
val p2 = "EEE, MMM d, ''yy"
val p3 = "h:mm a"
val p4 = "hh 'o''clock' a, zzzz"
val p5 = "K:mm a, z"
val p6 = "yyyyy.MMMMM.dd GGG hh:mm aaa"
val p7 = "EEE, d MMM yyyy HH:mm:ss Z"
val p8 = "yyMMddHHmmssZ"
val p9 = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZZZZ"
val p10 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
val p11 = "yyyy-MM-dd'T'HH:mm:ss.SSSXX"
val p12 = "yyyy-MM-dd'T'HH:mm:ss.SSSX"
val p14 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
val p13 = "YYYY-'W'ww-u"

val pf1 =new SimpleDateFormat(p1).format(myDateObject)
val pf2 =new SimpleDateFormat(p2).format(myDateObject) 
val pf3 =new SimpleDateFormat(p3).format(myDateObject) 
val pf4 =new SimpleDateFormat(p4).format(myDateObject) 
val pf5 =new SimpleDateFormat(p5).format(myDateObject) 
val pf6 =new SimpleDateFormat(p6).format(myDateObject) 
val pf7 =new SimpleDateFormat(p7).format(myDateObject) 
val pf8 =new SimpleDateFormat(p8).format(myDateObject) 
val pf9 =new SimpleDateFormat(p9).format(myDateObject) 
val pf10=new SimpleDateFormat(p10).format(myDateObject) 
val pf11=new SimpleDateFormat(p11).format(myDateObject) 
val pf12=new SimpleDateFormat(p12).format(myDateObject) 
val pf14=new SimpleDateFormat(p14).format(myDateObject) 
val pf13=new SimpleDateFormat(p11).format(myDateObject) 



myOwnDateFormat.format(myDateObject)



//1.2 parse : String --> Date
val localDate: Date = myOwnDateFormat.parse(myDateString)
val localDateTimeLong = localDate.getTime




 
//2 date timeZone
//2.1 setTimeZone you can set it for 
val differentZone = new SimpleDateFormat("yyyyMMdd")
differentZone.setTimeZone(TimeZone.getTimeZone("America/New_York"))
val differentZoneDate: Date = differentZone.parse(myDateString)
val differentZoneDateTimeLong = differentZoneDate.getTime
myOwnDateFormat.getTimeZone
differentZone.getTimeZone
val timeZoneId = TimeZone.getTimeZone("GMT-8").getID()
val timeZoneId2: TimeZone = TimeZone.getTimeZone("UTC")


//3 date and time Separate



//4 examples:
//eg4.1 UK Standar : https://openbanking.atlassian.net/wiki/spaces/DZ/pages/128909480/Balances+v2.0.0
//  String :  2017-04-05T10:43:07+00:00 
//  Pattern :  yyyy-MM-dd'T'HH:mm:ssXXX
val theUKStandarString = "2017-04-05T10:43:07+00:00"
val theUKStandarDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
theUKStandarDateTimeFormat.getTimeZone
//theUKStandarDateTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT+0000"))
//theUKStandarDateTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
theUKStandarDateTimeFormat.getTimeZone
val theUKDateObject = theUKStandarDateTimeFormat.parse(theUKStandarString)
//theUKStandarDateTimeFormat.getTimeZone.getDisplayName(true, TimeZone.LONG)
//theUKStandarDateTimeFormat.applyPattern("yyyy-MM-dd'T'HH:mm:ssXX")

val UKStardarString = theUKStandarDateTimeFormat.format(theUKDateObject) // 2018-04-25T09:17:22+02:00
//https://stackoverflow.com/questions/27920239/jodatime-datetime-with-0000-instead-z
//https://github.com/logmatic/logmatic-python/issues/6
//https://social.msdn.microsoft.com/Forums/en-US/0841b9c7-72b5-4743-aa9d-5776c3795db1/utc-datetime-canonical-z-ignored?forum=xmlandnetfx
UKStardarString.replaceAll("Z", "+00:00")



val formatTestLenient = new SimpleDateFormat("MM/dd/yyyy")
formatTestLenient.parse("03/11/2013 asfasf") //correct

formatTestLenient.setLenient(true)
formatTestLenient.parse("03/11/2013 asfasf") //throw exception

