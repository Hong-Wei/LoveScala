package LearnJson

import net.liftweb.json.Extraction._
import net.liftweb.json.Serialization.writePretty

import scala.collection.immutable.List

/**
  * Created by zhanghongwei on 08/09/2017.
  */
object solution_marko extends App {

  import net.liftweb.json.Serialization.writePretty

  import net.liftweb.json._;

  sealed trait ApiRole{
    val requiresBankId : Boolean
  }

  case class CanCreateUserCustomerLink(requiresBankId: Boolean = true) extends ApiRole
  lazy val canCreateUserCustomerLink = CanCreateUserCustomerLink()


  case class CanCreateUserCustomerLinkAtAnyBank(requiresBankId: Boolean = true) extends ApiRole
  lazy val canCreateUserCustomerLinkAtAnyBank = CanCreateUserCustomerLinkAtAnyBank()

  case class ABC (
    requiredRoles: List[ApiRole]
  )

  val oneABC: ABC = ABC(
    requiredRoles=List(canCreateUserCustomerLink,canCreateUserCustomerLinkAtAnyBank)
  )

  implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[CanCreateUserCustomerLink], classOf[CanCreateUserCustomerLinkAtAnyBank])))
  val aa = decompose(oneABC)
  
  println(aa)


}


