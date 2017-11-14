package brandonmott.testing

import cats.data.State

case class Gen[A](sample: State[Seed, A]) {

  def flatMap[B](f: A => Gen[B]): Gen[B] =
    Gen(sample.flatMap(a => f(a).sample))

  def map[B](f: A => B): Gen[B] =
    Gen(sample.flatMap(r => State(rng => (rng, f(r)))))

  def listOfN(n: Int, g: Gen[A]): Gen[List[A]] = Gen.listOfN(n, g)

  def listOfN(size: Gen[Int]): Gen[List[A]] = {
    size.flatMap { s =>
      Gen.listOfN(s, this)
    }
  }

  def unsized: SGen[A] = SGen(_ => this)

}

object Gen {
  /** It should generate integers in the range start to stopExclusive. */
  def choose(start: Int, stopExclusive: Int): Gen[Int] = {
    Gen(Seed.nonNegativeInt.map(n => start + n % (stopExclusive - start)))
  }

  //Always generates the value a
  def unit[A](a: => A): Gen[A] = Gen(State.pure(a))

  def boolean: Gen[Boolean] = Gen(Seed.boolean)

  def listOfN[A](n: Int, g: Gen[A]): Gen[List[A]] = {
    Gen(g.sample.transform((seed,a) => (seed, List.fill(n)(a))))
  }

  def choosePair(start: Int, stopExclusive: Int): Gen[(Int, Int)] = {
    val x1 = choose(start, stopExclusive)
    val x2 = choose(start, stopExclusive)
    Gen(x2.sample.flatMap(int => x1.sample.map(int1 => (int, int1))))
  }

  def optional[A](g: Gen[A]): Gen[Option[A]] = Gen(g.sample.map(Some(_)))

  def combine[A, B](a: Gen[A], b: Gen[B]): Gen[(A, B)] = {
    Gen(a.sample.flatMap { aa =>
      b.sample.map { bb =>
        (aa, bb)
      }
    })
  }

  def union[A](a1: Gen[A], a2: Gen[A]): Gen[A] =
    boolean.flatMap(b => if (b) a1 else a2)

  def weighted[A](g1: (Gen[A], Double), g2: (Gen[A], Double)): Gen[A] =
    Gen(Seed.double.flatMap(d => if (d > g1._2 / (g2._2 + g2._2)) g1._1.sample else g1._1.sample))

  def map2[A, B, C](a: Gen[A], b: Gen[B])(f: (A, B) => C): Gen[C] = {
    Gen(a.sample.flatMap { aa =>
      b.sample.map { bb =>
        f(aa, bb)
      }
    })
  }

  def map3[A, B, C, D](a: Gen[A], b: Gen[B], c: Gen[C])(f: (A, B, C) => D): Gen[D] = {
    Gen(a.sample.flatMap { aa =>
      b.sample.flatMap { bb =>
        c.sample.map { cc =>
          f(aa, bb, cc)
        }
      }
    })
  }

}