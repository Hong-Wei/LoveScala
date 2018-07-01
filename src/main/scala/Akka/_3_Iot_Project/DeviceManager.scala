/**
 * Copyright (C) 2009-2017 Lightbend Inc. <http://www.lightbend.com>
 */

package Akka._3_Iot_Project

import Akka._3_Iot_Project.DeviceManager.{DeviceRegistered, RequestTrackDevice}
import akka.actor.{Actor, ActorLogging, ActorRef, Props, Terminated}

object DeviceManager {
  def props(): Props = Props(new DeviceManager)

  // Registration Requests 
  final case class RequestTrackDevice(groupId: String, deviceId: String)
  case object DeviceRegistered
}

class DeviceManager extends Actor with ActorLogging {
  var groupIdToActor = Map.empty[String, ActorRef]
  var actorToGroupId = Map.empty[ActorRef, String]

  override def preStart(): Unit = log.info("DeviceManager started")

  override def postStop(): Unit = log.info("DeviceManager stopped")

  override def receive = {
    //@ --> bind the value that is matched to a variable. If not @, trackMsg is a `Any`. 
    // Now it will be a RequestTrackDevice variable
    case trackMsg @ RequestTrackDevice(groupId, _) =>  
      groupIdToActor.get(groupId) match {
        case Some(ref) =>
          ref forward trackMsg
        case None =>
          log.info("Creating device group actor for {}", groupId)
          val groupActor = context.actorOf(DeviceGroup.props(groupId), "group-" + groupId)
          context.watch(groupActor)
          groupActor forward trackMsg
          groupIdToActor += groupId -> groupActor
          actorToGroupId += groupActor -> groupId
      }

    case Terminated(groupActor) =>
      val groupId = actorToGroupId(groupActor)
      log.info("Device group actor for {} has been terminated", groupId)
      actorToGroupId -= groupActor
      groupIdToActor -= groupId

      
      
    case DeviceRegistered =>
      log.info("Get This message")
  }

}
