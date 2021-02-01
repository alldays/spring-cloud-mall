package com.kuqi.mall.mybatis.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * 对称加密算法
 */
@Slf4j
public class Encryptors {

    /**
     * 高级加密标准，新一代标准，加密速度更快，安全性更高
     */
    private final static String ALGORITHM = "AES";
    private final static String CHARSET_NAME = "UTF-8";

    private final SecretKeySpec key;

    public Encryptors(String key) {
        this.key = generateAESKey(key);
    }

    /**
     * generateAESKey
     *
     * @param key
     * @return This will generate aes key compatible with MySQL AES function
     * SELECT TO_BASE64(aes_encrypt('data', 'key'))
     */
    private SecretKeySpec generateAESKey(final String key) {
        try {
            final byte[] finalKey = new byte[16];
            int i = 0;
            for (byte b : key.getBytes(CHARSET_NAME)) {
                finalKey[i++ % 16] ^= b;
            }
            return new SecretKeySpec(finalKey, ALGORITHM);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 数据加密
     *
     * @param data
     * @return
     */
    public String encrypt(String data) {

        if (StringUtils.isBlank(data)) {
            return null;
        }
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(data.getBytes(Charset.forName(CHARSET_NAME)));
            return Hex.encodeHexString(encVal).toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 数据解密
     *
     * @param encryptedData
     * @return
     */
    public String decrypt(String encryptedData) {

        if (StringUtils.isBlank(encryptedData)) {
            return null;
        }
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = Hex.decodeHex(encryptedData.toCharArray());
            byte[] decValue = c.doFinal(decordedValue);
            return new String(decValue, Charset.forName(CHARSET_NAME));
        } catch (Exception e) {

            log.warn("decrypt error!encryptedData {}", encryptedData);
            return encryptedData;
        }
    }
}
