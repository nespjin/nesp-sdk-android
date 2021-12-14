package com.nesp.sdk.android.hardware;


import com.nesp.sdk.java.util.ArrayUtil;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/8/20 14:43
 **/
public final class ByteUtil {
    private static final String TAG = "ByteUtil";

    private ByteUtil() {
        //no instance
    }

    public static int[] byteArray2UByteArray(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
        int[] rUnByteArray = new int[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            rUnByteArray[i] = byte2UByte(bytes[i]);
        }
        return rUnByteArray;
    }

    /**
     * Signed byte to unsigned
     *
     * @param b signed byte
     * @return unsigned byte
     */
    public static int byte2UByte(byte b) {
        return b & 0XFF;
    }

    public static byte[] uByteArray2ByteArray(Integer[] uByteArray) {
        if (ArrayUtil.isEmpty(uByteArray)) return null;
        byte[] rByteArray = new byte[uByteArray.length];
        for (int i = 0; i < uByteArray.length; i++) {
            rByteArray[i] = uByte2Byte(uByteArray[i]);
        }
        return rByteArray;
    }

    /**
     * i in [0,256]
     *
     * @param i unByte
     * @return
     */
    public static byte uByte2Byte(Integer i) {
        return i.byteValue();
    }
}
