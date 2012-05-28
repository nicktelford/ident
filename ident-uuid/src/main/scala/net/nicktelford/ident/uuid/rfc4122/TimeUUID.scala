package net.nicktelford.ident.uuid.rfc4122

import java.util.concurrent.TimeUnit
import net.nicktelford.ident.uuid._
import util.Random

/** Companion object for generating TimeUUIDs. */
object TimeUUID extends RFC4122Factory[TimeUUID] {

  /** @see RFC4122Factory.version */
  val version: Short = 1

  /** @see UUIDFactory.max */
  val MaxValue: TimeUUID = new TimeUUID(encodeMSB(-1), encodeLSB(-1, -1))

  /** @see UUIDUUIDFactory.min */
  val MinValue: TimeUUID = new TimeUUID(encodeMSB(0), encodeLSB(0, 0))

  /** 100-ns offset of UNIX epoch from UUID epoch */
  private val EPOCH_OFFSET = 0x01B21DD213814000L

  /** Generates a UUID for a UNIX timestamp in arbitrary units. */
  def apply(timestamp: Long, unit: TimeUnit): TimeUUID = {
    // TODO: use real clock sequence and node
    // TODO: process ID for clock sequence?
    TimeUUID(timestamp, unit, Random.nextInt, Random.nextLong)
  }

  /** Generates a UUID for a UNIX timestamp in arbitrary units. */
  def apply(timestamp: Long, unit: TimeUnit, clock: Int, node: Long): TimeUUID = {
    TimeUUID(
      (unit.toNanos(timestamp) / 100).toLong - TimeUUID.EPOCH_OFFSET,
      clock,
      node
    )
  }
  
  /** Generates a UUID for a UUID timestamp. */
  def apply(nanos: Long, clock: Int, node: Long): TimeUUID = {
    new TimeUUID(encodeMSB(nanos), encodeLSB(clock, node))
  }

  /** Generates a TimeUUID derived from the current time. */
  def apply(): TimeUUID = {
    // TODO: replace with finer-grained clock
    TimeUUID(System.currentTimeMillis, TimeUnit.MILLISECONDS)
  }
}

/** A Version 1, time-based UUID that encodes the current time. */
case class TimeUUID(msb: Long, lsb: Long) extends UUID with RFC4122 with Ordered[TimeUUID] {

  /** @see RFC4122.version */
  val version: Short = 1

  // specific to TimeUUIDs only
  
  /** Timestamp as UNIX time in the specified unit. */
  def timestamp(unit: TimeUnit): Long = {
    unit.convert((super.timestamp * 100) + TimeUUID.EPOCH_OFFSET, TimeUnit.NANOSECONDS)
  }

  /** Compares two TimeUUIDs according to their timestamps.
   * 
   * Any two TimeUUIDs with the same timestamp will be ordered according to 
   * their clock sequence component first, and finally their node address.
   *
   * @param that The TimeUUID to compare to.
   * @return 1 if (this > that), -1 if (this < that) or 0 if (this == that)
   */
  def compare(that: TimeUUID): Int = {
    // TODO: could be cleaner, more concise - consider revising
    val x = super.timestamp.compare(that.timestamp)
    if (x == 0) {
      val y = clock.compare(that.clock)
      if (y == 0) {
        node.compare(that.node)
      } else {
        y
      }
    } else {
      x
    }
  }
}
