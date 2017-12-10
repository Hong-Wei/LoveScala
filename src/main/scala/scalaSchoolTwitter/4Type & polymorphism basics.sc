
// 变性 Variance
// 1 covariant 
class Covariant[+A]
val cv: Covariant[AnyRef] = new Covariant[String]

// 2 contravariant
class Covariant2[-A]
val cv2: Covariant2[String] = new Covariant2[AnyRef]



