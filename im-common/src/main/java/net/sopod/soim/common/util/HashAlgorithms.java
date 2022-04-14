package net.sopod.soim.common.util;

/**
 * hash函数
 * https://zhuanlan.zhihu.com/p/101390996
 *
 * @author tangmingyou
 * @date 2021-10-28 10:33
 */
public class HashAlgorithms {

    /**
     * 加法hash
     *
     * @param key   字符串
     * @param prime 一个质数
     * @return hash结果
     */
    public static int additiveHash(String key, int prime) {
        int hash, i;
        for (hash = key.length(), i = 0; i < key.length(); i++)
            hash += key.charAt(i);
        return (hash % prime);
    }

    /**
     * 旋转hash
     *
     * @param key   输入字符串
     * @param prime 质数
     * @return hash值
     */
    public static int rotatingHash(String key, int prime) {
        int hash, i;
        for (hash = key.length(), i = 0; i < key.length(); ++i)
            hash = (hash << 4) ^ (hash >> 28) ^ key.charAt(i);
        return (hash % prime);
        //  return (hash ^ (hash>>10) ^ (hash>>20));
    }
    // 替代：
    // 使用：hash = (hash ^ (hash>>10) ^ (hash>>20)) & mask;
    // 替代：hash %= prime;
    /**/
    /**
     * MASK值，随便找一个值，最好是质数
     */
    static int M_MASK = 0x8765fed1;

    /**
     * 一次一个hash
     *
     * @param key 输入字符串
     * @return 输出hash值
     */
    public static int oneByOneHash(String key) {
        int hash, i;
        for (hash = 0, i = 0; i < key.length(); ++i) {
            hash += key.charAt(i);
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);
        //  return (hash & M_MASK);
        return hash;
    }

    /**
     * Bernstein's hash
     *
     * @param key 输入字节数组
     * @return 结果hash
     */
    public static int bernstein(String key) {
        int hash = 0;
        int i;
        for (i = 0; i < key.length(); ++i) hash = 33 * hash + key.charAt(i);
        return hash;
    }

    //
    /**///// Pearson's Hash
    // char pearson(char[]key, ub4 len, char tab[256])
    // {
    //  char hash;
    //  ub4 i;
    //  for (hash=len, i=0; i<len; ++i)
    //   hash=tab[hash^key[i]];
    //  return (hash);
    // }
    /**///// CRC Hashing，计算crc,具体代码见其他
    // ub4 crc(char *key, ub4 len, ub4 mask, ub4 tab[256])
    // {
    //  ub4 hash, i;
    //  for (hash=len, i=0; i<len; ++i)
    //   hash = (hash >> 8) ^ tab[(hash & 0xff) ^ key[i]];
    //  return (hash & mask);
    // }
    /**/

    /**
     * CRC系列算法本身并非查表，可是，查表是它的一种最快的实现方式。以下是CRC32的实现
     */
    private static int[] signed_crc_table() {
        int c = 0;
        int[] table = new int[256];

        for (int n = 0; n != 256; ++n) {
            c = n;
            c = ((c & 1) != 0 ? (-306674912 ^ (c >>> 1)) : (c >>> 1));
            c = ((c & 1) != 0 ? (-306674912 ^ (c >>> 1)) : (c >>> 1));
            c = ((c & 1) != 0 ? (-306674912 ^ (c >>> 1)) : (c >>> 1));
            c = ((c & 1) != 0 ? (-306674912 ^ (c >>> 1)) : (c >>> 1));
            c = ((c & 1) != 0 ? (-306674912 ^ (c >>> 1)) : (c >>> 1));
            c = ((c & 1) != 0 ? (-306674912 ^ (c >>> 1)) : (c >>> 1));
            c = ((c & 1) != 0 ? (-306674912 ^ (c >>> 1)) : (c >>> 1));
            c = ((c & 1) != 0 ? (-306674912 ^ (c >>> 1)) : (c >>> 1));
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

    /**
     * Universal Hashing
     */
    public static int universal(char[] key, int mask, int[] tab) {
        int hash = key.length, i, len = key.length;
        for (i = 0; i < (len << 3); i += 8) {
            char k = key[i >> 3];
            if ((k & 0x01) == 0) hash ^= tab[i + 0];
            if ((k & 0x02) == 0) hash ^= tab[i + 1];
            if ((k & 0x04) == 0) hash ^= tab[i + 2];
            if ((k & 0x08) == 0) hash ^= tab[i + 3];
            if ((k & 0x10) == 0) hash ^= tab[i + 4];
            if ((k & 0x20) == 0) hash ^= tab[i + 5];
            if ((k & 0x40) == 0) hash ^= tab[i + 6];
            if ((k & 0x80) == 0) hash ^= tab[i + 7];
        }
        return (hash & mask);
    }

    /**
     * Zobrist Hashing
     */
    public static int zobrist(char[] key, int mask, int[][] tab) {
        int hash, i;
        for (hash = key.length, i = 0; i < key.length; ++i)
            hash ^= tab[i][key[i]];
        return (hash & mask);
    }

    // LOOKUP3
    // 见Bob Jenkins(3).c文件
    // 32位FNV算法
    static int M_SHIFT = 0;

    /**
     * 32位的FNV算法
     *
     * @param data 数组
     * @return int值
     */
    public static int FNVHash(byte[] data) {
        int hash = (int) 2166136261L;
        for (byte b : data)
            hash = (hash * 16777619) ^ b;
        if (M_SHIFT == 0)
            return hash;
        return (hash ^ (hash >> M_SHIFT)) & M_MASK;
    }

    /**
     * 改进的32位FNV算法1
     *
     * @param data 数组
     * @return int值
     */
    public static int FNVHash1(byte[] data) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (byte b : data)
            hash = (hash ^ b) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return hash;
    }

    /**
     * FNV-1a HASH
     * 改进的32位FNV算法1
     * <p>
     * costarring 与...碰撞 liquid
     * declinate 与...碰撞 macallums
     * altarage 与...碰撞 zinke
     * altarages 与...碰撞 zinkes
     *
     * @param data 字符串
     * @return int值
     */
    public static int FNVHash1a(String data) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < data.length(); i++)
            hash = (hash ^ data.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return hash;
    }

    /**
     * Thomas Wang的算法，整数hash
     */
    public static int intHash(int key) {
        key += ~(key << 15);
        key ^= (key >>> 10);
        key += (key << 3);
        key ^= (key >>> 6);
        key += ~(key << 11);
        key ^= (key >>> 16);
        return key;
    }

    /**
     * RS算法hash
     *
     * @param str 字符串
     */
    public static int RSHash(String str) {
        int b = 378551;
        int a = 63689;
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = hash * a + str.charAt(i);
            a = a * b;
        }
        return (hash & 0x7FFFFFFF);
    }
    /* End Of RS Hash Function */

    /**
     * JS算法
     */
    public static int JSHash(String str) {
        int hash = 1315423911;
        for (int i = 0; i < str.length(); i++) {
            hash ^= ((hash << 5) + str.charAt(i) + (hash >> 2));
        }
        return (hash & 0x7FFFFFFF);
    }
    /**//* End Of JS Hash Function */

    /**
     * PJW算法
     */
    public static int PJWHash(String str) {
        int BitsInUnsignedInt = 32;
        int ThreeQuarters = (BitsInUnsignedInt * 3) / 4;
        int OneEighth = BitsInUnsignedInt / 8;
        int HighBits = 0xFFFFFFFF << (BitsInUnsignedInt - OneEighth);
        int hash = 0;
        int test = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash << OneEighth) + str.charAt(i);
            if ((test = hash & HighBits) != 0) {
                hash = ((hash ^ (test >> ThreeQuarters)) & (~HighBits));
            }
        }
        return (hash & 0x7FFFFFFF);
    }
    /* End Of P. J. Weinberger Hash Function */

    /**
     * ELF算法
     */
    public static int ELFHash(String str) {
        int hash = 0;
        int x = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash << 4) + str.charAt(i);
            if ((x = (int) (hash & 0xF0000000L)) != 0) {
                hash ^= (x >> 24);
                hash &= ~x;
            }
        }
        return (hash & 0x7FFFFFFF);
    }

    /**
     * BKDR算法
     */
    public static int BKDRHash(String str) {
        int seed = 131; // 31 131 1313 13131 131313 etc..
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash * seed) + str.charAt(i);
        }
        return (hash & 0x7FFFFFFF);
    }

    /**
     * SDBM算法
     */
    public static int SDBMHash(String str) {
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash;
        }
        return (hash & 0x7FFFFFFF);
    }

    /**
     * DJB算法
     */
    public static int DJBHash(String str) {
        int hash = 5381;
        for (int i = 0; i < str.length(); i++) {
            hash = ((hash << 5) + hash) + str.charAt(i);
        }
        return (hash & 0x7FFFFFFF);
    }

    /**
     * DEK算法
     */
    public static int DEKHash(String str) {
        int hash = str.length();
        for (int i = 0; i < str.length(); i++) {
            hash = ((hash << 5) ^ (hash >> 27)) ^ str.charAt(i);
        }
        return (hash & 0x7FFFFFFF);
    }

    /**
     * AP算法
     */
    public static int APHash(String str) {
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash ^= ((i & 1) == 0) ? ((hash << 7) ^ str.charAt(i) ^ (hash >> 3)) :
                    (~((hash << 11) ^ str.charAt(i) ^ (hash >> 5)));
        }
        //    return (hash & 0x7FFFFFFF);
        return hash;
    }

    /**
     * JAVA自己带的算法
     */
    public static int java(String str) {
        int h = 0;
        int off = 0;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            h = 31 * h + str.charAt(off++);
        }
        return h;
    }

}
