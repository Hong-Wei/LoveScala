case class Resource(content: String)
val resourceFromConfigDir: Option[Resource] = None
val resourceFromClasspath: Option[Resource] = Some(Resource("I was found on the classpath"))
val resourceFromOther: Option[Resource] = Some(Resource("I was found from others"))
val resource = resourceFromConfigDir orElse resourceFromClasspath orElse resourceFromOther