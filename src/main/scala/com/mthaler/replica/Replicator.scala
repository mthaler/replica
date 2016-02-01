package com.mthaler.replica

object Replicator {

  def replicate[T <: Product](t: T): T = t
}
