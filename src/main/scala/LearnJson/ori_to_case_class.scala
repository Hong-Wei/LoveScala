package LearnJson

import scala.collection.immutable.List

/**
  * Created by zhanghongwei on 08/09/2017.
  */
object ori_to_case_class extends App {

  import net.liftweb.json.Extraction
  implicit val formats = net.liftweb.json.DefaultFormats

  import net.liftweb.json._;

  sealed trait ApiRole{
    var requiresBankId : Boolean
  }

  class CanSearchAllTransactions (var requiresBankId: Boolean) extends ApiRole 

  case class ABC (
    requiredRoles: List[ApiRole]
  )

  val oneABC: ABC = ABC(
    requiredRoles=List(new CanSearchAllTransactions(true),new CanSearchAllTransactions(false))
  )

  val aa = Extraction.decompose(oneABC)
  prettyRender(Extraction.decompose(oneABC))

  println(prettyRender(aa))

}


