package lamda

trait Eval[+A] {
  self =>
  def value: A
  def map[B](f: A => B): Eval[B] = flatMap(a => Eval.now(f(a)))
  def flatMap[B](f: A => Eval[B]): Eval[B] = Eval.defer(f(self.value))
}

/** It will be `eager` and `memoized`, or `Call-by-value`.
  *
  * As follows, you can see that after the first call, there is no more printing
  * `"called"`. Because, values behave as eager and memoized its own value.
  *
  * Example:
  * {{{
  * scala> val x = {println("called"); 2}
  * called
  * val a: Int = 2
  *
  * scala> a
  * res0: Int = 2
  *
  * scala> a
  * res1: Int = 2
  * }}}
  *
  * We can perform this behaviour with [[Now]].
  *
  * Example:
  * {{{
  * scala> import lamda.Eval
  * scala> Eval.now({println("called"); 1})
  * called
  * res0: lamda.Now[Int] = Now(2)
  *
  * scala> res0.value
  * res1: Int = 2
  * }}}
  */
final case class Now[A](a: A) extends Eval[A] {
  def value: A = a
}

/** It will be `lazy` and `not-memoized`, or `Call-by-name`.
  *
  * The lazy and not-memoized values are just a function.
  *
  * As follows, you can see the `"called"` printing whenever the function is
  * called.
  *
  * Example:
  * {{{
  * scala> val f = () => {println("called"); 2}
  *
  * scala> f()
  * called
  * res0: Int = 2
  *
  * scala> f()
  * called
  * res1: Int = 2
  * }}}
  *
  * We can perform this behaviour with [[Always]].
  *
  * Example:
  * {{{
  * scala> import lamda.Eval
  *
  * scala> val f = Eval.always({println("called"); 2})
  * val f: lamda.Always[Int] = Always(...)
  *
  * scala> f.value
  * called
  * res0: Int = 2
  *
  * scala> f.value
  * called
  * res1: Int = 2
  * }}}
  */
final case class Always[A](f: () => A) extends Eval[A] {
  def value: A = f()
}

/** It will be `lazy` and `memoized`, or `Call-by-need`.
  *
  * The lazy and memoized values behaves like both `val` and `function`. It is
  * not evaluated until call and also memoized the result.
  *
  * Example:
  * {{{
  * scala> lazy val f = {println("called"); 2}
  * lazy val f: Int // unevaluated
  *
  * scala> f
  * called
  * res0: Int = 2
  *
  * scala> f
  * res1: Int = 2
  * }}}
  *
  * We can perform this behaviour with [[Later]].
  *
  * Example:
  * {{{
  * scala> import lamda.Eval
  *
  * scala> val f = Eval.later({println("called"); 2})
  * val f: lamda.Later[Int] = Later(...)
  *
  * scala> f.value
  * called
  * res0: Int = 2
  *
  * scala> f.value
  * res1: Int = 2
  * }}}
  */
final case class Later[A](f: () => A) extends Eval[A] {
  override lazy val value: A = f()
}

/** Deferring means just postponing it a given action. We can perform this
  * action via `thunks`. Generally, without thunks, Scala evaluates the given
  * arguments eagerly before calling the function.
  *
  * Example:
  * {{{
  * scala> def f(a: Int): Int = {
  *   // a is already evaluated (a = 2)
  *   1 + a
  * }
  *
  * scala> f(1+1) // Scala first evaluates the `1+1` and pass the evaluated result into the function.
  * res0: Int = 3
  * }}}
  *
  * But with thunks, we can pass arguments without evaluate immediately. And
  * that is why we naming it this action as `deferring`.
  *
  * Example:
  * {{{
  * scala> def f(a: => Int) = {
  *   // a is not evaluated (a = unevaluated)
  *   1 + a
  * }
  *
  * scala> f(1+1) // Scala pass the `1+1` action into the function.
  * res0: Int = 3
  * }}}
  *
  * We can perform this behaviour with [[Defer]].
  *
  * Example:
  * {{{
  * scala> import lamda.Eval
  *
  * scala> val f = Eval.defer(Eval.now(1+1))
  * val f: lamda.Eval[Int] = Defer(...)
  *
  * scala> f.value
  * res0: Int = 2
  * }}}
  */
sealed case class Defer[A](f: () => Eval[A]) extends Eval[A] {
  def value: A = f().value
}

object Eval {
  def now[A](value: A): Now[A] = Now(value)
  def always[A](f: => A): Always[A] = Always(() => f)
  def later[A](f: => A): Later[A] = Later(() => f)
  def defer[A](thunk: => Eval[A]): Eval[A] = Defer(() => thunk)
}
