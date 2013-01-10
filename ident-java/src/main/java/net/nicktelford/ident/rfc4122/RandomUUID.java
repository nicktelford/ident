package net.nicktelford.ident.rfc4122;

/**
 * An RFC4122 Version 4, randomly generated UUID.
 *
 * @see <a href="http://www.ietf.org/rfc/rfc4122">RFC4122</a>
 */
public class RandomUUID extends RFC4122UUID {

    public static final short VERSION = 4;
    private static final Factory FACTORY = new Factory(VERSION);

    public static final RandomUUID MAX_VALUE
            = new RandomUUID(FACTORY.encodeMSB(-1L), FACTORY.encodeLSB(-1));
    public static final RandomUUID MIN_VALUE
            = new RandomUUID(FACTORY.encodeMSB(0), FACTORY.encodeLSB(0));

    public static RandomUUID generate() {
        return new RandomUUID(FACTORY.randomMSB(), FACTORY.randomLSB());
    }

    RandomUUID(final long msb, final long lsb) {
        super(msb, lsb);
    }

    @Override
    public short version() {
        return VERSION;
    }
}
