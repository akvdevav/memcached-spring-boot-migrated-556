package io.sixhours.memcached.cache;

import java.net.InetSocketAddress;

/**
 * {@link InetSocketAddress} wrapper class.
 *
 * @author Igor Bolic
 */
public final class SocketAddress {

    private final InetSocketAddress value;

    public SocketAddress(String value) {
        this.value = socketAddress(value);
    }

    public InetSocketAddress value() {
        return value;
    }

    /**
     * Validates and creates socket address value. Based on XMemcached's {@link net.rubyeye.xmemcached.utils.AddrUtil#getOneAddress(String)}.
     *
     * @param server Server address
     * @return InetSocketAddress
     */
    private InetSocketAddress socketAddress(String server) {
        if (server == null) {
            throw new IllegalArgumentException("Invalid server value. It should not be null");
        }
        if (server.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid server value. It cannot be empty");
        }
        server = server.trim();
        int finalColon = server.lastIndexOf(':');
        if (finalColon < 1) {
            throw new IllegalArgumentException("Invalid server value '" + server + "'");

        }

        final String hostPart = server.substring(0, finalColon).trim();
        final String portNum = server.substring(finalColon + 1).trim();

        return new InetSocketAddress(hostPart, Integer.parseInt(portNum));
    }
}