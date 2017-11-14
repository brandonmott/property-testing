package brandonmott.testing

trait Prop {
  import Prop._
  
  def check: Either[(FailedCase, SuccessCount), SuccessCount]

  def forAll[A](a: Gen[A])(f: A => Boolean): Prop

  def &&(p: Prop): Prop
}

object Prop {
  type FailedCase = String
  type SuccessCount = Int
}