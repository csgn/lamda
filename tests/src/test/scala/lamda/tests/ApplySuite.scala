package lamda.tests

import lamda.Apply
import lamda.instances.all._
import lamda.syntax.apply._

class ApplySuite extends munit.FunSuite {
  test(
    "ap should apply function wrapped in context to value wrapped in context"
  ) {
    val o1 = Option(1)
    val f = Option((x: Int) => x + 1)
    val expected = Some(2)
    val obtained = Apply[Option].ap(o1)(f)
    assert(obtained == expected)
  }

  test(
    "ap2 should apply function wrapped in context with two arguments wrapped in context"
  ) {
    val o1 = Option(1)
    val o2 = Option(2)
    val f = Option((x: Int, y: Int) => x + y)
    val expected = Some(3)
    val obtained = Apply[Option].ap2(o1, o2)(f)
    assert(obtained == expected)
  }

  test(
    "product which in terms of ap should combine two wrapped values into a tuple"
  ) {
    val o1 = Some(1)
    val o2 = Some(2)
    val expected = Some((1, 2))
    val obtained = Apply[Option].product(o1, o2)
    assert(obtained == expected)
  }

  test("productR should return second wrapped value") {
    val o1 = Option(1)
    val o2 = Option(2)
    val expected = o2
    val obtained = Apply[Option].productR(o1, o2)
    val obtained2 = o1 *> o2
    assert(obtained == expected)
    assert(obtained2 == expected)
  }

  test("productL should return first wrapped value") {
    val o1 = Option(1)
    val o2 = Option(2)
    val expected = o1
    val obtained = Apply[Option].productL(o1, o2)
    val obtained2 = o1 <* o2
    assert(obtained == expected)
    assert(obtained2 == expected)
  }
}
