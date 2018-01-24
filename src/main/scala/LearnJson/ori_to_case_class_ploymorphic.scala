package LearnJson

import net.liftweb.json.Serialization.writePretty

import scala.collection.immutable.List

/**
  * Created by zhanghongwei on 08/09/2017.
  */
object ori_to_case_class_ploymorphic extends App {


  import net.liftweb.json._;

  sealed trait ApiRole{
    val requiresBankId : Boolean
  }

  case class CanSearchAllTransactions (requiresBankId: Boolean=false) extends ApiRole 
  case class CanSearchAllTransactions2 (requiresBankId: Boolean=true) extends ApiRole 

  case class ABC (
    requiredRoles: List[ApiRole]
  )

  val oneABC: ABC = ABC(
    requiredRoles=List(CanSearchAllTransactions(true),CanSearchAllTransactions2(false))
  )
  implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[CanSearchAllTransactions], classOf[CanSearchAllTransactions2])))
  val ser: String = writePretty(oneABC)
  
  

  println(ser)

}


