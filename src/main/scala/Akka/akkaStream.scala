//http://www.cnblogs.com/tiger-xc/p/7364500.html

import akka.{Done, NotUsed}
import akka.actor._
import akka.stream._
import akka.stream.scaladsl.{Source, _}

import scala.concurrent.{ExecutionContextExecutor, Future}

object SourceDemo extends App {
  implicit val sys: ActorSystem = ActorSystem("demo")
  implicit val mat: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContextExecutor = sys.dispatcher

  val source: Source[Int, NotUsed] = Source(1 to 10)
  val sink: Sink[Any, Future[Done]] = Sink.foreach(println)

  val rg1: RunnableGraph[NotUsed] = source.to(sink)

  //  val rg2_0: ((NotUsed, Future[Done]) => Nothing) => RunnableGraph[Nothing] = source.toMat(sink)
  //  val rg2_1: RunnableGraph[Future[Done]] = source.toMat(sink)(Keep.right)
  val rg2: RunnableGraph[Future[Done]] = source.toMat(sink)(Keep.right)


  val res1: NotUsed = rg1.run()

  Thread.sleep(1000)

  val res2: Future[Done] = rg2.run()
  //  val res2_1: Future[Done] = rg2_1.run()

  //  res2_1.andThen {
  //    case _ => sys.terminate()
  //  }


  res2.andThen {
    case _ => sys.terminate()
  }
}

//http://www.cnblogs.com/tiger-xc/p/7364500.html

import akka.actor._
import akka.stream._
import akka.stream.scaladsl.{Source, _}

object SourceDemo2 extends App {
  implicit val sys = ActorSystem("demo")
  implicit val mat = ActorMaterializer()
  implicit val ec = sys.dispatcher


  val s1 = Source(1 to 10)
  val sink = Sink.foreach(println)
  val rg1: RunnableGraph[NotUsed] = s1.to(sink)
  val rg2: RunnableGraph[Future[Done]] = s1.toMat(sink)(Keep.right)
  val res1: NotUsed = rg1.run()
  val seq: Seq[Int] = Seq[Int](1, 2, 3)

  def toIterator() = seq.iterator

  val flow1: Flow[Int, Int, NotUsed] = Flow[Int].map(_ + 2)
  val flow2: Flow[Int, Int, NotUsed] = Flow[Int].map(_ * 3)
  val s2: Source[Int, NotUsed] = Source.fromIterator(toIterator)
  val s3: Source[Int, NotUsed] = s1 ++ s2

  Thread.sleep(1000)

  val s4: Source[Int, NotUsed] = s3.viaMat(flow1)(Keep.right)
  val s5: Source[Int, NotUsed] = s3.via(flow1).async.viaMat(flow2)(Keep.right)
  val s6: Source[Int, NotUsed] = s4.async.viaMat(flow2)(Keep.right)
  (s5.toMat(sink)(Keep.right).run()).andThen { case _ => sys.terminate() }


}

import akka.actor._
import akka.stream._
import akka.stream.scaladsl._

object SimpleGraphs extends App {

  implicit val sys = ActorSystem("streamSys")
  implicit val ec = sys.dispatcher
  implicit val mat = ActorMaterializer()

  val source: Source[Int, NotUsed] = Source(1 to 10)
  val flow: Flow[Int, Int, NotUsed] = Flow[Int].map(_ * 2)
  val sink: Sink[Any, Future[Done]] = Sink.foreach(println)


  val sourceGraph = GraphDSL.create() { implicit builder =>
    import GraphDSL.Implicits._
    val src = source.filter(_ % 2 == 0)
    val pipe = builder.add(Flow[Int])
    src ~> pipe.in
    SourceShape(pipe.out)
  }

  Source.fromGraph(sourceGraph).runWith(sink).andThen { case _ => } // sys.terminate()}

  val flowGraph = GraphDSL.create() { implicit builder =>
    import GraphDSL.Implicits._

    val pipe = builder.add(Flow[Int])
    FlowShape(pipe.in, pipe.out)
  }

  val (_, fut) = Flow.fromGraph(flowGraph).runWith(source, sink)
  fut.andThen { case _ => } //sys.terminate()}


  val sinkGraph = GraphDSL.create() { implicit builder =>
    import GraphDSL.Implicits._
    val pipe = builder.add(Flow[Int])
    pipe.out.map(_ * 3) ~> Sink.foreach(println)
    SinkShape(pipe.in)
  }

  val fut1 = Sink.fromGraph(sinkGraph).runWith(source)

  Thread.sleep(1000)
  sys.terminate()
}