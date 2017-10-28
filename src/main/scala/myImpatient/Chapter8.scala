package myImpatient.chapter8
/**
  * Created by zhanghongwei on 13/11/16.
  */
class Chapter8 {
  private[this] var _salary: Double = 1.0

  def salary: Double = _salary

  def salary_=(value: Double): Unit = {
    _salary = value
  }

  val age =18

}
//bk 8.1 Extending a Class
class Employee extends Chapter8 {
  private[this] var _salary: Double = 0.0

  override def salary: Double = _salary

  override def salary_=(value: Double): Unit = {
    _salary = value
  }

  private[this] var _name: String = "hongwei"

  def name: String = _name

  def name_=(value: String): Unit = {
    _name = value
  }

  override val age=20

  override def toString: String = getClass.getName + name

}

object Chapter8 extends  App{
  private val employee: Employee = new Employee
  employee.salary=20
  print(employee)
}


trait people

class Chapter81 (val age : Int){
  val a: Int = age
  val b: Int = age
}


//bk 8.1 Extending a Class
case class Employee1 (override val age : Int) extends Chapter81 (age : Int) with people