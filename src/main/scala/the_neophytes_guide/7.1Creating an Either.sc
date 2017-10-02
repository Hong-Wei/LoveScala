import scala.io.Source
import java.net.URL
def getContent(url: URL): Either[String, Source] =
  if (url.getHost.contains("google"))
    Left("Requested URL is blocked for the good of the people!")
  else
    Right(Source.fromURL(url))



val content: Either[String, Source] = getContent(new URL("http://danielwestheide.com"))
val content1: Either[String, Source] = getContent(new URL("https://plus.google.com"))

val conten3: Either[String, Iterator[String]] =
  getContent(new URL("http://danielwestheide.com")).right.map(_.getLines())
// content is a Right containing the lines from the Source returned by getContent
val moreContent: Either[String, Iterator[String]] =
  getContent(new URL("http://google.com")).right.map(_.getLines)
// moreContent is a Left, as already returned by getContent