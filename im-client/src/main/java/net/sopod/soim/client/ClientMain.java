package net.sopod.soim.client;

import net.sopod.soim.client.net.ImNetClient;

/**
 * Main
 *
 * @author tmy
 * @date 2022-03-27 22:32
 */
public class ClientMain {

    public static void main(String[] args) throws InterruptedException {
        ImNetClient client = new ImNetClient();
        client.connect("127.0.0.1", 8088);

    }

}
