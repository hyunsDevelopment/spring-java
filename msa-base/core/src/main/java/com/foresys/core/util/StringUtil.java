/**
 * ==================================================================
 *
 * (주)포이시스., Software License, Version 1.0
 *
 * Copyright (c) 2008 (주)포이시스.,
 * 서울 금천구 가산동 371-28 우림라이온스밸리 B동 1412호
 * All rights reserved.
 *
 * DON'T COPY OR REDISTRIBUTE THIS SOURCE CODE WITHOUT PERMISSION.
 * THIS SOFTWARE IS PROVIDED ''AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL (주)포이시스 OR ITS
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, \
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * For more information on this product, please see
 * http://www.foresys.co.kr
 *
 */

/*
 * @(#)StringUtil.java 1.10 2008. 08. 13
 */

package com.foresys.core.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

@Slf4j
public class StringUtil {

	public StringUtil() {
		super();
	}
	
	/**
	 * null or empty string -> 0
	 *
	 * @param param
	 * @param key
	 * @return
	 */
	public static int getIntValueInMap(Map<String, Object> param, String key) {
		return getIntValueInMap(param, key, 0);
	}
	
	public static int getIntValueInMap(Map<String, Object> param, String key, int defaultValue) {
		if (param.get(key) instanceof BigDecimal) {
			return ((BigDecimal) param.get(key)).intValue();
		} else if (param.get(key) instanceof Number) {
	        return ((Number) param.get(key)).intValue();
	    } else {
			return param.get(key) == null || param.get(key).equals("") ? defaultValue
					: Integer.parseInt((String) param.get(key));
		}
	}
	
	/**
	 * null or empty string -> 0.0
	 *
	 * @param param
	 * @param key
	 * @return
	 */
	public static double getDoubleValueInMap(Map<String, Object> param,
			String key) {
		if (param.get(key) instanceof BigDecimal) {
			return ((BigDecimal) param.get(key)).doubleValue();
		} else {
			return param.get(key) == null || param.get(key).equals("") ? 0
					: Double.parseDouble((String) param.get(key));
		}
	}

	/**
	 * null string -> defaultValue
	 *
	 * @param param
	 * @param key
	 * @return
	 */
	public static String getStringInMap(Map<String, Object> param, String key, String defaultValue) {
		String value = "";

		if (param != null) {
			value = param.get(key) == null ? defaultValue : (String) param.get(key);
		}

		return value;
	}
	
	public static String getStringInMap(Map<String, Object> param, String key) {
		return getStringInMap(param, key, "");	
	}
	

	/**
	 * <p>
	 * str이 null 이거나 ""일 경우 판단한다.
	 * </p>
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @param str 문자열
	 * @return null 이거나 "" 여부
	 */
	public static boolean isEmpty(Object str) {
		if (str != null) {
			if (!"".equals(str.toString().trim())) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	/**
	 * 형변환 Object >>> int
	 */
	public static int getToInt(Object obj) {
		int rtStr = 0;

		if (obj != null) {
			if (obj instanceof Short) {
				rtStr = ((Short) obj).intValue();
			} else if (obj instanceof Integer) {
				rtStr = ((Integer) obj).intValue();
			} else if (obj instanceof Long) {
				rtStr = ((Long) obj).intValue();
			} else if (obj instanceof Float) {
				rtStr = ((Float) obj).intValue();
			} else if (obj instanceof Double) {
				rtStr = ((Double) obj).intValue();
			} else if (obj instanceof BigDecimal) {
				rtStr = ((BigDecimal) obj).intValue();
			} else if (obj instanceof String) {
				if (!"".equals((String) obj)) {
					rtStr = Integer.parseInt((String) obj);
				} else {
					log.error("Item Value is empty string!");
				}
			} else {
				log.error("Item has unidentified Int Number Type!");
			}
		} else {
			log.error("Item is null");
		}

		return rtStr;
	}

	/**
	 * 형변환 Object >>> long
	 */
	public static long getToLong(Object obj) {
		long rtStr = 0;

		if (obj != null) {
			if (obj instanceof Short) {
				rtStr = ((Short) obj).longValue();
			} else if (obj instanceof Integer) {
				rtStr = ((Integer) obj).longValue();
			} else if (obj instanceof Long) {
				rtStr = ((Long) obj).longValue();
			} else if (obj instanceof Float) {
				rtStr = ((Float) obj).longValue();
			} else if (obj instanceof Double) {
				rtStr = ((Double) obj).longValue();
			} else if (obj instanceof BigDecimal) {
				rtStr = ((BigDecimal) obj).longValue();
			} else if (obj instanceof String) {
				if (!"".equals((String) obj)) {
					rtStr = Long.parseLong((String) obj);
				} else {
					log.error("Item Value is empty string!");
				}
			} else {
				log.error("Item has unidentified Long Number Type!");
			}
		} else {
			log.error("Item is null");
		}

		return rtStr;
	}
	
	/**
	 * 형변환 Object >>> Double
	 */
	public static double getToDouble(Object obj) {
		double rtStr = 0.0;
		
		if (obj != null) {
			if (obj instanceof Short) {
				rtStr = ((Short) obj).doubleValue();
			} else if (obj instanceof Integer) {
				rtStr = ((Integer) obj).doubleValue();
			} else if (obj instanceof Long) {
				rtStr = ((Long) obj).doubleValue();
			} else if (obj instanceof Float) {
				rtStr = ((Float) obj).doubleValue();
			} else if (obj instanceof Double) {
				rtStr = ((Double) obj).doubleValue();
			} else if (obj instanceof BigDecimal) {
				rtStr = ((BigDecimal) obj).doubleValue();
			} else if (obj instanceof String) {
				if (!"".equals((String) obj)) {
					rtStr = Double.parseDouble((String) obj);
				} else {
					log.error("Item Value is empty string!");
				}
			} else {
				log.error("Item has unidentified Double Number Type!");
			}
		} else {
			log.error("Item is null");
		}

		return rtStr;
	}
	
	/**
	 * 형변환 Object >>> Float
	 */
	public static float getToFloat(Object obj) {
		float rtStr = 0;
		
		if (obj != null) {
			if (obj instanceof Short) {
				rtStr = ((Short) obj).floatValue();
			} else if (obj instanceof Integer) {
				rtStr = ((Integer) obj).floatValue();
			} else if (obj instanceof Long) {
				rtStr = ((Long) obj).floatValue();
			} else if (obj instanceof Float) {
				rtStr = ((Float) obj).floatValue();
			} else if (obj instanceof Double) {
				rtStr = ((Double) obj).floatValue();
			} else if (obj instanceof BigDecimal) {
				rtStr = ((BigDecimal) obj).floatValue();
			} else if (obj instanceof String) {
				if (!"".equals((String) obj)) {
					rtStr = Float.parseFloat((String) obj);
				} else {
					log.error("Item Value is empty string!");
				}
			} else {
				log.error("Item has unidentified Float Number Type!");
			}
		} else {
			log.error("Item is null");
		}

		return rtStr;
	}

	public static String LPAD(String str, int len, String append) {
		String rtn = str;

		if (str.length() < len) {
			for (int i = (len - str.length()); i > 0; i--) {
				rtn = append + rtn;
			}
		} else {
			rtn = str.substring(0, len);
		}
		
		return rtn;
	}
	
	public static String RPAD(String str, int len, String append) {
		String rtn = str;

		if (str.length() < len) {
			for (int i = (len - str.length()); i > 0; i--) {
				rtn = rtn + append;
			}
		} else {
			rtn = str.substring(0, len);
		}
		
		return rtn;
	}

	/**
	 * [현금변환 long => String]
	 * 
	 * <pre>
	 *  현금 변환 (예) long  123000000 => 123,000,000
	 * </pre>
	 * 
	 * @param currency --> long
	 * @return currency --> String(변환된 문자열)
	 */
	public static String setCurrency(Object value) throws Exception {
		long currency = getToLong(value);

		int nStringsize = 0;
		int nSize = 0;
		int nMod = 0;

		String strCurrency = "";
		strCurrency = "" + currency;

		nStringsize = strCurrency.length();

		if (nStringsize == 0)
			return "0";

		StringBuffer sb1 = new StringBuffer(strCurrency);

		nSize = nStringsize / 3;
		nMod = nStringsize % 3;

		if (nMod == 0) {
			nSize = nSize - 1;
			nMod = 3;
		}

		for (int nX = 0; nX < nSize; nX++) {
			if (nStringsize <= 3)
				return strCurrency;
			else {
				sb1.insert(nMod + (nX * 3), ",");
				nMod++;
			}
		}
		
		return sb1.toString();
	}

	/**
	 * 계좌번호 format setting
	 */
	public static String formatAcctNo(String str) {
		String result = "";
		String acctNo = str.replaceAll("-", "");

		if (acctNo.length() > 8) {
			acctNo = str.replaceAll("-", "");
			acctNo = acctNo.substring(0, 3) + "-" + acctNo.substring(3, 5) + "-" + acctNo.substring(5, 7) + "-"
					+ acctNo.substring(7, acctNo.length());
			result = acctNo;
		} else {
			result = str;
		}
		
		return result;
	}

	public static String toString(Object obj) {
		String retStr = "";
		if (obj != null)
			retStr = obj.toString();
		
		return retStr;
	}

	public static String getReqString(@SuppressWarnings("rawtypes") Map hmap, String param_name) throws Exception {
		String param = "";
		try {
			if (param_name == null || param_name.length() == 0)
				param = "";
			else
				param = hmap.get(param_name).toString();
		} catch (Exception ex) {
			param = "";
		}
		
		return param;
	}

	public static String deleteChar(String value, String deleteStr) {
		if (value == null || value.equals(""))
			return value;

		StringBuffer temp = new StringBuffer("");
		StringTokenizer st = new StringTokenizer(value, deleteStr);

		while (st.hasMoreTokens()) {
			temp.append(st.nextToken());
		}

		return temp.toString();
	}

	public static String msg_string(String str, int len, boolean align) {
		String msg = "";
		int str_len = 0;

		if (str == null)
			str = " ";
		
		try {
			String str_kr = to_8859(str);
			str_len = str_kr.length();

			if (len == str_len)
				msg = str;
			else if (len < str_len)
				msg = to_euckr(str.substring(0, len));
			else {
				String filler = "";
				for (int i = 0; i < (len - str_len); i++)
					filler += " ";
				if (align)
					msg = filler + str;
				else
					msg = str + filler;
			}
		} catch (Exception ex) {}
		
		return msg;
	}

	public static String to_8859(String str) {
		String t = null;
		try {
			t = new String(str.getBytes("EUC-KR"), "8859_1");
		} catch (Exception e) {}
		
		return t;
	}

	public static String to_euckr(String str) {
		String t = null;
		try {
			t = new String(str.getBytes("8859_1"), "euc_kr");
		} catch (Exception e) {}
		
		return t;
	}
	
	public static String cutByByte(String str, int len) {
		byte[] bytes = str.getBytes();
		if (bytes.length > len) {
			byte[] truncatedBytes = new byte[len];
			System.arraycopy(bytes, 0, truncatedBytes, 0, len);
			str = new String(truncatedBytes);
		}
		return str;
	}

	/**
	 * @desc  : 코드를 문자열로 치환 공통
	 *  ->  [C1] -> '||CUST_NM||' 
	 * @author : Gye Hwan Kim
	 * @creatDate : 2024. 6. 4.
	 * @param Message, code, 치환값
	 * @return String
	 * @throws Exception
	 */    
	public static String replace(String str, String pattern, String replace) {
		  if( str == null || str.length() < 1 )
		    return "";

		  int s = 0;
		  int e = 0;

		  StringBuffer result = new StringBuffer();
		  
		  while( (e = str.indexOf(pattern, s)) >= 0 ) {
		    result.append(str.substring(s, e));
		    result.append(replace);
		    s = e+pattern.length();
		  }

		  result.append(str.substring(s));

		  return result.toString();
	}
	
	/**
	 * @desc  : 리스트 -> Map 치환
	 * @author : Gye Hwan Kim
	 * @creatDate : 2024. 6. 4.
	 * @param (List<Map<String, Object>>, Col1 , Col2) 
	 *        -> Map (key : Col , value : Col2)
	 * @return Map<String,Object>
	 * @throws Exception
	 */ 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap<String,Object> toListHashMap(List<?> arglist, String mapdata1, String mapdata2){
		HashMap<String,Object> map = new HashMap<String, Object>();
		HashMap<String, Object> item;

		for (int k = 0; k < arglist.size(); k++) {
			item = (HashMap) arglist.get(k);
			map.put(item.get(mapdata1).toString() ,item.get(mapdata2));
		}
		return map;
	}
	
}
