import concurrent.Future
import concurrent.ExecutionContext.Implicits.global
val f: Future[String] = Future { "Hello world!" }
// REPL output: 
// f: scala.concurrent.Future[String] = scala.concurrent.impl.Promise$DefaultPromise@793e6657
