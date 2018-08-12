//https://scalameta.org

//https://scalameta.org/#Features
import scala.meta._
//High-fidelity parsing

//The abstract syntax trees in the printout below contain comprehensive 

//1information about formatting and comments. 
val a9: Parsed[Term] = "x + y /* adds x and y */".parse[Term] 
val a10 = "List[ Int ]".parse[Type]


//2Tokens
val tree = "x + y /* adds x and y */".parse[Term].get
val a16 = tree.syntax

val a17 = tree.structure //The AST tree no comments (Term,,Term,,)
val a18 = tree.tokens.structure //The Tokens contains the comments.(Positions)

//3Quasiqutoes
val addition: Term.ApplyInfix = q"x + y"
val q"$x + $y" = addition
val a25:Term = q"$x"
val a26:Term = q"$y"

//This will cause: type mismatch when unquoting. 
//q"def y: $x"

//4Dialects
import scala.meta.dialects.Sbt0137
val a33: Parsed[Source] = Sbt0137("""
  lazy val root = (project in file(".")).
  settings(name := "hello")
""").parse[Source]


//5Semantic API.

//6Multiple platforms

