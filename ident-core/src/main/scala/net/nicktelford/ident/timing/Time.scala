package net.nicktelford.ident.timing

/** A high-resolution timer.
 *
 * Generates high-resolution timestamps for the current system time.
 *
 * Guarantees:
 *  - Precision: nanoseconds.
 *  - Accuracy: milliseconds.
 *  - Monotonicity; subsequent timestamps will be greater than or equal to all
 *    previous timestamps.
 */
object Time {
  // TODO: write tests to verify monotonicity, accuracy and precision guarantees
  import System.{nanoTime,currentTimeMillis}

  /** Fixed initial value of nanoTime for computing time offsets. */
  private val initialNanos = nanoTime

  /** Fixed initial value of currentTimeMillis for computing time offsets. */
  private val initialTime = currentTimeMillis * 1000000

  /** the current timestamp with nanosecond precision.*/
  def currentTimeNanos: Long = {
    initialTime + (nanoTime - initialNanos)
  }

  /** the approximate drift between system time and this timer. */
  def drift: Long = {
    currentTimeNanos - (System.currentTimeMillis * 1000000)
  }
}
