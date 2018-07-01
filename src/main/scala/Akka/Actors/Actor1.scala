//https://doc.akka.io/docs/akka/current/actors.html#defining-an-actor-class

package Akka.Actors

import com.typesafe.scalalogging.StrictLogging

object Example_01 extends App with StrictLogging{
  import akka.actor.{Actor, ActorSystem, Props}

  class MyActor extends Actor {

    def receive = {
      case "test" => logger.info("received test")
//      case _      => log.info("received unknown message")
    }
  }
  //创建ActorSystem对象
  val system = ActorSystem("MyActorSystem")
  
  //创建MyActor,指定actor名称为myactor
  val myactor = system.actorOf(Props[MyActor], name = "myactor")

  logger.debug("准备向myactor发送消息")
  //向myactor发送消息
  myactor!"test"
  myactor!123

  //关闭ActorSystem，停止程序的运行
  system.terminate()
}
    
