package net.sopod.soim.common.util;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

/**
 * TokenUtil
 *
 * @author tmy
 * @date 2022-04-14 14:04
 */
public class TokenUtil {

    /** TODO 后期存储到全局缓存中 */
    private static final String SALT = "ac614c954663124b62043e720e5c144d";

    /***
     * base64({id:1,time:unix}) + ".sign(payload)"
     */
    public static String genToken(long userId) {
        int random = ThreadLocalRandom.current().nextInt();
        ByteBuffer buf = ByteBuffer.allocate(20);
        buf.putLong(ImClock.millis());
        buf.putLong(userId);
        buf.putInt(random);
        byte[] bytes = buf.array();
        String payload = Base64.getUrlEncoder().encodeToString(bytes);
        // TODO 其他hash算法
        String signStr = sign(payload);
        return payload + "." + signStr;
    }

    public static Payload validateAndParse(String token) {
        int idx = token.indexOf('.');
        if (idx == -1) return null;

        String payload = token.substring(0, idx);
        String sign = token.substring(idx + 1);
        String calcSign = sign(payload);
        if (!calcSign.equals(sign)) return null;

        byte[] payloadBytes = Base64.getUrlDecoder().decode(payload);
        ByteBuffer buf = ByteBuffer.wrap(payloadBytes);
        Payload p = new Payload();
        p.time = buf.getLong();
        p.userId = buf.getLong();
        p.random = buf.getInt();
        return p;
    }

    public static class Payload {
        long time;
        long userId;
        int random;
        public long getTime() {
            return time;
        }
        public long getUserId() {
            return userId;
        }
        public int getRandom() {
            return random;
        }
        @Override
        public String toString() {
            return "Payload{" +
                    "time=" + time +
                    ", userId=" + userId +
                    ", random=" + random +
                    '}';
        }
    }

    private static String sign(String payload) {
        // TODO 其他hash算法
        int sign = HashAlgorithms.crc32(payload + SALT, 10086);
        byte[] signBytes = ByteBuffer.allocate(4).putInt(sign).array();
        return Base64.getUrlEncoder().encodeToString(signBytes);
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            long uid = Math.abs(ThreadLocalRandom.current().nextLong());
            String token = genToken(uid);
            Payload payload = validateAndParse(i == 3 ? token + "=" : token);
            System.out.println(token);
            System.out.println(uid + ": " + payload);
            Thread.sleep(2000L);
        }
    }

}
