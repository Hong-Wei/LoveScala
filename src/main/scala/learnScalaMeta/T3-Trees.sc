//https://scalameta.org/tutorial/#Trees 
import scala.meta._
val a3: Defn.Class = q"case class User(name: String, age: Int)"

val method = q"def `is a baby` = age < 1"
val a6 = q"""case class User(name: String, age: Int) {
  $method
}
"""

val a11 = q"def `is a baby` = age < 1" match {
  case q"def $name = $body" =>
    s"You ${name.syntax} if your ${body.syntax}"
}
//ignore the comments
val a16: String = q"val x = 2 // assignment".syntax
//if you need the comments
val a18: String ="val x = 2 // assignment".parse[Stat].get.syntax

val a20: Source = "object Main extends App { println(1) }".parse[Source].get

val a22: Parsed[Source] = new java.io.File("/Users/zhanghongwei/Documents/workspaceScala/LoveScala/src/main/scala/learnScalaMeta/ParseMe.scala").parse[Source]

// "val x = 2".parse[Source] will throw the exception, 
// can not parse a statement as a compilation unit
val a26: Stat = "val x = 2".parse[Stat].get
val a27: Case = "case Foo(bar) if bar > 2 => println(bar)".parse[Case].get
//Look into following object, there are 16 types can parse.
scala.meta.parsers.Parse


val a32: Source = dialects.Sbt0137(
  """
  lazy val core = project.settings(commonSettings)

  lazy val extra = project.dependsOn(core)
  """
).parse[Source].get

//Tree.collect

val a42 = source"""sealed trait Op[A]
    object Op extends B {
      case class Foo(i: Int) extends Op[Int]
      case class Bar(s: String) extends Op[String]
    }""".collect { case cls: Defn.Class => cls.name }


//Tree.transform
//myList.filter(_ > 3 + a).headOption --> myList.filter(_ > 3 + a) 
val a51 = q"myList.filter(_ > 3 + a).headOption // comments are removed :(".transform {
  case q"$lst.filter($cond).headOption" => q"$lst.find($cond)"
} 


val a56 = q"kas foobar".structure
val a57 = q"kas foobar".syntax
