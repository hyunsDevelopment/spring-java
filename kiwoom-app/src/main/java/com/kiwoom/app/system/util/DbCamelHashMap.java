package com.kiwoom.app.system.util;

import org.springframework.jdbc.support.JdbcUtils;

import java.io.Serial;
import java.util.HashMap;

public class DbCamelHashMap extends HashMap<String, Object> {

    @Serial
    private static final long serialVersionUID = 3644971232378075998L;

    @Override
    public Object put(String key, Object value) {
        return super.put(JdbcUtils.convertUnderscoreNameToPropertyName(key), value);
    }

    public Object putCamel(String key, Object value) {
        return this.put(key, value);
    }
}
