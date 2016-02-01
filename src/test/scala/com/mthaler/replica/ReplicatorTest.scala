package com.mthaler.replica

import org.scalatest.FunSuite

object ReplicatorTest {
    abstract class HasName extends Product {

    def name: String
  }
  case class Person(name: String, age: Int) extends HasName
  case class Pet(name: String) extends HasName
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

  test("baseClassCopy") {
    implicit class RichHasName[T <: HasName](value: T) {
      def withName(name: String) = Replicator.copy(value, Map("name" -> name))
    }
    val p = Person("Richard Feynman", 42).withName("Paul Dirac")
    println(p)
  }
}
