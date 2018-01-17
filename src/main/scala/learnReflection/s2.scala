//https://docs.scala-lang.org/overviews/reflection/overview.html

package learnReflection

import scala.reflect.runtime.{universe => ru}
object s2 extends App {

  //BK 2 INSTANTIATING A TYPE AT RUNTIME
  //Types obtained through reflection can be instantiated by invoking their 
  // constructor using an appropriate “invoker” mirror
  case class Person(name: String)

  // In the first step we obtain a mirror m which makes all classes and types 
  // available that are loaded by the current classloader, including class Person.
  val m = ru.runtimeMirror(getClass.getClassLoader)
  val classPerson = ru.typeOf[Person].typeSymbol.asClass
  val cm = m.reflectClass(classPerson)

}