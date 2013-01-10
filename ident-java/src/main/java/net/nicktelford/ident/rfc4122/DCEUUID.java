package net.nicktelford.ident.rfc4122;

/**
 * An RFC4122 Version 2, DCE Security UUID.
 * <p/>
 * This variant is described by The Open Group</a> as part of DCE (Distributed
 * Computing Environment).
 *
 * @see <a href="http://www.ietf.org/rfc/rfc4122">RFC4122</a>
 * @see <a href="http://pubs.opengroup.org/onlinepubs/9696989899/chap5.htm#tagcjh_08_02_01_01">DCE 1.1: Authentication and Security Services</a>
 */
public class DCEUUID extends RFC4122UUID {

    public static final short VERSION = 2;
    private static final Factory FACTORY = new Factory(VERSION);

    public static final DCEUUID MAX_VALUE
            = new DCEUUID(FACTORY.encodeMSB(-1L), FACTORY.encodeLSB(-1L));
    public static final DCEUUID MIN_VALUE
            = new DCEUUID(FACTORY.encodeMSB(0), FACTORY.encodeLSB(0));

    DCEUUID(final long msb, final long lsb) {
        super(msb, lsb);
    }
}
