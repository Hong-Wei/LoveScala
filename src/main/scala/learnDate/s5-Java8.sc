//1 Local Date-Time API
import java.time._
import java.util.Date
// Get the current date and time// Get the current date and time

val currentTime = LocalDateTime.now

val date1 = currentTime.toLocalDate
val time1 = currentTime.toLocalTime

val month = currentTime.getMonth
val day = currentTime.getDayOfMonth
val seconds = currentTime.getSecond

//2012-04-10
val date2 = currentTime.withDayOfMonth(10).withYear(2012)
//12 december 2014
val date3 = LocalDate.of(2014, Month.DECEMBER, 12)
//22 hour 15 minutes
val date4 = LocalTime.of(22, 15)
//parse a string
val date5 = LocalTime.parse("20:15:30")



//2 Zoned Date-Time API
import java.time.ZoneId
import java.time.ZonedDateTime
// Get the current date and time// Get the current date and time

val date33: ZonedDateTime = ZonedDateTime.parse("2007-12-03T10:15:30+05:30[Asia/Karachi]")

val id: ZoneId = ZoneId.of("Europe/Paris")
val id36: ZoneId = ZoneId.of("UTC")

val currentZone: ZoneId = ZoneId.systemDefault



//3 Chrono Units Enum
import java.time.temporal.ChronoUnit
//Get the current date//Get the current date

val today = LocalDate.now

//add 1 week to the current date
val nextWeek = today.plus(1, ChronoUnit.WEEKS)

//add 1 month to the current date
val nextMonth = today.plus(1, ChronoUnit.MONTHS)

//add 1 year to the current date
val nextYear = today.plus(1, ChronoUnit.YEARS)

//add 10 years to the current date
val nextDecade = today.plus(1, ChronoUnit.DECADES)




//4 Period and Duration

import java.time.Period
import java.time.temporal.ChronoUnit
//Get the current date
val date66 = LocalDate.now
val date70 = date1.plus(1, ChronoUnit.MONTHS)
val period71 = Period.between(date66, date70)


import java.time.Duration
val time76 = LocalTime.now
val twoHours = Duration.ofHours(2)
val time79 = time76.plus(twoHours)
val duration = Duration.between(time76, time79)



//5 Temporal Adjusters
import java.time.DayOfWeek
import java.time.temporal.TemporalAdjusters

val date83 = LocalDate.now
//get the next tuesday
val nextTuesday = date83.`with`(TemporalAdjusters.next(DayOfWeek.TUESDAY))

//get the second saturday of next month
val firstInYear = LocalDate.of(date83.getYear, date83.getMonth, 1)
val secondSaturday = firstInYear.`with`(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)).`with`(TemporalAdjusters.next(DayOfWeek.SATURDAY))


//6 Backward Compatibility
//Get the current date
val currentDate = new Date()

//Get the instant of current date in terms of milliseconds
val now: Instant = currentDate.toInstant()
val currentZone98 = ZoneId.systemDefault()

val localDateTime100 = LocalDateTime.ofInstant(now, currentZone98)

val zonedDateTime102 = ZonedDateTime.ofInstant(now, currentZone98)
