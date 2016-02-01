package com.mthaler.replica

import org.scalatest.FunSuite

object ReplicatorTest {
  case class Person(name: String, age: Int)
}

class ReplicatorTest extends FunSuite {

  import ReplicatorTest._

  test("replicate") {
    val p = Person("Richard Feynman", 42)

    assertResult(p) {
      Replicator.replicate(p)
    }
  }
}
