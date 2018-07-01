implicit val system = ActorSystem("QuickStart")

  //Materializer是stream执行引擎的一个工厂，它使得streams可以执行.
  //——你现在不需要了解它的更多细节，仅需要知道你可以调用Source的run*方法。
  //物化(materializer)实际上就是将Source、Sink、Flow构建起来的蓝图(blueprint)提供可执行实现。
  implicit val materializer = ActorMaterializer()


  val source: Source[Int, NotUsed] = Source(1 to 100)
  val done: Future[Done] = source.runForeach(i ⇒ println(i))(materializer)


