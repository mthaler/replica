package com.mthaler.replica

object Replicator {

  def copy[T <: Product](t: T, updated: Map[String, Any] = Map.empty[String, Any]): T = {
    val values = t.productIterator.toList.map(_.asInstanceOf[AnyRef])
    val clazz = t.getClass
    val fields = clazz.getDeclaredFields.toList
    val fieldNames = fields.map(_.getName)
    val newValues = for((field, value) <- fieldNames zip values) yield if (updated.contains(field)) updated(field).asInstanceOf[AnyRef] else value
    val result = clazz.getConstructors.head.newInstance(newValues: _*).asInstanceOf[T]
    result
  }
}
