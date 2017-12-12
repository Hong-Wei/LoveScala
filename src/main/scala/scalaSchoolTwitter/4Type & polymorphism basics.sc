//http://twitter.github.io/scala_school/type-basics.html
import scala.collection.immutable.Seq
//Types in Scala
//Scala’s powerful type system allows for very rich expression. Some of its chief features are:
  //1 parametric polymorphism roughly, generic programming
  //2 (local) type inference roughly, why you needn’t say val i: Int = 12: Int
  //3 existential quantification roughly, defining something for some unnamed type
  //4 views we’ll learn these next week; roughly, “castability” of values of one type to another


//Parametric polymorphism
val list: List[Any] = List(2, 1.1, "bar") 
//Input is Any, we can not recover any type info about individual members
//We need devolve into series of casets `asInstanceOf[]` and we would lack type safety(They are all dynamic)

//BK 1 Polymorphism is achieved through specifying type variables.
//Here we add type A, when you have the input, Scala can inference the Type Int, and used it when return value
def drop1[A](l: List[A]) = l.tail
val drop: List[Int] = drop1(List(1,2,3))

//Scala has rank-1 polymorphism

def toList[A](a: A) = List(a) //rank-1 is ok 

//This does not compile, because all type variables have to be fixed at the invocation site. Even if you “nail down” type B,
//def foo[A, B](f: A => List[A], b: B) = f(b)//rank-2 is wrong. f: A => List[A] is rank-1, but f(b), b is still generic, rank-2 is wrong



//BK 2 Type inference
//The classic method for type inference in functional programming languages is Hindley-Milner, and it was first employed in ML.
//Scala’s type inference system works a little differently, but it’s similar in spirit: infer constraints, and attempt to unify a type.

//This is wrong 
//{ x => x }

//This is right, it is `def intToInt: (Int) => Int = { x:Int => x }`, scala always need the Type explicitly
{ x:Int => x }

//In scala all type inference is local. Scala considers one expression at a time. For example:
def id[T](x: T) = x
val id1: Int = id(322)
val id2: String = id("String")
val id3: Array[Int] = id(Array(1,2,3))




//BK 3 变性 Variance
//Type system has to account for class hierarchies together with polymorphism
  // A central question that comes up when mixing OO with polymorphism is: 
  // if T’ is a subclass of T  `----->` is Container[T’] considered a subclass of Container[T]? 
  // Variance annotations allow you to express the following relationships between class hierarchies & polymorphic types
// ___________________________________________________________________________
//                  |            Meaning	               |   Scala notation
//covariant	        | C[T’] is a subclass of C[T]	       |    [+T]
//contravariant	    | C[T] is a subclass of C[T’]	       |    [-T]
//invariant	        | C[T] and C[T’] are not related     |  	[T]
// ___________________________________________________________________________



//eg : `String` is a subtype of `AnyRef`
// 1 covariant 
class Covariant[+A]
val cv: Covariant[AnyRef] = new Covariant[String]  // Covariant[String]  is subtype of Covariant[AnyRef]  

// 2 contravariant
class Covariant2[-A]
val cv2: Covariant2[String] = new Covariant2[AnyRef]  // Covariant[AnyRef]  is subtype of Covariant[String]       

//eg contravariant2: 
//逆变似乎很奇怪。什么时候才会用到它呢？令人惊讶的是，函数特质的定义就使用了它！
trait Function1 [-T1, +R] extends AnyRef

//eg contravariant3: 
//如果你仔细从替换的角度思考一下，会发现它是非常合理的。让我们先定义一个简单的类层次结构：
class Animal { val sound = "rustle" }
class Bird extends Animal { override val sound = "call" }
class Chicken extends Bird { override val sound = "cluck" }
//假设你需要一个以Bird为参数的函数
//--> 标准动物库有一个函数满足了你的需求，但它的参数是Animal。
//在大多数情况下，如果你说“我需要一个`Animal`，我有一个`Animal`的子类”是可以的。
//但是，在函数参数这里是逆变的。如果你需要一个接受参数类型Bird的函数变量，但却将这个变量指向了接受参数类型为Chicken的函数，
//那么给它传入一个`Chicken`时就会出错。然而，如果将该变量指向一个接受参数类型为Animal的函数就不会有这种问题
//参数 `Bird` 是逆变点
val getTweet: (Bird => String) = ((a: Animal) => a.sound )
//val getTweet2: (Bird => String) = ((a: Chicken) => a.sound ) --Wrong

//返回值 `Bird` 是协变点
//函数的返回值类型是协变的。如果你需要一个返回Bird的函数，但指向的函数返回类型是Chicken，这当然是可以的。
val hatch: (() => Bird) = (() => new Chicken )
//val hatch2: (() => Bird) = (() => new Animal ) --Wrong



//BK 3 Bounds
//Scala allows you to restrict polymorphic variables using bounds. These bounds express subtype relationships.
//def cacophony[T](things: Seq[T]) = things map (_.sound) --Wrong
def biophony[T <: Animal](things: Seq[T]) = things map (_.sound)// upper bound Animal
biophony(Seq(new Chicken, new Bird))


val flock: List[Bird] = List(new Bird, new Bird)
//eg1: List[+T] is covariant; a list of Birds is a list of Animals.
val birds: Seq[Bird] = new Chicken :: flock

//eg2: List also defines ::[B >: T](x: B) which returns a List[B]. Notice the B >: T. That specifies type B as a superclass of T. 
// That lets us do the right thing when prepending an Animal to a List[Bird]:
val animals: Seq[Animal] = new Animal :: flock
val animals2: List[Object] = flock :: new Animal ::Nil


//BK 4 Quantification
def count[A](l: List[A]): Int = l.size
//有时候，你并不关心是否能够命名一个类型变量
def count1(l: List[_]): Int = l.size 
//This is shorthand for
def count3(l: List[T forSome { type T }]) = l.size


//You lose the Type info here.
def drop2(l: List[_]) = l.tail
drop2(List(1,2,3))
drop2(List("one", "two", "three")) 


//You can use _ to constrain the `Type` info.
def drop3(l: List[_ <: AnyRef]) = l map (_.hashCode)
drop3(List("one", "two", "three"))
//drop3(List(1,2,3)) //--> this is RuntimeException: Int is not `AnyRef`, it is only `Any`.
