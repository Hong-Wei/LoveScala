//https://scalameta.org/tutorial/#Tokens
import scala.meta._
val a2: Tokenized ="val x = 2".tokenize //String -> Tokenized 
val a3: Tokens ="val x = 2".tokenize.get
val a4: String = "val x = 2".tokenize.get.syntax //return a String
val a5: String = "val x = 2".tokenize.get.structure
val a6: Token ="val x = 2".tokenize.get.head
val a7: String ="val x = 2".tokenize.get.
  map(x => f"${x.structure}%10s -> ${x.productPrefix}").
  mkString("\n")

val a11= "foobar".tokenize.get(1)
val a12="foobar kas".tokenize.get(1)
a11==a12 //false: Token equality is implemented with reference equality. 
//Need to be explict if your mean syntactic or structural 
"foobar".tokenize.get(1).syntax == "foobar kas".tokenize.get(1).syntax
"kas foobar".tokenize.get(3).syntax == "foobar kas".tokenize.get(1).syntax
"foobar".tokenize.get(1).structure == "foobar kas".tokenize.get(1).structure
"kas foobar".tokenize.get(3).structure == "foobar kas".tokenize.get(1).structure
"kas foobar".tokenize.get(3).structure 
"foobar kas".tokenize.get(1).structure


val a23: Tokenized = """ val str = "unclosed literal" """.tokenize

""" val str = "closed literal" """.tokenize match {
  case tokenizers.Tokenized.Success(tokenized) => tokenized.tokens
  case tokenizers.Tokenized.Error(e, _, _) => ???
}