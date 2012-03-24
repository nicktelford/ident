package net.nicktelford.ident.timing

/** High-resolution timer.
 *
 * Computes high-resolution timestamps that are guaranteed to:
 *  - have `nanosecond precision`.
 *  - have `millisecond accuracy`.
 *  - are `monotonic`; subsequent timestamps will be greater than or equal to all
 *    previous timestamps.
 *
 * While the accuracy is the same as `System.currentTimeMillis`, the additional
 * precision, together with the monotonicity guarantee, reduce the probability
 * that successive calls will yield the same timestamp; a useful property when
 * generating time-derived identifiers.
 */
object Time {
  // TODO: write tests to verify monotonicity, accuracy and precision guarantees

  import System.{nanoTime,currentTimeMillis}

  // TODO: handle negative nanoTime results, perhaps wrap nanoTime calls?
  // TODO: handle nanoTime wrap-around
  /** Fixed initial value of nanoTime for computing time offsets. */
  private val initialNanos = nanoTime

  /** Fixed initial value of currentTimeMillis for computing time offsets. */
  private val initialTime = currentTimeMillis * 1000000

  /** Current timestamp with nanosecond precision. */
  def currentTimeNanos: Long = {
    initialTime + (nanoTime - initialNanos)
  }
}
