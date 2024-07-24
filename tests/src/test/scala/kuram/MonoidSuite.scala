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

import semigroup.syntax.*

import monoid.Monoid
import monoid.instances.given

class MonoidSuite extends munit.FunSuite:
  test("combine int values"):
    val (x, y) = (1, 2)

    val expected = x + y
    val obtained1 = Monoid[Int].combine(x, y)
    val obtained2 = x |+| y

    assertEquals(obtained1, expected)
    assertEquals(obtained2, expected)

  test("combine string values"):
    val (s1, s2) = ("foo", "bar")

    val expected = s1 + s2
    val obtained1 = Monoid[String].combine(s1, s2)
    val obtained2 = s1 |+| s2

    assertEquals(obtained1, expected)
    assertEquals(obtained2, expected)

  test("combine list of integer values"):
    val (l1, l2) = (List(1, 2, 3), List(4, 5))

    val expected = l1 ++ l2
    val obtained1 = Monoid[List[Int]].combine(l1, l2)
    val obtained2 = l1 |+| l2

    assertEquals(obtained1, expected)
    assertEquals(obtained2, expected)

  test("combine list of string values"):
    val (l1, l2) = (List("a", "b", "c"), List("d", "e"))

    val expected = l1 ++ l2
    val obtained1 = Monoid[List[String]].combine(l1, l2)
    val obtained2 = l1 |+| l2

    assertEquals(obtained1, expected)
    assertEquals(obtained2, expected)


  test("combine list of integer lists lists"):
    val (l1, l2) = (List(List(1), List(2, 3), List(4)), List(List(5, 6), List(7)))

    val expected = l1 ++ l2
    val obtained1 = Monoid[List[List[Int]]].combine(l1, l2)
    val obtained2 = l1 |+| l2

    assertEquals(obtained1, expected)
    assertEquals(obtained2, expected)

  test("associativity"):
    val (a, b, c) = (1, 2, 3)
    
    val expected1 = a + (b + c)
    val expected2 = (a + b) + c

    val obtained1 = a |+| (b |+| c)
    val obtained2 = (a |+| b) |+| c


    assertEquals(obtained1, expected1)
    assertEquals(obtained2, expected2)
    assertEquals(obtained1, obtained2)

  test("homomorphism"):
    val (s1, s2) = ("hello", "world")

    val expected1 = s1.length + s2.length
    val expected2 = (s1 + s2).length

    val obtained1 = s1.length |+| s2.length
    val obtained2 = (s1 |+| s2).length

    assertEquals(obtained1, expected1)
    assertEquals(obtained2, expected2)
    assertEquals(obtained1, obtained2)

  test("identity element"):
    val expected = ""
    val obtained = Monoid[String].empty

    assertEquals(obtained, expected)

end MonoidSuite