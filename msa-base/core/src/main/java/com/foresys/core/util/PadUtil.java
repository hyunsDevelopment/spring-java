package com.foresys.core.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class PadUtil {

	public static byte[] encodeCharsetBytes(String src, String charsetName) throws UnsupportedEncodingException {
		byte[] s = null;
		if (src != null) {
			s = src.getBytes(charsetName);
		}

		return s;
	}

	public static byte[] rpad(byte[] byteSrc, byte b, int size, String charsetName)
			throws UnsupportedEncodingException {
		byte[] result = new byte[size];
		byte[] temp = new byte[size];
		int slen = byteSrc == null ? 0 : byteSrc.length;

		for (int i = 0; i < size; ++i) {
			temp[i] = i < slen ? byteSrc[i] : b;
		}

		System.arraycopy(temp, 0, result, 0, size);
		return result;
	}
	
	public static byte[] rpad(byte[] byteSrc, byte[] b, int size, String charsetName)
			throws UnsupportedEncodingException {
		byte[] result = new byte[size];
		byte[] temp = new byte[size];
		int slen = byteSrc == null ? 0 : byteSrc.length;

		for (int i = 0; i < size; ++i) {
			temp[i] = i < slen ? byteSrc[i] : b[0];
		}

		System.arraycopy(temp, 0, result, 0, size);
		return result;
	}

	public static byte[] rpad(byte[] byteSrc, char c, int size, String charsetName)
			throws UnsupportedEncodingException {
		byte[] result = new byte[size];
		String charStr = String.valueOf(c);
		ByteBuffer buffer = ByteBuffer.wrap(charStr.getBytes(charsetName));
		byte b = buffer.get();
		if (charStr.length() == 1) {
			byte[] temp = new byte[size];
			int slen = byteSrc == null ? 0 : byteSrc.length;

			for (int i = 0; i < size; ++i) {
				temp[i] = i < slen ? byteSrc[i] : b;
			}

			System.arraycopy(temp, 0, result, 0, size);
		}

		return result;
	}

	public static byte[] rpadChar(byte[] byteSrc, String padStr, int len, String charsetName)
			throws UnsupportedEncodingException {
		int srcLength = byteSrc == null ? 0 : byteSrc.length;
		byte[] temp = new byte[len];
		byte[] tempBytePad = null;
		int padLen = 0;
		if (padStr != null && !"".equals(padStr)) {
			tempBytePad = padStr.getBytes(charsetName);
			padLen = tempBytePad.length;
		}

		if (srcLength >= 0 && srcLength < len) {
			int endCount = len - srcLength;
			StringBuffer sb = null;
			if (srcLength > 0) {
				sb = new StringBuffer(byteSrc.toString());
			} else {
				sb = new StringBuffer();
			}

			for (int i = 0; i < endCount; ++i) {
				if (padLen >= 1) {
					sb.append(tempBytePad.toString());
					i += padLen;
				}
			}

			byte[] byteVal = sb.toString().getBytes(charsetName);
			System.arraycopy(byteVal, 0, temp, 0, len);
		} else if (srcLength == len) {
			temp = byteSrc;
		} else if (srcLength > len) {
			System.arraycopy(byteSrc, 0, temp, 0, len);
		}

		return temp;
	}

	public static String rpad(byte[] byteSrc, String padStr, int len, String charsetName)
			throws UnsupportedEncodingException {
		int srcLength = byteSrc == null ? 0 : byteSrc.length;
		byte[] temp = new byte[len];
		byte[] tempBytePad = null;
		int padLen = 0;
		if (padStr != null && !"".equals(padStr)) {
			tempBytePad = padStr.getBytes(charsetName);
			padLen = tempBytePad.length;
		}

		if (srcLength >= 0 && srcLength < len) {
			int endCount = len - srcLength;
			StringBuffer sb = null;
			if (srcLength > 0) {
				sb = new StringBuffer(byteSrc.toString());
			} else {
				sb = new StringBuffer();
			}

			for (int i = 0; i < endCount; ++i) {
				if (padLen >= 1) {
					sb.append(tempBytePad.toString());
					i += padLen;
				}
			}

			byte[] byteVal = sb.toString().getBytes(charsetName);
			System.arraycopy(byteVal, 0, temp, 0, len);
		} else if (srcLength == len) {
			temp = byteSrc;
		} else if (srcLength > len) {
			System.arraycopy(byteSrc, 0, temp, 0, len);
		}

		return temp.toString();
	}

	public static byte[] rpad(String s, char c, int size, String charsetName) throws Exception {
		byte[] src = null;
		if ("KSC-5601".equals(charsetName)) {
			src = s.getBytes();
		} else {
			src = s.getBytes(charsetName);
		}

		if (charsetName == null) {
			return rpad(src, (byte) c, size, charsetName);
		} else {
			return "KSC-5601".equals(charsetName)
					? rpadKSCSpace(src, (byte) c, size)
					: rpad(src, (byte) c, size, charsetName);
		}
	}

	public static String rpad(String str, String padStr, int size, String charsetName)
			throws UnsupportedEncodingException {
		byte[] byteSrc = str.getBytes(charsetName);
		int strLength = byteSrc.length;
		byte[] temp = new byte[size];
		byte[] tempBytePad = null;
		int padLen = 0;
		if (padStr != null && !"".equals(padStr)) {
			tempBytePad = padStr.getBytes(charsetName);
			padLen = tempBytePad.length;
		}

		if (strLength >= 0 && strLength < size) {
			int endCount = size - strLength;
			StringBuffer sb = new StringBuffer(str);

			for (int i = 0; i < endCount; ++i) {
				if (padLen >= 1) {
					sb.append(tempBytePad.toString());
					i += padLen;
				}
			}

			byte[] byteVal = sb.toString().getBytes(charsetName);
			System.arraycopy(byteVal, 0, temp, 0, size);
		} else if (strLength == size) {
			temp = byteSrc;
		} else if (strLength > size) {
			System.arraycopy(byteSrc, 0, temp, 0, size);
		}

		return temp.toString();
	}

	public static String rpadByte(String src, char c, int maxlength, String charsetName)
			throws UnsupportedEncodingException {
		String returnStr = "";
		byte[] buffer = new byte[maxlength];

		for (int i = 0; i < buffer.length; ++i) {
			buffer[i] = (byte) c;
		}

		byte[] strByte = src.getBytes(charsetName);
		int loopCnt = buffer.length > strByte.length ? strByte.length : buffer.length;

		for (int i = 0; i < loopCnt; ++i) {
			buffer[i] = strByte[i];
		}

		returnStr = new String(buffer, 0, maxlength, charsetName);
		return returnStr;
	}

	public static byte[] encodeKsc(byte[] src) throws Exception {
		byte BIT_MASK = -128;
		int length = src == null ? 0 : src.length;
		byte[] buffer = new byte[length * 2];
		int index = 0;

		for (int i = 0; i < length; ++index) {
			if ((src[i] & BIT_MASK) == BIT_MASK) {
				buffer[index] = src[i];
				++index;
				++i;
				buffer[index] = src[i];
			} else {
				buffer[index] = -93;
				if (src[i] < 32) {
					++index;
					buffer[index] = 0;
				} else if (src[i] == 32) {
					buffer[index] = -95;
					++index;
					buffer[index] = -95;
				} else {
					++index;
					buffer[index] = (byte) (src[i] | -128);
				}
			}

			++i;
		}

		byte[] result = new byte[index];
		System.arraycopy(buffer, 0, result, 0, index);
		return result;
	}

	public static byte[] rpadKSCSpace(byte[] src, byte b, int size) throws Exception {
		byte[] result = new byte[size];
		Arrays.fill(result, b);
		byte[] enc = encodeKsc(src);
		System.arraycopy(enc, 0, result, 0, enc.length > size ? size : enc.length);
		return result;
	}

	public static byte[] lpad(byte[] s, byte b, int length) {
		byte[] result = new byte[length];
		int slen = s == null ? 0 : s.length;
		int count = length - slen;

		for (int i = length; i > 0; --i) {
			result[i - 1] = i <= count ? b : s[slen - (length - i) - 1];
		}

		return result;
	}

	public static byte[] lpad(String s, char c, int length, String charsetName) throws Exception {
		byte[] result;
		if (s == null) {
			result = new byte[length];
		} else if ("".equals(s)) {
			result = new byte[length];
		} else {
			result = new byte[length];			
			if (charsetName == null) {
				charsetName = "UTF-8";
			}

			if ("".equals(charsetName)) {
				charsetName = "UTF-8";
			} else if ("KSC_5601_F".equals(charsetName)) {
				charsetName = "KSC_5601";
			}

			byte[] src = s.getBytes(charsetName);
			int slen = src.length;
			int count = length - slen;

			for (int i = length; i > 0; --i) {
				result[i - 1] = i <= count ? (byte) c : src[slen - (length - i) - 1];
			}
		}

		return result;
	}

	public static byte[] lpad(String s, byte b, int length, String charsetName) throws Exception {
		byte[] result;
		if (s == null) {
			result = new byte[length];
		} else if ("".equals(s)) {
			result = new byte[length];
		} else {
			result = new byte[length];
			if (charsetName == null) {
				charsetName = "UTF-8";
			}

			if ("".equals(charsetName)) {
				charsetName = "UTF-8";
			} else if ("KSC_5601_F".equals(charsetName)) {
				charsetName = "KSC_5601";
			}

			byte[] src = s.getBytes(charsetName);
			int slen = src.length;
			int count = length - slen;

			for (int i = length; i > 0; --i) {
				result[i - 1] = i <= count ? b : src[slen - (length - i) - 1];
			}
		}

		return result;
	}

	public static byte[] lpad(byte[] src, byte b, int length, String charsetName) throws Exception {
		byte[] result = null;
		int slen = src == null ? 0 : src.length;
		if (length > 0) {
			result = new byte[length];
		}

		if (slen > 0 && b > 0) {
			int count = length - slen;

			for (int i = length; i > 0; --i) {
				result[i - 1] = i <= count ? b : src[slen - (length - i) - 1];
			}
		}

		return result;
	}

	public static byte[] lpad(byte[] byteSrc, String padStr, int len, String charsetName)
			throws UnsupportedEncodingException {
		int srcLength = byteSrc == null ? 0 : byteSrc.length;
		byte[] temp = new byte[len];
		byte[] tempBytePad = null;
		int padLen = 0;
		if (padStr != null && !"".equals(padStr)) {
			tempBytePad = padStr.getBytes(charsetName);
			padLen = tempBytePad.length;
		}

		if (srcLength >= 0 && srcLength < len) {
			int endCount = len - srcLength;
			StringBuffer sb = new StringBuffer("");

			for (int i = 0; i < endCount; ++i) {
				if (padLen >= 1) {
					sb.append(tempBytePad.toString());
					i += padLen;
				}
			}

			if (srcLength > 0) {
				sb.append(byteSrc.toString());
			}

			byte[] byteVal = sb.toString().getBytes(charsetName);
			System.arraycopy(byteVal, 0, temp, 0, len);
		} else if (srcLength == len) {
			temp = byteSrc;
		} else if (srcLength > len) {
			System.arraycopy(byteSrc, 0, temp, 0, len);
		}

		return temp;
	}

	public static byte[] rtrimByte(byte[] src, String charsetName) throws UnsupportedEncodingException {
		byte[] temp = src.toString().getBytes(charsetName);

		int len;
		for (len = temp == null ? 0 : temp.length; len > 0 && temp[len] <= 32; --len) {
			;
		}

		int size = len + 1;
		byte[] result = new byte[size];
		System.arraycopy(temp, 0, result, 0, size);
		return result;
	}

	public static byte[] rtrim(byte[] src) {
		int len;
		for (len = src == null ? 0 : src.length; len > 0 && src[len] <= 32; --len) {
			;
		}

		int size = len + 1;
		byte[] result = new byte[size];
		System.arraycopy(src, 0, result, 0, size);
		return result;
	}

	public static String rtrim(byte[] b, String charsetName) throws UnsupportedEncodingException {
		byte[] temp = b.toString().getBytes(charsetName);
		int len = temp.length;

		String str;
		for (str = temp.toString(); len > 0 && str.charAt(len - 1) <= ' '; --len) {
			;
		}

		return str.substring(0, len);
	}

	public static String rtrim(String str) {
		int len;
		for (len = str.length(); len > 0 && str.charAt(len - 1) <= ' '; --len) {
			;
		}

		return str.substring(0, len);
	}

	public static boolean isEmpty(String str) {
		boolean isEmpty = true;
		if (str != null && !"".equals(str)) {
			isEmpty = false;
		}

		return isEmpty;
	}

	public static byte[] assemblyBytes(byte[] oldBytes, byte[] addBytes) {
		int oldSize = oldBytes == null ? 0 : oldBytes.length;
		int addSize = addBytes == null ? 0 : addBytes.length;
		byte[] message = new byte[oldSize + addSize];
		System.arraycopy(oldBytes, 0, message, 0, oldSize);
		System.arraycopy(addBytes, 0, message, oldSize, addSize);
		return message;
	}
}
