package net.nicktelford.ident.uuid.rfc4122

import util.Random
import net.nicktelford.ident.uuid.{UUIDFactory, Variant, UUID}

/**Base trait for RFC4122 compliant UUIDs. */
trait RFC4122 {
  self: UUID =>

  /**Version number of this UUID */
  def version: Short

  /**Variant of this UUID */
  def variant: Variant = Variant.RFC

  /**Timestamp section of the UUID. */
  def timestamp: Long = {
    val time_lo = msb >> 32
    val time_mid = (msb & 0xFFFF0000L) << 16
    val time_hi = (msb & 0x0FFF) << 48

    time_hi | time_mid | time_lo
  }

  /**Clock sequence section of the UUID. */
  def clock: Short = {
    val clock_lo = (lsb >> 48) & 0xF
    val clock_hi = (lsb >> 56) & (0xF - this.variant.mask)

    ((clock_hi << 8) | clock_lo).toShort
  }

  /**Node address section of the UUID. */
  def node: Long = {
    lsb & 0xFFFFFFFFFFFFL
  }
}

/**Base trait for factory objects that generate RFC4122 compliant UUIDs. */
trait RFC4122Factory {
  self: UUIDFactory[_ <: RFC4122] =>

  /**UUID version this Factory generates. */
  def version: Short

  /**UUID variant this Factory generates. */
  def variant: Variant = Variant.RFC

  /**Encodes the input bits with the version and swaps octets accordingly. */
  protected def encodeMSB(bits: Long): Long = {
    val time_lo = bits & 0xFFFFFFFF
    val time_mid = (bits >> 32) & 0xFFFF
    val time_hi = (bits >> 48) & 0x0FFF
    val time_hi_and_version = (version << 4) | time_hi

    (time_lo << 32) | (time_mid << 16) | time_hi_and_version
  }

  /**Encodes the given clock and node ID with the variant. */
  protected def encodeLSB(clock: Int, node: Long): Long = {
    val clock_hi = (0xF - variant.mask) & (clock >> 8)
    val clock_hi_and_variant = (variant.mask & variant.value) | clock_hi
    val clock_lo = clock & 0xF

    (clock_hi_and_variant << 56) | (clock_lo << 48) | node & 0xFFFFFFFFFFFFL
  }

  /**Generates a random MSB encoded with the UUID version. */
  protected def randomMSB: Long = {
    encodeMSB(Random.nextLong)
  }

  /**Generates a random LSB encoded with the UUID Variant */
  protected def randomLSB: Long = {
    encodeLSB(Random.nextInt, Random.nextLong)
  }
}
