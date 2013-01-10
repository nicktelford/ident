package net.nicktelford.ident.rfc4122;

import net.nicktelford.ident.UUID;

/**
 * A set of pre-defined namespaces that may be used for {@link NameUUID}s.
 * <p/>
 * This is the set of pre-defined namespaces for common use by {@link NameUUID}s
 * as defined in Appendix C of <a href="http://www.ietf.org/rfc/rfc4122">RFC4122
 * </a>.
 *
 * @see http://www.ietf.org/rfc/rfc4122
 */
public class Namespaces {

    /**
     * Namespace for full-qualified domain names.
     *
     * <pre>6ba7b810-9dad-11d1-80b4-00c04fd430c8</pre>
     */
    public static UUID DNS
            = new TimeUUID(7757371264673321425L, -9172705715073830712L);

    /**
     * Namespace for URLs.
     *
     * <pre>6ba7b811-9dad-11d1-80b4-00c04fd430c8</pre>
     */
    public static UUID URL
            = new TimeUUID(7757371268968288721L, -9172705715073830712L);

    /**
     * Namespace for ISO OIDs.
     *
     * <pre>6ba7b812-9dad-11d1-80b4-00c04fd430c8</pre>
     */
    public static UUID ISO_OID
            = new TimeUUID(7757371273263256017L, -9172705715073830712L);

    /**
     * Namespace for X.500 Distinguished Names (in DER or a text output format).
     *
     * <pre>6ba7b814-9dad-11d1-80b4-00c04fd430c8</pre>
     */
    public static UUID X500
            = new TimeUUID(7757371281853190609L, -9172705715073830712L);
}
