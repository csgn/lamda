/*
 * Copyright (c) 2024 lamdalib
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package kuram
package data

import java.util.concurrent.atomic.AtomicReference
import scala.jdk.FunctionConverters._

trait Ref[F[_], A] {
  def get: F[A]
  def set(a: A): F[Unit]
  def update(f: A => A): F[Unit]
  def modify[Z](f: A => (A, Z)): F[Z]
}

private[kuram] class AtomicRef[F[_]: Monad, A](initial: A) extends Ref[F, A] {
  private val value: AtomicReference[A] = new AtomicReference(initial)

  override def get: F[A] = Monad[F].pure(value.get())
  override def set(a: A): F[Unit] = Monad[F].pure(value.set(a))
  override def update(f: A => A): F[Unit] = Monad[F].pure(value.updateAndGet(f.asJava))
  override def modify[Z](f: A => (A, Z)): F[Z] = Monad[F].pure {
    val (a, z) = f(value.get)
    value.set(a)
    z
  }
}

object Ref {
  def apply[F[_]: Monad, A](a: A): F[Ref[F, A]] = {
    Monad[F].pure(new AtomicRef[F, A](a))
  }
}
