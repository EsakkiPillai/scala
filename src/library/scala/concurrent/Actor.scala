/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2003-2007, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

// $Id$


package scala.concurrent

import java.lang.Thread

/**
 * The class <code>Actor</code> ...
 *
 * @author Martin Odersky
 * @version 1.0
 * @deprecated  use scala.actors package instead
 */
@deprecated
abstract class Actor extends Thread {
  private val in = new MailBox

  def send(msg: in.Message) =
    in.send(msg)

  def receive[A](f: PartialFunction[in.Message, A]): A =
    if (currentThread == this) in.receive(f)
    else throw new IllegalArgumentException("receive called not on own process")

  def receiveWithin[A](msec: Long)(f: PartialFunction[in.Message, A]): A =
    if (currentThread == this) in.receiveWithin(msec)(f)
    else throw new IllegalArgumentException("receiveWithin called not on own process")

  private var pid: Pid = null

  def self = {
    if (pid eq null) pid = new Pid(this)
    pid
  }

  def self_= (p: Pid) = pid = p
}
