package LearnJson.doing1

import java.util.{Date, TimeZone}

import com.typesafe.scalalogging.Logger
import net.liftweb.json.JsonAST.JValue

import scala.collection.immutable.List

/**
  * Created by zhanghongwei on 08/09/2017.
  */
object Doing extends App {

  case class OutBoundCaseClass(
    date: Date,
    dateString: String
  )

}


