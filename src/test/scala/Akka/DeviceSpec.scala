package Akka

import akka.actor.ActorSystem
import akka.testkit.{TestKit, TestProbe}
import com.lightbend.akka.sample.Device
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.language.postfixOps

class DeviceSpec(_system: ActorSystem)
  extends TestKit(_system)
    with Matchers
    with WordSpecLike
    with BeforeAndAfterAll
{
  
  def this() = this(ActorSystem("DeviceSpec"))
  
  override def afterAll: Unit =
  {
    shutdown(system)
  }
  
  "reply with empty reading if no temperature is known" in
    {
      val probe = TestProbe()
      val deviceActor = system.actorOf(Device.props("group", "device"))
      
      deviceActor.tell(Device.ReadTemperature(requestId = 42), probe.ref)
      val response = probe.expectMsgType[Device.RespondTemperature]
      response.requestId should ===(42)
      response.value should ===(None)
    }
  
  "reply with latest temperature reading" in
    {
      val probe = TestProbe()
      val deviceActor = system.actorOf(Device.props("group", "device"))
      
      deviceActor.tell(Device.RecordTemperature(requestId = 1, 24.0), probe.ref)
      probe.expectMsg(Device.TemperatureRecorded(requestId = 1))
      
      deviceActor.tell(Device.ReadTemperature(requestId = 2), probe.ref)
      val response1: Device.RespondTemperature = probe.expectMsgType[Device.RespondTemperature]
      response1.requestId should ===(2)
      response1.value should ===(Some(24.0))
      
      deviceActor.tell(Device.RecordTemperature(requestId = 3, 55.0), probe.ref)
      probe.expectMsg(Device.TemperatureRecorded(requestId = 3))
      
      deviceActor.tell(Device.ReadTemperature(requestId = 4), probe.ref)
      val response2 = probe.expectMsgType[Device.RespondTemperature]
      response2.requestId should ===(4)
      response2.value should ===(Some(55.0))
    }
  
  
}