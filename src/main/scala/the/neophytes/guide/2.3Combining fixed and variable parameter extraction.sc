object Names {
  def unapplySeq(name: String): Option[(String, String, Seq[String])] = {
    val names: Seq[String] = name.trim.split(" ")
    if (names.size < 2) None
    else Some((names.last, names.head, names.drop(1).dropRight(1)))
  }
}

def greet(fullName: String) = fullName match {
  case Names(lastName, firstName, _*) => "Good morning, " + firstName + " " + lastName + "!"
  case _ => "Welcome! Please make sure to fill in your name!"
}

greet("hongwei zhang yanlu wang")


