//1 模式匹配(pattern-matching) -- http://hongjiang.info/scala-pattern-matching-1/

//1.1 表示某种结构的
// 匹配一个数组，它由三个元素组成，第一个元素为1，第二个元素为2，第三个元素为3
Array(1,2,3) match { case Array(1,2,3) => println("ok") ;}

// 匹配一个数组，它至少由一个元素组成，第一个元素为1
Array(1,2,3) match { case Array(1,_*) => println("ok")}

// 匹配一个List，它由三个元素组成，第一个元素为“A"，第二个元素任意类型，第三个元素为"C"
List("A","B","C") match{ case List("A",_,"C") => println("ok") }

//1.2 可以是常量，或类型
val a = 100
// 常量模式，如果a与100相等则匹配成功
a match { case 100 => println("ok") }
// 类型模式，如果a是Int类型就匹配成功
a match { case _:Int => println("ok") }

//在 scala里对pattern有明确的定义，在形式上有以下几种pattern：
//1)常量模式(constant patterns) 包含常量变量和常量字面量
val site = "alibaba.com"
site match { case "alibaba.com" => println("ok") }
val ALIBABA="alibaba.com"
//注意这里常量必须以大写字母开头,否则认为是变量.case 里面会重新定义变量
def foo(s:String) { s match { case ALIBABA => println("ok") } }

//2) 变量模式(variable patterns)
//确切的说单纯的变量模式没有匹配判断的过程，只是把传入的对象给起了一个新的变量名。
//不过这里有个约定，对于变量，要求必须是以小写字母开头，否则会把它对待成一个常量变量
val Unit1 = site match { case whateverName => println(whateverName) }

//变量模式通常不会单独使用，而是在多种模式组合时使用，比如
List(1,2) match{ case List(x,2) => println(x) }

//3) 通配符模式(wildcard patterns)
//通配符用下划线表示："_" ，可以理解成一个特殊的变量或占位符.通常用于代表所不关心的部分，它不像变量模式可以后续的逻辑中使用这个变量。
List(1,2,3) match{ case List(_,_,3) => println("ok") }

//4) 构造器模式(constructor patterns)
//这个是真正能体现模式匹配威力的一个模式！
//我们来定义一个二叉树
//抽象节点
trait Node
//具体的节点实现，有两个子节点
case class TreeNode(v:String, left:Node, right:Node) extends Node
//Tree，构造参数是根节点
case class Tree(root:TreeNode)

val tree = Tree(TreeNode("root",TreeNode("left",null,null),TreeNode("right",null,null)))

//期望一个树的构成是根节点的左子节点值为”left”，右子节点值为”right”并且右子节点没有子节点
tree.root match {
  case TreeNode(_, TreeNode("left",_,_), TreeNode("right",null,null)) =>
    println("bingo")
}

//5) 类型模式(type patterns)
"hello" match { case _:String => println("ok") }
//跟 isInstanceOf 判断类型的效果一样，需要注意的是scala匹配泛型时要注意，
def foo(a:Any) = a match {
  case a :List[String] => println("ok"); //通常对于泛型直接用通配符替代，上面的写为 case a : List[_] => …
  case _ =>
}
foo(List("A"))
foo(List(2))
//如果使用了泛型，它会被擦拭掉，如同java的做法，所以上面的 List[String] 里的String运行时并不能检测
//通常对于泛型直接用通配符替代，上面的写为 case a : List[_] => …

// Gerneric use pattern-matching
//http://stackoverflow.com/questions/16056645/how-to-pattern-match-on-generic-type-in-scala/16057002#16057002
import reflect.runtime.universe._
def matchContainer[A: TypeTag](c: List[A]) = c match {
  case c: List[String] if typeOf[A] <:< typeOf[String] => println("string: ")
  case c: List[Double] if typeOf[A] <:< typeOf[Double] => println("double" )
  case c: List[_] => println("other")
}
matchContainer(List("A"))
matchContainer(List(2))
print("I_am_here !!!!!!!!!!!!!!!!!!!!!!!!!!!!!#########################!!!!!!!!!!!!!!!!!!!!!!!!!!!!!###################")

//6) 变量绑定模式 (variable binding patterns)
//希望匹配到左边节点值为”left”就返回这个节点
tree.root match {
  case TreeNode(_, leftNode@TreeNode("left",_,_), _) => leftNode
}


