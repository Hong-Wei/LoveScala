//Error handling
import scala.util.control.Exception.catching
def handling[Ex <: Throwable, T](exType: Class[Ex])(block: => T): Either[Ex, T] =
  catching(exType).either(block).asInstanceOf[Either[Ex, T]]


case class Customer(age: Int)
class Cigarettes
case class UnderAgeFailure(age: Int, required: Int)
def buyCigarettes(customer: Customer): Either[UnderAgeFailure, Cigarettes] =
  if (customer.age < 16) Left(UnderAgeFailure(customer.age, 16))
  else Right(new Cigarettes)



//Processing collections
import java.net.URL
type Citizen = String
case class BlackListedResource(url: URL, visitors: Set[Citizen])

val blacklist = List(
  BlackListedResource(new URL("https://google.com"), Set("John Doe", "Johanna Doe")),
  BlackListedResource(new URL("http://yahoo.com"), Set.empty),
  BlackListedResource(new URL("https://maps.google.com"), Set("John Doe")),
  BlackListedResource(new URL("http://plus.google.com"), Set.empty)
)

val checkedBlacklist: List[Either[URL, Set[Citizen]]] =
  blacklist.map(resource =>
    if (resource.visitors.isEmpty) Left(resource.url)
    else Right(resource.visitors))

val suspiciousResources = checkedBlacklist.flatMap(_.left.toOption)
val problemCitizens = checkedBlacklist.flatMap(_.right.toOption).flatten.toSet