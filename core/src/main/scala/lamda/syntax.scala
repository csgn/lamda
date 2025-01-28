package lamda

private[lamda] trait FunctorOps extends Functor.Ops
private[lamda] trait SemigroupalOps extends Semigroupal.Ops
private[lamda] trait ApplyOps extends Apply.Ops
private[lamda] trait ApplicativeOps extends Applicative.Ops
private[lamda] trait FlatMapOps extends FlatMap.Ops
private[lamda] trait MonadOps extends Monad.Ops
private[lamda] trait SemigroupOps extends Semigroup.Ops
private[lamda] trait SemigroupKOps extends SemigroupK.Ops
private[lamda] trait FoldableOps extends Foldable.Ops
private[lamda] trait TraverseOps extends Traverse.Ops

private[lamda] trait AllOps
    extends FunctorOps
    with SemigroupalOps
    with ApplyOps
    with ApplicativeOps
    with FlatMapOps
    with MonadOps
    with SemigroupOps
    with SemigroupKOps
    with FoldableOps
    with TraverseOps

package object syntax {
  object functor extends FunctorOps
  object semigroupal extends SemigroupalOps
  object apply extends ApplyOps
  object applicative extends ApplicativeOps
  object flatmap extends FlatMapOps
  object monad extends MonadOps
  object semigroup extends SemigroupOps
  object semigroupk extends SemigroupKOps
  object foldable extends FoldableOps
  object traverse extends TraverseOps
  object all extends AllOps
}
