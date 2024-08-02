package com.dp.dpapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

public class SignUtils {

	public static String genSign(String body, String secretKey){
		Digester sha256 = new Digester(DigestAlgorithm.SHA256);
		String content = body + "." + secretKey;
		return sha256.digestHex(content);
	}
}
