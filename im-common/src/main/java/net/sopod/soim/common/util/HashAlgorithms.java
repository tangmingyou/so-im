package net.sopod.soim.common.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * hash函数
 *
 * @author tangmingyou
 * @date 2021-10-28 10:33
 */
public class HashAlgorithms {

    /**
     * MD5 Hash
     */
    public static long md5Hash(String value) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported", e);
        }
        md5.reset();
        byte[] keyBytes = value.getBytes(StandardCharsets.UTF_8);
        md5.update(keyBytes);
        byte[] digest = md5.digest();
        // hash code, Truncate to 32-bits
        long hashCode = ((long) (digest[3] & 0xFF) << 24)
                | ((long) (digest[2] & 0xFF) << 16)
                | ((long) (digest[1] & 0xFF) << 8)
                | (digest[0] & 0xFF);
        return hashCode & 0xffffffffL;
    }


    /**
     * CRC系列算法本身并非查表，可是，查表是它的一种最快的实现方式。以下是CRC32的实现
     */
    private static int[] signed_crc_table() {
        int c = 0;
        int[] table = new int[256];

        for (int n = 0; n != 256; ++n) {
            c = n;
            for (int i = 0; i < 8; i++) {
                c = ((c & 1) != 0 ? (-306674912 ^ (c >>> 1)) : (c >>> 1));
            }
//            c = ((c & 1) != 0 ? (-306674912 ^ (c >>> 1)) : (c >>> 1));
//            c = ((c & 1) != 0 ? (-306674912 ^ (c >>> 1)) : (c >>> 1));
//            c = ((c & 1) != 0 ? (-306674912 ^ (c >>> 1)) : (c >>> 1));
//            c = ((c & 1) != 0 ? (-306674912 ^ (c >>> 1)) : (c >>> 1));
//            c = ((c & 1) != 0 ? (-306674912 ^ (c >>> 1)) : (c >>> 1));
//            c = ((c & 1) != 0 ? (-306674912 ^ (c >>> 1)) : (c >>> 1));
//            c = ((c & 1) != 0 ? (-306674912 ^ (c >>> 1)) : (c >>> 1));
//            c = ((c & 1) != 0 ? (-306674912 ^ (c >>> 1)) : (c >>> 1));
            table[n] = c;
        }
        return table;
    }

    private static final int[] T = signed_crc_table();

    /**
     * CRC32 的查表实现
     */
    public static int crc32(String str, int seed) {
        int C = ~seed; // 按位取反 seed ^ -1
        for (int i = 0, L = str.length(), c, d; i < L; ) {
            c = str.charAt(i++);
            if (c < 0x80) {
                C = (C >>> 8) ^ T[(C ^ c) & 0xFF];
            } else if (c < 0x800) {
                C = (C >>> 8) ^ T[(C ^ (192 | ((c >> 6) & 31))) & 0xFF];
                C = (C >>> 8) ^ T[(C ^ (128 | (c & 63))) & 0xFF];
            } else if (c >= 0xD800 && c < 0xE000) {
                c = (c & 1023) + 64;
                d = str.charAt(i++) & 1023;
                C = (C >>> 8) ^ T[(C ^ (240 | ((c >> 8) & 7))) & 0xFF];
                C = (C >>> 8) ^ T[(C ^ (128 | ((c >> 2) & 63))) & 0xFF];
                C = (C >>> 8) ^ T[(C ^ (128 | ((d >> 6) & 15) | ((c & 3) << 4))) & 0xFF];
                C = (C >>> 8) ^ T[(C ^ (128 | (d & 63))) & 0xFF];
            } else {
                C = (C >>> 8) ^ T[(C ^ (224 | ((c >> 12) & 15))) & 0xFF];
                C = (C >>> 8) ^ T[(C ^ (128 | ((c >> 6) & 63))) & 0xFF];
                C = (C >>> 8) ^ T[(C ^ (128 | (c & 63))) & 0xFF];
            }
        }
        return ~C;
    }

}
