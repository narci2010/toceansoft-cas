package com.toceansoft.cas.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchHtmlElementAttrValue {
	/**
	 * 获取指定HTML标签的指定属性的值
	 * 
	 * @param source
	 *            要匹配的源文本
	 * @param element
	 *            标签名称
	 * @param attr
	 *            标签的属性名称
	 * @return 属性值列表
	 */
	public static List<String> match(String source, String element, String attr) {
		List<String> result = new ArrayList<String>();
		String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?(\\s.*?)?>";
		Matcher m = Pattern.compile(reg).matcher(source);
		while (m.find()) {
			String r = m.group(1);
			result.add(r);
		}
		return result;
	}

	public static void main(String[] args) {
		getTgtId();
	}

	public static String getTgtId(String source) {
		List<String> list = match(source, "form", "action");
		String rs = null;
		for (String str : list) {
			String[] tmpStr = str.split("/");

			if (tmpStr != null) {
				rs = tmpStr[tmpStr.length - 1];
			}
			// System.out.println(rs);
		}
		return rs;
	}

	// for embed test
	private static void getTgtId() {
		String source = "<!DOCTYPE HTML PUBLIC \\\"-//IETF//DTD HTML 2.0//EN\\\">\r\n"
				+ "<html>\r\n" + "    <head>\r\n" + "        <title>201 Created</title>\r\n"
				+ "    </head>\r\n" + "    <body>\r\n" + "        <h1>TGT Created</h1>\r\n"
				+ "        <form action=\"https://localhost:8443/cas/v1/st/TGT-1-YCyRNCTVcNcVkFQoEcaKdRiw3StM7SPSGVWvNgNzJf7l-fZsvJKmkpsXW5w2Rh-bK4kSKY-20171031ICW\" method=\"POST\">Service:\r\n"
				+ "            <input type=\"text\" name=\"service\" value=\"\">\r\n"
				+ "            <br>\r\n"
				+ "            <input type=\"submit\" value=\"Submit\">\r\n" + "        </form>\r\n"
				+ "    </body>\r\n" + "</html>";
		List<String> list = match(source, "form", "action");
		for (String str : list) {
			String[] tmpStr = str.split("/");
			String rs = null;
			if (tmpStr != null) {
				rs = tmpStr[tmpStr.length - 1];
			}
			System.out.println(rs);
		}
	}
}
