import dispatch._, Defaults._
val svc = url("http://api.hostip.info/country.php")
val country: Future[String] = Http.default(svc OK as.String)
//Even we do not know about future, but we can still transfer it and compute it. 
val length: Future[Int] = for (c <- country) yield c.length


val print1: String = country.print