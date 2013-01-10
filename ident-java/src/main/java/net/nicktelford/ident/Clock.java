package net.nicktelford.ident;

/**
 * Interface for implementations of a system clock.
 * <p/>
 * Implementations must provide the <i>precision</i> applicable to each method,
 * but may vary the <i>accuracy</i>.
 * <p/>
 * For example, {@link #currentTimeNanos()} requires a precision in
 * nano-seconds, but implementations may only produce a number that's accurate
 * to milliseconds.
 * <p/>
 * All implementations must strive to be monotonic.
 */
public interface Clock {

    public long currentTimeNanos();

    public long currentTimeMillis();

    public long currentTime();
}
