package com.mthaler.replica

import org.scalatest.FunSuite

object ReplicatorTest {
  abstract class HasName extends Product {

    def name: String
  }
  case class Person(name: String, age: Int) extends HasName
  case class Pet(name: String) extends HasName
  case class Test(a: Boolean, b: String, c: Person)
  case class Time(val ticks: Long) extends AnyVal
  case class Test2(enabled: Boolean, time: Time)
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
    assertResult(Person("Paul Dirac", 42)) {
      Person("Richard Feynman", 42).withName("Paul Dirac")
    }
  }

  test("complexClass") {
    implicit class RichTest(value: Test) {
      def withBoolean(b: Boolean) = Replicator.copy(value, Map("a" -> b))
    }
    val test = Test(true, "test", Person("Richard Feynman", 42))
  }

  test("valueClass") {
    implicit class RichTest(value: Test2) {
      def withBoolean(b: Boolean) = Replicator.copy(value, Map("enabled" -> b))
    }
    val test = Test2(true, Time(0))
    assertResult(Test2(false, Time(0))) {
      test.withBoolean(false)
    }
  }

  test("unwrap") {
    assertResult(0l) {
      val t = Time(0)
      Replicator.unwrap(t)
    }
  }
}
