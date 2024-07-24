/*
 * Copyright (c) 2024 kattulib
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
package monoid

import semigroup.Semigroup

/** Monoid
  *
  * Must obey the laws following:
  * 1. a + (b + c) = (a + b) + c
  * 2. id + f = f
  *    f + id = f
  */
trait Monoid[T] extends Semigroup[T]:
  def empty: T

object Monoid:
  /** Creating instance of [[kuram.monoid.Monoid]] with given T.
    *
    * Example:
    * {{{
    * scala> import kuram.monoid.Monoid
    * scala> import kuram.monoid.instances.given
    *
    * scala> Monoid[Int]
    * val res0: kuram.monoid.Monoid[Int] = kuram.monoid.MonoidInstances$given_Monoid_Int
    * }}}
    *
    * References:
    * - [[https://bartoszmilewski.com/2014/12/05/categories-great-and-small/]]
    */
  def apply[T](using instance: Monoid[T]): Monoid[T] = instance
