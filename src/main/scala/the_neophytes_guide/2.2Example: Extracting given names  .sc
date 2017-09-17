object GivenNames {
  def unapplySeq(name: String): Option[Seq[String]] = {
    val names: Seq[String] = name.trim.split(" ")
    if (names.forall(_.isEmpty)) None else Some(names)
  }
}


def greetWithFirstName(name: String) = name match {
  case GivenNames(firstName, _*) => "Good morning, " + firstName + "!"
  case _ => "Welcome! Please make sure to fill in your name!"
}


greetWithFirstName("hongwei zhang")