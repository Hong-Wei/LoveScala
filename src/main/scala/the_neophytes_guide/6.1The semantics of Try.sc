import scala.util.Try
import java.net.URL
def parseURL(url: String): Try[URL] = Try(new URL(url))
val l: Try[URL] = parseURL("http://danielwestheide.com")
val l1: Try[URL] = parseURL("garbage")

val default = new URL("http://danielwestheide.com")
val orElse: URL = l1.getOrElse(default)
val triedUrl: Try[URL] = l1 orElse Try(new URL("http://danielwestheide.com"))
triedUrl.get


//Chaining operations
parseURL("http://danielwestheide.com").map(_.getProtocol)
parseURL("garbage").map(_.getProtocol)


import java.io.InputStream
def inputStreamForURL(url: String): Try[Try[Try[InputStream]]] = parseURL(url).map { u =>
  Try(u.openConnection()).map(conn => Try(conn.getInputStream))
}

def inputStreamForURLFlatmap(url: String): Try[InputStream] = parseURL(url).flatMap { u =>
  Try(u.openConnection()).flatMap(conn => Try(conn.getInputStream))
}

//For comprehensions
import scala.io.Source
def getURLContent(url: String): Try[Iterator[String]] =
  for {
    url <- parseURL(url)
    connection <- Try(url.openConnection())
    is <- Try(connection.getInputStream)
    source = Source.fromInputStream(is)
  } yield 
    source.getLines()

///Pattern Matching
import scala.util.Success
import scala.util.Failure
getURLContent("http://danielwestheide.com/foobar") match {
  case Success(lines) => lines.foreach(println)
  case Failure(ex) => println(s"Problem rendering URL content: ${ex.getMessage}")
}