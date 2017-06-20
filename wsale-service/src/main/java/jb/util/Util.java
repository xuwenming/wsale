package jb.util;

import jb.absx.F;

import java.util.Date;
import java.util.Random;

public class Util {

	public static String CreateNoncestr(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < length; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}
	public static String CreateNonceNumstr(int length) {
		String chars = "0123456789";
		String res = "";
		for (int i = 0; i < length; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	/**
	 * 交易流水号
	 * @return
	 */
	public static String CreatePayOrderNo() {
		return CreateNo("ZCP");
	}

	/**
	 * 拍品订单号
	 * @return
	 */
	public static String CreateOrderNo() {
		return CreateNo("ZC");
	}

	/**
	 * 拍品编号
	 * @return
	 */
	public static String CreatePNo() {
		return CreateNo("P");
	}

	/**
	 * 钱包交易号
	 * @return
	 */
	public static String CreateWalletNo() {
		return CreateNo("ZCW");
	}

	/**
	 * 退款单号
	 * @return
	 */
	public static String CreateRefundNo() {
		return CreateNo("ZCR");
	}

	/**
	 * 提现单号
	 * @return
	 */
	public static String CreateTransfersNo() {
		return CreateNo("ZCT");
	}

	/**
	 * 转账汇款单号
	 * @return
	 */
	public static String CreateHKNo() {
		return CreateNo("ZCH");
	}

	public static String CreateNo(String tag) {
		tag = tag == null ? "" : tag;
		return tag + DateUtil.format(new Date(), "yyMMddHHmmss") + Util.CreateNonceNumstr(4);
	}

	private static boolean isNotEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) ||
				(codePoint == 0x9) ||
				(codePoint == 0xA) ||
				(codePoint == 0xD) ||
				((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
				((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
				((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}
	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {
		if(F.empty(source)) return source;

		int len = source.length();
		StringBuilder buf = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if(isNotEmojiCharacter(codePoint)) {
				buf.append(codePoint);
			}
		}
		return buf.toString();
	}

	public static String replaceFace(String source, int len) {
		String regex = "\\[em_(.*?)\\]|<\\s*img\\s+([^>]*)\\s*>";
		source = source.replaceAll(regex, "※");
		if(source.length() > len) {
			source = source.substring(0, len) + "...";
		}
		return source.replaceAll("※", "[表情]");
	}

}
