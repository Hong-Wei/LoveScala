case class MyInt(num: Int)

val int = MyInt(1)
val int1 = MyInt(2)
var abc:Any = List(int,int1)
// var abc:Any = "String"



val ab = abc match {
  case l : String  => println("String")
  case l:List[f] => println("list === " +f.getClass.getCanonicalName)
  case List(f) => println("list === " +f.getClass.getCanonicalName)
  case List(f,_*) => println("list === " +f.getClass.getCanonicalName)
  case l => println("l")
}