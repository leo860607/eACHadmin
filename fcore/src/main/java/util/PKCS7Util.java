package util;

import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.util.Base64;

import sun.security.pkcs.ContentInfo;
import sun.security.pkcs.PKCS7;
import sun.security.pkcs.SignerInfo;

public class PKCS7Util{
	public boolean pkcs7Verify(String pkcs7String) throws Exception{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());      
		Base64.Decoder decoder = Base64.getDecoder();
		PKCS7 pkcs7 = new PKCS7(decoder.decode(pkcs7String.getBytes("UTF-8")));
		X509Certificate[] certs = (X509Certificate[])pkcs7.getCertificates();
		PublicKey pKey=certs[0].getPublicKey();
		ContentInfo cInfo = pkcs7.getContentInfo();
		byte[] data = cInfo.getData();
		SignerInfo signInfo = pkcs7.getSignerInfos()[0];

		byte[] signValue = signInfo.getEncryptedDigest();
		String algorithm = "SHA1WITHRSAENCRYPTION"; 
		boolean verify = verifySign(algorithm,pKey,data,signValue);
		return verify;

	}
	private boolean verifySign(String algoName,PublicKey pKey,byte[] data,byte[] signValue) {
		try{
	        Signature signature = Signature.getInstance(algoName);
	        signature.initVerify(pKey);
	        signature.update(data);
	        boolean b = signature.verify(signValue);
	        return b;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
