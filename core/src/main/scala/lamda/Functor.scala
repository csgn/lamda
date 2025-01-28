package lamda

trait Functor[F[_]] {

  /** Example:
    * {{{
    * scala> import lamda.Functor
    * scala> import lamda.syntax.functor._
    * scala> import lamda.instances.option._
    *
    * scala> Functor[Option].map(Some(1))(_ + 1)
    * res0: Option[Int] = Some(2)
    * }}}
    */
  def map[A, B](fa: F[A])(f: A => B): F[B]

  /** Example:
    * {{{
    * scala> import lamda.Functor
    * scala> import lamda.syntax.functor._
    * scala> import lamda.instances.option._
    *
    * scala> Functor[Option].as(Some(1))(List(1, 2, 3))
    * res0: List[Int] = List(1, 2, 3)
    * }}}
    */
  def as[A, B](fa: F[A])(b: => B): F[B] = map(fa)(_ => b)

  /** Example:
    * {{{
    * scala> import lamda.Functor
    * scala> import lamda.syntax.functor._
    * scala> import lamda.instances.option._
    *
    * scala> Functor[Option].void(Some(1))
    * res0: Option[Unit] = Some(())
    * }}}
    */
  def void[A](fa: F[A]): F[Unit] = as(fa)(())

  /** Example:
    * {{{
    * scala> import lamda.Functor
    * scala> import lamda.syntax.functor._
    * scala> import lamda.instances.option._
    *
    * scala> Functor[Option].lift((a: Int) => a + 1)(Option(1))
    * res0: Option[Int] = Some(2)
    * }}}
    */
  def lift[A, B](f: A => B): F[A] => F[B] = map(_)(f)

  /** @see
    *   Alias of [[lamda.Functor.map]]
    */
  final def fmap[A, B](fa: F[A])(f: A => B): F[B] = map(fa)(f)
}

object Functor {
  def apply[F[_]](implicit instance: Functor[F]): Functor[F] = instance

  trait Ops {
    implicit class FunctorOps[F[_], A](fa: F[A])(implicit F: Functor[F]) {
      final def map[B](f: A => B): F[B] = F.map(fa)(f)

      final def as[B](b: => B): F[B] = F.as(fa)(b)

      final def void: F[Unit] = F.void(fa)

      final def fmap[B](f: A => B): F[B] = F.fmap(fa)(f)
    }

    implicit class FunctorFunctionOps[F[_], A, B](f: A => B)(implicit
      F: Functor[F]
    ) {
      final def lift: F[A] => F[B] = F.lift(f)
    }
  }
}
