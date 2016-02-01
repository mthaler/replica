package com.mthaler.replica

object Replicator {

  /**
    * Copies a case class replacing values with updated values
    *
    * @param t case class
    * @param updated map containing updated values
    * @tparam T
    * @return copy of case class with updated values
    */
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
