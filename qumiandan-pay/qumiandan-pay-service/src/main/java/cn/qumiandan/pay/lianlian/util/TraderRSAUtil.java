package cn.qumiandan.pay.lianlian.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
public class TraderRSAUtil {

	private static TraderRSAUtil instance;

	public static TraderRSAUtil getInstance() {
		return instance == null ? new TraderRSAUtil() : instance;
	}

	public static String sign(String prikeyvalue, String sign_str) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(cn.qumiandan.pay.lianlian.util.Base64.getBytesBASE64(prikeyvalue));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey myprikey = keyf.generatePrivate(priPKCS8);
			Signature signet = Signature.getInstance("MD5withRSA");
			signet.initSign(myprikey);
			signet.update(sign_str.getBytes("UTF-8"));
			byte[] signed = signet.sign();
			return new String(Base64.encodeBase64(signed));
		} catch (Exception var7) {
			return null;
		}
	}

	public static boolean checksign(String pubkeyvalue, String oid_str, String signed_str) {
		try {
			X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(
					cn.qumiandan.pay.lianlian.util.Base64.getBytesBASE64(pubkeyvalue));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
			byte[] signed = cn.qumiandan.pay.lianlian.util.Base64.getBytesBASE64(signed_str);
			Signature signetcheck = Signature.getInstance("MD5withRSA");
			signetcheck.initVerify(pubKey);
			signetcheck.update(oid_str.getBytes("UTF-8"));
			return signetcheck.verify(signed);
		} catch (Exception var8) {
			return false;
		}
	}
}
