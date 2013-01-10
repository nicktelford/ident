package net.nicktelford.ident.rfc4122;

import net.nicktelford.ident.UUID;
import net.nicktelford.ident.util.Bytes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * An RFC4122 Version 3 or 5 Name-based UUID.
 * <p/>
 * The {@link #version()} of this UUID depends on the hashing algorithm chosen:
 * <ul>
 *     <li><b>3</b>: {@link #MD5}</li>
 *     <li><b>5</b>: {@link #SHA1}</li>
 * </ul>
 * To create a name-based UUID, first select an algorithm using one of the
 * builders; {@link #MD5} and {@link #SHA1}.
 * <p/>
 * It is nearly always preferable to use a {@link #SHA1} (version 5) UUID as
 * they have a lower probability of collisions than {@link #MD5} (version 3)
 * UUIDs.
 *
 * @see <a href="http://www.ietf.org/rfc/rfc4122">RFC4122</a>
 */
public class NameUUID extends RFC4122UUID {

    public static Scheme MD5 = Scheme.MD5;
    public static Scheme SHA1 = Scheme.SHA1;

    static enum Scheme {

        MD5(3, "md5"),
        SHA1(5, "sha1");

        private final short version;
        private final MessageDigest algorithm;
        private final Factory factory;

        public final NameUUID MAX_VALUE;
        public final NameUUID MIN_VALUE;

        Scheme(final int version,
               final String algorithm) throws NoSuchAlgorithmException {
            this((short) version, algorithm);
        }

        Scheme(final short version,
               final String algorithm) throws NoSuchAlgorithmException {
            this.version = (short) version;
            this.algorithm = MessageDigest.getInstance(algorithm);
            this.factory = new Factory(this.version);
            MAX_VALUE = new NameUUID(factory.encodeMSB(-1),
                                     factory.encodeLSB(-1),
                                     version);
            MIN_VALUE = new NameUUID(factory.encodeMSB(0),
                                     factory.encodeLSB(0),
                                     version);
        }

        public short version() {
            return version;
        }

        public NameUUID forName(final UUID namespace, final byte[] name) {
            final byte[] hash = hash(namespace.toBytes(), name);
            final long msb = factory.encodeMSB(Bytes.toLong(hash));
            final long lsb = factory.encodeLSB(Bytes.toLong(hash, 8));

            return new NameUUID(msb, lsb, version());
        }

        private byte[] hash(final byte[] data) {
            return algorithm.digest(data);
        }

        private byte[] hash(final byte[] namespace, final byte[] name) {
            return hash(Bytes.concat(namespace, name));
        }
    }

    private final short version;

    NameUUID(final long msb, final long lsb, final short version) {
        super(msb, lsb);
        this.version = version;
    }

    @Override
    public short version() {
        return version;
    }
}
