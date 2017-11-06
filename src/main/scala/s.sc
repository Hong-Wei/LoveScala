val author = "Peter vander Linden"
author match {
  case Name(first, last) if (last.contains(" ")) =>  print(last) // Matches if the author is Peter van der Linden
  case Name(first, last) => 1;print(last)
}

author

object Name {
  def unapply(input: String): Option[(String, String)] = {
    val pos = input.indexOf(" ")
    if (pos == -1) None
    else Some((input.substring(0, pos), input.substring(pos + 1)))
  }
}

object IsCompound {
  def unapply(input: String): Boolean = input.contains(" ")
}