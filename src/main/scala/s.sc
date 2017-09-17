//1 for loop, can have multiple generators of the form i<- expression
var sum =0 
val a: Unit = for (
  i <- 1 to 3;
  j <- 1 to 3
) sum = sum + 1

sum

//This basic for loop, no yield part. it only has the side affect. 
//it return the Unit 
// if you want to get value from this, you need extra var to set and get values.


//2

//BK 
for (
  i <- 1 to 3;
  j <- 1 to 3
)
  print((10 * i + j) + " ")

for(i<-Some(5))
  print(i)
for (i <- 1 to 3; j <- 1 to 3 if i != j)
  print((10 * i + j) + " ")

val a6 = for {
  i <- 1 to 10
  if i > 5
  a7 = 1000

} yield {
  1
  i
  a7
  1 to 10
} // for {} yield {} 是 for 推导式.与第一个生成器的类型兼容!


val a7 = for (c <- "Hello"; i <- 0 to 1) yield (c + i).toChar
val a8 = for (i <- 0 to 1; c <- "Hello") yield (c + i).toChar
val a9 = for (c <- "Hello") yield c
val a10 = for (c <- "Hello") yield c.toChar

val a5 = a6