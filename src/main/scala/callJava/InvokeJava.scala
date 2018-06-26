import callJava.MyJavaClass

object InvokeJavaClass {

  def main(args: Array[String]) = {
    val javaClass2 = new MyJavaClass()
    val addResult = javaClass2.adder(3, 4)
    println(addResult);
  }
}
   


import java.io.PrintWriter
object IODemo {
 
  def main(args: Array[String]) = {
    val outFile = "1.txt"
    //将第15行数据输出到一个文件中
    writeToFile(outFile, "hello scala")
     
  }
 
  //将内容写入某个文件中,由于scala没有提供写文件的支持，可以使用java.io中的类代替
  def writeToFile(outFile: String, content: String) {
    val out = new PrintWriter(outFile)
    out.write(content)
    out.close()
  }
}