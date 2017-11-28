trait Doctrait {
  def getDog: String
}

case class MyDog(
                  getDog: String
                ) extends Doctrait


case class MyDog2(
                  getDog: String,
                  newDog:String
                )  extends Doctrait{
  val newFilst: String=newDog
}



val dog = MyDog2("ongwei","Lulu")

dog.getDog
dog.newFilst