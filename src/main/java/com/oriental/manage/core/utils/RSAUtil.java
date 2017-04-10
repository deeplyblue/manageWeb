package com.oriental.manage.core.utils;


import com.oriental.paycenter.commons.utils.ConvertUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.*;
import java.security.cert.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * Created by lupf
 */
public class RSAUtil {
	private static Logger logger = LoggerFactory.getLogger(RSAUtil.class);
	private static String method = "NONEwithRSA";
	private KeyPair keyPair = null;

	/**
	 * 生成密钥对
	 *
	 */
	public boolean generateKeyPair() {
		boolean result = false;
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			// 这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
			int KEY_SIZE = 1024;
			keyPairGen.initialize(KEY_SIZE, new SecureRandom());
			keyPair = keyPairGen.genKeyPair();
			result = true;
		} catch (Exception e) {
			logger.error("产生密钥对出错，{}", e);
		}
		return result;
	}

	/**
	 * 生成公钥
	 */
	public byte[] generateRSAPublicKeyWithByte() {
		byte[] pubKey = null;
		try {
			if(keyPair == null) {
				logger.error("密钥对未生成");
				return null;
			}
			pubKey = keyPair.getPublic().getEncoded();
		} catch (Exception e) {
			logger.error("生成公钥异常，{}", e);
		}
		return pubKey;
	}

	/**
	 * 生成公钥(BASE64)
	 */
	public String generateRSAPublicKey() {
		String pubKey = null;
		try {
			if(keyPair == null) {
				logger.error("密钥对未生成");
				return null;
			}
			pubKey = ConvertUtil.base64Encode(keyPair.getPublic().getEncoded());
//			pubKey = Base64.encodeBase64String(keyPair.getPublic().getEncoded());
		} catch (Exception e) {
			logger.error("生成公钥异常，{}", e);
		}
		return pubKey;
	}

	/**
	 * 生成私钥
	 */
	public byte[] generateRSAPrivateKeyWithByte() {
		byte[] priKey = null;
		try {
			if(keyPair == null) {
				logger.error("密钥对未生成");
				return null;
			}
			priKey = keyPair.getPrivate().getEncoded();
		} catch (Exception e) {
			logger.error("生成私钥异常，{}", e);
		}
		return priKey;
	}

	/**
	 * 生成私钥(BASE64)
	 */
	public String generateRSAPrivateKey() {
		String priKey = null;
		try {
			if(keyPair == null) {
				logger.error("密钥对未生成");
				return null;
			}
			priKey = ConvertUtil.base64Encode(keyPair.getPrivate().getEncoded());
		} catch (Exception e) {
			logger.error("生成私钥异常，{}", e);
		}
		return priKey;
	}

	/* ============================================================================*/
    /*  生成的公钥明文经过BASE64编码后，通过此方法可以还原成公钥对象				   */
    /* ============================================================================*/
	/**
	 * 生成公钥对象
	 * @param key 公钥明文（BASE64）
	 */
	public static RSAPublicKey generateRSAPublicKey(String key) {
		RSAPublicKey pubKey = null;
		try {
			byte[] keyValue = ConvertUtil.base64DecodeToBytes(key);
			X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(keyValue);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			pubKey = (RSAPublicKey)keyFactory.generatePublic(bobPubKeySpec);
		} catch (Exception e) {
			logger.error("生成公钥异常，{}", e);
		}
		return pubKey;
	}
	
	/* ============================================================================*/
    /*  生成的私钥明文经过BASE64编码后，通过此方法可以还原成私钥对象				   */
    /* ============================================================================*/
	/**
	 * 生成私钥对象
	 * @param key 私钥明文(BASE64)
	 */
	public static RSAPrivateKey generateRSAPrivateKey(String key) {
		RSAPrivateKey priKey = null;
		try {
			logger.debug("BASE64私钥明文：{}，长度{}", key, key.length());
			byte[] keyValue = ConvertUtil.base64DecodeToBytes(key);
			PKCS8EncodedKeySpec priPKCS8=new PKCS8EncodedKeySpec(keyValue);
			KeyFactory keyf=KeyFactory.getInstance("RSA");
			priKey = (RSAPrivateKey)keyf.generatePrivate(priPKCS8);
		} catch (Exception e) {
			logger.error("生成私钥异常，{}", e);
		}
		return priKey;
	}

	/**
	 * 加密
	 * @throws Exception
	 */
	public static byte[] encrypt(Key key, byte[] data) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		
		/*
		 * 获得加密块大小，如：加密前数据为128个byte，
		 * 而key_size=1024 加密块大小为127 byte,加密后为128个byte;
		 * 因此共有2个加密块，第一个127 byte第二个为1个byte
		 */
		int blockSize = cipher.getBlockSize();

		//获得加密块加密后块大小
		int outputSize = cipher.getOutputSize(data.length);
		int leavedSize = data.length % blockSize;
		int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
		byte[] raw = new byte[outputSize * blocksSize];
		int i = 0;
		while (data.length - i * blockSize > 0) {
			/*
			 * 这里面doUpdate方法不可用
			 * 查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到 ByteArrayOutputStream中
			 * 而最后doFinal的时候才将所有的byte[]进行加密
			 * 可是到了此时加密块大小很可能已经超出了 OutputSize所以只好用dofinal方法
			 */
			if (data.length - i * blockSize > blockSize)
				cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
			else
				cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
			i++;
		}
		return raw;
	}

	/**
	 * 解密
	 * @throws Exception
	 */
	public static byte[] decrypt(Key key, byte[] raw) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, key);
		int blockSize = cipher.getBlockSize();
		ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
		int j = 0;
		while (raw.length - j * blockSize > 0) {
			bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
			j++;
		}
		return bout.toByteArray();
	}
	
	/* ============================================================================*/
    /* 商户发起交易请求用私钥签名，平台用公钥验签 					   				   */
    /* ============================================================================*/
	/**
	 * 签名
	 * @param prikey 私钥对象
	 * @param data 需要签名的原始数据
	 */
	public static byte[] sign(RSAPrivateKey prikey, String data) {
		byte[] res = null;
		try {
			/*使用 Signature 对象签名数据或验证签名；分为三个阶段：
			 * 1.，使用(initSign)
			 * 2.更新(update)
			 * 3.签署或验证所有更新字节的签名(sign或者verify方法)
			 */
			Signature sign = Signature.getInstance(method);
			sign.initSign(prikey);
			sign.update(data.getBytes());
			res = sign.sign();
		} catch (Exception e) {
			logger.error("签名失败，{}", e);
		}
		return res;
	}

	/**
	 * 签名，签名结果用BASE64编码
	 * @param prikey	私钥对象
	 * @param data	需要签名的原始数据
	 */
	public static String signBase64(RSAPrivateKey prikey, String data) {
		String res = null;
		try {
			byte[] tmp = sign(prikey, data);
			if(!ArrayUtils.isEmpty(tmp)) {
				res = ConvertUtil.base64Encode(tmp);
			}
		} catch (Exception e) {
			logger.error("签名失败，{}", e);
		}
		return res;
	}

	/**
	 * 验签，公钥进行验签
	 * @param pubKey 公钥对象
	 * @param oriData 原数据
	 * @param signData 签名(BASE64)
	 */
	public static boolean verifySign(RSAPublicKey pubKey, String oriData, String signData) {
//		data = data.substring(0, data.indexOf("sign="));
		boolean res = false;
		try {
			logger.debug("原签名内容：{}", signData);
			Signature sign = Signature.getInstance(method);
			sign.initVerify(pubKey);
			sign.update(oriData.getBytes());

			byte[] signByte = ConvertUtil.base64DecodeToBytes(signData);
			res = sign.verify(signByte);
		} catch (Exception e) {
			logger.error("验签失败，{}", e);
		}
		return res;
	}

	public static String readCerficate(InputStream in) throws CertificateException {
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		java.security.cert.Certificate certificate = certificateFactory.generateCertificate(in);
		return Base64.encodeBase64String(certificate.getPublicKey().getEncoded());
	}

}
