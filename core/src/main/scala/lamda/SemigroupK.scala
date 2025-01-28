package lamda

trait SemigroupK[F[_]] {

  /** Example:
    * {{{
    * scala> import lamda.SemigroupK
    * scala> import lamda.syntax.semigroupk._
    * scala> import lamda.instances.option._
    *
    * scala> SemigroupK[Option].combineK(Some(1), Some(2))
    * res0: Option[Int] = Some(1)
    *
    * scala> SemigroupK[Option].combineK(Some(1), None)
    * res1: Option[Int] = Some(1)
    *
    * scala> SemigroupK[Option].combineK(None, Some(2))
    * res2: Option[Int] = Some(2)
    *
    * scala> SemigroupK[Option].combineK(None, None)
    * res3: Option[Int] = None
    * }}}
    */
  def combineK[A](a: F[A], b: F[A]): F[A]
}

object SemigroupK {
  def apply[F[_]](implicit instance: SemigroupK[F]): SemigroupK[F] = instance

  trait Ops {
    implicit class SemigroupKOps[F[_], A](a: F[A])(implicit F: SemigroupK[F]) {
      final def combineK(b: F[A]): F[A] = F.combineK(a, b)
    }
  }
}
