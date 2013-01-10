package net.nicktelford.ident.util;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;

/**
 * Encapsulates meta-data about the physical device the application is running
 * on.
 * <p/>
 * Specifically, this calculates an appropriate <i>node address</i> for use by
 * UUIDs.
 * <p/>
 * If there is no hardware address, none of the available addresses are
 * appropriate or none are obtainable; a random node address will be generated,
 * with the "local" bit set to ensure it doesn't collide with legitimate node
 * addresses.
 */
public class Node {

    private static long mac;

    static {
        try {
            final Enumeration<NetworkInterface> ifaces
                    = NetworkInterface.getNetworkInterfaces();

            while (ifaces.hasMoreElements()) {
                final byte[] hwaddr = ifaces.nextElement().getHardwareAddress();
                // ensure the address exists and isn't a multi-cast address
                if (hwaddr != null && (hwaddr[0] & 2) == 0) {
                    mac = (long) hwaddr[0] << 40 |
                            (long) hwaddr[1] << 32 |
                            (long) hwaddr[2] << 24 |
                            (long) hwaddr[3] << 16 |
                            (long) hwaddr[4] << 8 |
                            hwaddr[5];
                    break;
                }
            }
        } catch (final SocketException e) {
            // todo: generate a *cryptographically secure* random MAC
            // generate a random address and set the "local" bit
            mac = new Random().nextLong() | 0x0001000000000000L;
        }
    }

    /**
     * The hardware address of this Node.
     *
     * @return the hardware address of this Node.
     */
    public static long getAddress() {
        return mac;
    }
}
