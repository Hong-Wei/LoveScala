/**
 * Copyright (C) 2009-2017 Lightbend Inc. <http://www.lightbend.com>
 */
package Akka._3_Iot_Project

import Akka._3_Iot_Project.DeviceGroup._
import Akka._3_Iot_Project.DeviceManager.RequestTrackDevice
import akka.actor.{Actor, ActorLogging, ActorRef, Props, Terminated}

import scala.concurrent.duration._

object DeviceGroup {

  def props(groupId: String): Props = Props(new DeviceGroup(groupId))

  final case class RequestDeviceList(requestId: Long)
  final case class ReplyDeviceList(requestId: Long, ids: Set[String])

  final case class RequestAllTemperatures(requestId: Long)
  final case class RespondAllTemperatures(requestId: Long, temperatures: Map[String, TemperatureReading])

  sealed trait TemperatureReading
  final case class Temperature(value: Double) extends TemperatureReading
  case object TemperatureNotAvailable extends TemperatureReading
  case object DeviceNotAvailable extends TemperatureReading
  case object DeviceTimedOut extends TemperatureReading
}

class DeviceGroup(groupId: String) extends Actor with ActorLogging {
  var deviceIdToActor: Map[String, ActorRef] = Map.empty
  var actorToDeviceId: Map[ActorRef, String] = Map.empty
  var nextCollectionId: Long = 0L

  override def preStart(): Unit = log.info("DeviceGroup {} started", groupId)

  override def postStop(): Unit = log.info("DeviceGroup {} stopped", groupId)

  override def receive: Receive = {
    // Note the backticks
    case trackMsg @ RequestTrackDevice(`groupId`, _) =>
      deviceIdToActor.get(trackMsg.deviceId) match {
        case Some(ref) =>
          ref forward trackMsg // forward != !, forward will use the orignial sender
        case None =>
          log.info("Creating device actor for {}", trackMsg.deviceId)
          val deviceActor = context.actorOf(Device.props(groupId, trackMsg.deviceId), "device-" + trackMsg.deviceId)
          
          //Unlike supervision, watching is not limited to parent-child relationships, 
          // any actor can watch any other actor as long as it knows the ActorRef.
          // After a watched actor stops, the watcher receives a Terminated(actorRef) 
          // message which also contains the reference to the watched actor.
          context.watch(deviceActor)
          
          deviceActor forward trackMsg
          deviceIdToActor += trackMsg.deviceId -> deviceActor
          actorToDeviceId += deviceActor -> trackMsg.deviceId
      }

    case RequestTrackDevice(groupId, deviceId) =>
      log.warning(
        "Ignoring TrackDevice request for {}. This actor is responsible for {}.",
        groupId, this.groupId
      )

    case RequestDeviceList(requestId) =>
      sender() ! ReplyDeviceList(requestId, deviceIdToActor.keySet)
      
      //After a watched actor stops, the watcher receives a Terminated(actorRef)
    case Terminated(deviceActor) =>
      val deviceId = actorToDeviceId(deviceActor)
      log.info("Device actor for {} has been terminated", deviceId)
      actorToDeviceId -= deviceActor
      deviceIdToActor -= deviceId

    case RequestAllTemperatures(requestId) =>
      context.actorOf(
        DeviceGroupQuery.props(
          actorToDeviceId = actorToDeviceId,
          requestId = requestId,
          requester = sender(),
          3.seconds
        )
      )
  }

}
