import concurrent.Future
import concurrent.ExecutionContext.Implicits.global
val f: Future[String] = Future { "Hello world!" }