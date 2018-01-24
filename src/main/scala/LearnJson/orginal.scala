package LearnJson

import scala.collection.immutable.List

/**
  * Created by zhanghongwei on 08/09/2017.
  */
object orginal extends App {

  import net.liftweb.json.Extraction
  implicit val formats = net.liftweb.json.DefaultFormats

  import net.liftweb.json._;

  sealed trait ApiRole{
    val requiresBankId : Boolean
  }

  case object CanSearchAllTransactions extends ApiRole {
    val requiresBankId = false
  }
 
  case class ABC (
    requiredRoles: List[ApiRole]
  )

  val oneABC: ABC = ABC(
    requiredRoles=List(CanSearchAllTransactions,CanSearchAllTransactions)
  )

  val aa = Extraction.decompose(oneABC)
  prettyRender(Extraction.decompose(oneABC))

  println(prettyRender(aa))

}


