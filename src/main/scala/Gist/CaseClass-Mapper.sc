
val doc = <html><head><title>Fred's Memoirs</title></head><body>...</body></html>

val items = <li>Fred</li><li>Wilma</li>;

val (x, y) = (1, 2)
x < y // OK
x<y // OK
//  x <y // Error—unclosed XML literal

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
foo("123")

//2) 变量模式(variable patterns)
//确切的说单纯的变量模式没有匹配判断的过程，只是把传入的对象给起了一个新的变量名。
//不过这里有个约定，对于变量，要求必须是以小写字母开头，否则会把它对待成一个常量变量
val Unit1 = site match { case whateverName => println(whateverName) }


print("I am here !!!")
print("I am here !!!")
print("I am here !!!")
print("I am here !!!")
print("I am here !!!")