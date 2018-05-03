package hongwei.good.boy

object s1 extends App {

 import scala.reflect.runtime.universe._

 class Foo {
  val getCurrentClassName = this.getClass.getName
  def getCurrentMethodName = Thread.currentThread.getStackTrace()(2).getMethodName

  def foo(s: String, i: Int) = s * i

  def getMeASammy(a: String, b : String)(implicit cacheKey: String = getCurrentClassName+"1 "+getCurrentMethodName+typeOf[Foo].member(TermName(getCurrentMethodName)).asMethod.paramLists.head.map(_.typeSignature.typeSymbol)+"2 "+a+b) = {
   
   println(typeOf[Foo].member(TermName("getMeASammy")).asMethod.paramLists.head.map(_.typeSignature.typeSymbol).toString())
   
   println("cacheKey: "+cacheKey) // parameters types.
  }
 }

 new Foo().getMeASammy("H1","H2")
 
}