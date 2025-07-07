package com.foresys.app2.fulltext.component;

import com.foresys.app2.fulltext.annotation.FullTextField;
import com.foresys.app2.fulltext.annotation.FullTextInfo;
import com.foresys.app2.fulltext.annotation.HeadValue;
import com.foresys.app2.fulltext.model.ByteArrayVO;
import com.foresys.app2.fulltext.model.ObjectVO;
import com.foresys.app2.fulltext.type.LenType;
import com.foresys.app2.fulltext.type.PadType;
import com.foresys.core.util.PadUtil;
import com.foresys.core.util.StringUtil;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Validated
public class FullTextComponent {

    public Object getReqHead(@NotNull Object obj) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        FullTextInfo fti = obj.getClass().getAnnotation(FullTextInfo.class);
        if (fti == null || fti.reqHeadClass() == Void.class) {
            return null;
        }
    
        // 1. reqHead 객체 생성
        Object reqHead = fti.reqHeadClass().getDeclaredConstructor().newInstance();
    
        // 2. 필드 캐시 (Map<String, Field>)
        Field[] declaredFields = reqHead.getClass().getDeclaredFields();
        Map<String, Field> fieldMap = new HashMap<>();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            fieldMap.put(field.getName(), field);
        }
    
        // 3. HeadValue 설정
        for (HeadValue headValue : fti.headValues()) {
            Field field = fieldMap.get(headValue.name());
            if (field == null) continue;
    
            String typeName = field.getType().getSimpleName().toLowerCase();
            String rawValue = headValue.value();
    
            Object parsedValue = switch (typeName) {
                case "int", "integer" -> StringUtil.getToInt(rawValue);
                case "long"           -> StringUtil.getToLong(rawValue);
                case "float"          -> StringUtil.getToFloat(rawValue);
                case "double"         -> StringUtil.getToDouble(rawValue);
                default               -> field.getType().cast(rawValue);
            };
    
            field.set(reqHead, parsedValue);
        }
    
        return reqHead;
    }

    public Class<?> getReqHeadClass(@NotNull Object obj) {
        FullTextInfo fti = obj.getClass().getAnnotation(FullTextInfo.class);
        if(fti != null)
            return fti.reqHeadClass() == Void.class ? null : fti.reqHeadClass();

        return null;
    }

    public Class<?> getResHeadClass(@NotNull Object obj) {
        FullTextInfo fti = obj.getClass().getAnnotation(FullTextInfo.class);
        if(fti != null)
            return fti.resHeadClass() == Void.class ? null : fti.resHeadClass();

        return null;
    }

    public Class<?> getResBodyClass(@NotNull Object obj) {
        FullTextInfo fti = obj.getClass().getAnnotation(FullTextInfo.class);
        if(fti != null)
            return fti.resBodyClass() == Void.class ? null : fti.resBodyClass();

        return null;
    }

    public Object setLenField(LenType type, @NotNull Object obj, int len) {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for(Field field : fields) {
                if (field.getAnnotation(FullTextField.class) != null && field.getAnnotation(FullTextField.class).length() > 0) {
                    FullTextField ft = field.getAnnotation(FullTextField.class);
                    if((type == LenType.TOTAL && ft.totLenField())
                            || (type == LenType.HEAD && ft.headLenField())
                            || (type == LenType.BODY && ft.bodyLenField())
                    ) {
                        field.setAccessible(true);
                        field.set(obj, len);

                        break;
                    }
                }
            }
        }catch (Exception ignored) {}

        return obj;
    }

    public int getLenField(LenType type, @NotNull Object obj) {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for(Field field : fields) {
                if(field.getAnnotation(FullTextField.class) != null && field.getAnnotation(FullTextField.class).length() > 0) {
                    FullTextField ft = field.getAnnotation(FullTextField.class);
                    if((type == LenType.TOTAL && ft.totLenField())
                            || (type == LenType.HEAD && ft.headLenField())
                            || (type == LenType.BODY && ft.bodyLenField())
                    ) {
                        field.setAccessible(true);
                        return StringUtil.getToInt(field.get(obj));
                    }
                }
            }
        }catch (Exception ignored) {}

        return 0;
    }

    public int getPrefixFieldLen(Object obj) {
        int len = 0;
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for(Field field : fields) {
                if(field.getAnnotation(FullTextField.class) != null && field.getAnnotation(FullTextField.class).length() > 0) {
                    FullTextField ft = field.getAnnotation(FullTextField.class);
                    if(ft.totLenField())
                        break;
                    else
                        len += ft.length();
                }
            }
        }catch (Exception ignored) {}

        return len;
    }

    public int getTotLenFieldLen(@NotNull Object obj) {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for(Field field : fields) {
                if(field.getAnnotation(FullTextField.class) != null && field.getAnnotation(FullTextField.class).length() > 0) {
                    FullTextField ft = field.getAnnotation(FullTextField.class);
                    if(ft.totLenField())
                        return ft.length();
                }
            }
        }catch (Exception ignored) {}

        return 0;
    }

    public String getRsltCd(@NotNull Object obj) {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getAnnotation(FullTextField.class) != null && field.getAnnotation(FullTextField.class).length() > 0) {
                    FullTextField ft = field.getAnnotation(FullTextField.class);
                    if (ft.rsltCdField()) {
                        return (String) field.get(obj);
                    }
                }
            }
        }catch (IllegalArgumentException | IllegalAccessException ignored) {}

        return null;
    }

    public ByteArrayVO getByteArray(@NotNull Object obj, String charset) throws Exception {
        ByteArrayVO byteArrayVO = new ByteArrayVO();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldValue = field.get(obj);

            // 리스트 필드 처리
            if (fieldValue instanceof List<?> list) {
                for (Object item : list) {
                    ByteArrayVO childByteArray = this.getByteArray(item, charset);
                    byteArrayVO.setBytes(PadUtil.assemblyBytes(byteArrayVO.getBytes(), childByteArray.getBytes()));
                    byteArrayVO.setExceptLen(byteArrayVO.getExceptLen() + childByteArray.getExceptLen());
                }
                continue;
            }

            // 일반 필드 처리
            FullTextField ft = field.getAnnotation(FullTextField.class);
            if (ft == null || ft.length() <= 0) continue;

            String stringValue = fieldValue != null ? fieldValue.toString() : "";
            String typeName = field.getType().getSimpleName().toLowerCase();

            byte[] value;
            if (typeName.equals("int") || typeName.equals("integer") || typeName.equals("long")) {
                value = PadUtil.lpad(stringValue, '0', ft.length(), charset);
            } else {
                char padChar = ft.padChar();
                value = switch (ft.pad()) {
                    case LEFT -> PadUtil.lpad(stringValue, padChar, ft.length(), charset);
                    case RIGHT -> PadUtil.rpad(stringValue, padChar, ft.length(), charset);
                };
            }

            byteArrayVO.setBytes(PadUtil.assemblyBytes(byteArrayVO.getBytes(), value));
            if (ft.exceptLenField()) {
                byteArrayVO.setExceptLen(byteArrayVO.getExceptLen() + value.length);
            }
        }

        return byteArrayVO;
    }

    public ObjectVO getObject(@NotNull Object obj, @NotNull byte[] data, int index, String charset) throws Exception {
        Field[] fields = obj.getClass().getDeclaredFields();

        Map<String, Integer> countMap = new HashMap<>();

        for (Field field : fields) {
            field.setAccessible(true);

            // List 필드 처리
            if (List.class.isAssignableFrom(field.getType())) {
                List<Object> list = new ArrayList<>();
                Integer cnt = countMap.get(field.getName());

                if (cnt != null && cnt > 0) {
                    Class<?> listElementClass = Class.forName(((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].getTypeName());

                    for (int i = 0; i < cnt; i++) {
                        Object cObj = listElementClass.getDeclaredConstructor().newInstance();
                        ObjectVO tObj = getObject(cObj, data, index, charset);
                        list.add(tObj.getObj());
                        index = tObj.getIndex();
                    }
                }
                field.set(obj, list);
                continue;
            }

            // 일반 필드 처리
            FullTextField ft = field.getAnnotation(FullTextField.class);
            if (ft == null || ft.length() <= 0) continue;

            byte[] tmp = new byte[ft.length()];
            if (data.length < index + ft.length()) continue;

            System.arraycopy(data, index, tmp, 0, tmp.length);
            String value = new String(tmp, charset).strip();

            Object parsedValue = switch (field.getType().getSimpleName().toLowerCase()) {
                case "int", "integer" -> StringUtil.getToInt(value);
                case "long" -> StringUtil.getToLong(value);
                case "float" -> StringUtil.getToFloat(value);
                case "double" -> StringUtil.getToDouble(value);
                default -> field.getType().cast(value);
            };

            field.set(obj, parsedValue);
            index += tmp.length;

            if (!ft.tgtListFieldName().isEmpty()) {
                countMap.put(ft.tgtListFieldName(), parsedValue instanceof Number ? ((Number) parsedValue).intValue() : 0);
            }
        }

        return ObjectVO.builder()
                .obj(obj)
                .index(index)
                .build();
    }

}
