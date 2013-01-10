package net.nicktelford.ident.clocks;

import net.nicktelford.ident.Clock;

/**
 * Basic {@link Clock} implementation that provides access to the standard JVM
 * system clock.
 * <p/>
 * <ul>
 *     <li><b>Best possible accuracy: milliseconds</b></li>
 * </ul>
 */
public class SystemClock implements Clock {

    private static final Clock INSTANCE = new SystemClock();

    public static Clock getInstance() {
        return INSTANCE;
    }

    @Override
    public long currentTimeNanos() {
        return currentTimeMillis() * 1000;
    }

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override
    public long currentTime() {
        return currentTimeMillis() / 1000;
    }
}
