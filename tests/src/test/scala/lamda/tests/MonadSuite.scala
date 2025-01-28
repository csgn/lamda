package lamda.tests

import lamda.Monad
import lamda.instances.all._

class MonadSuite extends munit.FunSuite {
  test(
    "map which in terms of flatMap and pure should apply function to wrapped value"
  ) {
    val o1 = Some(1)
    val f = (a: Int) => a + 1
    val expected = Some(2)
    val obtained = Monad[Option].map(o1)(f)
    assert(expected == obtained)
  }
}
