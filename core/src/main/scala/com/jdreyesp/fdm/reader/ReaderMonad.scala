package com.jdreyesp.fdm.reader

/**
  * Created by jreyes on 3/1/18.
  */
case class ReaderMonad[R, A](run: R => A) {
  def map[B](f: A => B): ReaderMonad[R, B] =
    ReaderMonad(r => f(run(r)))
  def flatMap[B](f: A => ReaderMonad[R, B]): ReaderMonad[R, B] =
    ReaderMonad(r => f(run(r)).run(r))
}

