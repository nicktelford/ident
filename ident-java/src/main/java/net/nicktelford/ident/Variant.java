package net.nicktelford.ident;

/**
 * Enumeration of UUID Variants.
 */
public enum Variant {

    /**
     * Variant reserved for NCS backward compatibility.
     */
    NCS (0x8, 0x0),

    /**
     * Variant for UUIDs that adhere to RFC-4122.
     *
     * @see http://www.ietf.org/rfc/rfc4122.txt
     */
    RFC (0xC, 0x8),

    /**
     * Variant reserved for Microsoft backwards compatibility.
     */
    Microsoft (0xE, 0xC),

    /**
     * Variant for UUIDs that adhere to a custom scheme.
     * <p/>
     * <i>Note: {@link UUID}s with a {@code Custom} {@link Variant} are not
     * guaranteed to be universally unique as custom UUID formats will vary
     * between vendors.</i>
     */
    Custom (0xE, 0xE),

    /**
     * Special Variant only to be used by the {@link UUID#Nil Nil UUID}.
     */
    Nil (0x0, 0x0);

    private final byte mask;
    private final byte value;

    Variant(final int mask, final int value) {
        this((byte) mask, (byte) value);
    }

    Variant(final byte mask, final byte value) {
        this.mask = mask;
        this.value = value;
    }

    /**
     * The {@link Variant} bit-mask, used to determine which bits encode the
     * {@link #value()} in a {@link UUID}.
     *
     * @return the bit-mask of this {@link Variant }that determines the bits
     *         that encode the {@link #value()}.
     */
    public byte mask() {
        return mask;
    }

    /**
     * The {@link Variant} value, which uniquely describes each {@link Variant}.
     *
     * @return the {@link Variant} value.
     */
    public byte value() {
        return value;
    }
}
