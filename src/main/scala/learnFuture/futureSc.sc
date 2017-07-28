//http://thomassun.iteye.com/blog/2088666
import concurrent.Future
import concurrent.ExecutionContext.Implicits.global
val f: Future[String] = Future { "Hello world!" }

import concurrent.Promise
case class TaxCut(reduction: Int)
// 在apply方法中提供类型参数：  
val taxcut = Promise[TaxCut]()
// 或者明确常量的类型，以便让Scala的类型推断系统能工作：  
val taxcut2: Promise[TaxCut] = Promise()

//返回的Future可能和Promise不是同一对象，但是多次调用Promise的
//future方法确定无疑的总是返回同样的对象，这维持了Promise和Future的一对一关系
val taxcutF: Future[TaxCut] = taxcut.future


//要成功地完成一个Promise，你调用它的success方法并传递一个结果值，值是对应的Future应该拥有的：
taxcut.success(TaxCut(20))
//一旦你这样做了，Promise实例就会变成只读，任何试图写的操作都会抛出异常。
//并且这样的方式完成Promise也会同时让相关联的Future成功完成。任何成功或完成的处理器都将被调用，或者当你在map那个Future时，map方法将被执行。
//通常，完成Promise和完成Future的操作不会发生在同一个线程上
//更多的场景是你生成了Promise并且在另一个线程开始进行结果的计算，立刻返回尚未完成的Future给调用者


object Government {
  def redeemCampaignPledge(): Future[TaxCut] = {
    val p = Promise[TaxCut]()
    Future {
      println("Starting the new legislative period.")
      Thread.sleep(2000)
      p.success(TaxCut(20))
      println("We reduced the taxes! You must reelect us!!!!1111")
    }
    p.future
  }
  import scala.util.{Success, Failure}
  val taxCutF: Future[TaxCut] = Government.redeemCampaignPledge()
  println("Now that they're elected, let's see if they remember their promises...")
  taxCutF.onComplete {
    case Success(TaxCut(reduction)) =>
      println(s"A miracle! They really cut our taxes by $reduction percentage points!")
    case Failure(ex) =>
      println(s"They broke their promises! Again! Because of a ${ex.getMessage}")
  }

}

