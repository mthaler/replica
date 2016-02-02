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
    // create list of values of the class
    val values = t.productIterator.toList.map(_.asInstanceOf[AnyRef])
    val clazz = t.getClass
    // create list of fields of the class
    val fields = clazz.getDeclaredFields.toList
    // unwrap value classes
    val unwrapped = for ((field, value) <- fields.zip(values)) yield if (field.getType != value.getClass) unwrap(value).asInstanceOf[AnyRef] else value
    val fieldNames = fields.map(_.getName)
    val newValues = for ((field, value) <- fieldNames zip unwrapped) yield if (updated.contains(field)) updated(field).asInstanceOf[AnyRef] else value
    val result = clazz.getConstructors.head.newInstance(newValues: _*).asInstanceOf[T]
    result
  }

  private[replica] def unwrap(value: Any): Any = {
    val clazz = value.getClass
    val fields = clazz.getDeclaredFields.toList
    if (fields.size == 1) {
      val f = fields.head
      f.setAccessible(true)
      f.get(value)
    } else {
      value
    }
  }
}
