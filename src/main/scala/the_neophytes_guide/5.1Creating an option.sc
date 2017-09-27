val greetingSome: Option[String] = Some("Hello world")
val greetingNone: Option[String] = None


val presentGreeting: Option[String] = Option("Hello world") // presentGreeting will be Some("Hello!")
val absentGreeting: Option[String] = Option(null) // absentGreeting will be None


greetingSome == presentGreeting  //True
greetingNone == absentGreeting   //True