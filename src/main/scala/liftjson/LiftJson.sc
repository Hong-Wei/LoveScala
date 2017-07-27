import net.liftweb.json
import net.liftweb.json.JsonAST.{JNothing, _}



//1
val nothing: JValue = JNothing
val nothing2: JNothing.type = JNothing
//2
val jNull: JValue = JNull
val jNull2: JNull.type = JNull

//3
val string3: JString = JString("hongwei")
//4
val double4: JDouble = JDouble(1.2)
//5
val int5: JInt = JInt(5)
//6
val bool6:JBool = JBool(true)
//7
val field7: JField = JField("MyJFieldString",string3)
field7.name
field7.values
field7.value
field7.apply(0)
val field71: JField = JField("double",double4)
val field72: JField = JField("int",int5)
val field73: JField = JField("bool",bool6)

//8  List of Jfield
val jObject8: JObject = JObject(field7::field71::Nil)
//9  List of JValue
val array9: JArray = JArray(string3::double4::Nil)


val field74: JField = JField("JArray",array9)
field74.apply(1)
field74.apply(0)
val field75: JField = JField("JObject",jObject8)

//10 excises
//10.1 Any valid json can be parsed into internal AST format
val parseStringToJsonAST = json.parse(""" {
                                          "numbers": [
                                            1,
                                            2,
                                            3,
                                            {"numbers":"numbers"}
                                          ]
                                        } """)
//10.2 Path expressions
val JArray(x) = parseStringToJsonAST \ "numbers" 
val x2 = parseStringToJsonAST \ "numbers"
val x3 = parseStringToJsonAST \\ "numbers"
x2.values
x2.apply(1)


////https://github.com/lift/lift/tree/master/framework/lift-base/lift-json#producing-json
////11 Producing JSON
val jsonPrimitiveList = List(1, 2, 3)
val jsonTuple = ("name" -> "joe")
//val jsonCombine = ("name" -> "joe") ~ ("age" -> 35)
//json.compact(json.render(json.parse(jsonPrimitiveList.toString())))
//json.compact(json.render(json.parse(jsonTuple)))



//918 kbps 115 kB/s
//9.7 Mbps 1.2 MB/s