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
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Validated
public class FullTextComponent {

    public Object getReqHead(@NotNull Object obj) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        FullTextInfo fti = obj.getClass().getAnnotation(FullTextInfo.class);
        if(fti != null) {
            if(fti.reqHeadClass() == Void.class)
                return null;

            Object reqHead = fti.reqHeadClass().getConstructor().newInstance();

            HeadValue[] values = fti.headValues();
            for(HeadValue headValue : values) {
                Field[] fields = reqHead.getClass().getDeclaredFields();
                for(Field field : fields) {
                    if(field.getName().equals(headValue.name())) {
                        field.setAccessible(true);
                        switch (field.getType().getSimpleName().toLowerCase()) {
                            case "int":
                            case "integer":
                                field.set(reqHead, StringUtil.getToInt(headValue.value()));
                                break;
                            case "long":
                                field.set(reqHead, StringUtil.getToLong(headValue.value()));
                                break;
                            case "float":
                                field.set(reqHead, StringUtil.getToFloat(headValue.value()));
                                break;
                            case "double":
                                field.set(reqHead, StringUtil.getToDouble(headValue.value()));
                                break;
                            default:
                                field.set(reqHead, field.getType().cast(headValue.value()));
                                break;
                        }
                        break;
                    }
                }
            }

            return reqHead;
        }

        return null;
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
                        int len = StringUtil.getToInt(field.get(obj));

                        return len;
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
        for(Field field : fields) {
            field.setAccessible(true);
            if(field.get(obj) instanceof List<?> l) {
                for(Object cObj : l) {
                    ByteArrayVO cByteArrayVO = this.getByteArray(cObj, charset);
                    byteArrayVO.setBytes(PadUtil.assemblyBytes(byteArrayVO.getBytes(), cByteArrayVO.getBytes()));
                    byteArrayVO.setExceptLen(byteArrayVO.getExceptLen() + cByteArrayVO.getExceptLen());
                }
            }else {
                if(field.getAnnotation(FullTextField.class) != null && field.getAnnotation(FullTextField.class).length() > 0) {
                    FullTextField ft = field.getAnnotation(FullTextField.class);
                    byte[] value = new byte[0];
                    if(!field.getType().getSimpleName().isEmpty() && "int|integer|long".contains(field.getType().getSimpleName().toLowerCase()))
                        value = PadUtil.lpad(field.get(obj) != null ? field.get(obj).toString() : "", '0', ft.length(), charset);
                    else if(ft.pad() == PadType.LEFT)
                        value = PadUtil.lpad(field.get(obj) != null ? field.get(obj).toString() : "", ft.padChar(), ft.length(), charset);
                    else if(ft.pad() == PadType.RIGHT)
                        value = PadUtil.rpad(field.get(obj) != null ? field.get(obj).toString() : "", ft.padChar(), ft.length(), charset);
                    byteArrayVO.setBytes(PadUtil.assemblyBytes(byteArrayVO.getBytes(), value));
                    if(ft.exceptLenField())
                        byteArrayVO.setExceptLen(byteArrayVO.getExceptLen() + value.length);
                }
            }
        }

        return byteArrayVO;
    }

    public ObjectVO getObject(@NotNull Object obj, @NotNull byte[] data, int index, String charset) throws Exception {
        Field[] fields = obj.getClass().getDeclaredFields();
        List<Map<String, Object>> cntList = new ArrayList<>();
        for(Field field : fields) {
            field.setAccessible(true);
            if(List.class.isAssignableFrom(field.getType())) {
                List<Object> tmp = new ArrayList<>();
                for(Map<String, Object> tmpMap : cntList) {
                    if(StringUtil.getStringInMap(tmpMap, "nm").equals(field.getName())) {
                        int cnt = StringUtil.getIntValueInMap(tmpMap, "cnt");
                        for(int i = 0; i < cnt; i++) {
                            Object cObj = Class.forName(((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].getTypeName()).getConstructor().newInstance();
                            ObjectVO tObj = getObject(cObj, data, index, charset);
                            tmp.add(tObj.getObj());
                            index = tObj.getIndex();
                        }
                    }
                }
                field.set(obj, tmp);
            }else if(field.getAnnotation(FullTextField.class) != null && field.getAnnotation(FullTextField.class).length() > 0) {
                FullTextField ft = field.getAnnotation(FullTextField.class);
                byte[] tmp = new byte[ft.length()];
                if(data.length >= (index + tmp.length)) {
                    System.arraycopy(data, index, tmp, 0, tmp.length);
                    String value = new String(tmp, charset).strip();
                    switch (field.getType().getSimpleName().toLowerCase()) {
                        case "int":
                        case "integer":
                            field.set(obj, StringUtil.getToInt(value));
                            break;
                        case "long":
                            field.set(obj, StringUtil.getToLong(value));
                            break;
                        case "float":
                            field.set(obj, StringUtil.getToFloat(value));
                            break;
                        case "double":
                            field.set(obj, StringUtil.getToDouble(value));
                            break;
                        default:
                            field.set(obj, field.getType().cast(value));
                            break;
                    }
                    index += tmp.length;
                    if(!ft.tgtListFieldName().isEmpty()) {
                        cntList.add(Map.of("nm", ft.tgtListFieldName(), "cnt", field.get(obj)));
                    }
                }
            }
        }

        return ObjectVO.builder()
                .obj(obj)
                .index(index)
                .build();
    }

}
