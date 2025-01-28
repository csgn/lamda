package lamda

trait Applicative[F[_]] extends Apply[F] {

  /** Example:
    * {{{
    * scala> import lamda.Applicative
    * scala> import lamda.syntax.applicative._
    * scala> import lamda.instances.option._
    *
    * scala> Applicative[Option].pure(1)
    * res0: Option[Int] = Some(1)
    * }}}
    */
  def pure[A](a: => A): F[A]

  /** Example:
    * {{{
    * scala> import lamda.Applicative
    * scala> import lamda.syntax.applicative._
    * scala> import lamda.instances.option._
    *
    * scala> Applicative[Option].map(Some(1))(_ + 1)
    * res0: Option[Int] = Some(2)
    * }}}
    */
  override def map[A, B](fa: F[A])(f: A => B): F[B] = ap(fa)(pure(f))

  /** Example:
    * {{{
    * scala> import lamda.Applicative
    * scala> import lamda.syntax.applicative._
    * scala> import lamda.instances.option._
    *
    * scala> Applicative[Option].map2(Some(1), Some(2))(_ + _)
    * res0: Option[Int] = Some(3)
    * }}}
    */
  def map2[A, B, Z](fa: F[A], fb: F[B])(f: (A, B) => Z): F[Z] =
    ap(fa)(map(fb)(b => a => f(a, b)))
}

object Applicative {
  def apply[F[_]](implicit instance: Applicative[F]): Applicative[F] = instance

  trait Ops {
    implicit class ApplicativeOps[F[_], A](fa: F[A])(implicit
      F: Applicative[F]
    ) {
      final def map[B](f: A => B): F[B] = F.map(fa)(f)
      final def map2[B, Z](fb: F[B])(f: (A, B) => Z): F[Z] = F.map2(fa, fb)(f)
    }
  }
}
