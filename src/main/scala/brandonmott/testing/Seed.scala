package brandonmott.testing

import cats.data.State

case class Seed(long: Long) {
  def next = Seed(long * 6364136223846793005L + 1442695040888963407L)
  
}

object Seed {
  import SeedOps._
  val long: State[Seed, Long] = State(seed => (seed.next, seed.long))
  val boolean: State[Seed, Boolean] = long.map(long => long > 0)
  val int: State[Seed, Int] = long.map(long => long.toValidInt)
  val double: State[Seed, Double] = int.map(long => long / (Int.MaxValue.toDouble + 1))
  val nonNegativeInt: State[Seed,Int] = int.map(next => if(next < 0) -(next + 1) else next)
  
}

object SeedOps {
  implicit class LongHelpers(long: Long) {
    def toValidInt: Int = {
      def init(l: Long): Long = if (l > Int.MaxValue) init(l / (Int.MaxValue >>> 16)) else l
      init(long).toInt
    }
  }
}