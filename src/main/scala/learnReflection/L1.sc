//https://docs.scala-lang.org/overviews/reflection/overview.html
//BK 1 INSPECTING A RUNTIME TYPE (INCLUDING GENERIC TYPES AT RUNTIME)

import scala.reflect.runtime.{universe => ru}
val l = List(1,2,3)
//context bounds->[T:...]
def getTypeTag[T: ru.TypeTag](obj: T) = ru.typeTag[T]
val typeTag = getTypeTag(l)
val theType = getTypeTag(l).tpe
val decls = theType.decls.take(100)




