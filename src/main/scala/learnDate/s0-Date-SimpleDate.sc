//java处理时区的注意事项
//https://blog.csdn.net/wangpeng047/article/details/8560690

//also check this one: https://blog.csdn.net/halfclear/article/details/77573956

//effect timezone factors
//1 Operation System TimeZone
//2 data propagation TimeZone

import java.text.SimpleDateFormat
import java.util.{Calendar, Date, TimeZone}

//3 it is really important to set the TimeZone in the project.
TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai")) 
val dateFormatDefault = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //The default TimeZone is Shanghai

//4 another date TimeZone is GMT. -8 late than default
val dateFormatGMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
dateFormatGMT.setTimeZone(TimeZone.getTimeZone("UTC"));


//1 long number --> Date with different TimeZone
// Java Date is `It is an instant in time` , from UTC Timezone , `1359641834000L`
val dateObject: Date = new Date(1359641834000L)// 2013-1-31 22:17:14 (When you see DateTime, you need to know the default Timezone.)
dateObject.toString // when you see the value, it actually call the toString method. But toString use the default TimeZone.


val defaultDateString: String = dateFormatDefault.format(dateObject) //Shanghai

val dateStrTmp: String = dateFormatGMT.format(dateObject); // UTC
val defaultDateToInstantString = dateObject.toInstant.toString 



// 2 String --> Date
val dateString = "2013-1-31 22:17:14"
val dateStringToObjctDefalut: Date = dateFormatDefault.parse(dateString)
dateStringToObjctDefalut.getTime //1359641834000 the same as before.
dateStringToObjctDefalut.toInstant
val dateStrToObjectToStringDefault: String = dateFormatGMT.format(dateStringToObjctDefalut)

val dateStringToObjctGMT: Date = dateFormatGMT.parse(dateString)
dateStringToObjctGMT.toString // toSting will use the default TimeZone again..
dateStringToObjctGMT.toLocaleString
dateStringToObjctGMT.toGMTString
dateStringToObjctGMT.toInstant
dateStringToObjctGMT.getTime
val dateStrToObjectToStringGMT: String = dateFormatGMT.format(dateStringToObjctGMT)



//3 Calendar不涉及到日期与字符串的转化，因此不像SimpleDateFormat那么复杂，与日期转字符串的思路类似。
val date = new Date(1359641834000L);// 2013-1-31 22:17:14  
val calendar = Calendar.getInstance();
calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
// 或者可以 Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));  
calendar.setTime(date)
calendar.get(Calendar.YEAR) + "  "+ calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE)

calendar.getTime()

import java.util.Calendar

val calendar2 = Calendar.getInstance

calendar2.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
calendar2.getTime() 