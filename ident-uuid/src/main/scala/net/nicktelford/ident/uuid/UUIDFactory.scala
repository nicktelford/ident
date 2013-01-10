package net.nicktelford.ident.uuid

import annotation.implicitNotFound

/** Generic factory for a UUID implementation */
@implicitNotFound("Cannot find UUIDFactory for creating UUIDs of type ${A}")
trait UUIDFactory[A <: UUID] {

  /** Variant of UUID this UUIDFactory generates. */
  def variant: Variant

  /** Generates the maximum possible UUID for this version. */
  def MaxValue: A

  /** Generates the minimum possible UUID for this version. */
  def MinValue: A

  /** Creates a UUID from the raw MSB and LSB pair. */
  def from(msb: Long, lsb: Long): Option[A]

  /** Creates a UUID from the raw binary representation. */
  def from(bytes: Array[Byte]): Option[A] = {
    if (bytes.length == 16) {
      val msb = (
        bytes(0).toLong << 56 |
          bytes(1).toLong << 48 |
          bytes(2).toLong << 40 |
          bytes(3).toLong << 32 |
          bytes(4).toLong << 24 |
          bytes(5).toLong << 16 |
          bytes(6).toLong << 8 |
          bytes(7).toLong
        )

      val lsb = (
        bytes(8).toLong << 56 |
          bytes(9).toLong << 48 |
          bytes(10).toLong << 40 |
          bytes(11).toLong << 32 |
          bytes(12).toLong << 24 |
          bytes(13).toLong << 16 |
          bytes(14).toLong << 8 |
          bytes(15).toLong
        )

      from(msb, lsb)
    } else {
      None
    }
  }
}
