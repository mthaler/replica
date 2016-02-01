package com.mthaler.replica

import org.scalatest.FunSuite

object ReplicatorTest {
  case class Person(name: String, age: Int)
}

class ReplicatorTest extends FunSuite {

  import ReplicatorTest._

  test("copy") {
    assertResult(Person("Richard Feynman", 42)) {
      Replicator.copy(Person("Richard Feynman", 42))
    }
    assertResult(Person("Richard Feynman", 43)) {
      Replicator.copy(Person("Richard Feynman", 42), Map("age" -> 43))
    }
  }
}
