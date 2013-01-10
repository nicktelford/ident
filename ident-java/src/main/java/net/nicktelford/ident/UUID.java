package net.nicktelford.ident;

/**
 * Minimal interface for UUIDs.
 */
public interface UUID {

    /**
     * The variant of the UUID, which defines the structure of the UUID.
     *
     * @return the UUID variant.
     */
    public Variant variant();

    /**
     * The 64 most significant bits of the UUID as a long.
     *
     * @see #lsb()
     * @return the 64 most significant bits of the UUID as a long.
     */
    public long msb();

    /**
     * The 64 least significant bits of the UUID as a long.
     *
     * @see #msb()
     * @return the 64 least significant bits of the UUID as a long.
     */
    public long lsb();

    /**
     * Generates a binary representation of this UUID.
     *
     * @return a binary representation of this {@link UUID}.
     */
    public byte[] toBytes();

    /**
     * The special Nil UUID, common to all {@link Variant}s.
     */
    public static UUID Nil = new NilUUID();

    /**
     * The special Nil UUID, common to all {@link Variant}s.
     */
    class NilUUID implements UUID {

        @Override
        public Variant variant() {
            return Variant.Nil;
        }

        @Override
        public long msb() {
            return 0;
        }

        @Override
        public long lsb() {
            return 0;
        }

        @Override
        public byte[] toBytes() {
            return new byte[]
                    { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        }
    }

    /**
     * Base class for {@link UUID}s that provides a base common to many, not not
     * necessarily all, UUID {@link Variant}s.
     */
    abstract class BaseUUID implements UUID {

        protected final long msb;
        protected final long lsb;

        public BaseUUID(final long msb, final long lsb) {
            this.msb = msb;
            this.lsb = lsb;
        }

        public long msb() {
            return msb;
        }

        public long lsb() {
            return lsb;
        }

        /**
         * Generates a binary representation of this UUID.
         * <p/>
         * The binary representation will be in {@link
         * java.nio.ByteOrder#BIG_ENDIAN network order}.
         *
         * @return a binary representation of this {@link UUID} in {@link
         *         java.nio.ByteOrder#BIG_ENDIAN network order}.
         */
        @Override
        public byte[] toBytes() {
            return new byte[] {
                    (byte) (msb >> 56),
                    (byte) (msb >> 48),
                    (byte) (msb >> 40),
                    (byte) (msb >> 32),
                    (byte) (msb >> 24),
                    (byte) (msb >> 16),
                    (byte) (msb >> 8),
                    (byte) msb,
                    (byte) (lsb >> 56),
                    (byte) (lsb >> 48),
                    (byte) (lsb >> 40),
                    (byte) (lsb >> 32),
                    (byte) (lsb >> 24),
                    (byte) (lsb >> 16),
                    (byte) (lsb >> 8),
                    (byte) lsb,
            };
        }

        /**
         * Tests the equality of two {@link UUID}s.
         * <p/>
         * Two {@link UUID}s are considered equal if all of their bits match.
         * <p/>
         * If the {@code other} object is not a {@link UUID}, they are
         * considered not equal.
         *
         * @param other the object to compare to this {@link UUID}.
         * @return {@code true} if {@code other} is a {@link UUID} and all of
         *         its bits match that of this {@link UUID}.
         */
        @Override
        public boolean equals(final Object other) {
            if (other instanceof UUID) {
                final UUID uuid = (UUID) other;
                return uuid.msb() == msb && uuid.lsb() == lsb;
            }
            return false;
        }

        /**
         * Renders a human-readable representation of the {@link UUID}.
         *
         * @return a human-readable representation of the UUID.
         */
        @Override
        public String toString() {
            return String.format(
                    "%08x-%04x-%04x-%04x-%012x",
                    (int) (msb >> 32),
                    (short) (msb >> 16),
                    (short) msb,
                    (short) (lsb >> 48),
                    (lsb & 0xFFFFFFL));
        }
    }
}
