package lamda

trait Apply[F[_]] extends Functor[F] with Semigroupal[F] {

  /** Example:
    * {{{
    * scala> import lamda.Apply
    * scala> import lamda.syntax.apply._
    * scala> import lamda.instances.option._
    *
    * scala> val o1 = Option(1)
    * scala> val f = Option((x: Int) => x + 1)
    *
    * scala> Apply[Option].ap(o1)(f)
    * res0: Option[Int] = Some(2)
    * }}}
    */
  def ap[A, B](fa: F[A])(ff: F[A => B]): F[B]

  /** Example:
    * {{{
    * scala> import lamda.Apply
    * scala> import lamda.syntax.apply._
    * scala> import lamda.instances.option._
    *
    * scala> val o1 = Option(1)
    * scala> val o2 = Option(2)
    * scala> val f = Option((x: Int, y: Int) => x + y)
    *
    * scala> Apply[Option].ap2(o1, o2)(f)
    * res0: Option[Int] = Some(3)
    * }}}
    */
  def ap2[A, B, Z](fa: F[A], fb: F[B])(ff: F[(A, B) => Z]): F[Z] =
    ap(fb)(ap(fa)(map(ff)(_.curried)))

  /** Example:
    * {{{
    * scala> import lamda.Apply
    * scala> import lamda.syntax.apply._
    * scala> import lamda.instances.option._
    *
    * scala> Apply[Option].product(Option(1), Option(2))
    * res0: Option[(Int, Int)] = Some((1, 2))
    * }}}
    */
  override def product[A, B](fa: F[A], fb: F[B]): F[(A, B)] =
    ap(fb)(map(fa)(a => (b: B) => (a, b)))

  /** Example:
    * {{{
    * scala> import lamda.Apply
    * scala> import lamda.syntax.apply._
    * scala> import lamda.instances.option._
    *
    * scala> Apply[Option].productR(Option(1), Option(2))
    * res0: Option[Int] = Some(2)
    * }}}
    */
  def productR[A, B](fa: F[A], fb: F[B]): F[B] = ap(fb)(as(fa)((b: B) => b))

  /** Example:
    * {{{
    * scala> import lamda.Apply
    * scala> import lamda.syntax.apply._
    * scala> import lamda.instances.option._
    *
    * scala> Apply[Option].productL(Option(1), Option(2))
    * res0: Option[Int] = Some(1)
    * }}}
    */
  def productL[A, B](fa: F[A], fb: F[B]): F[A] =
    ap(fb)(map(fa)(a => (_: B) => a))
}

object Apply {
  def apply[F[_]](implicit instance: Apply[F]): Apply[F] = instance

  trait Ops {
    implicit class ApplyOps[F[_], A](fa: F[A])(implicit F: Apply[F]) {
      final def ap[B](ff: F[A => B]): F[B] = F.ap(fa)(ff)

      /** Example:
        * {{{
        * scala> import lamda.syntax.apply._
        * scala> import lamda.instances.option._
        *
        * scala> Option(1) <*> Option((x: Int) => x + 1)
        * res0: Option[Int] = Some(2)
        * }}}
        *
        * @see
        *   Alias of [[lamda.Apply.ap]]
        */
      final def <*>[B](ff: F[A => B]): F[B] = F.ap(fa)(ff)

      final def ap2[B, Z](fb: F[B])(ff: F[(A, B) => Z]): F[Z] = F
        .ap2(fa, fb)(ff)

      final def product[B](fb: F[B]): F[(A, B)] = F.product(fa, fb)

      final def productR[B](fb: F[B]): F[B] = F.productR(fa, fb)

      final def productL[B](fb: F[B]): F[A] = F.productL(fa, fb)

      /** Example:
        * {{{
        * scala> import lamda.syntax.apply._
        * scala> import lamda.instances.option._
        *
        * scala> Option(1) *> Option(2) res0: Option[Int] = Option(2)
        * }}}
        *
        * @see
        *   Alias of [[lamda.Apply.productR]]
        */
      final def *>[B](fb: F[B]): F[B] = F.productR(fa, fb)

      /** Example:
        * {{{
        * scala> import lamda.syntax.apply._
        * scala> import lamda.instances.option._
        *
        * scala> Option(1) <* Option(2)
        * res0: Option[Int] = Some(1)
        * }}}
        *
        * @see
        *   Alias of [[lamda.Apply.productL]]*
        */
      final def <*[B](fb: F[B]): F[A] = F.productL(fa, fb)
    }
  }
}
