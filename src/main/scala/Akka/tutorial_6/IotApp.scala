/**
 * Copyright (C) 2009-2017 Lightbend Inc. <http://www.lightbend.com>
 */
package Akka.tutorial_6

import Akka.tutorial_6.DeviceManager.RequestTrackDevice
import akka.actor.{ActorRef, ActorSystem}

import scala.io.StdIn

object IotApp {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("iot-system")

    try {
      // Create top level supervisor
      val supervisor: ActorRef = system.actorOf(DeviceManager.props(), "iot-supervisor")

      supervisor ! RequestTrackDevice("mygroup", "device1")
      

      // Exit the system after ENTER is pressed
      StdIn.readLine()
    } finally {
      system.terminate()
    }
  }

}
