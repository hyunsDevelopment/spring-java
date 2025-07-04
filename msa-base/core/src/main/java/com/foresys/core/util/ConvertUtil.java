package com.foresys.core.util;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.foresys.core.handler.CustomObjectMapper;
import org.springframework.jdbc.support.JdbcUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConvertUtil {

	/**
	 * object -> map
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> objToMap(Object obj) throws Exception {
		return new ObjectMapper().convertValue(obj, Map.class);
	}

	/**
	 * list<object> -> List<Map<String, Object>>
	 * @param objList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> objToMapList(List<Object> objList) throws Exception {
	    ObjectMapper objectMapper = new ObjectMapper();
	    List<Map<String, Object>> tmp = new ArrayList<>();
	    if(objList != null) {
	    	for(Object obj : objList) {
	    		tmp.add(objectMapper.convertValue(obj, Map.class));
	    	}
	    }
	    return tmp;
	}

	/**
	 * map -> object
	 * @param <T>
	 * @param map
	 * @param cls
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Object mapToObj(Map<String, Object> map, Class cls) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new CustomObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configOverride(String.class).setSetterInfo(JsonSetter.Value.forValueNulls(Nulls.AS_EMPTY));
		return mapper.convertValue(map, cls);
	}

	/**
	 * object -> json string
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String ObjToJsonstring(Object obj) throws Exception {
		return new ObjectMapper().writeValueAsString(obj);
	}

	/**
	 * object -> xml string
	 * @param <T>
	 * @param t
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static <T> String objToXmlString(Object t, T data) throws Exception {
		ObjectMapper mapper = new XmlMapper();
		mapper.configOverride(String.class).setSetterInfo(JsonSetter.Value.forValueNulls(Nulls.AS_EMPTY));
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + mapper.writeValueAsString("");
	}

	/**
	 * xml -> map
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map xmlToMap(String xml) throws Exception {
		ObjectMapper mapper = new XmlMapper();
		return mapper.readValue(xml, Map.class);
	}

	/**
	 * Convert a key inside map using "camelCase" to a corresponding column name with underscores.
	 * @param map
	 * @return
	 */
	public static Map<String, Object> convertKeysCamelToUnderscore(Map<String, Object> map) {
		Map<String, Object> tmpMap = new HashMap<>();
		if (map == null || map.keySet() == null)
			return tmpMap;

		for (String key : map.keySet()) {
			tmpMap.put(JdbcUtils.convertPropertyNameToUnderscoreName(key), map.get(key));
		}
		
		return tmpMap;
	}

	/**
	 * Convert a key inside map with underscores to the corresponding property name using "camelCase".
	 * @param map
	 * @return
	 */
	public static Map<String, Object> convertKeysUnderscoreToCamel(Map<String, Object> map) {
		Map<String, Object> tmpMap = new HashMap<>();
		if (map == null || map.keySet() == null)
			return tmpMap;

		for (String key : map.keySet()) {
			tmpMap.put(JdbcUtils.convertUnderscoreNameToPropertyName(key), map.get(key));
		}
		
		return tmpMap;
	}

}
