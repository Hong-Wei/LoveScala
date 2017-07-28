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



//https://segmentfault.com/a/1190000009455103
//1.flatMap
//我们可以利用flatMap来组合多个Future，不多说，先上代码：
val fut1 = Future {
  println("enter task1")
  Thread.sleep(2000)
  1 + 1
}

val fut2 = Future {
  println("enter task2")
  Thread.sleep(1000)
  2 + 2
}

fut1.flatMap { v1 =>
  fut2.map { v2 =>
    println(s"the result is ${v1 + v2}")
  }
}
Thread.sleep(2500)

//2.for yield表达式
for {
  v1 <- fut1
  v2 <- fut2
} yield 
  println(s"the result is ${v1 + v2}")


//https://stackoverflow.com/questions/24028114/getting-data-out-of-a-future-in-scala


//There are multiple ways:

//fut1.map { personList =>
//    ....
//  }
////This map returns another Future composed with whatever you return from the map. The map will execute only if the future completes successfully. If you need to handle failure you can use onComplete
//
//fut1.onComplete {
//  case Success(personList) => ...
//  case Failure(exception)  =>  ...
//}
////Or you can wait for the future to complete (this is blocking):
//
//val personList: List[Person] = Await.result(fut1, 1 minutes)



//https://blog.knoldus.com/2016/07/01/getting-asynchronous-in-scala-part-1-future-callbacks-combinators-etc/https://blog.knoldus.com/2016/07/01/getting-asynchronous-in-scala-part-1-future-callbacks-combinators-etc/
//1). Callbacks
//2). Combinators
//3). For Comprehensive
//4). Await.result
//5). The async library (async,await)

//https://blog.knoldus.com/2016/07/13/the-async-library-in-scala/

