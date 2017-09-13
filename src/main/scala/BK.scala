
/**
  *
  */
//BK 1 when ,how to use None, isEmpty ???who has these feature???

//BK 2 P168 ,why use tree structure to store data??

//BK day01 Swagger
//  /Users/zhanghongwei/Documents/workspaceScala/LoveScala/src/main/scala/Swagger


class BK {}

//BK 1 learn sealed 关键字
//check 14.14 Sealed Classes in Chapter14.scala

//BK 2 Learn Lift Json


//BK 3 Learn Future


class Account {
  val id = Account.newUniqueNumber()
  private var balance = 0.0

  def deposit(amount: Double) {
    balance += amount
  }

}

object Account extends Account with App {

  var lastNumber = 0

  private def newUniqueNumber() = {
    lastNumber += 1;
    lastNumber
  }

  println(new Account().balance)

}


object TrafficLightColor extends Enumeration {
  val Red, Yellow, Green = Value
}

import TrafficLightColor._
def doWhat(color: TrafficLightColor.Value) = {

  if (color == Red) "stop"

  else if (color == Yellow) "hurry up"

  else "go"
}