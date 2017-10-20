import net.liftweb.common.{Box, Full}
import net.liftweb.util.Helpers.tryo

val map: Box[Box[Int]] = tryo{Full(1).map(a => a*2).map(b => b/0)} ?~! {"error "}

val map2: Box[Int] = for{
  a<-  Full(1)
  b<-  Full(a*2)
  c<-  tryo{(b/0)} ?~! {"error "}
} yield 
  c
