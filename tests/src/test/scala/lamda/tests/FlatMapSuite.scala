package lamda.tests

import lamda.FlatMap
import lamda.instances.all._
import lamda.syntax.flatmap._

class FlatMapSuite extends munit.FunSuite {
  test("flatmap should apply function to unwrapped value and flatten result") {
    val o1 = Some(1)
    val f = (a: Int) => Some(a + 1)
    val expected = Some(2)
    val obtained = FlatMap[Option].flatMap(o1)(f)
    assert(obtained == expected)
  }

  test(">> should sequence computations and return the result of the second") {
    val o1 = Option(1)
    val o2 = Option(2)
    val expected = o2
    val obtained = o1 >> o2
    assert(obtained == expected)
  }

  test("flatten should reduce nested structure by one level") {
    val oo1 = Some(Some(1))
    val expected = Some(1)
    val obtained = FlatMap[Option].flatten(oo1)
    assert(obtained == expected)
  }

  test(
    "ap which in terms of flatMap should apply function wrapped in context to value wrapped in context"
  ) {
    val o1 = Option(1)
    val f = Option((x: Int) => x + 1)
    val expected = Some(2)
    val obtained = FlatMap[Option].ap(o1)(f)
    assert(obtained == expected)
  }
}
