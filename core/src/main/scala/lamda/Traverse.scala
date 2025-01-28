package lamda

trait Traverse[F[_]] extends Functor[F] with Foldable[F] {

  /** Example:
    * {{{
    * scala> import lamda.Traverse
    * scala> import lamda.instances.list._
    *
    * scala> Traverse[List].traverse(List(1, 2, 3))(Option(_))
    * res0: Option[List[Int]] = Some(List(1, 2, 3))
    * }}}
    */
  def traverse[G[_]: Applicative, A, B](fa: F[A])(f: A => G[B]): G[F[B]]

  /** Example:
    * {{{
    * scala> import lamda.Traverse
    * scala> import lamda.instances.list._
    *
    * scala> Traverse[List].sequence(List(Option(1), Option(2), Option(3)))
    * res0: Option[List[Int]] = Some(List(1, 2, 3))
    * }}}
    */
  def sequence[G[_]: Applicative, A](fga: F[G[A]]): G[F[A]] =
    traverse(fga)(identity)
}

object Traverse {
  def apply[F[_]](implicit instance: Traverse[F]): Traverse[F] = instance

  trait Ops {
    implicit class TraverseOps[F[_], A](fa: F[A])(implicit F: Traverse[F]) {
      final def traverse[G[_]: Applicative, B](f: A => G[B]): G[F[B]] = F
        .traverse(fa)(f)
    }

    implicit class TraverseSequenceOps[F[_], G[_]: Applicative, A](
      fga: F[G[A]]
    )(implicit F: Traverse[F]) {
      final def sequence: G[F[A]] = F.sequence(fga)
    }
  }
}
