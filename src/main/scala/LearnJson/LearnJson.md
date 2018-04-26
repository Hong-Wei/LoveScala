# 1 what is Deserialize & Serialize in JSON

https://stackoverflow.com/questions/3316762/what-is-deserialize-serialize-in-json  
Serialization   --  objects --> String  
Deserialization --  String  --> an object  


# 2 lift-json 
Parsing(Deserialization)  -- String  --> objects.  
Formating(Serialization)  -- objects --> String. 

A central concept in lift-json library is Json AST which models the structure of
a JSON document as a syntax tree.

    sealed abstract class JValue
    case object JNothing extends JValue // 'zero' for JValue
    case object JNull extends JValue
    case class JString(s: String) extends JValue
    case class JDouble(num: Double) extends JValue
    case class JInt(num: BigInt) extends JValue
    case class JBool(value: Boolean) extends JValue
    case class JObject(obj: List[JField]) extends JValue
    case class JArray(arr: List[JValue]) extends JValue

    case class JField(String, JValue)
    
All features are implemented in terms of above AST. Functions are used to transform
the AST itself, or to transform the AST between different formats. Common transformations
are summarized in a following picture.

![Json AST](/Users/zhanghongwei/Documents/workspaceScala/LoveScala/src/main/scala/LearnJson/json.png)







#Questions
1 what is the difference between the renderCompact and deserialize both are from  Json AST -->string

Serialize to String.(Case class a --> JValue --> String)

    def write[A <: Any](a: A)(implicit formats: Formats): String =
        compactRender(Extraction.decompose(a)(formats))


Deserialize from a String (String json-->JValue --> Case class )

    def read[A](json: String)(implicit formats: Formats, mf: Manifest[A]): A =
        parse(json).extract(formats, mf)

