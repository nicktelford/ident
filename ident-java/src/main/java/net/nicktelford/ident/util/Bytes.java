package net.nicktelford.ident.util;

/**
 * Utility for working with byte arrays efficiently.
 */
public class Bytes {

    public static byte[] concat(final byte[] first, final byte[] second) {
        final byte[] result = new byte[first.length + second.length];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static byte[] concat(final byte[] first,
                                final byte[] second,
                                final byte[] third) {
        final byte[] result = new byte[first.length + second.length + third.length];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        System.arraycopy(third, 0, result, first.length + second.length, third.length);
        return result;
    }

    public static byte[] concat(final byte[]... arrays) {
        int length = 0;
        for (final byte[] bytes : arrays) {
            length += bytes.length;
        }

        final byte[] result = new byte[length];
        int i = 0;
        for (final byte[] bytes : arrays) {
            final int len = bytes.length;
            System.arraycopy(bytes, 0, result, i, len);
            i += len;
        }
        return result;
    }

    // strips all non-hexadecimal characters from the given string
    public static String hexOnly(final String s) {
        int len = 0;
        final char[] out = new char[s.length()];
        final char[] chars = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            final char c = chars[i];
            if ((c > 47 && c < 58) || (c > 64 && c < 71) || (c > 96 && c < 103)) {
                out[len++] = c;
            }
        }
        return new String(out, 0, len);
    }

    // todo: make this nicer
    public static byte[] fromHexadecimalString(final String s) {
        String hex = hexOnly(s);

        // left-pad with a zero
        if (hex.length() % 2 != 0) {
            hex = "0" + hex;
        }

        final byte[] result = new byte[hex.length() / 2];
        for (int i = 0; i < result.length; i++) {
            final int n = i * 2;
            final char first = hex.charAt(n);
            final char second = hex.charAt(n+1);

            result[i] = (byte) ((byteFromHexChar(first) << 4) | byteFromHexChar(second));
        }

        return result;
    }
    // todo: make this nicer
    private static byte byteFromHexChar(final char c) {
        // use ordinal char value and offset it to match hexadecimal value
        if (c > 47 && c < 58) {
            return (byte) (c - 48);
        } else if (c > 64 && c < 71) {
            return (byte) (c - 55);
        } else if (c > 96 && c < 103) {
            return (byte) (c - 87);
        }
        return 0;
    }

    public static byte[] fromLong(final long l) {
        return new byte[] {
                (byte) (l >> 56),
                (byte) (l >> 48),
                (byte) (l >> 40),
                (byte) (l >> 32),
                (byte) (l >> 24),
                (byte) (l >> 16),
                (byte) (l >> 8),
                (byte) l
        };
    }

    public static byte[] fromInt(final int i) {
        return new byte[] {
                (byte) (i >> 24),
                (byte) (i >> 16),
                (byte) (i >> 8),
                (byte) i,
        };
    }

    public static byte[] fromShort(final short s) {
        return new byte[] { (byte) (s >> 8), (byte) s };
    }

    public static long toLong(final byte[] bytes) {
        return toLong(bytes, 0);
    }

    public static long toLong(final byte[] bytes, final int offset) {
        int i = offset;
        return ((long) bytes[i++]) << 56 |
                ((long) bytes[i++]) << 48 |
                ((long) bytes[i++]) << 40 |
                ((long) bytes[i++]) << 32 |
                ((long) bytes[i++]) << 24 |
                ((long) bytes[i++]) << 16 |
                ((long) bytes[i++]) << 8 |
                bytes[i];
    }

    public static int toInt(final byte[] bytes) {
        return toInt(bytes, 0);
    }

    public static int toInt(final byte[] bytes, final int offset) {
        int i = offset;
        return ((int) bytes[i++]) << 24 |
                ((int) bytes[i++]) << 16 |
                ((int) bytes[i++]) << 8 |
                bytes[i];
    }

    public static short toShort(final byte[] bytes) {
        return toShort(bytes, 0);
    }

    public static short toShort(final byte[] bytes, final int offset) {
        int i = offset;
        return (short) (((short) bytes[i++] << 8) | bytes[i]);
    }
}
