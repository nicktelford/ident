package net.nicktelford.ident.uuid

/** Base trait for UUID variants. */
sealed trait Variant {

  /** Bit-mask for the Variant. */
  def mask: Byte

  /** Value for the Variant. */
  def value: Byte
}

object Variant {

  /** Variant reserved for NCS backward compatibility */
  case object NCS extends Variant {
    val mask: Byte = 0x8
    val value: Byte = 0x0
  }

  /** Variant for UUIDs that adhere to RFC-4122.
   *
   * @see http://www.ietf.org/rfc/rfc4122.txt
   */
  case object RFC extends Variant {
    val mask: Byte = 0xC
    val value: Byte = 0x8
  }

  /** Variant reserved for Microsoft backwards compatibility. */
  case object Microsoft extends Variant {
    val mask: Byte = 0xE
    val value: Byte = 0xC
  }

  /** Variant for UUIDs that adhere to a custom scheme. */
  case object Custom extends Variant {
    val mask: Byte = 0xE
    val value: Byte = 0xE
  }

  /** Special Variant only to be used by the NilUUID. */
  case object Nil extends Variant {
    val mask: Byte = 0x0
    val value: Byte = 0x0
  }
}
