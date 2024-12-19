package com.base.modules.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加密，公私钥、加密串等都是16进制编码
 *
 * @author mamingli
 */
@SuppressWarnings("restriction")
public class RSA {

    private static final Logger logger = LoggerFactory.getLogger(RSA.class);

    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    /** */
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** */
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 得到私钥对象
     *
     * @param key 密钥字符串（经过16进制编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) {
        try {
            byte[] keyBytes = StringUtil.hexStrToBytes(key.trim());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            String info = "getPrivateKey failed: " + key + " | " + e.getMessage();
            logger.error(info, e);
            return null;
        }
    }

    /**
     * 得到公钥对象
     *
     * @param key 密钥字符串（经过16进制编码）
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) {
        try {
            byte[] keyBytes = StringUtil.hexStrToBytes(key.trim());
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            String info = "getPublicKey failed: " + key + " | " + e.getMessage();
            logger.error(info, e);
            return null;
        }
    }

    /**
     * 本方法使用SHA1withRSA签名算法产生签名
     *
     * @param privateKey privateKey 签名时使用的私钥(16进制编码)
     * @param src        src 签名的原字符串
     * @return String 签名的返回结果(16进制编码)。当产生签名出错的时候，返回null。
     */
    public static String sign(PrivateKey privateKey, String src, String encode) {
        try {
            Signature sigEng = Signature.getInstance(SIGN_ALGORITHMS);
            sigEng.initSign(privateKey);
            sigEng.update(src.getBytes(encode));
            byte[] signature = sigEng.sign();
            return StringUtil.bytesToHexStr(signature);
        } catch (Exception e) {
            String info = "sign failed: " + src + " | " + e.getMessage();
            logger.error(info, e);
            // AppMonitorLogger.increase(AppMonitorLogger.FAIL_PAY_RSA_SIGN_COUNT);
            return null;
        }
    }

    /**
     * 本方法使用SHA1withRSA签名算法产生签名
     *
     * @param privateKey privateKey 签名时使用的私钥(16进制编码)
     * @param src        src 签名的原字符串
     * @return String 签名的返回结果(16进制编码)。当产生签名出错的时候，返回null。
     */
    public static String signBase64(PrivateKey privateKey, String src, String encode) {
        try {
            Signature sigEng = Signature.getInstance(SIGN_ALGORITHMS);
            sigEng.initSign(privateKey);
            sigEng.update(src.getBytes(encode));
            byte[] signature = sigEng.sign();
            return Base64.encodeBase64String(signature);
        } catch (Exception e) {
            String info = "sign failed: " + src + " | " + e.getMessage();
            logger.error(info, e);
            // AppMonitorLogger.increase(AppMonitorLogger.FAIL_PAY_RSA_SIGN_COUNT);
            return null;
        }
    }

    /**
     * 本方法使用SHA1withRSA签名算法验证签名
     *
     * @param publicKey pubKey 验证签名时使用的公钥(16进制编码)
     * @param sign      sign 签名结果(16进制编码)
     * @param src       src 签名的原字符串
     */
    public static void verify(PublicKey publicKey, String sign, String src, String encode) throws Exception {
        try {
            if (StringUtils.isBlank(sign) || StringUtils.isBlank(src)) {
                throw new RuntimeException("sign或内容不容为空");
            }
            Signature sigEng = Signature.getInstance("SHA1withRSA");
            sigEng.initVerify(publicKey);
            sigEng.update(src.getBytes(encode));
            byte[] sign1 = StringUtil.hexStrToBytes(sign);
            boolean aaa = sigEng.verify(sign1);
            System.out.println("=====" + aaa + "=====");
            if (!aaa) {
                // AppMonitorLogger.increase(AppMonitorLogger.FAIL_PAY_RSA_VERIFY_COUNT);
                throw new Exception("验签失败");
            }
        } catch (Exception e) {
            String info = "verify failed: " + sign + " | " + src + " | " + e.getMessage();
            // logger.error(info, e);
            // AppMonitorLogger.increase(AppMonitorLogger.FAIL_PAY_RSA_VERIFY_COUNT);
            throw new Exception("验签失败");
        }
    }

    /**
     * 本方法使用SHA1withRSA签名算法验证签名
     *
     * @param publicKey pubKey 验证签名时使用的公钥(16进制编码)
     * @param sign      sign 签名结果(16进制编码)
     * @param src       src 签名的原字符串
     */
    public static void verifyBase64(PublicKey publicKey, String sign, String src, String encode) throws Exception {
        try {
            if (StringUtils.isBlank(sign) || StringUtils.isBlank(src)) {
                throw new RuntimeException("sign或者内容不能为空");
            }
            Signature sigEng = Signature.getInstance("SHA1withRSA");
            sigEng.initVerify(publicKey);
            sigEng.update(src.getBytes(encode));
            byte[] sign1 = Base64.decodeBase64(sign);
            if (!sigEng.verify(sign1)) {
                // AppMonitorLogger.increase(AppMonitorLogger.FAIL_PAY_RSA_VERIFY_COUNT);
                throw new RuntimeException("验签失败");
            }
        } catch (Exception e) {
            String info = "verify failed: " + sign + " | " + src + " | " + e.getMessage();
            logger.error(info, e);
            // AppMonitorLogger.increase(AppMonitorLogger.FAIL_PAY_RSA_VERIFY_COUNT);
            throw new RuntimeException("验签失败");
        }
    }

    /**
     * 验签，专门为网易宝红包使用
     */
    public static boolean rsaVerify(String pubKey, String sign, String src, String encoding) {
        try {
            BASE64Decoder base64decoder = new BASE64Decoder();
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(base64decoder.decodeBuffer(pubKey));
            KeyFactory fac = KeyFactory.getInstance("RSA");
            RSAPublicKey rsaPubKey = (RSAPublicKey) fac.generatePublic(keySpec);
            Signature sigEng = Signature.getInstance("SHA1withRSA");
            sigEng.initVerify(rsaPubKey);
            sigEng.update(src.getBytes(encoding));
            byte[] signature = base64decoder.decodeBuffer(sign);
            return sigEng.verify(signature);
        } catch (Exception e) {
            logger.error("rsaVerify error: ", e);
            return false;
        }
    }

    /**
     * 生成签名,专门为网易宝红包使用
     */
    public static String rsaSign(String priKey, String src, String encoding) {
        try {
            BASE64Decoder base64decoder = new BASE64Decoder();
            BASE64Encoder base64encoder = new BASE64Encoder();
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(base64decoder.decodeBuffer(priKey));
            KeyFactory fac = KeyFactory.getInstance("RSA");
            RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);
            Signature sigEng = Signature.getInstance("SHA1withRSA");
            sigEng.initSign(privateKey);
            sigEng.update(src.getBytes(encoding));
            byte[] signature = sigEng.sign();
            String sign = base64encoder.encodeBuffer(signature);
            return sign.replaceAll("\r|\n", "");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 本方法用于产生1024位RSA公私钥对。
     *
     * @return 私钥、公钥
     */
    public static String[] genRSAKeyPair() {
        KeyPairGenerator rsaKeyGen = null;
        KeyPair rsaKeyPair = null;
        try {
            logger.error("Generating a pair of RSA key ... ");
            rsaKeyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = new SecureRandom();
            random.setSeed(("" + System.currentTimeMillis() * Math.random() * Math.random()).getBytes(StandardCharsets.UTF_8));
            rsaKeyGen.initialize(1024, random);
            rsaKeyPair = rsaKeyGen.genKeyPair();
            PublicKey rsaPublic = rsaKeyPair.getPublic();
            PrivateKey rsaPrivate = rsaKeyPair.getPrivate();
            String[] privateAndPublic = new String[2];
            privateAndPublic[0] = StringUtil.bytesToHexStr(rsaPrivate.getEncoded());
            privateAndPublic[1] = StringUtil.bytesToHexStr(rsaPublic.getEncoded());
            logger.info("私钥:" + privateAndPublic[0]);
            logger.error("公钥:" + privateAndPublic[1]);
            logger.error("1024-bit RSA key GENERATED.");

            return privateAndPublic;
        } catch (Exception e) {
            logger.error("genRSAKeyPair error：" + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 公钥加密
     *
     * @param publicKey 公钥
     * @param src       明文
     * @param encode    编码方式
     * @return
     * @throws Exception
     */
    public static String encrypt(PublicKey publicKey, String src, String encode) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] data = src.getBytes(encode);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64.encodeBase64String(encryptedData);
    }

    /**
     * 私钥解密
     *
     * @param privateKey 私钥
     * @param data       密文
     * @param encode     编码方式
     * @return
     * @throws Exception
     */
    public static String decrypt(PrivateKey privateKey, String data, String encode) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] encryptedData = Base64.decodeBase64(data);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData, encode);
    }
}