package com.nesp.sdk.android.hardware;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/8/19 17:35
 **/
public final class HexUtil {
    private static final String TAG = "HexUtil";

    private HexUtil() {
        //no instance
    }

    /*
     * 字节数组转16进制字符串
     */
    public static String byteArray2HexString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(bytes.length);
        String sTmp;

        for (final byte aByte : bytes) {
            sTmp = Integer.toHexString(0xFF & aByte);
            if (sTmp.length() < 2)
                sb.append(0);
            sb.append(sTmp);
        }

        return sb.toString();
    }

    /*
     * 字节数组转16进制字符串
     */
    public static String uByteArray2HexString(int[] uBytes) {
        if (uBytes == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(uBytes.length);
        String sTmp;

        for (final int uByte : uBytes) {
            sTmp = Integer.toHexString(0xFF & uByte);
            if (sTmp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTmp);
        }

        return sb.toString();
    }

    public static String int2HexString(int i, int minLength) {
        StringBuilder hex = new StringBuilder(int2HexString(i));
        while (hex.length() < minLength) {
            hex.insert(0, "0");
        }
        return hex.toString();
    }

    /**
     * @param n
     * @Title: intTohex
     * @Description: int型转换成16进制
     * @return: String
     */
    public static String int2HexString(int n) {
        StringBuffer s = new StringBuffer();
        String a;
        char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        while (n != 0) {
            s = s.append(b[n % 16]);
            n = n / 16;
        }
        a = s.reverse().toString();
        if ("".equals(a)) {
            a = "00";
        }
        if (a.length() == 1) {
            a = "0" + a;
        }
        return a;
    }


    /**
     * 字符串转16进制字符串
     *
     * @param strPart
     * @return
     */
    public static String string2HexString(String strPart) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < strPart.length(); i++) {
            int ch = (int) strPart.charAt(i);
            String strHex = Integer.toHexString(ch);
            hexString.append(strHex);
        }
        return hexString.toString();
    }

    /**
     * 十六进制转字节数组
     *
     * @param src
     * @return
     */
    public static byte[] hexString2ByteArray(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = Integer
                    .valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }


    /**
     * Hex字符串转byte
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hex2Byte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }


    /**
     * hex字符串转byte数组
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    public static byte[] hex2ByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hex2Byte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }


    /**
     * 十六进制转十进制
     *
     * @param content 十六进制字符串
     * @return 十进制
     */
    public static int covert(String content) {
        content = content.toUpperCase();
        int number = 0;
        String[] HighLetter = {"A", "B", "C", "D", "E", "F"};
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i <= 9; i++) {
            map.put(i + "", i);
        }
        for (int j = 10; j < HighLetter.length + 10; j++) {
            map.put(HighLetter[j - 10], j);
        }
        String[] str = new String[content.length()];
        for (int i = 0; i < str.length; i++) {
            str[i] = content.substring(i, i + 1);
        }
        for (int i = 0; i < str.length; i++) {
            number += map.get(str[i]) * Math.pow(16, str.length - 1 - i);
        }
        return number;
    }

    public static String reverseCRC(String crc) {
        StringBuilder crcBuilder = new StringBuilder(crc);
        while (crcBuilder.length() < 4) crcBuilder.insert(0, "0");
        crc = crcBuilder.toString();
        crc = crc.substring(2, 4) + crc.substring(0, 2);
        return crc.toUpperCase();
    }

    public static String crcReversed(String hex) {
        return reverseCRC(crc(hex));
    }

    public static String crcReversed(byte[] bytes) {
        return reverseCRC(crc(bytes));
    }

    public static String crc(String hex) {
        return crc(hex2ByteArray(hex));
    }

    public static String crc(byte[] bytes) {
        int CRC = 0x0000FFFF;
        final int POLYNOMIAL = 0x0000A001;
        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000FF);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        return Integer.toHexString(CRC).toUpperCase();
    }

}
