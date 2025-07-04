package com.foresys.app1.system.handler;

import org.springframework.jdbc.support.JdbcUtils;

import java.util.HashMap;

/**
 *---------------------------------------------------------------------
 * @Title : Mybatic 에서 resultType 이 Map 인 경우 Key 를 카멜 방식으로 변경
 * @Desc  : 주의 사항
 *          1. DbCamelHashMap 인 경우 put 사용시 key 값이 convert 되므로 putCamel 사용
 *           ex ) a.put("loanType","111") -> key 가 loantype 로 변경됨
 *                a.putCamel("loanType","111") -> key 가 loanType
 * @author: foresys
 *----------------------------------------------------------------------
 *                   변         경         사         항
 *----------------------------------------------------------------------
 *    DATE			AUTHOR                      DESCRIPTION
 * ----------  ---------------------  ----------------------------------
 * 2024.03.21.  	foresys  				    최초작성
 *-----------------------------------------------------------------------
 */
public class DbCamelHashMap extends HashMap<String, Object> {

	private static final long serialVersionUID = 3644971232378075998L;

	@Override
	public Object put(String key, Object value) {
		return super.put(JdbcUtils.convertUnderscoreNameToPropertyName(key), value);
	}

	/**
	 * 서비스에서 return Map 에 put 해야하는 경우 사용
	 */
	public Object putCamel(String key, Object value) {
		return this.put(key, value);
	}
}
