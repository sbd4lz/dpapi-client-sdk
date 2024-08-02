package com.dp.dpapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.dp.dpapiclientsdk.model.User;
import com.dp.dpapiclientsdk.utils.SignUtils;

import java.util.HashMap;
import java.util.Map;

public class DpApiClient {

	private String accessKey;
	private String secretKey;

	public DpApiClient(String accessKey, String secretKey) {
		this.accessKey = accessKey;
		this.secretKey = secretKey;
	}

	public String getNameByGet(String name){
		//可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("name", name);
		String result= HttpUtil.get("http://localhost:8123/api/name/get", paramMap);
		System.out.println(result);
		return result;
	}

	public String getNameByPost(String name){
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("name", name);
		String result= HttpUtil.post("http://localhost:8123/api/name/post", paramMap);
		System.out.println(result);
		return result;
	}

	private Map<String, String> getHeaderMap(String body){
		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("accessKey", accessKey);	// 用于标识用户身份，利用它在服务端找对应的secretKey
//		hashMap.put("secretKey", secretKey);   该参数不能传递
		hashMap.put("nonce", RandomUtil.randomNumbers(4));
		hashMap.put("body", body);
		hashMap.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
		hashMap.put("sign", SignUtils.genSign(body, secretKey));	// 使用参数 + 密钥做加密生成API签名认证
		return hashMap;
	}


	public String getNameByUser(User user){
		String json = JSONUtil.toJsonStr(user);
		HttpResponse result = HttpRequest.post("http://localhost:8123/api/name/user")
				.addHeaders(getHeaderMap(json))
				.body(json)
				.execute();
		String body = result.body();
		System.out.println(body);
		return body;
	}


}
