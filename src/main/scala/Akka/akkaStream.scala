//http://www.cnblogs.com/tiger-xc/p/7364500.html

import akka.actor._
import akka.stream._
import akka.stream.scaladsl.{Source, _}

object SourceDemo extends App {
  implicit val sys=ActorSystem("demo")
  implicit val mat=ActorMaterializer()
  implicit val ec=sys.dispatcher

  val s1 = Source(1 to 10)
  val sink = Sink.foreach(println)
  val rg1= s1.to(sink)
  val rg2 = s1.toMat(sink)(Keep.right)
  val res1 = rg1.run()

  Thread.sleep(1000)

  val res2 = rg2.run()
  res2.andThen {
    case _ => sys.terminate()
  }

}

//http://www.cnblogs.com/tiger-xc/p/7364500.html

import akka.actor._
import akka.stream._
import akka.stream.scaladsl.{Source, _}

object SourceDemo2 extends App {
  implicit val sys=ActorSystem("demo")
  implicit val mat=ActorMaterializer()
  implicit val ec=sys.dispatcher


  val s1 = Source(1 to 10)
  val sink = Sink.foreach(println)
  val rg1= s1.to(sink)
  val rg2 = s1.toMat(sink)(Keep.right)
  val res1 = rg1.run()
  val seq = Seq[Int](1,2,3)
  def toIterator() = seq.iterator
  val flow1= Flow[Int].map(_ + 2)
  val flow2 = Flow[Int].map(_ * 3)
  val s2 = Source.fromIterator(toIterator)
  val s3 = s1 ++ s2

  Thread.sleep(1000)
  
  val s4= s3.viaMat(flow1)(Keep.right)
  val s5 = s3.via(flow1).async.viaMat(flow2)(Keep.right)
  val s6 = s4.async.viaMat(flow2)(Keep.right)
  (s5.toMat(sink)(Keep.right).run()).andThen {case _ => sys.terminate()}

  

}