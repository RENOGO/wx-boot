package com.wx.common.utils;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wwx
 * @date 2021/10/21 10:51
 * @description
 */
public class StringUtil {

	/**
	 * 对请求地址中的query数据进行排序
	 *
	 * @param query b=123&a=2222   格式
	 * @return a=2222&b=123   返回
	 */
	public static String sort(String query) {
		if (StrUtil.isEmpty(query)) {
			return "";
		}
		//1.切分字符串
		String[] stringArray = query.split("&");
		Arrays.sort(stringArray);
		StringBuilder returnString = new StringBuilder();
		for (int i = 0; i < stringArray.length; i++) {
			returnString.append(stringArray[i]);
			if (i != stringArray.length - 1) {
				returnString.append("&");
			}
		}
		return returnString.toString();
	}

	/**
	 * @param query 带分隔的url参数  b=123&a=2222
	 * @return
	 */
	public static Map<String, String> query2Map(String query) {
		if (StrUtil.isEmpty(query)) {
			return null;
		}
		Map<String, String> map = new HashMap<>();
		String[] param = query.split("&");
		for (String kv : param) {
			int dash = kv.indexOf("=");
			if (dash > 0) {
				String key = kv.substring(0, dash);
				String value = kv.substring(dash + 1);
				map.put(key, value);
				continue;
			}
			map.put(kv, "");
		}
		return map;
	}

	/**
	 * 将map转成 a=2222&b=123格式,会升序
	 *
	 * @param queryMap
	 * @return
	 */
	public static String map2Query(Map<String, String> queryMap) {
		if (queryMap == null) {
			return null;
		}
		StringBuilder queryBuilder = new StringBuilder();
		queryMap.forEach((s, s2) -> {
			if (!StrUtil.isEmpty(s2)) {
				queryBuilder.append(s).append("=").append(s2).append("&");
			} else {
				queryBuilder.append(s).append("&");
			}
		});
		String query = queryBuilder.toString();
		if (StrUtil.isEmpty(query)) {
			return "";
		}
		query = query.substring(0, query.length() - 1);
		return sort(query);
	}

	/**
	 * 如传入 http://localhost:8080/path?query=123,返回/path
	 *
	 * @param url
	 * @return
	 */
	public static String getUrlPath(String url) {
		int dash1 = url.indexOf("/");
		if (dash1 < 0) {

		}
		int dash2 = url.indexOf("/", dash1 + 1);
		if (dash2 < 0) {
			return "/";
		}
		int dash3 = url.indexOf("/", dash2 + 1);
		if (dash3 < 0) {
			return "/";
		}
		String pathAndQuery = url.substring(dash3);
		int dash4 = pathAndQuery.indexOf("?");
		String path = null;
		if (dash4 < 0) {
			path = pathAndQuery;
		} else {
			path = pathAndQuery.substring(0, dash4);
		}
		return path;
	}

	/**
	 * 如传入 http://localhost:8080/path?query1=123&query2=234,返回query1=123&query2=234
	 *
	 * @param url
	 * @return
	 */
	public static String getUrlQuery(String url) {
		String[] split = url.split("\\?");
		if (split.length < 2) {
			return null;
		}
		return split[1];
	}

}
