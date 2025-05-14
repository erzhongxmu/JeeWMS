package com.base.common.util;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;

import lombok.extern.slf4j.Slf4j;
//import com.owinfo.cds.util.StringUtil;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * RSA加密，公私钥、加密串等都是16进制编码
 *
 * @author mamingli
 */
@SuppressWarnings("restriction")
@Slf4j
public class CDSRSA {

    private static final Logger logger = LoggerFactory.getLogger(CDSRSA.class);

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
            byte[] keyBytes = hexToByteArray(key.trim());
            int i = Integer.parseInt(key, 16);
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
            byte[] keyBytes = hexToByteArray(key.trim());
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
            return bytesToHex(signature);
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
            if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(src)) {
                throw new RuntimeException("sign或内容不容为空");
            }
            Signature sigEng = Signature.getInstance("SHA1withRSA");
            sigEng.initVerify(publicKey);
            sigEng.update(src.getBytes(encode));
            byte[] sign1 = hexToByteArray(sign);
            if (!sigEng.verify(sign1)) {
                // AppMonitorLogger.increase(AppMonitorLogger.FAIL_PAY_RSA_VERIFY_COUNT);
                throw new RuntimeException("验签失败");
            }
        } catch (Exception e) {
            // AppMonitorLogger.increase(AppMonitorLogger.FAIL_PAY_RSA_VERIFY_COUNT);
            throw new RuntimeException("验签失败");
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
            if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(src)) {
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
            logger.info("Generating a pair of RSA key ... ");
            rsaKeyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = new SecureRandom();
            random.setSeed(("" + System.currentTimeMillis() * Math.random() * Math.random()).getBytes(Charset
                    .forName("UTF-8")));
            rsaKeyGen.initialize(1024, random);
            rsaKeyPair = rsaKeyGen.genKeyPair();
            PublicKey rsaPublic = rsaKeyPair.getPublic();
            PrivateKey rsaPrivate = rsaKeyPair.getPrivate();

            String[] privateAndPublic = new String[2];
            privateAndPublic[0] = bytesToHex(rsaPrivate.getEncoded());
            privateAndPublic[1] = bytesToHex(rsaPublic.getEncoded());
            logger.info("私钥:" + privateAndPublic[0]);
            logger.info("公钥:" + privateAndPublic[1]);
            logger.info("1024-bit RSA key GENERATED.");

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

    public static byte[] hexToByteArray(String inHex){
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1){
            //奇数
            hexlen++;
            result = new byte[(hexlen/2)];
            inHex="0"+inHex;
        }else {
            //偶数
            result = new byte[(hexlen/2)];
        }
        int j=0;
        for (int i = 0; i < hexlen; i+=2){
            result[j]=(byte)Integer.parseInt(inHex.substring(i,i+2),16);
            j++;
        }
        return result;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if(hex.length() < 2){
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception{
//        genRSAKeyPair();
        String json = "{\"company_name\":\"KYXH\"}";
        String privateKey = "30820276020100300d06092a864886f70d0101010500048202603082025c02010002818100aadc1f7d749b8602a6a909c60ba5933dd07f1a2887174ef684bad28e72b98708e0554ee2291f6bc47279ed3ceb595543645cca06cfe787877d2892931c18fbc8c4ff17858089029ddbaf1b6cd41a322cb63c91fdbba3da647efc184f92ab9f8b8486bc1b7908752875ea9b4b25c6b758af6b6f8591a69c80b080530a2762441b0203010001028180239ed695d21ca658a124369c6a4b2fa240786f1ca9e54b1a0bdbe30fbd8d3e40b47c3480e3efe008c990efcbb6891e447984fabd66d4af25a90b2f5baa0cef58b97566d6a2dbc28531f63305e1c5744d811d06649ea7c6a7d6d4679366414fa2bf24a9818b75ebcfa184fada5a2754adac857dcd13c3be8de12bd0b1274a4f39024100e292ca2910d76878faaa5ed0e8922463a7f46a7087fe03df67f76c8e9c20b4072ab16511871fd1da638c3de2f67a45742cf035bdc2fb9c8c26a97fd2ad310b07024100c10cf204cf6e5950d8560459ba1dcbe39912db94ad42b232521731a66f5ccf992253d6983b7aaabbacea9eff80a40606702c26908f90e4c1ac606e21f2fab54d024014d703993b5f54d0abddb7d2e75169df70b75e82f33f3e03f28023900a74479ad12677f160c8607901e7085a33e99331d8604fd1207a1c3017ee473f8d869149024100b9f75a7cbfc5b14c6bda10becec73d4db67bb002fd14aecc42c00d182356e8b4bf98bbb45237cc9895ee3dd42b7bd6420574c281a78b10927117438078ac82510240369388b3ea45ee44e14a6cedbd46e075c79936675a6814d471b7ac447a26d54584c934c7d2db78cd18142c36d44a67d96cc215dfbb7050abee835d2f42548f00";
        String sign = CDSRSA.sign(CDSRSA.getPrivateKey(privateKey), json, "UTF-8");
        System.out.println(sign);
        byte[] bytes = json.getBytes("UTF-8");
        System.out.println("数组："+ Arrays.toString(bytes));
        PrivateKey privateKey1 = CDSRSA.getPrivateKey(privateKey);
        System.out.println(privateKey1.toString());
    }
}
