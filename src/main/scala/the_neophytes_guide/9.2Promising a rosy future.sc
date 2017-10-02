import concurrent.{Future, Promise}

case class TaxCut(reduction: Int)
// either give the type as a type parameter to the factory method:
val taxcut = Promise[TaxCut]()
// or give the compiler a hint by specifying the type of your val:
val taxcut2: Promise[TaxCut] = Promise()

//Once you created a Promise, you can get the Future belonging to it
//by calling the future method 
val taxcutF: Future[TaxCut] = taxcut.future

//Completing a Promise
taxcut.success(TaxCut(20)) //Promise instance is no longer writable.

import concurrent.Future
import concurrent.ExecutionContext.Implicits.global
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
val taxCutF: Future[TaxCut] = redeemCampaignPledge()
println("Now that they're elected, let's see if they remember their promises...")
taxCutF.onComplete {
  case Success(TaxCut(reduction)) =>
    println(s"A miracle! They really cut our taxes by $reduction percentage points!")
  case Failure(ex) =>
    println(s"They broke their promises! Again! Because of a ${ex.getMessage}")
}
