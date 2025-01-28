package lamda

trait Semigroup[T] {

  /** Example:
    * {{{
    * scala> import lamda.Semigroup
    * scala> import lamda.syntax.semigroup._
    * scala> import lamda.instances.option._
    * scala> import lamda.instances.int._
    * scala> import lamda.instances.string._
    *
    * scala> Semigroup[Option[Int]].combine(Some(1), Some(2))
    * res0: Option[Int] = Some(3)
    *
    * scala> Semigroup[Option[String]].combine(Some("hello"), Some(" world"))
    * res1: Option[String] = Some(hello world)
    * }}}
    */
  def combine(a: T, b: T): T
}

object Semigroup {
  def apply[T](implicit instance: Semigroup[T]): Semigroup[T] = instance

  trait Ops {
    implicit class SemigroupOps[T](a: T)(implicit F: Semigroup[T]) {
      final def combine(b: T): T = F.combine(a, b)

      /** Example:
        * {{{
        * scala> import lamda.syntax.semigroup._
        * scala> import lamda.instances.option._
        * scala> import lamda.instances.int._
        * scala> import lamda.instances.string._
        *
        * scala> Some(1) |+| Some(2)
        * res0: Option[Int] = Some(3)
        *
        * scala> Some("hello") |+| Some(" world")
        * res1: Option[Int] = Some(hello world)
        *
        * scala> "hello" |+| " world"
        * res2: String = hello world
        * }}}
        *
        * @see
        *   Alias of [[lamda.Semigroup.combine]]
        */
      final def |+|(b: T): T = F.combine(a, b)
    }
  }
}
