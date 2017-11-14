package scalacheck

import org.scalacheck.Gen
import org.scalacheck.Prop
import org.scalacheck.Prop._
import org.scalacheck.Properties

/**
  * ListSpecification created by Brandon Mott on 8/31/17.
  */
object ListSpecification extends Properties("List") {
  val intList = Gen.listOf(Gen.choose(0, 100))
  
  val prop = forAll(intList)(ns => ns.reverse.reverse == ns) && 
    forAll(intList)(ns => ns.headOption == ns.reverse.lastOption)
  
  val failingProp = forAll(intList)(ns => ns.reverse == ns)

}

object StringSpecification extends Properties("String") {

  property("startsWith") = forAll { (a: String, b: String) =>
    (a + b).startsWith(a)
  }

  property("endsWith") = forAll { (a: String, b: String) =>
    (a + b).endsWith(b)
  }

  property("substring") = forAll { (a: String, b: String) =>
    (a + b).substring(a.length) == b
  }

  property("substring") = forAll { (a: String, b: String, c: String) =>
    (a + b + c).substring(a.length, a.length + b.length) == b
  }
}