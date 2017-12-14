//https://www.implicitdef.com/2015/11/19/comparing-scala-http-client-libraries.html

package LearnMockedServer

import dispatch.Future

import scala.xml.Elem

object _Dispatch extends App {
  //BK 1 Defining Requests  -- https://dispatchhttp.org/Dispatch.html#Defining+requests
  import dispatch._
  import Defaults._
  val svc: Req = url("http://api.hostip.info/country.php")
  val country =  Http.default(svc OK as.String)

  //BK 2 Deferring action -- https://dispatchhttp.org/Dispatch.html#Deferring+action
  for (c <- country) println("********"+c)
  
  //BK 3 Demanding answers
  val c = country()
  
}

object _1Dispatch_Bargaining_with_futures extends App{
  //BK 4 https://dispatchhttp.org/Bargaining+with+futures.html#Transformations
  import dispatch._, Defaults._
  val svc = url("http://api.hostip.info/country.php")
  val country: Future[String] = Http.default(svc OK as.String)
  //Even we do not know about future, but we can still transfer it and compute it. 
  val length: Future[Int] = for (c <- country) yield c.length
  
  //BK 5 https://dispatchhttp.org/Bargaining+with+futures.html#Future%23print

  println("xxxx1"+country.print)
  Thread.sleep(300); 
  for (c <- country) println("********"+c)
  println("xxxx2"+country.print)
  
  //BK 6 https://dispatchhttp.org/Bargaining+with+futures.html#Future%23completeOption
  val lengthNow: Int = length.completeOption.getOrElse(-1)
  
  //BK 7 https://dispatchhttp.org/Abstraction+over+future+information.html#Palling+around+with+Weather+Underground
  import dispatch._, Defaults._

  case class Location(city: String, state: String)

  def weatherSvc(loc: Location): Req = {
    host("api.wunderground.com") / "api" / "5a7c66db0ba0323a" /
      "conditions" / "q" / loc.state / (loc.city + ".xml")
  }
  val nyc = Location("New York", "NY")
//  for (str <- Http.default(weatherSvc(nyc) OK as.String)) println(str)

//  https://dispatchhttp.org/Abstraction+over+future+information.html#Parsing+XML
  def weatherXml(loc: Location) = Http.default(weatherSvc(loc) OK as.xml.Elem)

  def printer = new scala.xml.PrettyPrinter(90, 2)
//  for (xml <- weatherXml(nyc)) println(printer.format(xml))

  def extractTemp(xml: scala.xml.Elem): Float = {
    val seq = for {
      elem <- xml \\ "temp_c"
    } yield elem.text.toFloat
    seq.head
  }

  def temperature(loc: Location) =
    for (xml <- weatherXml(loc))
      yield extractTemp(xml)

  val la = Location("Los Angeles", "CA")
  for (t <- temperature(la)) println(t)

//  https://dispatchhttp.org/Working+with+multiple+futures.html#Yielding+combined+results
  def tempCompare(locA: Location, locB: Location) = {
    val pa = temperature(locA)
    val pb = temperature(locB)
    for {
      a <- pa
      b <- pb
    } yield a.compare(b)
  }


//  https://dispatchhttp.org/Working+with+multiple+futures.html#Dependent+futures+and+concurrency
def sequentialTempCompare(locA: Location, locB: Location) =
  for {
    a <- temperature(locA)
    b <- temperature(locB)
  } yield a.compare(b)
  //It's still non-blocking, but it does not perform the two requests in parallel.
  //--> the bindings of a and b
  // And that’s exactly the case. -->`Each clause of the for-expression on a future represents a future callback.` 
  // This is necessary for cases where one future value depends on another. 
  // Independent futures should be assigned outside for-expressions to maximize concurrency


//  https://dispatchhttp.org/Arbitrarily+many+futures.html#Iterables+of+futures
  val locs = List(Location("New York", "NY"),
    Location("Los Angeles", "CA"),
    Location("Chicago", "IL"))
  
  val temps: List[Future[(Float, Location)]] =
    for(loc <- locs)
      yield for (t <- temperature(loc))
        yield (t -> loc)

//  https://dispatchhttp.org/Arbitrarily+many+futures.html#Future.sequence
  val hottest: Future[(Float, Location)] =
    // List[Future[(Float, Location)]]-->   List[(Float,Location)] , sequence, reduce many futures to single future.
    for (ts: List[(Float,Location)] <- Future.sequence(temps))
      yield ts.maxBy { _._1 }



//  https://dispatchhttp.org/Arbitrarily+many+futures.html#A+future+of+the+hottest
  def hottest(locs: Location*): Future[Location] = {
    val temps =
      for(loc <- locs)
        yield for (t <- temperature(loc))
          yield (t -> loc)
    for (ts <- Future.sequence(temps))
      yield ts.maxBy { _._1 }._2
  }
}

object _2Dispatch_A_future_of_success_and_failure extends App{
  //BK 2.1.1 Failed futures -- https://dispatchhttp.org/A+future+of+success+and+failure.html#Failed+futures
  import dispatch._, Defaults._
  val str: Future[String] = Http.default(host("example.com") OK as.String)
  
  
  //BK 2.1.2 Fture#print for failed futures -- https://dispatchhttp.org/A+future+of+success+and+failure.html#Future%23print+for+failed+futures
  private val print1: String = str.print
  Thread.sleep(3000)// wait future finished. 
  println(str.print) // Future(!Unexpected response status: 302!), the error is in the future
  
  //BK 2.1.3 Applying Failed futures -- https://dispatchhttp.org/A+future+of+success+and+failure.html#Applying+failed+futures
  str()
  
  //BK 2.1.4 Transforming broken futures -- https://dispatchhttp.org/A+future+of+success+and+failure.html#Transforming+broken+futures
  val length = for (s <- str) yield s.length
  length.print
  
  //BK 2.1.5 deferred failed futures -- https://dispatchhttp.org/A+future+of+success+and+failure.html#Deferred+failed+futures
  for (s <- str) println(s)


  //Success as the only option https://dispatchhttp.org/Success+as+the+only+option.html#Success+as+the+only+option
  //BK 2.2.1 Future#option -- https://dispatchhttp.org/Success+as+the+only+option.html#Future%23option

  import dispatch._, Defaults._
  val str1: Future[Option[String]] = Http.default(host("example.com") OK as.String).option
  
  //BK 2.2.2 Optional weather -- https://dispatchhttp.org/Success+as+the+only+option.html#Optional+weather
  case class Location(city: String, state: String)
  def weatherSvc(loc: Location): Req = {
    host("api.wunderground.com") / "api" / "5a7c66db0ba0323a" /
      "conditions" / "q" / loc.state / (loc.city + ".xml")
  }
  def weatherXml(loc: Location): Future[Option[Elem]] =
    Http.default(weatherSvc(loc) OK as.xml.Elem).option
  
  //Bk 2.2.3 Optional temperature -- https://dispatchhttp.org/Success+as+the+only+option.html#Optional+temperature

  def extractTemp(xml: scala.xml.Elem): Option[Float] = {
    val seq = for {
      elem <- xml \\ "temp_c"
    } yield elem.text.toFloat
    seq.headOption
  }

  def temperature(loc: Location): Future[Option[Float]] =
    for (xmlOpt <- weatherXml(loc))
      yield for {
        xml <- xmlOpt
        t <- extractTemp(xml)
      } yield t

  //BK 2.2.3 Optional Hotness -- https://dispatchhttp.org/Success+as+the+only+option.html#Optional+hotness
  def hottest(locs: Location*): Future[Option[(Float, Location)]] = {
    val temps: Seq[Future[Option[(Float, Location)]]] =
      for(loc <- locs)
        yield for (tOpt <- temperature(loc))
          yield for (t <- tOpt)
            yield (t -> loc)
    for (ts <- Future.sequence(temps)) yield {
      val valid = ts.flatten
      for (_ <- valid.headOption)
        yield valid.maxBy { _._1 }
    }
  }
  
  //BK 2.2.4  Average or failure -- https://dispatchhttp.org/Either+type+will+do.html#Either+an+error+or+success
  def average(nums: Traversable[Int]): Either[String, Int] = {
    if (nums.isEmpty) Left("Can't average emptiness")
    else Right(nums.sum / nums.size)
  }
  
  //BK 2.2.5 Top of the class -- https://dispatchhttp.org/Either+type+will+do.html#Top+of+the+class
  val johnny = List(85, 60, 90)
  val sarah  = List(88, 65, 85)
  val billy  = List.empty[Int]

  for {
    j <- average(johnny).right
    s <- average(sarah).right
    b <- average(billy).right
  } yield List(j, s, b).max //-->  Either[java.lang.String,Int] = Left(Can't average emptiness)
  
  
  //BK2.2.6 Why not eject? --https://dispatchhttp.org/Either+type+will+do.html#Why+not+eject%3F
  // With asynchronous callbacks it’s as if you’re flying over enemy territory, or into orbit. 
  // The cost and complexity of recovering an ejected body becomes prohibitive.
  
  //Bk 2.2.7 Future Either -- https://dispatchhttp.org/Either+Future.html#Future%23either
  //1 Weather with either
  def weatherXml1(loc: Location): Future[Either[String, xml.Elem]] = {
    val res: Future[Either[Throwable, xml.Elem]] =
      Http.default(weatherSvc(loc) OK as.xml.Elem).either
    for (exc <- res.left)
      yield "Can't connect to weather service: \n" +
        exc.getMessage
  }
  
  
  //2 Handling missing input
  def extractTemp1(xml: scala.xml.Elem): Either[String,Float] = {
    val seq = for {
      elem <- xml \\ "temp_c"
    } yield elem.text.toFloat
    seq.headOption.toRight {
      "Temperature missing in service response"
    }
  }
  
  //3 Composing with either 
  def temperature1(loc: Location) =
    for (xmlEither <- weatherXml1(loc))
      yield for {
        xml <- xmlEither.right
        t <- extractTemp1(xml).right
      } yield t

  //4 Composing futures of either 
  def temperature2(loc: Location): Future[Either[String,Float]] = {
    for {
      xml <- weatherXml1(loc).right
      t <- Future.successful(extractTemp1(xml)).right
    } yield t
  }
  
  //5 Testing the error handling
  temperature(Location("New York","NY"))() //--> Either[String,Float] = Right(11.9)
  temperature(Location("nowhere","NO"))() //-->Left(Temperature missing in service response)
  

  //6 Hottness to the max 
  def hottest1(locs: Location*): Future[(Option[Location], Seq[(Location, Any)])] = {
    val temps =
      for(loc <- locs)
        yield for (tEither <- temperature1(loc))
          yield (loc, tEither)
    for (ts <- Future.sequence(temps)) yield {
      val valid = for ((loc, Right(t)) <- ts)
        yield (t, loc)
      val max = for (_ <- valid.headOption)
        yield valid.maxBy { _._1 }._2
      val errors = for ((loc, Left(err)) <- ts)
        yield (loc, err)
      (max, errors)
    }
  }
  
  //7 Testing the hottest
  hottest1(Location("New York","NY"),
    Location("Chicago", "IL"),
    Location("nowhere", "NO"),
    Location("Los Angeles", "CA"))()
//  -->
//  (
//    Some(Location(Los Angeles,CA)),
//    ArrayBuffer((Location(nowhere,NO),
//    Temperature missing in service response))
//  )
  
}

//BK 3 https://dispatchhttp.org/Defining+requests.html
object _3Defining_requests extends App{
  import dispatch._
  import Defaults._
  //1 Free-form URLs
  val myRequest: Req = url("http://example.com/some/path")
  
  //2 Explicit host builder
  val myHost: Req = host("example.com")
  val myHost2: Req = host("example.com",8888)
  
  //3 Using HTTPS
  val mySecureHost: Req = host("example.com").secure
  
  //4 Appending path elements
  val myRequest1: Req = myHost / "some" / "path"
  
  
  //BK 3.1 HTTP methods and parameters https://dispatchhttp.org/HTTP+methods+and+parameters.html
  //5 HTTP methods == HTTP methods can be specified in the same way.
  //  HEAD
  //  GET
  //  POST
  //  PUT
  //  DELETE
  //  PATCH
  //  TRACE
  //  OPTIONS
  def myPost = myRequest.POST
  
  //BK 6 POST parameters
  def myPostWithParams = myPost.addParameter("key", "value")
  
  //BK 7 POST verb <<  
  //  The << verb sets the request method to POST and adds form-encoded parameters to the body at once:
  def myPostWithParamsPost = myRequest << Map("key" -> "value")
//  def myRequestAsJson = myRequest.setContentType("application/json,UTF8")
//  def myPostWithBody = myRequestAsJson << """{"key": "value"}"""
  
  
  //BK 8 Query parameters
  def myRequestWithParams = myRequest.addQueryParameter("key", "value")
  def myRequestWithParams1 = myRequest
    .addQueryParameter("key", "value1")
    .addQueryParameter("key", "value2")
  def myRequestWithParams2 = myRequest <<? Map("key" -> "value")
  def myRequestWithParams3 = myRequest <<? List(
    ("key", "value1"),
    ("key", "value2")
  )

  //BK 9 PUT a file
//  def myPut = myRequest <<< myFile


}
object _4Unraveling_for_expressions extends App{}
