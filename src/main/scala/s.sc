
// trait extends the class
trait LoggedException131 extends Exception  {
  def log() {
    getMessage
  }
}

// trait use self type
trait LoggedException132   {
  this: Exception =>
  def log() {
    getMessage
  }
}


class Some1 extends LoggedException131
class Some2 extends Exception with LoggedException132