import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

val fut = Future {
  Thread.sleep(10000); 21 + 21
}

fut.isCompleted
fut.value
