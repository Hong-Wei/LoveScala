package LearnJson.doing1

import net.liftweb.json.Serialization.writePretty

import scala.collection.immutable.List

/**
  * Created by zhanghongwei on 08/09/2017.
  */
object solution extends App {

  import net.liftweb.json._;


  case class CanSearchAllTransactions2(requiresBankId : Boolean =false) extends ApiRole

  case class ABC (
    requiredRoles: List[ApiRole]
  )

  val oneABC: ABC = ABC(
    requiredRoles=List(CanSearchAllTransactions,CanSearchAllTransactions2())
  )

  implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[CanSearchAllTransactions2], classOf[CanSearchAllTransactions2])))
  val aa: String = writePretty(oneABC)

  println(aa)
  sealed trait ApiRole{
    val requiresBankId : Boolean
  }

  case object CanSearchAllTransactions extends ApiRole {
    val requiresBankId : Boolean =false
  }

}


