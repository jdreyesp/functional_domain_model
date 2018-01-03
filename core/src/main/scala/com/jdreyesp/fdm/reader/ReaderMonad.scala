package com.jdreyesp.fdm.reader

/**
  * Created by jreyes on 3/1/18.
  *
  * In functional programming, it’s a common practice to compose and build computation pipelines
  * and defer evaluation until the end.
  *
  * The computation op in the earlier section is a Function1 that may need to be composed with a monadic pipeline
  * containing a List or an Option. You need monad transformers for this kind of composition.
  * But Function1 doesn’t have any transformer that can be used to stack up your computation into a monadic pipeline.
  *
  * Reader can have a transformer, ReaderT, which can be used to compose other monads with the Reader (not covered here)
  */
case class ReaderMonad[R, A](run: R => A) {

  def map[B](f: A => B): ReaderMonad[R, B] =
    ReaderMonad(r => f(run(r)))

  def flatMap[B](f: A => ReaderMonad[R, B]): ReaderMonad[R, B] =
    ReaderMonad(r => f(run(r)).run(r))

}

