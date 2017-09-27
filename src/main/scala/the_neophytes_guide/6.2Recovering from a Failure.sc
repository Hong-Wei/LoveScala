import scala.util.Try
import java.net.URL
def parseURL(url: String): Try[URL] = Try(new URL(url))

import scala.io.Source
import scala.util.Try
def getURLContent(url: String): Try[Iterator[String]] =
  for {
    url <- parseURL(url)
    connection <- Try(url.openConnection())
    is <- Try(connection.getInputStream)
    source = Source.fromInputStream(is)
  } yield
    source.getLines()

import java.net.MalformedURLException
import java.io.FileNotFoundException

val content1: Try[Iterator[String]] = getURLContent("garbage")
val content: Try[Iterator[String]] = content1 recover {
  case e: FileNotFoundException => Iterator("Requested page does not exist")
  case e: MalformedURLException => Iterator("Please make sure to enter a valid URL")
  case _ => Iterator("An unexpected error has occurred. We are so sorry!")
}

content.get.toList

