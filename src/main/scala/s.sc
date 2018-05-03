import scala.reflect.runtime.universe._

class Foo {
 def currentMethodName() : String = Thread.currentThread.getStackTrace()(2).getMethodName
 
 def foo(s: String, i: Int) = s * i

 def getMeASammy(a: String) = {
  val a = typeOf[Foo].member(TermName(currentMethodName())).asMethod.paramLists.head
  
  
  println(a ) 
  println(currentMethodName()) 
 
 }
}

val params = typeOf[Foo].member(TermName("foo")).asMethod.paramLists.head
val params2 = typeOf[Foo].member(TermName("getMeASammy")).asMethod.paramLists.head

params.map(_.typeSignature.typeSymbol) 

typeOf[Foo].members.getClass

new Foo().getMeASammy("hng")

this.getClass.getName