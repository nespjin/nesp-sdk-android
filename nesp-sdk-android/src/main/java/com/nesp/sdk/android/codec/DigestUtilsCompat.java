/*
 *
 *   Copyright (c) 2021  NESP Technology Corporation. All rights reserved.
 *
 *   This program is not free software; you can't redistribute it and/or modify it
 *   without the permit of team manager.
 *
 *   Unless required by applicable law or agreed to in writing.
 *
 *   If you have any questions or if you find a bug,
 *   please contact the author by email or ask for Issues.
 *
 *   Author:JinZhaolu <1756404649@qq.com>
 */

package com.nesp.sdk.android.codec;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RequiresApi;

import com.nesp.sdk.android.utils.Log;
import com.nesp.sdk.java.codec.binary.Hex;
import com.nesp.sdk.java.util.OnResultListener;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Operations to simplify common {@link MessageDigest} tasks.
 * This class is immutable and thread-safe.
 * However the MessageDigest instances it creates generally won't be.
 * <p>
 * The {@link MessageDigestAlgorithms} class provides constants for standard
 * digest algorithms that can be used with the {@link #getDigest(String)} method
 * and other methods that require the Digest algorithm name.
 * <p>
 * Note: the class has short-hand methods for all the algorithms present as standard in Java 6.
 * This approach requires lots of methods for each algorithm, and quickly becomes unwieldy.
 * The following code works with all algorithms:
 * <pre>
 * import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_224;
 * ...
 * byte [] digest = new DigestUtils(SHA_224).digest(dataToDigest);
 * String hdigest = new DigestUtils(SHA_224).digestAsHex(new File("pom.xml"));
 * </pre>
 *
 * @see MessageDigestAlgorithms
 */
public class DigestUtilsCompat {

    private static final String TAG = "DigestUtilsCompat";

    private static final int STREAM_BUFFER_LENGTH = 1024;

    /**
     * Reads through a byte array and returns the digest for the data. Provided for symmetry with other methods.
     *
     * @param messageDigest The MessageDigest to use (e.g. MD5)
     * @param data          Data to digest
     * @return the digest
     * @since 1.11
     */
    public static byte[] digest(final MessageDigest messageDigest, final byte[] data) {
        return messageDigest.digest(data);
    }

    /**
     * Reads through a ByteBuffer and returns the digest for the data
     *
     * @param messageDigest The MessageDigest to use (e.g. MD5)
     * @param data          Data to digest
     * @return the digest
     * @since 1.11
     */
    public static byte[] digest(final MessageDigest messageDigest, final ByteBuffer data) {
        messageDigest.update(data);
        return messageDigest.digest();
    }

    /**
     * Reads through a File and returns the digest for the data
     *
     * @param messageDigest The MessageDigest to use (e.g. MD5)
     * @param data          Data to digest
     * @return the digest
     * @throws IOException On error reading from the stream
     * @since 1.11
     */
    public static byte[] digest(final MessageDigest messageDigest, final File data) throws IOException {
        return updateDigest(messageDigest, data).digest();
    }

    /**
     * Reads through an InputStream and returns the digest for the data
     *
     * @param messageDigest The MessageDigest to use (e.g. MD5)
     * @param data          Data to digest
     * @return the digest
     * @throws IOException On error reading from the stream
     * @since 1.11 (was private)
     */
    public static byte[] digest(final MessageDigest messageDigest, final InputStream data) throws IOException {
        return updateDigest(messageDigest, data).digest();
    }

    /**
     * Reads through a File and returns the digest for the data
     *
     * @param messageDigest The MessageDigest to use (e.g. MD5)
     * @param data          Data to digest
     * @param options       options How to open the file
     * @return the digest
     * @throws IOException On error reading from the stream
     * @since 1.14
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static byte[] digest(final MessageDigest messageDigest, final Path data, final OpenOption... options)
            throws IOException {
        return updateDigest(messageDigest, data, options).digest();
    }

    /**
     * Reads through a RandomAccessFile using non-blocking-io (NIO) and returns the digest for the data
     *
     * @param messageDigest The MessageDigest to use (e.g. MD5)
     * @param data          Data to digest
     * @return the digest
     * @throws IOException On error reading from the stream
     * @since 1.14
     */
    public static byte[] digest(final MessageDigest messageDigest, final RandomAccessFile data) throws IOException {
        return updateDigest(messageDigest, data).digest();
    }

    /**
     * Returns a {@code MessageDigest} for the given {@code algorithm}.
     *
     * @param algorithm the name of the algorithm requested. See <a
     *                  href="http://docs.oracle.com/javase/6/docs/technotes/guides/security/crypto/CryptoSpec.html#AppA"
     *                  >Appendix A in the Java Cryptography Architecture Reference Guide</a> for information about standard
     *                  algorithm names.
     * @return A digest instance.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught.
     * @see MessageDigest#getInstance(String)
     */
    public static MessageDigest getDigest(final String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Returns a {@code MessageDigest} for the given {@code algorithm} or a default if there is a problem
     * getting the algorithm.
     *
     * @param algorithm            the name of the algorithm requested. See
     *                             <a href="http://docs.oracle.com/javase/6/docs/technotes/guides/security/crypto/CryptoSpec.html#AppA" >
     *                             Appendix A in the Java Cryptography Architecture Reference Guide</a> for information about standard
     *                             algorithm names.
     * @param defaultMessageDigest The default MessageDigest.
     * @return A digest instance.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught.
     * @see MessageDigest#getInstance(String)
     * @since 1.11
     */
    public static MessageDigest getDigest(final String algorithm, final MessageDigest defaultMessageDigest) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (final Exception e) {
            return defaultMessageDigest;
        }
    }

    /**
     * Returns an MD2 MessageDigest.
     *
     * @return An MD2 digest instance.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught, which should never happen because MD2 is a
     *                                  built-in algorithm
     * @see MessageDigestAlgorithms#MD2
     * @since 1.7
     */
    public static MessageDigest getMd2Digest() {
        return getDigest(MessageDigestAlgorithms.MD2);
    }

    /**
     * Returns an MD5 MessageDigest.
     *
     * @return An MD5 digest instance.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught, which should never happen because MD5 is a
     *                                  built-in algorithm
     * @see MessageDigestAlgorithms#MD5
     */
    public static MessageDigest getMd5Digest() {
        return getDigest(MessageDigestAlgorithms.MD5);
    }

    /**
     * Returns an SHA-1 digest.
     *
     * @return An SHA-1 digest instance.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught, which should never happen because SHA-1 is a
     *                                  built-in algorithm
     * @see MessageDigestAlgorithms#SHA_1
     * @since 1.7
     */
    public static MessageDigest getSha1Digest() {
        return getDigest(MessageDigestAlgorithms.SHA_1);
    }

    /**
     * Returns an SHA-256 digest.
     *
     * @return An SHA-256 digest instance.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught, which should never happen because SHA-256 is a
     *                                  built-in algorithm
     * @see MessageDigestAlgorithms#SHA_256
     */
    public static MessageDigest getSha256Digest() {
        return getDigest(MessageDigestAlgorithms.SHA_256);
    }

    /**
     * Returns an SHA3-224 digest.
     *
     * @return An SHA3-224 digest instance.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught, which should not happen on
     *                                  Oracle Java 9 andgreater.
     * @see MessageDigestAlgorithms#SHA3_224
     * @since 1.12
     */
    public static MessageDigest getSha3_224Digest() {
        return getDigest(MessageDigestAlgorithms.SHA3_224);
    }

    /**
     * Returns an SHA3-256 digest.
     *
     * @return An SHA3-256 digest instance.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught, which should not happen on
     *                                  Oracle Java 9 and greater.
     * @see MessageDigestAlgorithms#SHA3_256
     * @since 1.12
     */
    public static MessageDigest getSha3_256Digest() {
        return getDigest(MessageDigestAlgorithms.SHA3_256);
    }

    /**
     * Returns an SHA3-384 digest.
     *
     * @return An SHA3-384 digest instance.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught, which should not happen on
     *                                  Oracle Java 9 and greater.
     * @see MessageDigestAlgorithms#SHA3_384
     * @since 1.12
     */
    public static MessageDigest getSha3_384Digest() {
        return getDigest(MessageDigestAlgorithms.SHA3_384);
    }

    /**
     * Returns an SHA3-512 digest.
     *
     * @return An SHA3-512 digest instance.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught, which should not happen
     *                                  on Oracle Java 9 and greater.
     * @see MessageDigestAlgorithms#SHA3_512
     * @since 1.12
     */
    public static MessageDigest getSha3_512Digest() {
        return getDigest(MessageDigestAlgorithms.SHA3_512);
    }

    /**
     * Returns an SHA-384 digest.
     *
     * @return An SHA-384 digest instance.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught, which should never happen
     *                                  because SHA-384 is a built-in algorithm
     * @see MessageDigestAlgorithms#SHA_384
     */
    public static MessageDigest getSha384Digest() {
        return getDigest(MessageDigestAlgorithms.SHA_384);
    }

    /**
     * Returns an SHA-512/224 digest.
     *
     * @return An SHA-512/224 digest instance.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught.
     * @see MessageDigestAlgorithms#SHA_512_224
     */
    public static MessageDigest getSha512_224Digest() {
        return getDigest(MessageDigestAlgorithms.SHA_512_224);
    }

    /**
     * Returns an SHA-512/256 digest.
     *
     * @return An SHA-512/256 digest instance.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught.
     * @see MessageDigestAlgorithms#SHA_512_224
     */
    public static MessageDigest getSha512_256Digest() {
        return getDigest(MessageDigestAlgorithms.SHA_512_256);
    }

    /**
     * Returns an SHA-512 digest.
     *
     * @return An SHA-512 digest instance.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught, which should never happen
     *                                  because SHA-512 is a built-in algorithm
     * @see MessageDigestAlgorithms#SHA_512
     */
    public static MessageDigest getSha512Digest() {
        return getDigest(MessageDigestAlgorithms.SHA_512);
    }

    /**
     * Returns an SHA-1 digest.
     *
     * @return An SHA-1 digest instance.
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught
     * @deprecated (1.11) Use {@link #getSha1Digest()}
     */
    @Deprecated
    public static MessageDigest getShaDigest() {
        return getSha1Digest();
    }

    /**
     * Test whether the algorithm is supported.
     *
     * @param messageDigestAlgorithm the algorithm name
     * @return {@code true} if the algorithm can be found
     * @since 1.11
     */
    public static boolean isAvailable(final String messageDigestAlgorithm) {
        return getDigest(messageDigestAlgorithm, null) != null;
    }

    /**
     * Calculates the MD2 digest and returns the value as a 16 element {@code byte[]}.
     *
     * @param data Data to digest
     * @return MD2 digest
     * @since 1.7
     */
    public static byte[] md2(final byte[] data) {
        return getMd2Digest().digest(data);
    }

    /**
     * Calculates the MD2 digest and returns the value as a 16 element {@code byte[]}.
     *
     * @param data Data to digest
     * @return MD2 digest
     * @throws IOException On error reading from the stream
     * @since 1.7
     */
    public static byte[] md2(final InputStream data) throws IOException {
        return digest(getMd2Digest(), data);
    }

    /**
     * Calculates the MD2 digest and returns the value as a 16 element {@code byte[]}.
     *
     * @param data Data to digest; converted to bytes using {@link StringUtils#getBytesUtf8(String)}
     * @return MD2 digest
     * @since 1.7
     */
    public static byte[] md2(final String data) {
        return md2(StringUtils.getBytesUtf8(data));
    }

    /**
     * Calculates the MD2 digest and returns the value as a 32 character hex string.
     *
     * @param data Data to digest
     * @return MD2 digest as a hex string
     * @since 1.7
     */
    public static String md2Hex(final byte[] data) {
        return Hex.encodeHexString(md2(data));
    }

    /**
     * Calculates the MD2 digest and returns the value as a 32 character hex string.
     *
     * @param data Data to digest
     * @return MD2 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.7
     */
    public static String md2Hex(final InputStream data) throws IOException {
        return Hex.encodeHexString(md2(data));
    }

    /**
     * Calculates the MD2 digest and returns the value as a 32 character hex string.
     *
     * @param data Data to digest
     * @return MD2 digest as a hex string
     * @since 1.7
     */
    public static String md2Hex(final String data) {
        return Hex.encodeHexString(md2(data));
    }

    /**
     * Calculates the MD5 digest and returns the value as a 16 element {@code byte[]}.
     *
     * @param data Data to digest
     * @return MD5 digest
     */
    public static byte[] md5(final byte[] data) {
        return getMd5Digest().digest(data);
    }

    /**
     * Calculates the MD5 digest and returns the value as a 16 element {@code byte[]}.
     *
     * @param data Data to digest
     * @return MD5 digest
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static byte[] md5(final InputStream data) throws IOException {
        return digest(getMd5Digest(), data);
    }

    /**
     * Calculates the MD5 digest and returns the value as a 16 element {@code byte[]}.
     *
     * @param data Data to digest; converted to bytes using {@link StringUtils#getBytesUtf8(String)}
     * @return MD5 digest
     */
    public static byte[] md5(final String data) {
        return md5(StringUtils.getBytesUtf8(data));
    }

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex string.
     *
     * @param data Data to digest
     * @return MD5 digest as a hex string
     */
    public static String md5Hex(final byte[] data) {
        return Hex.encodeHexString(md5(data));
    }

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex string.
     *
     * @param data Data to digest
     * @return MD5 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static String md5Hex(final InputStream data) throws IOException {
        return Hex.encodeHexString(md5(data));
    }

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex string.
     *
     * @param data Data to digest
     * @return MD5 digest as a hex string
     */
    public static String md5Hex(final String data) {
        return Hex.encodeHexString(md5(data));
    }

    private static final long MAX_LENGTH_FOR_COMPUTE = 512 * 1024 * 1024;
    private static final int COMPUTE_BUFFER_SIZE = 1 << 24;

    public static ExecutorService md5HexAsync(final InputStream inputStream, final long length, OnResultListener<String> onResultListener) {
        final ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
        long lengthRemaining = length;
        long seek = 0;
        List<String> md5HexList = new ArrayList<>();
        int chunkCount = (int) Math.ceil(length / (MAX_LENGTH_FOR_COMPUTE * 1F));
        for (int chunkIndex = 0; chunkIndex < chunkCount; chunkIndex++) {
            long readLength;
            if (chunkIndex != chunkCount - 1) {
                readLength = MAX_LENGTH_FOR_COMPUTE;
            } else {
                readLength = lengthRemaining;
            }

            cacheThreadPool.submit(new ComputeMd5HexThread(md5HexList, inputStream, seek,
                    chunkIndex, chunkCount, readLength, onResultListener));

            lengthRemaining -= readLength;
            seek += readLength;
        }
        return cacheThreadPool;
    }

    private static final class ComputeMd5HexThread extends Thread {

        private final List<String> mMd5HexList;
        private final InputStream mInputStream;
        private final long mSeek;
        private final int mChunkIndex;
        private final int mChunkCount;
        private final long mReadLength;
        private final OnResultListener<String> mOnResultListener;
        private final Handler mMainHandler = new Handler(Looper.getMainLooper());

        private ComputeMd5HexThread(final List<String> md5HexList,
                                    final InputStream inputStream,
                                    final long seek,
                                    final int chunkIndex,
                                    final int chunkCount, final long readLength,
                                    final OnResultListener<String> onResultListener) {
            mMd5HexList = md5HexList;
            mInputStream = inputStream;
            mSeek = seek;
            mChunkIndex = chunkIndex;
            mChunkCount = chunkCount;
            mReadLength = readLength;
            mOnResultListener = onResultListener;
        }

        @Override
        public void run() {
            Log.e(TAG, "run:" +
                    " mChunkIndex = " + mChunkIndex + "\n" +
                    " mChunkCount = " + mChunkCount + "\n"
            );
            try {
                final byte[] buffer = new byte[COMPUTE_BUFFER_SIZE];
                final long skipLength = mInputStream.skip(mSeek);
                int readLengthCurrent = 0;
                StringBuilder md5HexChunkStringBuilder = new StringBuilder();
                while ((readLengthCurrent += mInputStream.read(buffer)) <= mReadLength) {
                    md5HexChunkStringBuilder.append(md5Hex(buffer));
                }
                mMd5HexList.add(mChunkIndex, md5HexChunkStringBuilder.toString());

                Log.e(TAG, "run finish:" +
                        " mChunkIndex = " + mChunkIndex + "\n" +
                        " mChunkCount = " + mChunkCount + "\n"
                );
                if (mChunkIndex + 1 == mChunkCount && mOnResultListener != null) {
                    mMainHandler.post(() -> {
                        StringBuilder md5HexStringBuilder = new StringBuilder();
                        for (final String s : mMd5HexList) md5HexStringBuilder.append(s);
                        mOnResultListener.onResult(md5HexStringBuilder.toString());
                    });
                }
            } catch (IOException e) {
                Log.e(TAG, "run: ", e);
                e.printStackTrace();
            }
        }
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA-1 digest
     * @deprecated (1.11) Use {@link #sha1(byte[])}
     */
    @Deprecated
    public static byte[] sha(final byte[] data) {
        return sha1(data);
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA-1 digest
     * @throws IOException On error reading from the stream
     * @since 1.4
     * @deprecated (1.11) Use {@link #sha1(InputStream)}
     */
    @Deprecated
    public static byte[] sha(final InputStream data) throws IOException {
        return sha1(data);
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA-1 digest
     * @deprecated (1.11) Use {@link #sha1(String)}
     */
    @Deprecated
    public static byte[] sha(final String data) {
        return sha1(data);
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA-1 digest
     * @since 1.7
     */
    public static byte[] sha1(final byte[] data) {
        return getSha1Digest().digest(data);
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA-1 digest
     * @throws IOException On error reading from the stream
     * @since 1.7
     */
    public static byte[] sha1(final InputStream data) throws IOException {
        return digest(getSha1Digest(), data);
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest; converted to bytes using {@link StringUtils#getBytesUtf8(String)}
     * @return SHA-1 digest
     */
    public static byte[] sha1(final String data) {
        return sha1(StringUtils.getBytesUtf8(data));
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-1 digest as a hex string
     * @since 1.7
     */
    public static String sha1Hex(final byte[] data) {
        return Hex.encodeHexString(sha1(data));
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-1 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.7
     */
    public static String sha1Hex(final InputStream data) throws IOException {
        return Hex.encodeHexString(sha1(data));
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-1 digest as a hex string
     * @since 1.7
     */
    public static String sha1Hex(final String data) {
        return Hex.encodeHexString(sha1(data));
    }

    /**
     * Calculates the SHA-256 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA-256 digest
     * @since 1.4
     */
    public static byte[] sha256(final byte[] data) {
        return getSha256Digest().digest(data);
    }

    /**
     * Calculates the SHA-256 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA-256 digest
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static byte[] sha256(final InputStream data) throws IOException {
        return digest(getSha256Digest(), data);
    }

    /**
     * Calculates the SHA-256 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest; converted to bytes using {@link StringUtils#getBytesUtf8(String)}
     * @return SHA-256 digest
     * @since 1.4
     */
    public static byte[] sha256(final String data) {
        return sha256(StringUtils.getBytesUtf8(data));
    }

    /**
     * Calculates the SHA-256 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-256 digest as a hex string
     * @since 1.4
     */
    public static String sha256Hex(final byte[] data) {
        return Hex.encodeHexString(sha256(data));
    }

    /**
     * Calculates the SHA-256 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-256 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static String sha256Hex(final InputStream data) throws IOException {
        return Hex.encodeHexString(sha256(data));
    }

    /**
     * Calculates the SHA-256 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-256 digest as a hex string
     * @since 1.4
     */
    public static String sha256Hex(final String data) {
        return Hex.encodeHexString(sha256(data));
    }

    /**
     * Calculates the SHA3-224 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA3-224 digest
     * @since 1.12
     */
    public static byte[] sha3_224(final byte[] data) {
        return getSha3_224Digest().digest(data);
    }

    /**
     * Calculates the SHA3-224 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA3-224 digest
     * @throws IOException On error reading from the stream
     * @since 1.12
     */
    public static byte[] sha3_224(final InputStream data) throws IOException {
        return digest(getSha3_224Digest(), data);
    }

    /**
     * Calculates the SHA3-224 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest; converted to bytes using {@link StringUtils#getBytesUtf8(String)}
     * @return SHA3-224 digest
     * @since 1.12
     */
    public static byte[] sha3_224(final String data) {
        return sha3_224(StringUtils.getBytesUtf8(data));
    }

    /**
     * Calculates the SHA3-224 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA3-224 digest as a hex string
     * @since 1.12
     */
    public static String sha3_224Hex(final byte[] data) {
        return Hex.encodeHexString(sha3_224(data));
    }

    /**
     * Calculates the SHA3-224 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA3-224 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.12
     */
    public static String sha3_224Hex(final InputStream data) throws IOException {
        return Hex.encodeHexString(sha3_224(data));
    }

    /**
     * Calculates the SHA3-224 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA3-224 digest as a hex string
     * @since 1.12
     */
    public static String sha3_224Hex(final String data) {
        return Hex.encodeHexString(sha3_224(data));
    }

    /**
     * Calculates the SHA3-256 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA3-256 digest
     * @since 1.12
     */
    public static byte[] sha3_256(final byte[] data) {
        return getSha3_256Digest().digest(data);
    }

    /**
     * Calculates the SHA3-256 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA3-256 digest
     * @throws IOException On error reading from the stream
     * @since 1.12
     */
    public static byte[] sha3_256(final InputStream data) throws IOException {
        return digest(getSha3_256Digest(), data);
    }

    /**
     * Calculates the SHA3-256 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest; converted to bytes using {@link StringUtils#getBytesUtf8(String)}
     * @return SHA3-256 digest
     * @since 1.12
     */
    public static byte[] sha3_256(final String data) {
        return sha3_256(StringUtils.getBytesUtf8(data));
    }

    /**
     * Calculates the SHA3-256 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA3-256 digest as a hex string
     * @since 1.12
     */
    public static String sha3_256Hex(final byte[] data) {
        return Hex.encodeHexString(sha3_256(data));
    }

    /**
     * Calculates the SHA3-256 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA3-256 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.12
     */
    public static String sha3_256Hex(final InputStream data) throws IOException {
        return Hex.encodeHexString(sha3_256(data));
    }

    /**
     * Calculates the SHA3-256 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA3-256 digest as a hex string
     * @since 1.12
     */
    public static String sha3_256Hex(final String data) {
        return Hex.encodeHexString(sha3_256(data));
    }

    /**
     * Calculates the SHA3-384 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA3-384 digest
     * @since 1.12
     */
    public static byte[] sha3_384(final byte[] data) {
        return getSha3_384Digest().digest(data);
    }

    /**
     * Calculates the SHA3-384 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA3-384 digest
     * @throws IOException On error reading from the stream
     * @since 1.12
     */
    public static byte[] sha3_384(final InputStream data) throws IOException {
        return digest(getSha3_384Digest(), data);
    }

    /**
     * Calculates the SHA3-384 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest; converted to bytes using {@link StringUtils#getBytesUtf8(String)}
     * @return SHA3-384 digest
     * @since 1.12
     */
    public static byte[] sha3_384(final String data) {
        return sha3_384(StringUtils.getBytesUtf8(data));
    }

    /**
     * Calculates the SHA3-384 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA3-384 digest as a hex string
     * @since 1.12
     */
    public static String sha3_384Hex(final byte[] data) {
        return Hex.encodeHexString(sha3_384(data));
    }

    /**
     * Calculates the SHA3-384 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA3-384 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.12
     */
    public static String sha3_384Hex(final InputStream data) throws IOException {
        return Hex.encodeHexString(sha3_384(data));
    }

    /**
     * Calculates the SHA3-384 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA3-384 digest as a hex string
     * @since 1.12
     */
    public static String sha3_384Hex(final String data) {
        return Hex.encodeHexString(sha3_384(data));
    }

    /**
     * Calculates the SHA3-512 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA3-512 digest
     * @since 1.12
     */
    public static byte[] sha3_512(final byte[] data) {
        return getSha3_512Digest().digest(data);
    }

    /**
     * Calculates the SHA3-512 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA3-512 digest
     * @throws IOException On error reading from the stream
     * @since 1.12
     */
    public static byte[] sha3_512(final InputStream data) throws IOException {
        return digest(getSha3_512Digest(), data);
    }

    /**
     * Calculates the SHA3-512 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest; converted to bytes using {@link StringUtils#getBytesUtf8(String)}
     * @return SHA3-512 digest
     * @since 1.12
     */
    public static byte[] sha3_512(final String data) {
        return sha3_512(StringUtils.getBytesUtf8(data));
    }

    /**
     * Calculates the SHA3-512 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA3-512 digest as a hex string
     * @since 1.12
     */
    public static String sha3_512Hex(final byte[] data) {
        return Hex.encodeHexString(sha3_512(data));
    }

    /**
     * Calculates the SHA3-512 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA3-512 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.12
     */
    public static String sha3_512Hex(final InputStream data) throws IOException {
        return Hex.encodeHexString(sha3_512(data));
    }

    /**
     * Calculates the SHA3-512 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA3-512 digest as a hex string
     * @since 1.12
     */
    public static String sha3_512Hex(final String data) {
        return Hex.encodeHexString(sha3_512(data));
    }

    /**
     * Calculates the SHA-384 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA-384 digest
     * @since 1.4
     */
    public static byte[] sha384(final byte[] data) {
        return getSha384Digest().digest(data);
    }

    /**
     * Calculates the SHA-384 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA-384 digest
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static byte[] sha384(final InputStream data) throws IOException {
        return digest(getSha384Digest(), data);
    }

    /**
     * Calculates the SHA-384 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest; converted to bytes using {@link StringUtils#getBytesUtf8(String)}
     * @return SHA-384 digest
     * @since 1.4
     */
    public static byte[] sha384(final String data) {
        return sha384(StringUtils.getBytesUtf8(data));
    }

    /**
     * Calculates the SHA-384 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-384 digest as a hex string
     * @since 1.4
     */
    public static String sha384Hex(final byte[] data) {
        return Hex.encodeHexString(sha384(data));
    }

    /**
     * Calculates the SHA-384 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-384 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static String sha384Hex(final InputStream data) throws IOException {
        return Hex.encodeHexString(sha384(data));
    }

    /**
     * Calculates the SHA-384 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-384 digest as a hex string
     * @since 1.4
     */
    public static String sha384Hex(final String data) {
        return Hex.encodeHexString(sha384(data));
    }

    /**
     * Calculates the SHA-512 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA-512 digest
     * @since 1.4
     */
    public static byte[] sha512(final byte[] data) {
        return getSha512Digest().digest(data);
    }

    /**
     * Calculates the SHA-512 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA-512 digest
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static byte[] sha512(final InputStream data) throws IOException {
        return digest(getSha512Digest(), data);
    }

    /**
     * Calculates the SHA-512 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest; converted to bytes using {@link StringUtils#getBytesUtf8(String)}
     * @return SHA-512 digest
     * @since 1.4
     */
    public static byte[] sha512(final String data) {
        return sha512(StringUtils.getBytesUtf8(data));
    }

    /**
     * Calculates the SHA-512/224 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA-512/224 digest
     * @since 1.14
     */
    public static byte[] sha512_224(final byte[] data) {
        return getSha512_224Digest().digest(data);
    }

    /**
     * Calculates the SHA-512/224 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA-512/224 digest
     * @throws IOException On error reading from the stream
     * @since 1.14
     */
    public static byte[] sha512_224(final InputStream data) throws IOException {
        return digest(getSha512_224Digest(), data);
    }

    /**
     * Calculates the SHA-512/224 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest; converted to bytes using {@link StringUtils#getBytesUtf8(String)}
     * @return SHA-512/224 digest
     * @since 1.14
     */
    public static byte[] sha512_224(final String data) {
        return sha512_224(StringUtils.getBytesUtf8(data));
    }

    /**
     * Calculates the SHA-512/224 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-512/224 digest as a hex string
     * @since 1.14
     */
    public static String sha512_224Hex(final byte[] data) {
        return Hex.encodeHexString(sha512_224(data));
    }

    /**
     * Calculates the SHA-512/224 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-512/224 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.14
     */
    public static String sha512_224Hex(final InputStream data) throws IOException {
        return Hex.encodeHexString(sha512_224(data));
    }

    /**
     * Calculates the SHA-512/224 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-512/224 digest as a hex string
     * @since 1.14
     */
    public static String sha512_224Hex(final String data) {
        return Hex.encodeHexString(sha512_224(data));
    }

    /**
     * Calculates the SHA-512/256 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA-512/256 digest
     * @since 1.14
     */
    public static byte[] sha512_256(final byte[] data) {
        return getSha512_256Digest().digest(data);
    }

    /**
     * Calculates the SHA-512/256 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest
     * @return SHA-512/256 digest
     * @throws IOException On error reading from the stream
     * @since 1.14
     */
    public static byte[] sha512_256(final InputStream data) throws IOException {
        return digest(getSha512_256Digest(), data);
    }

    /**
     * Calculates the SHA-512/256 digest and returns the value as a {@code byte[]}.
     *
     * @param data Data to digest; converted to bytes using {@link StringUtils#getBytesUtf8(String)}
     * @return SHA-512/224 digest
     * @since 1.14
     */
    public static byte[] sha512_256(final String data) {
        return sha512_256(StringUtils.getBytesUtf8(data));
    }

    /**
     * Calculates the SHA-512/256 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-512/256 digest as a hex string
     * @since 1.14
     */
    public static String sha512_256Hex(final byte[] data) {
        return Hex.encodeHexString(sha512_256(data));
    }

    /**
     * Calculates the SHA-512/256 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-512/256 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.14
     */
    public static String sha512_256Hex(final InputStream data) throws IOException {
        return Hex.encodeHexString(sha512_256(data));
    }

    /**
     * Calculates the SHA-512/256 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-512/256 digest as a hex string
     * @since 1.14
     */
    public static String sha512_256Hex(final String data) {
        return Hex.encodeHexString(sha512_256(data));
    }

    /**
     * Calculates the SHA-512 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-512 digest as a hex string
     * @since 1.4
     */
    public static String sha512Hex(final byte[] data) {
        return Hex.encodeHexString(sha512(data));
    }

    /**
     * Calculates the SHA-512 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-512 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static String sha512Hex(final InputStream data) throws IOException {
        return Hex.encodeHexString(sha512(data));
    }

    /**
     * Calculates the SHA-512 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-512 digest as a hex string
     * @since 1.4
     */
    public static String sha512Hex(final String data) {
        return Hex.encodeHexString(sha512(data));
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-1 digest as a hex string
     * @deprecated (1.11) Use {@link #sha1Hex(byte[])}
     */
    @Deprecated
    public static String shaHex(final byte[] data) {
        return sha1Hex(data);
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-1 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.4
     * @deprecated (1.11) Use {@link #sha1Hex(InputStream)}
     */
    @Deprecated
    public static String shaHex(final InputStream data) throws IOException {
        return sha1Hex(data);
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a hex string.
     *
     * @param data Data to digest
     * @return SHA-1 digest as a hex string
     * @deprecated (1.11) Use {@link #sha1Hex(String)}
     */
    @Deprecated
    public static String shaHex(final String data) {
        return sha1Hex(data);
    }

    /**
     * Updates the given {@link MessageDigest}.
     *
     * @param messageDigest the {@link MessageDigest} to update
     * @param valueToDigest the value to update the {@link MessageDigest} with
     * @return the updated {@link MessageDigest}
     * @since 1.7
     */
    public static MessageDigest updateDigest(final MessageDigest messageDigest, final byte[] valueToDigest) {
        messageDigest.update(valueToDigest);
        return messageDigest;
    }

    /**
     * Updates the given {@link MessageDigest}.
     *
     * @param messageDigest the {@link MessageDigest} to update
     * @param valueToDigest the value to update the {@link MessageDigest} with
     * @return the updated {@link MessageDigest}
     * @since 1.11
     */
    public static MessageDigest updateDigest(final MessageDigest messageDigest, final ByteBuffer valueToDigest) {
        messageDigest.update(valueToDigest);
        return messageDigest;
    }

    /**
     * Reads through a File and updates the digest for the data
     *
     * @param digest The MessageDigest to use (e.g. MD5)
     * @param data   Data to digest
     * @return the digest
     * @throws IOException On error reading from the stream
     * @since 1.11
     */
    public static MessageDigest updateDigest(final MessageDigest digest, final File data) throws IOException {
        try (final BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(data))) {
            return updateDigest(digest, inputStream);
        }
    }

    /**
     * Reads through a RandomAccessFile and updates the digest for the data using non-blocking-io (NIO).
     * <p>
     * TODO Decide if this should be public.
     *
     * @param digest The MessageDigest to use (e.g. MD5)
     * @param data   Data to digest
     * @return the digest
     * @throws IOException On error reading from the stream
     * @since 1.14
     */
    private static MessageDigest updateDigest(final MessageDigest digest, final FileChannel data) throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocate(STREAM_BUFFER_LENGTH);
        while (data.read(buffer) > 0) {
            buffer.flip();
            digest.update(buffer);
            buffer.clear();
        }
        return digest;
    }

    /**
     * Reads through an InputStream and updates the digest for the data
     *
     * @param digest      The MessageDigest to use (e.g. MD5)
     * @param inputStream Data to digest
     * @return the digest
     * @throws IOException On error reading from the stream
     * @since 1.8
     */
    public static MessageDigest updateDigest(final MessageDigest digest, final InputStream inputStream)
            throws IOException {
        final byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
        int read = inputStream.read(buffer, 0, STREAM_BUFFER_LENGTH);

        while (read > -1) {
            digest.update(buffer, 0, read);
            read = inputStream.read(buffer, 0, STREAM_BUFFER_LENGTH);
        }

        return digest;
    }

    /**
     * Reads through a Path and updates the digest for the data
     *
     * @param digest  The MessageDigest to use (e.g. MD5)
     * @param path    Data to digest
     * @param options options How to open the file
     * @return the digest
     * @throws IOException On error reading from the stream
     * @since 1.14
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static MessageDigest updateDigest(final MessageDigest digest, final Path path, final OpenOption... options)
            throws IOException {
        try (final BufferedInputStream inputStream = new BufferedInputStream(Files.newInputStream(path, options))) {
            return updateDigest(digest, inputStream);
        }
    }

    /**
     * Reads through a RandomAccessFile and updates the digest for the data using non-blocking-io (NIO)
     *
     * @param digest The MessageDigest to use (e.g. MD5)
     * @param data   Data to digest
     * @return the digest
     * @throws IOException On error reading from the stream
     * @since 1.14
     */
    public static MessageDigest updateDigest(final MessageDigest digest, final RandomAccessFile data)
            throws IOException {
        return updateDigest(digest, data.getChannel());
    }

    /**
     * Updates the given {@link MessageDigest} from a String (converted to bytes using UTF-8).
     * <p>
     * To update the digest using a different charset for the conversion,
     * convert the String to a byte array using
     * {@link String#getBytes(java.nio.charset.Charset)} and pass that
     * to the {@link DigestUtilsCompat#updateDigest(MessageDigest, byte[])} method
     *
     * @param messageDigest the {@link MessageDigest} to update
     * @param valueToDigest the value to update the {@link MessageDigest} with;
     *                      converted to bytes using {@link StringUtils#getBytesUtf8(String)}
     * @return the updated {@link MessageDigest}
     * @since 1.7
     */
    public static MessageDigest updateDigest(final MessageDigest messageDigest, final String valueToDigest) {
        messageDigest.update(StringUtils.getBytesUtf8(valueToDigest));
        return messageDigest;
    }

    private final MessageDigest messageDigest;

    /**
     * Preserves binary compatibility only.
     * As for previous versions does not provide useful behavior
     *
     * @deprecated since 1.11; only useful to preserve binary compatibility
     */
    @Deprecated
    public DigestUtilsCompat() {
        this.messageDigest = null;
    }

    /**
     * Creates an instance using the provided {@link MessageDigest} parameter.
     * <p>
     * This can then be used to create digests using methods such as
     * {@link #digest(byte[])} and {@link #digestAsHex(File)}.
     *
     * @param digest the {@link MessageDigest} to use
     * @since 1.11
     */
    public DigestUtilsCompat(final MessageDigest digest) {
        this.messageDigest = digest;
    }

    /**
     * Creates an instance using the provided {@link MessageDigest} parameter.
     * <p>
     * This can then be used to create digests using methods such as
     * {@link #digest(byte[])} and {@link #digestAsHex(File)}.
     *
     * @param name the name of the {@link MessageDigest} to use
     * @throws IllegalArgumentException when a {@link NoSuchAlgorithmException} is caught.
     * @see #getDigest(String)
     * @since 1.11
     */
    public DigestUtilsCompat(final String name) {
        this(getDigest(name));
    }

    /**
     * Reads through a byte array and returns the digest for the data.
     *
     * @param data Data to digest
     * @return the digest
     * @since 1.11
     */
    public byte[] digest(final byte[] data) {
        return updateDigest(messageDigest, data).digest();
    }

    /**
     * Reads through a ByteBuffer and returns the digest for the data
     *
     * @param data Data to digest
     * @return the digest
     * @since 1.11
     */
    public byte[] digest(final ByteBuffer data) {
        return updateDigest(messageDigest, data).digest();
    }

    /**
     * Reads through a File and returns the digest for the data
     *
     * @param data Data to digest
     * @return the digest
     * @throws IOException On error reading from the stream
     * @since 1.11
     */
    public byte[] digest(final File data) throws IOException {
        return updateDigest(messageDigest, data).digest();
    }

    /**
     * Reads through an InputStream and returns the digest for the data
     *
     * @param data Data to digest
     * @return the digest
     * @throws IOException On error reading from the stream
     * @since 1.11
     */
    public byte[] digest(final InputStream data) throws IOException {
        return updateDigest(messageDigest, data).digest();
    }

    /**
     * Reads through a File and returns the digest for the data
     *
     * @param data    Data to digest
     * @param options options How to open the file
     * @return the digest
     * @throws IOException On error reading from the stream
     * @since 1.14
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public byte[] digest(final Path data, final OpenOption... options) throws IOException {
        return updateDigest(messageDigest, data, options).digest();
    }

    /**
     * Reads through a byte array and returns the digest for the data.
     *
     * @param data Data to digest treated as UTF-8 string
     * @return the digest
     * @since 1.11
     */
    public byte[] digest(final String data) {
        return updateDigest(messageDigest, data).digest();
    }

    /**
     * Reads through a byte array and returns the digest for the data.
     *
     * @param data Data to digest
     * @return the digest as a hex string
     * @since 1.11
     */
    public String digestAsHex(final byte[] data) {
        return Hex.encodeHexString(digest(data));
    }

    /**
     * Reads through a ByteBuffer and returns the digest for the data
     *
     * @param data Data to digest
     * @return the digest as a hex string
     * @since 1.11
     */
    public String digestAsHex(final ByteBuffer data) {
        return Hex.encodeHexString(digest(data));
    }

    /**
     * Reads through a File and returns the digest for the data
     *
     * @param data Data to digest
     * @return the digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.11
     */
    public String digestAsHex(final File data) throws IOException {
        return Hex.encodeHexString(digest(data));
    }

    /**
     * Reads through an InputStream and returns the digest for the data
     *
     * @param data Data to digest
     * @return the digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.11
     */
    public String digestAsHex(final InputStream data) throws IOException {
        return Hex.encodeHexString(digest(data));
    }

    /**
     * Reads through a File and returns the digest for the data
     *
     * @param data    Data to digest
     * @param options options How to open the file
     * @return the digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.11
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String digestAsHex(final Path data, final OpenOption... options) throws IOException {
        return Hex.encodeHexString(digest(data, options));
    }

    /**
     * Reads through a byte array and returns the digest for the data.
     *
     * @param data Data to digest treated as UTF-8 string
     * @return the digest as a hex string
     * @since 1.11
     */
    public String digestAsHex(final String data) {
        return Hex.encodeHexString(digest(data));
    }

    /**
     * Returns the message digest instance.
     *
     * @return the message digest instance
     * @since 1.11
     */
    public MessageDigest getMessageDigest() {
        return messageDigest;
    }

}
