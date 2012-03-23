package net.nicktelford.ident.uuid.rfc4122

import java.util.concurrent.TimeUnit
import net.nicktelford.ident.uuid._

/** Companion object for generating TimeUUIDs. */
object TimeUUID extends UUIDFactory[TimeUUID] with RFC4122Factory {

  /** @see RFC4122Factory.version */
  val version: Short = 1

  /** @see UUIDFactory.max */
  val MaxValue: TimeUUID = TimeUUID(encodeMSB(-1), encodeLSB(-1, -1))

  /** @see UUIDUUIDFactory.min */
  val MinValue: TimeUUID = TimeUUID(encodeMSB(0), encodeLSB(0, 0))

  /** 100-ns offset of UNIX epoch from UUID epoch */
  private val EPOCH_OFFSET = 0x01B21DD213814000L

  // TODO: extract somewhere more abstract
  // TODO: write tests to verify monotonicity, accuracy and precision guarantees
  /** Utility for computing high-precision monotonic timestamps. */
  private object Time {
    import System.{nanoTime,currentTimeMillis}

    /** Fixed initial value of nanoTime for computing time offsets. */
    private val initialNanos = nanoTime

    /** Fixed initial value of currentTimeMillis for computing time offsets. */
    private val initialTime = currentTimeMillis * 1000000

    /** Gets the current timestamp with nanosecond precision.
     *
     * This method doesn't guarantee accuracy beyond milliseconds but does 
     * guarantee monotonicity; subsequent timestamps will always be larger.
     * 
     * @return the current timestamp with nanosecond precision.
     */
    def currentTimeNanos: Long = {
      initialTime + (nanoTime - initialNanos)
    }

    /** Calculates the approximate drift between system time and this timer. */
    def drift: Long = {
      currentTimeNanos - (System.currentTimeMillis * 1000000)
    }
  }

  /** Generates a UUID for a UNIX timestamp in arbitrary units. */
  def apply(timestamp: Long, unit: TimeUnit): TimeUUID = {
    TimeUUID((unit.toNanos(timestamp) / 100).toLong - TimeUUID.EPOCH_OFFSET)
  }
  
  /** Generates a UUID for a UUID timestamp. */
  def apply(nanos: Long): TimeUUID = {
    // TODO: compute LSB from a real clock sequence and node address
    // TODO: idea: use current process ID (PID) for clock sequence?
    TimeUUID(encodeMSB(nanos), randomLSB)
  }
  
  /** Generates a TimeUUID derived from the current time. */
  def apply: TimeUUID = {
    TimeUUID(Time.currentTimeNanos, TimeUnit.NANOSECONDS)
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
