package net.nicktelford.ident.rfc4122;

import net.nicktelford.timing.clocks.Clock;
import net.nicktelford.timing.clocks.HighPrecisionClock;

import java.math.BigInteger;

/**
 * A {@link Clock} that generates a UUID timestamp for times provided by a given
 * {@link Clock}.
 */
public class UUIDClock implements HighPrecisionClock {

    private static final long EPOCH_OFFSET = 0x01B21DD213814000L;

    private final HighPrecisionClock underlying;

    public UUIDClock(final HighPrecisionClock underlying) {
        this.underlying = underlying;
    }

    /**
     * The number of 100ns periods between the beginning of the UUID epoch and
     * the current system time.
     * <p/>
     * Below milliseconds, the number of nanoseconds are just an estimate,
     * though it is guaranteed to be monotonic.
     * <p/>
     * While the timestamp is derived from UNIX time, the range of the timestamp
     * is that of signed 64bit UUID timestamps; therefore, the upper-bound for
     * the timestamp will be smaller than that of UNIX time.
     *
     * @return the UUID timestamp for the current system time.
     */
    public long currentTimeUUID() {
        final long millis = currentTimeMillis();
        final long nanos = underlying.currentNanos();

        return (((millis * 10000) + (nanos / 100))) - EPOCH_OFFSET;
    }

    @Override
    public long currentTimeMillis() {
        return underlying.currentTimeMillis();
    }

    @Override
    public long currentTime() {
        return underlying.currentTime();
    }

    @Override
    public long currentNanos() {
        return underlying.currentNanos();
    }

    @Override
    public BigInteger currentTimeNanos() {
        return underlying.currentTimeNanos();
    }
}
