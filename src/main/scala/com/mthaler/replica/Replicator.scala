package com.mthaler.replica

object Replicator {

  def replicate[T <: Product](t: T): T = {
    val values = t.productIterator.toList.map(_.asInstanceOf[AnyRef])
    val result = t.getClass.getConstructors.head.newInstance(values: _*).asInstanceOf[T]
    result
  }
}
