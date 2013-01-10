package net.nicktelford.ident.rfc4122;

import net.nicktelford.ident.UUID;
import net.nicktelford.ident.Variant;
import net.nicktelford.ident.util.Bytes;

import java.util.Random;

/**
 * Base class for {@link net.nicktelford.ident.UUID}s defined by <a
 * href="http://www.ietf.org/rfc/rfc4122.txt">RFC4122</a>.
 */
abstract class RFC4122UUID extends UUID.BaseUUID implements UUID {

    private static final Variant VARIANT = Variant.RFC;

    RFC4122UUID(final long msb, final long lsb) {
        super(msb, lsb);
    }

    /**
     * Version number of the {@link UUID}.
     *
     * @return the version number of the UUID.
     */
    public short version() {
        return (short) ((msb & 0xF000) >> 12);
    }

    /**
     * @see UUID#variant()
     * @return the {@link Variant#RFC RFC} UUID Variant.
     */
    @Override
    public Variant variant() {
        return VARIANT;
    }

    public static RFC4122UUID from(final String uuid) {
        final byte[] bytes = Bytes.fromHexadecimalString(uuid);
        if (bytes.length < 16) {
            throw new IllegalArgumentException(
                    "Must contain 32 hexadecimal characters describing the UUID");
        }
        return from(bytes);
    }

    public static RFC4122UUID from(final byte[] uuid) {
        if (uuid.length < 16) {
            throw new IllegalArgumentException(
                    "Not enough bytes, UUIDs are 16 bytes wide");
        }

        if (uuid.length > 16) {
            throw new IllegalArgumentException(
                    "Too many bytes, UUIDs are 16 bytes wide");
        }

        // verify variant
        if ((uuid[8] & VARIANT.mask()) != VARIANT.value()) {
            throw new IllegalArgumentException(
                    "Incorrect variant, either not a valid UUID or not an RFC4122 UUID");
        }

        // determine version
        final short version = (short) (uuid[6] >> 4);
        final long msb = Bytes.toLong(uuid);
        final long lsb = Bytes.toLong(uuid, 8);

        // todo: don't hard-code this logic here
        switch (version) {
            case TimeUUID.VERSION: return new TimeUUID(msb, lsb);
            case RandomUUID.VERSION: return new RandomUUID(msb, lsb);
            case NameUUID.MD5.VERSION: return new NameUUID(msb, lsb, version);
            case NameUUID.SHA1.VERSION: return new NameUUID(msb, lsb, version);
            default: throw new IllegalArgumentException(
                    "Invalid version, not a valid UUID");
        }
    }

    static class Factory {

        private final Random RAND = new Random();

        private final short version;

        public Factory(final short version) {
            this.version = version;
        }

        public long encodeMSB(final long bits) {
            final long time_lo = bits & 0xFFFFFFFFL;
            final long time_mid = (bits >> 32) & 0xFFFF;
            final long time_hi = (bits >> 48) & 0x0FFF;
            final long time_hi_and_version = (version << 4) | time_hi;

            return (time_lo << 32) | (time_mid << 16) | time_hi_and_version;
        }

        public long encodeLSB(final long bits) {
            final long clock = bits >> 48;
            final long clock_hi = (0xF - VARIANT.mask()) & (clock >> 8);
            final long clock_hi_and_variant = (VARIANT.mask() & VARIANT.value()) | clock_hi;
            final long clock_lo = clock & 0xF;

            return (clock_hi_and_variant << 56) | (clock_lo << 48) | bits & 0xFFFFFFFFFFFFL;
        }

        public long decodeMSB(final long bits) {
            final long time_lo = bits >> 32;
            final long time_mid = (bits >> 16) & 0xFFFF;
            final long time_hi_and_version = bits & 0xFFFF;
            final long time_hi = time_hi_and_version & 0x0FFF;

            return time_hi << 48 | time_mid << 32 | time_lo;
        }

        public long decodeLSB(final long bits) {
            final long clock_hi_and_variant = bits >> 56;
            final long clock_hi = ((~VARIANT.mask() << 4) | 0xF) & clock_hi_and_variant;
            final long clock_lo = (bits >> 48) & 0xF;

            return clock_hi << 56 | clock_lo << 48 | bits & 0xFFFFFFFFFFFFL;
        }

        public long randomMSB() {
            return encodeMSB(RAND.nextLong());
        }

        public long randomLSB() {
            return encodeLSB(RAND.nextLong());
        }
    }
}
