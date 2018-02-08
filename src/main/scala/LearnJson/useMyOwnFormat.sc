import java.text.SimpleDateFormat
import java.util.Date

import net.liftweb.json
import net.liftweb.json.{Extraction, JValue, JsonParser}
import net.liftweb.json._

//implicit val formats = Serialization.formats(ShortTypeHints(Nil))
// added the dateFormat here. up line is wrong .
implicit val formats = new Formats {
  val dateFormat =  net.liftweb.json.DefaultFormats.dateFormat
  override val typeHints = ShortTypeHints(Nil)
}




val exampleDateString: String = "22/08/2013"
val simpleDateFormat: SimpleDateFormat = new SimpleDateFormat("dd/mm/yyyy")
val exampleDate = simpleDateFormat.parse(exampleDateString)
val defaultFilterFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
val fallBackFilterFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")

case class PostKycCheckJSON(
  customer_number: String,
  date: Date,
  how: String,
  staff_user_id: String,
  staff_name: String,
  satisfied: Boolean,
  comments: String
)


val postKycCheckJSON = PostKycCheckJSON(
  customer_number = "1239879",
  date = exampleDate,
  how = "online_meeting",
  staff_user_id = "67876",
  staff_name = "Simon",
  satisfied = true,
  comments = "String"
)
val decompose: JValue = Extraction.decompose(postKycCheckJSON)
json.prettyRender(decompose)





decompose.extract[PostKycCheckJSON]























