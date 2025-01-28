package lamda.tests

import lamda.Applicative
import lamda.instances.all._

class ApplicativeSuite extends munit.FunSuite {
  test("pure should wrap value in context") {
    val a = 1
    val expected = Some(a)
    val obtained = Applicative[Option].pure(a)
    assert(expected == obtained)
  }

  test(
    "map which in terms of ap and pure should apply function to wrapped value"
  ) {
    val o1 = Some(1)
    val f = (a: Int) => a + 1
    val expected = Some(2)
    val obtained = Applicative[Option].map(o1)(f)
    assert(expected == obtained)
  }

  test("map2 should combine two wrapped values with given function") {
    val o1 = Some(1)
    val o2 = Some(2)
    val f = (a: Int, b: Int) => a + b
    val expected = Some(3)
    val obtained = Applicative[Option].map2(o1, o2)(f)
    assert(expected == obtained)
  }
}
