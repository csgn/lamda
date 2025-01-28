package lamda

trait FlatMap[F[_]] extends Apply[F] {

  /** Example:
    * {{{
    * scala> import lamda.FlatMap
    * scala> import lamda.syntax.flatmap._
    * scala> import lamda.instances.option._
    *
    * scala> FlatMap[Option].flatMap(Some(1))((a: Int) => Some(a+1))
    * res0: Option[Int] = Some(2)
    * }}}
    */
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  /** Example:
    * {{{
    * scala> import lamda.FlatMap
    * scala> import lamda.syntax.flatmap._
    * scala> import lamda.instances.option._
    *
    * scala> FlatMap[Option].flatten(Some(Some(1)))
    * res0: Option[Int] = Some(1)
    * }}}
    */
  def flatten[A](ffa: F[F[A]]): F[A] = flatMap(ffa)(identity)

  /** Example:
    * {{{
    * scala> import lamda.FlatMap
    * scala> import lamda.syntax.flatmap._
    * scala> import lamda.instances.option._
    *
    * scala> FlatMap[Option].ap(Some(1))(Option((x: Int) => x + 1))
    * res0: Option[Int] = Some(2)
    * }}}
    */
  override def ap[A, B](fa: F[A])(ff: F[A => B]): F[B] = flatMap(fa) { a =>
    map(ff)(f => f(a))
  }
}

object FlatMap {
  def apply[F[_]](implicit instance: FlatMap[F]): FlatMap[F] = instance

  trait Ops {
    implicit class FlatMapOps[F[_], A](fa: F[A])(implicit F: FlatMap[F]) {
      final def flatMap[B](f: A => F[B]): F[B] = F.flatMap(fa)(f)
      final def ap[B](ff: F[A => B]): F[B] = F.ap(fa)(ff)
      final def >>[B](fb: F[B]): F[B] = F.flatMap(fa)(_ => fb)
    }

    implicit class FlatMapFlattenOps[F[_], A](ffa: F[F[A]])(implicit
      F: FlatMap[F]
    ) {
      final def flatten: F[A] = F.flatten(ffa)
    }
  }
}
