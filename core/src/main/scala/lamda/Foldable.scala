package lamda

trait Foldable[F[_]] {

  /** Example:
    * {{{
    * scala> import lamda.Foldable
    * scala> import lamda.instances.list._
    *
    * scala> Foldable[List].foldRight(List(1, 2, 3), 0)(_ - _)
    * res0: Int = 2
    * }}}
    */
  def foldRight[A, B](fa: F[A], acc: B)(f: (A, B) => B): B

  /** Example:
    * {{{
    * scala> import lamda.Foldable
    * scala> import lamda.instances.list._
    *
    * scala> Foldable[List].foldLeft(List(1, 2, 3), 0)(_ - _)
    * res0: Int = -6
    * }}}
    */
  def foldLeft[A, B](fa: F[A], acc: B)(f: (B, A) => B): B =
    foldRight(fa, acc)((a, b) => f(b, a))

  /** Example:
    * {{{
    * scala> import lamda.Foldable
    * scala> import lamda.implicits.monoid._
    * scala> import lamda.instances.list._
    *
    * scala> Foldable[List].foldMap(List(1, 2, 3))(_ * 2)
    * res0: Int = 12
    * }}}
    */
  def foldMap[A, B](fa: F[A])(f: A => B)(implicit M: Monoid[B]): B =
    foldRight(fa, M.empty)((a, b) => M.combine(f(a), b))
}

object Foldable {
  def apply[F[_]](implicit instance: Foldable[F]): Foldable[F] = instance

  trait Ops {
    implicit class FoldableOps[F[_], A](fa: F[A])(implicit F: Foldable[F]) {
      final def foldRight[B](acc: B)(f: (A, B) => B): B = F
        .foldRight(fa, acc)(f)

      final def foldLeft[B](acc: B)(f: (B, A) => B): B = F.foldLeft(fa, acc)(f)

      final def foldMap[B](f: A => B)(implicit M: Monoid[B]): B = F
        .foldMap(fa)(f)

      /** @see
        *   Alias of [[lamda.Foldable.foldRight]]
        */
      final def foldR[B](acc: B)(f: (A, B) => B): B = F.foldRight(fa, acc)(f)

      /** @see
        *   Alias of [[lamda.Foldable.foldRight]]
        */
      final def foldL[B](acc: B)(f: (B, A) => B): B = F.foldLeft(fa, acc)(f)

      /** @see
        *   Alias of [[lamda.Foldable.foldMap]]
        */
      final def foldM[B](f: A => B)(implicit M: Monoid[B]): B = F.foldMap(fa)(f)
    }
  }
}
