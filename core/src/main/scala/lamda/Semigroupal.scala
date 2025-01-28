package lamda

trait Semigroupal[F[_]] {

  /** Example:
    * {{{
    * scala> import lamda.Semigroupal
    * scala> import lamda.syntax.semigroupal._
    * scala> import lamda.instances.option._
    *
    * scala> Semigroupal[Option].product(Some(1), Some(2))
    * res: Option[(Int, Int)] = Some((1, 2))
    * }}}
    */
  def product[A, B](fa: F[A], fb: F[B]): F[(A, B)]
}

object Semigroupal {
  def apply[F[_]](implicit instance: Semigroupal[F]): Semigroupal[F] = instance

  trait Ops {
    implicit class SemigroupalOps[F[_], A](fa: F[A])(implicit
      F: Semigroupal[F]
    ) {
      final def product[B](fb: F[B]): F[(A, B)] = F.product(fa, fb)
    }
  }
}
