//https://doc.akka.io/docs/akka/current/stream/stream-quickstart.html

package Akka

import akka.stream._
import akka.stream.scaladsl._
import akka.{NotUsed, Done}
import akka.actor.ActorSystem
import akka.util.ByteString
import scala.concurrent._
import scala.concurrent.duration._
import java.nio.file.Paths


object Main extends App {
  // Code here
  implicit val system = ActorSystem("QuickStart")

  //Materializer是stream执行引擎的一个工厂，它使得streams可以执行.
  //——你现在不需要了解它的更多细节，仅需要知道你可以调用Source的run*方法。
  //物化(materializer)实际上就是将Source、Sink、Flow构建起来的蓝图(blueprint)提供可执行实现。
  implicit val materializer = ActorMaterializer()


  val source: Source[Int, NotUsed] = Source(1 to 100)
  val done: Future[Done] = source.runForeach(i ⇒ println(i))(materializer)


  val factorials: Source[BigInt, NotUsed] = source.scan(BigInt(1))((acc, next) ⇒ acc * next)

  val result: Future[IOResult] =
    factorials
      .map(num ⇒ ByteString(s"$num\n"))
      .runWith(FileIO.toPath(Paths.get("factorials.txt")))


  def lineSink(filename: String): Sink[String, Future[IOResult]] =
    Flow[String]
      .map(s => ByteString(s + "\n"))
      .toMat(FileIO.toPath(Paths.get(filename)))(Keep.right)

  factorials.map(_.toString).runWith(lineSink("factorial2.txt"))


  //基于时间处理
  //现在我们通过zip实现将两个source转换为一个stream
  //http://galudisu.info/2017/05/26/akka/streaming/akka-streams-learning/
  factorials
    .zipWith(Source(0 to 100))((num, idx) => s"$idx! = $num")
    // 我们使用throttle来协调stream的速率(每1秒钟，传输一个元素)，以此保证，即使是百万级以上的数据，也不会出现JVM内存溢出的情况。
    //这里的throttle在Akka Streams被称作 combinators——协调器，Akka Streams中所有 combinators 都遵循back-pressure设计实现。
    .throttle(1, 1.second, 1, ThrottleMode.shaping)
    .runForeach(println)


  import akka.NotUsed
  import akka.actor.ActorSystem
  import akka.stream.ActorMaterializer
  import akka.stream.scaladsl._

  final case class Author(handle: String)

  final case class Hashtag(name: String)

  final case class Tweet(author: Author, timestamp: Long, body: String) {
    def hashtags: Set[Hashtag] = body.split(" ").collect {
      case t if t.startsWith("#") ⇒ Hashtag(t.replaceAll("[^#\\w]", ""))
    }.toSet
  }

  val akkaTag = Hashtag("#akka")

  val tweets: Source[Tweet, NotUsed] = Source(
    Tweet(Author("rolandkuhn"), System.currentTimeMillis, "#akka rocks!") ::
      Tweet(Author("patriknw"), System.currentTimeMillis, "#akka !") ::
      Tweet(Author("bantonsson"), System.currentTimeMillis, "#akka !") ::
      Tweet(Author("drewhk"), System.currentTimeMillis, "#akka !") ::
      Tweet(Author("ktosopl"), System.currentTimeMillis, "#akka on the rocks!") ::
      Tweet(Author("mmartynas"), System.currentTimeMillis, "wow #akka !") ::
      Tweet(Author("akkateam"), System.currentTimeMillis, "#akka rocks!") ::
      Tweet(Author("bananaman"), System.currentTimeMillis, "#bananas rock!") ::
      Tweet(Author("appleman"), System.currentTimeMillis, "#apples rock!") ::
      Tweet(Author("drama"), System.currentTimeMillis, "we compared #apples to #oranges!") ::
      Nil)

  val done1: Future[Done] = tweets
    .map(_.hashtags) // Get all sets of hashtags ...
    .reduce(_ ++ _) // ... and reduce them to a single set, removing duplicates across all tweets
    .mapConcat(identity) // Flatten the stream of tweets to a stream of hashtags
    .map(_.name.toUpperCase) // Convert all hashtags to upper case
    .runWith(Sink.foreach(println)) // Attach the Flow to a Sink that will finally print the hashtags

  // $FiddleDependency org.akka-js %%% akkajsactorstream % 1.2.5.1

  val authors: Source[Author, NotUsed] =
    tweets
      .filter(_.hashtags.contains(akkaTag))
      .map(_.author)
  //物化(materializer)
  authors.runWith(Sink.foreach(println))


  //  Flow处理
  val count: Flow[Tweet, Int, NotUsed] = Flow[Tweet].map(_ => 1)

  val sumSink: Sink[Int, Future[Int]] = Sink.fold[Int, Int](0)(_ + _)

  val counterGraph: RunnableGraph[Future[Int]] =
    tweets
      .via(count)
      .toMat(sumSink)(Keep.right)

  val sum: Future[Int] = counterGraph.run()

  sum.foreach(c => println(s"Total tweets processed: $c"))


  implicit val ec = system.dispatcher
  done1.onComplete(_ ⇒ system.terminate())


}

