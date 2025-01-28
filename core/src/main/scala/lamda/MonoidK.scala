package lamda

trait MonoidK[F[_]] extends SemigroupK[F] {

  /** Example:
    * {{{
    * scala> import lamda.MonoidK
    * scala> import lamda.syntax.semigroupk._
    * scala> import lamda.instances.option._
    *
    * scala> MonoidK[Option].empty
    * res0: Option[Nothing] = None
    * }}}
    */
  def empty[A]: F[A]
}

object MonoidK {
  def apply[F[_]](implicit instance: MonoidK[F]): MonoidK[F] = instance
}
