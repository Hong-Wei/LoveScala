//http://danielwestheide.com/blog/2013/01/23/the-neophytes-guide-to-scala-part-10-staying-dry-with-higher-order-functions.html


//BK 1 On higher-order functions
// parameters and return may contain the function
//1 One or more of its parameters is a function, and it returns some value.
//2 It returns a function, but none of its parameters is a function.
//3 Both of the above: One or more of its parameters is a function, and it returns a function.




//BK 2 And out of nowhere, a function was born
case class Email(
  subject: String,
  text: String,
  sender: String,
  recipient: String
)

// a type alias for our function
type EmailFilter = Email => Boolean
def newMailsForUser(mails: Seq[Email], f: EmailFilter) = mails.filter(f)


// Each of these four vals is a function that returns an EmailFilter
val sentByOneOf: Set[String] => EmailFilter = senders => email => senders.contains(email.sender)
val notSentByAnyOf: Set[String] => EmailFilter = senders => email => !senders.contains(email.sender)
val minimumSize: Int => EmailFilter = n => email => email.text.size >= n
val maximumSize: Int => EmailFilter = n => email => email.text.size <= n

//val emailFilter: EmailFilter = notSentByAnyOf(Set("johndoe@example.com"))
val emailFilter: EmailFilter = sentByOneOf(Set("johndoe@example.com"))
val mails = 
  Email(
    subject = "It's me again, your stalker friend!",
    text = "Hello my friend! How are you?",
    sender = "johndoe@example.com",
    recipient = "me@example.com") ::
  Email(
  subject = "It's me again, your stalker friend!",
    text = "Hello my friend! How are you?",
    sender = "johndoe@example.com",
    recipient = "me@example.com") :: 
    Nil
val user: Seq[Email] = newMailsForUser(mails, emailFilter)
user // returns an empty list



//BK 3 Reusing existing functions
type SizeChecker = Int => Boolean
val sizeConstraint: SizeChecker => EmailFilter = f => email => f(email.text.size)
val minimumSize1: Int => EmailFilter = n => sizeConstraint(_ >= n)
val maximumSize1: Int => EmailFilter = n => sizeConstraint(_ <= n)


//BK 4 Function composition
def complement[A](predicate: A => Boolean) = (a: A) => !predicate(a)

val notSentByAnyOf1 = sentByOneOf andThen(g => complement(g))
val notSentByAnyOf2 = sentByOneOf andThen(complement(_))


//BK 5 Composing predicates

def any[A](predicates: (A => Boolean)*): A => Boolean =
  a => predicates.exists(pred => pred(a))
def none[A](predicates: (A => Boolean)*) = complement(any(predicates: _*))
def every[A](predicates: (A => Boolean)*) = none(predicates.view.map(complement(_)): _*)

val filter: EmailFilter = every(
  notSentByAnyOf(Set("johndoe@example.com")),
  minimumSize(100),
  maximumSize(10000)
)

//BK 6 Composing a transformation pipeline
val addMissingSubject = (email: Email) =>
  if (email.subject.isEmpty) email.copy(subject = "No subject")
  else email
val checkSpelling = (email: Email) =>
  email.copy(text = email.text.replaceAll("your", "you're"))
val removeInappropriateLanguage = (email: Email) =>
  email.copy(text = email.text.replaceAll("dynamic typing", "**CENSORED**"))
val addAdvertismentToFooter = (email: Email) =>
  email.copy(text = email.text + "\nThis mail sent via Super Awesome Free Mail")

val pipeline = Function.chain(Seq(
  addMissingSubject,
  checkSpelling,
  removeInappropriateLanguage,
  addAdvertismentToFooter))


//BK 7 Higher-order functions and partial functions

//val handler = fooHandler orElse barHandler orElse bazHandler