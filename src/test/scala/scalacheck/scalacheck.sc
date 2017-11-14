import org.scalacheck.Prop, Prop._
import org.scalacheck.Gen

val intList = Gen.listOf(Gen.choose(0, 100))
val prop =
  forAll(intList)(ns => ns.reverse.reverse == ns) &&
    forAll(intList)(ns => ns.headOption == ns.reverse.lastOption)
val failingProp = forAll(intList)(ns => ns.reverse == ns)	

prop.check

failingProp.check
