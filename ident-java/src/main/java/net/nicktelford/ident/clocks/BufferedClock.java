package net.nicktelford.ident.clocks;

import net.nicktelford.ident.Clock;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A {@link Clock} that buffers its current time to improve query performance.
 */
public class BufferedClock implements Clock {

    private static final ThreadFactory THREAD_FACTORY = new ClockThreadFactory();
    private static final ScheduledExecutorService EXECUTOR
            = Executors.newSingleThreadScheduledExecutor(THREAD_FACTORY);

    /**
     * A {@link ThreadFactory} for {@link Thread}s that update a {@link Clock}.
     * <p/>
     * The {@link Thread}s produced by this {@link ThreadFactory} have a {@link
     * Thread.MAX_PRIORITY high priority} to ensure that the clock is updated as
     * timely as possible.
     */
    static class ClockThreadFactory implements ThreadFactory {

        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);

        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix
                = "clock-" + POOL_NUMBER.getAndIncrement() + "-thread-";

        ClockThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            this.group = (s != null)
                    ? s.getThreadGroup()
                    : Thread.currentThread().getThreadGroup();
        }

        @Override
        public Thread newThread(final Runnable task) {
            final Thread t = new Thread(
                    group, task, namePrefix + threadNumber.getAndIncrement());
            t.setDaemon(false);
            t.setPriority(Thread.MAX_PRIORITY);
            return t;
        }
    }

    private long millis;
    private long nanos;
    private long seconds;

    class ClockTick implements Runnable {

        @Override
        public void run() {
            tick();
        }
    }

    public BufferedClock(final TimeUnit targetAccuracy) {
        tick();
        EXECUTOR.schedule(new ClockTick(), 1L, targetAccuracy);
    }

    @Override
    public long currentTimeNanos() {
        return nanos;
    }

    @Override
    public long currentTimeMillis() {
        return millis;
    }

    @Override
    public long currentTime() {
        return seconds;
    }

    protected void tick() {
        millis = System.currentTimeMillis();
        nanos = millis * 1000;
        seconds = millis / 1000;
    }
}
