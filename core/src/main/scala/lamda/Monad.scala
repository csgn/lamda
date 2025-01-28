package lamda

trait Monad[F[_]] extends Applicative[F] with FlatMap[F] {

  /** Example:
    * {{{
    * scala> import lamda.Monad
    * scala> import lamda.syntax.monad._
    * scala> import lamda.instances.option._
    *
    * scala> Monad[Option].map(Some(1))(_ + 1)
    * res0: Option[Int] = Some(2)
    * }}}
    */
  override def map[A, B](fa: F[A])(f: A => B): F[B] =
    flatMap(fa)(a => pure(f(a)))
}

object Monad {
  def apply[F[_]](implicit instance: Monad[F]): Monad[F] = instance

  trait Ops {
    implicit class MonadOps[F[_], A](fa: F[A])(implicit F: Monad[F]) {
      final def map[B](f: A => B): F[B] = F.map(fa)(f)
    }
  }
}
