package net.nicktelford.ident

import java.util.Arrays

/** Base trait for all identifiers */
trait Identifier[A <: Identifier[A]] {

  /** A binary representation of the Identifier. */
  def bytes: Array[Byte]

  /** A human readable String representation of the Identifier. */
  def asString: String

  /** Length of the identifier in bytes.
   * 
   * By default, the length of the `bytes` is used. It is strongly recommended
   * that implementations override this if they know the length in advance.
   * 
   * @see Identifier.bytes
   *
   * @return the number of bytes in this `Identifier`
   */
  def length: Int = bytes.length

  /** A human readable String representation of the Identifier.
   *
   * @see asString for implementation
   */
  override def toString: String = asString

  /** Checks the equality of this Identifier with another of the same type.
   *
   * By default, two identifiers are equal if they are of the same type and
   * produce the same binary representation.
   *
   * @param obj The object to compare to this Identifier.
   * @return `true` if `obj` is an `Identifier` is equal to this one, otherwise `false`.
   */
  override def equals(obj: Any): Boolean = {
    obj match {
      case o: A => Arrays.equals(bytes, o.bytes)
      case _ => false
    }
  }
}
