package net.nicktelford.ident.uuid

import net.nicktelford.ident.Identifier

/** Base trait for a UUID. */
trait UUID extends Identifier[UUID] {

  /** Variant of the UUID */
  def variant: Variant

  /** Most-significant 64bits as a Long. */
  def msb: Long

  /** Least significant 64bits as a Long. */
  def lsb: Long

  /** @see Identifier.bytes */
  def bytes: Array[Byte] = {
    Array(
      (msb >> 56).toByte,
      (msb >> 48).toByte,
      (msb >> 40).toByte,
      (msb >> 32).toByte,
      (msb >> 24).toByte,
      (msb >> 16).toByte,
      (msb >> 8).toByte,
      msb.toByte,
      (lsb >> 56).toByte,
      (lsb >> 48).toByte,
      (lsb >> 40).toByte,
      (lsb >> 32).toByte,
      (lsb >> 24).toByte,
      (lsb >> 16).toByte,
      (lsb >> 8).toByte,
      lsb.toByte
    )
  }

  /** @see Identifier.asString */
  def asString: String = BigInt(bytes).toString(16)

  /** @see Identifier.length */
  override def length: Int = 16

  /** Checks the equality of this UUID with another of the same type.
   * 
   * We override the default `Identifier` implementation of `equals` because we
   * can compare faster on the bits in the UUID.
   *
   * @see Identifier.equals
   */
  override def equals(obj: Any): Boolean = {
    obj match {
      case o: UUID => msb == o.msb && lsb == o.lsb
      case _ => false
    }
  }
}

/** The Nil UUID, a special UUID specified to have all it's bits set to 0 */
case object NilUUID extends UUID {

  /** @see UUID.variant */
  def variant = Variant.Nil

  /** @see UUID.msb */
  def msb = 0L

  /** @see UUID.lsb */
  def lsb = 0L
}
