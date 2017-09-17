//High Order Function -- produce a function
import scala.math.{ceil, sqrt}

//1: function as a parameter
def valueAtOneQuarter(f: Double => Double): Double = f(0.25)

valueAtOneQuarter(ceil _)
valueAtOneQuarter(sqrt _)
//2 function type: is a function with one parameter ---> ((Double) => Double) => Double 
// It is a function that receivers a function, `higher-order-function`
val functionType: ((Double) => Double) => Double = valueAtOneQuarter
functionType(ceil)

//type : normal  --f1: (x: Double          )Double : input Double, output Double
//type : this    --f2: (f: Double => Double)Double : input fuction,output Double

//eg2: function as a result
def mulBy(factor: Double): (Double => Double) = (x: Double) => factor * x

// by = (x:Double)=>4*x
private val by: (Double => Double) = mulBy(4)
private val by2: Double = by(3)
private val by1: Double = mulBy(4)(3)