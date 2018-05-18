import net.liftweb.common.{Box, Full}
import net.liftweb.util.Helpers.{tryo, _}

val box: Box[Int] = for{
  b <- tryo{1/0} ?~!"hongwei"
  a <- Some(1)
} yield{
  a
}