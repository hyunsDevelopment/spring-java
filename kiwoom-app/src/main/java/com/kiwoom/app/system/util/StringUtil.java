package com.kiwoom.app.system.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class StringUtil {

    /**
     * [Character Set 변경 EUC-KR => ISO8859-1]
     *
     * <pre>
     * String 으로 들어온 문자열의 Character Set 을
     * EUC-KR 에서 ISO8859-1로 변경한다.
     * </pre>
     *
     * @param str --> String
     * @return t --> String
     */
    public String to_8859(String str) {
        String t = null;

        try {
            t = new String(str.getBytes("EUC-KR"), "8859_1");
        } catch (Exception e) {
        }

        return t;
    }

    /**
     * [Character Set 변경 ISO8859-1 => EUC-KR]
     *
     * <pre>
     * String 으로 들어온 문자열의 Character Set 을
     * ISO8859-1 에서 EUC-KR로 변경한다.
     * </pre>
     *
     * @param str --> String
     * @return t --> String
     */
    public String to_euckr(String str) {
        String t = null;

        try {
            t = new String(str.getBytes("8859_1"), "euc_kr");
        } catch (Exception e) {
        }

        return t;
    }

    /**
     * [제목 컷트 하기]
     *
     * <pre>
     * 게스판 등에서 제목의 길이가 너무 길때
     * 일정부분만 보이게 제목을 일정 길이로 자른다.
     * </pre>
     *
     * @param mystr --> String(제목에 해당하는 문자열)
     * @param kmax  --> int(제목의 길이)
     * @return TempHead --> Stirng(입력된 숫자 만큼 컷 된 문자열)
     */
    public String TitleCut(String mystr, int kmax) {

        String TempHead;

        if (mystr == null || mystr.length() < 1)
            return "";

        int kend = 0;
        int k = 0;

        if ((k = mystr.length()) > kmax) {
            TempHead = mystr.substring(kend, kmax) + "...";

        } else
            TempHead = mystr.substring(kend, k);

        return TempHead;

    }

    /**
     * [특수문자 => 문자로 변환]
     *
     * <pre>
     *  스트링에서 특수문자를 문자로 변환하여 리턴
     *  (예 &, ", <, >, ' -> &amp, &quot, &lt, &gt, \')
     * </pre>
     *
     * @param _value --> String
     * @return _value --> String(변환된 문자열)
     */
    public String toHtmlChr(String _value) {

        try {
            if (_value.indexOf('&') != -1 || _value.indexOf('<') != -1 || _value.indexOf('>') != -1
                    || _value.indexOf('\'') != -1 || _value.indexOf('\"') != -1 || _value.indexOf('\n') != -1) {

                StringBuffer sb = new StringBuffer();
                char c;

                for (int i = 0, j = _value.length(); i < j; i++) {
                    if ((c = _value.charAt(i)) == '&')
                        sb.append("&amp;");
                    else if (c == '<')
                        sb.append("〈");
                    else if (c == '>')
                        sb.append("〉");
                    else if (c == '\'')
                        sb.append("‘");
                    else if (c == '\"')
                        sb.append("＂");
                        // else if ( c == '\\' )
                        // sb.append("\\\\");
                        // else if ( c == '\n' )
                        // sb.append("^");
                    else
                        sb.append(c);
                }

                return sb.toString();

            } else {
                return _value;
            }

        } catch (Exception ex) {
            return _value;
        }
    }

    /**
     * @param mystr
     * @return
     * @desc : 문자열 치환 "\n" -> "\n<br>"
     * @author : Kim Min Jae
     * @creatDate : 2021. 3. 15. 오후 8:07:15
     */
    public String n2br(String mystr) {
        return replace(mystr, "\n", "\n<br>");
    }

    /**
     * @param mystr
     * @return
     * @desc : 문자열 치환 "\n<br>" -> "\n"
     * @author : Kim Min Jae
     * @creatDate : 2021. 3. 15. 오후 8:07:00
     */
    public String n3br(String mystr) {
        return replace(mystr, "\n<br>", "\n");
    }

    /**
     * @param mystr
     * @return
     * @desc : 문자열 치환 " " -> "&nbsp"
     * @author : Kim Min Jae
     * @creatDate : 2021. 3. 15. 오후 8:07:03
     */
    public String n4br(String mystr) {

        return replace(mystr, " ", "&nbsp;");
    }

    /**
     * @param mystr
     * @return
     * @desc : 문자열 치환 "\n" -> "<br>"
     * @author : Kim Min Jae
     * @creatDate : 2021. 3. 15. 오후 8:07:05
     */
    public String n5br(String mystr) {
        return replace(mystr, "\n", "<br>");
    }

    /**
     * @param mystr
     * @return
     * @desc : 문자열 치환 " -> * or ' -> *
     * @author : Kim Min Jae
     * @creatDate : 2021. 3. 15. 오후 8:07:09
     */
    public String n6br(String mystr) {

        mystr = replace(mystr, "\"", "*");
        mystr = replace(mystr, "\'", "*");

        return mystr;
    }

    /**
     * 스트링 치환
     */
    public String replace(String str, String pattern, String replace) {

        if (str == null || str.length() < 1)
            return "";

        int s = 0;
        int e = 0;

        StringBuffer result = new StringBuffer();

        while ((e = str.indexOf(pattern, s)) >= 0) {
            result.append(str.substring(s, e));
            result.append(replace);
            s = e + pattern.length();
        }

        result.append(str.substring(s));

        return result.toString();
    }

    /**
     * [현금변환 String => String]
     *
     * <pre>
     *  현금 변환 (예) String  123000000 => 123,000,000
     * </pre>
     *
     * @param amtVal --> String
     * @return currency --> String(변환된 문자열)
     */
    public String setCurrency(String amtVal) throws Exception {

        int maxNStringsize = 3;

        if ("".equals(amtVal) || amtVal == null) {
            return amtVal = "0";
        } else {
            int nStringsize = 0;
            int nSize = 0;
            int nMod = 0;

            String strCurrency = "";
            strCurrency = amtVal;

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
                if (nStringsize <= maxNStringsize)
                    return strCurrency;
                else {
                    sb1.insert(nMod + (nX * 3), ",");
                    nMod++;
                }
            }

            return sb1.toString();
        }
    }

    /**
     * [현금변환 int => String]
     *
     * <pre>
     *  현금 변환 (예) int  123000000 => 123,000,000
     * </pre>
     *
     * @param currency --> int
     * @return currency --> String(변환된 문자열)
     */
    public String setCurrency(int currency) throws Exception {

        int nStringsize = 0;
        int maxNStringsize = 3;
        int nSize = 0;
        int nMod = 0;

        String strCurrency = "";
        strCurrency = Long.toString(currency);

        if (currency < 0) { //음수일 경우
            nStringsize = strCurrency.length() - 1;
            strCurrency = strCurrency.substring(1, nStringsize + 1);
            StringBuffer sb1 = new StringBuffer(strCurrency);

            nSize = nStringsize / 3;
            nMod = nStringsize % 3;

            if (nMod == 0) {
                nSize = nSize - 1;
                nMod = 3;
            }

            for (int nX = 0; nX < nSize; nX++) {
                if (nStringsize <= maxNStringsize) {
                    return strCurrency;
                } else {
                    sb1.insert(nMod + (nX * 3), ",");
                    nMod++;
                }
            }

            return "-" + sb1.toString();

        } else {
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
                if (nStringsize <= maxNStringsize)
                    return strCurrency;
                else {
                    sb1.insert(nMod + (nX * 3), ",");
                    nMod++;
                }
            }

            return sb1.toString();
        }
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
    public String setCurrency(long currency) throws Exception {

        int nStringsize = 0;
        int nSize = 0;
        int nMod = 0;

        String strCurrency = "";
        strCurrency = Long.toString(currency);

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
     * [현금변환 float => String]
     *
     * <pre>
     *  현금 변환 (예) float  123000000 => 123,000,000
     * </pre>
     *
     * @param currency --> float
     * @return currency --> String(변환된 문자열)
     */
    public String setCurrency(float currency) throws Exception {

        int nStringsize = 0;
        int nSize = 0;
        int nMod = 0;

        String strCurrency = "";
        strCurrency = Float.toString(currency);

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
     * -를 +로 변경
     */
    public long ufVal(long num) {

        if (num < 0) {
            return num = num * -1;
        } else {
            return 0;
        }

    }

    /**
     * [전문 길이 채움]
     *
     * @param str --> String(들어온문자열)
     * @param len --> int(채우려는 문자열의 길이)
     * @param set -->
     * @param ba  -->
     * @return str
     */
    public String setStringLength(String str, int len, String set, int ba) {
        if (str == null || str.equals("")) {
            if (ba == 1)
                str = "0";
            else
                str = " ";
        }

        // int a = (int)len/2;

        // if(str.getBytes().length>len){
        // str = str.substring(0,a);
        // }else{

        while (str.getBytes().length < len) {
            if (ba == 1)
                str = set + str;
            else
                str = str + set;
        }

        if (str.getBytes().length > len)
            str = str.substring(0, len);
        // }
        return str;
    }

    /**
     * [Map 담긴 값들중 null을 갖는 값을 제거한다.]
     *
     * @param model --> Map
     * @return item --> HashMap
     */
    public HashMap<String, Object> mapNullCut(HashMap<String, Object> model) {
        Object[] kk = model.keySet().toArray();
        HashMap<String, Object> item = new HashMap<String, Object>();
        String itemOne;

        for (int i = 0; i < kk.length; i++) {
            itemOne = model.get(kk[i]) + "";
            if (!(itemOne == null || itemOne.equals("null")))
                item.put(kk[i].toString().toLowerCase(), itemOne);
        }

        return item;

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public HashMap<String, Object> toListHashMap(List<?> arglist, String mapdata1, String mapdata2) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        HashMap<String, Object> item;

        for (int k = 0; k < arglist.size(); k++) {
            item = (HashMap) arglist.get(k);
            map.put(item.get(mapdata1).toString(), item.get(mapdata2));
        }
        return map;
    }

    /**
     * 6 자리 RANDOM 숫자 구함
     *
     * @return
     */

    static public String makeRdNum() {

        SecureRandom rd = new SecureRandom();

        int choiceNum = 0;

        for (int i = 0; i < 100; i++) {
            choiceNum = rd.nextInt(999999);

            while (choiceNum < 100000) {
                choiceNum = rd.nextInt(999999);
            }
        }

        return String.valueOf(choiceNum);

    }

    /**
     * 랜덤 6자리 문자 가져오기
     *
     * @return
     */
    public static String getRandumStr() {
        // String iStr="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        // // 랜덤으로 표시될 문자

        String iStr = "abcdefghijklmnopqrstuvwxyz0123456789"; // 랜덤으로 표시될 문자

        int strlength = 36; // 62

        SecureRandom rd = new SecureRandom();
        int n = 0;
        String s = "";
        int len = 6;

        for (int x = 1; x <= len; x++) {

            n = rd.nextInt(strlength) + 1;

            s += iStr.substring((n - 1), n);
        }

        return s;
    }

    public int setRegisterCheck(String regNo) {
        String regYy = "";
        String regMm = "";
        String regDd = "";
        int getAge = 0;
        int regSex = 0;
        try {
            regYy = regNo.substring(0, 2);
            regMm = regNo.substring(2, 4);
            regDd = regNo.substring(4, 6);
            regSex = Integer.parseInt(regNo.substring(6, 7));

            if (regSex == 1 || regSex == 2)
                regYy = "19" + regYy;
            else
                regYy = "20" + regYy;

            /*
             * //년나이 Calendar cal= Calendar.getInstance(); int today =
             * cal.get(Calendar.YEAR); int reg_year = Integer.parseInt( regYy); getAge =
             * today - reg_year;
             */
            getAge = getCountYear(regYy + regMm + regDd, ""); // 만나이

        } catch (Exception e) {
            //log.debug("setRegisterCheck=====[age]===>" + e);
        }

        return getAge;
    }

    public int getCalendarAge(int yy, int mm, int dd) {

        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR) - yy, cal.get(Calendar.MONTH) - mm, cal.get(Calendar.DATE) - dd);
        // 년
        return cal.get(Calendar.YEAR);
    }

    /**
     * @param fromDate
     * @param toDate
     * @return
     * @title : 만나이 계산
     * @author : Kim Min Jae
     * @creatDate : 2021. 3. 15. 오후 4:14:03
     */
    public int getCountYear(String fromDate, String toDate) {
        int tempDay = 0;
        int fromYear = Integer.parseInt(fromDate.substring(0, 4));
        int fromMonth = Integer.parseInt(fromDate.substring(4, 6));
        int fromDay = Integer.parseInt(fromDate.substring(6, 8));

        int cntYear = 0;
        int toYear = 0;
        int toMonth = 0;
        int toDay = 0;

        if ("".equals(toDate.trim())) {
            // toDate가 ""일때는 현재 일 기준으로 계산
            Calendar cal = Calendar.getInstance();
            toYear = cal.get(Calendar.YEAR);
            toMonth = cal.get(Calendar.MONTH) + 1; // 0번이 January 임
            toDay = cal.get(Calendar.DAY_OF_MONTH);
        }
        // to_date가 "" 아닐때는 입력값 기준으로 계산
        else {
            toYear = Integer.parseInt(toDate.substring(0, 4));
            toMonth = Integer.parseInt(toDate.substring(4, 6));
            toDay = Integer.parseInt(toDate.substring(6, 8));
        }

        if (fromMonth > toMonth) {
            cntYear = toYear - fromYear - 1;

        } else if (fromMonth < toMonth) {
            cntYear = toYear - fromYear;

        } else if (fromMonth == toMonth) {

            if (fromDay <= toDay)
                cntYear = toYear - fromYear;
            else
                cntYear = toYear - fromYear - 1;
        }
        tempDay = cntYear;

        return tempDay;
    }

    /**
     * 문자열 NULL체크 String형 리턴
     *
     * @param param
     * @return
     * @throws Exception
     */
    public String getReqString(String param) throws Exception {

        try {
            if (param == null)
                param = "";
            else if (param.length() == 0)
                param = "";
            else
                param = param.trim();
        } catch (Exception ex) {
            param = "";
        }
        return param;
    }

    /**
     * 문자열 NULL체크 long형 리턴
     *
     * @param param
     * @return
     * @throws Exception
     */
    public long getReqLong(String param) throws Exception {
        long rsParam = 0;

        try {
            if (param == null)
                param = "0";
            else if (param.length() == 0)
                param = "0";
            else
                param = param.trim();

            rsParam = Long.parseLong(param);
        } catch (Exception ex) {
            param = "0";
            rsParam = 0;
        }
        return rsParam;
    }

    /**
     * @param hmap
     * @param paramName
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public long getReqLong2(Map hmap, String paramName) throws Exception {

        long rs_param = 0;

        try {
            if (paramName == null || paramName.length() == 0) {
                rs_param = 0;
            } else {
                rs_param = Long.parseLong(hmap.get(paramName).toString());
            }

        } catch (Exception ex) {
            rs_param = 0;
        }

        return rs_param;
    }

    /**
     * @param hmap
     * @param paramName
     * @return int
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public int getReqInt2(Map hmap, String paramName) throws Exception {

        int rs_param = 0;

        try {
            if (paramName == null || paramName.length() == 0) {
                rs_param = 0;
            } else {
                rs_param = Integer.parseInt(hmap.get(paramName).toString());
            }

        } catch (Exception ex) {
            rs_param = 0;
        }

        return rs_param;
    }

    /**
     * 스트링에서 특정문자 없애기
     *
     * @param value
     * @param deleteStr
     * @return
     */
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

    /**
     * @param arg1
     * @return
     * @desc : long -> floor 로 변환
     * @author : Kim Min Jae
     * @creatDate : 2021. 3. 15. 오후 7:34:14
     */
    public int doFloor(long arg1) {

        String befStr = "" + arg1;
        int befStrSize = befStr.length();

        String resultAgr = ""; // 반올림후 결과값
        String headNum = ""; // 뒷 두자리를 제외한 나머지 숫자.
        int intHeadNum = 0;

        try {
            if (arg1 > 9) {
                headNum = befStr.substring(0, befStrSize - 1);
                intHeadNum = Integer.parseInt(headNum);
                resultAgr = intHeadNum + "0";
            } else {
                resultAgr = "0";

            }
            return Integer.parseInt(resultAgr);

        } catch (Exception e) {
            //log.error("doFloor Exception arg[" + arg1 + "]", e);
            return 0;
        }

    }

    /**
     * @param arg1
     * @return
     * @desc : String -> long
     * @author : Kim Min Jae
     * @creatDate : 2021. 3. 15. 오후 7:35:07
     */
    public long doLong(String arg1) {

        int befStrSize = arg1.length();

        String aftAgr = "";
        long longAftAgr = 0;

        try {
            if (befStrSize > 5) {
                aftAgr = arg1.substring(0, befStrSize - 5);
                longAftAgr = Long.parseLong(aftAgr);
            } else {
                longAftAgr = 0;
            }

            return longAftAgr;

        } catch (Exception e) {
            log.error("doLong Exception arg[" + arg1 + "]", e);
            return 0;
        }

    }

    /**
     * @param str
     * @return
     * @desc : 숫자 여부 체크
     * @author : Kim Min Jae
     * @creatDate : 2021. 3. 15. 오후 7:35:31
     */
    public static boolean isNumber(String str) {
        boolean result = true;
        if (str.equals("")) {
            result = false;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < 48 || c > 59) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 실수형인지 체크
     */
    public static boolean isRealNumber(String str) {
        boolean result = true;
        if (str == null || "".equals(str)) {
            result = false;
        }else {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (c != 46 && (c < 48 || c > 57)) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    /*****************************************************************************
     * 숫자 필드포맷 함수
     * <p>
     *****************************************************************************
     * @param num     숫자값
     * @param width   필드너비
     * @param align   정렬방향(true: 우측정렬)
     * @param padding Zero-padding 여부
     * @return 포맷된 문자열
     *****************************************************************************/

    public String msgToInt(int num, int width, boolean align, boolean padding) {
        String msg = "";
        String str = "";
        int len;

        str = Integer.toString(num);
        str = to_8859(str);
        len = str.length();
        try {
            if (width == len)
                msg = str;
            else if (width < len)
                msg = str.substring(0, width);
            else {
                String filler = "";
                String fillchar;

                if (padding)
                    fillchar = "0";
                else
                    fillchar = " ";

                for (int i = 0; i < (width - len); i++)
                    filler += fillchar;

                if (align)
                    msg = filler + str;
                else
                    msg = str + filler;
            }
        } catch (Exception ex) {
            log.error("\n", ex);
        }
        return msg;
    }

    /**
     * @param str
     * @param len
     * @param align
     * @return
     * @desc : by
     * @author : Kim Min Jae
     * @creatDate : 2021. 3. 15. 오후 7:36:57
     */
    public String msgToString(String str, int len, boolean align) {

        String msg = "";
        int str_len = 0; // n

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
        } catch (Exception ex) {
            log.error("\n", ex);
        }
        return msg;
    }

    /**
     * Object 가 NULL 인지 판단한다.
     *
     * @param obj 검사할 Object
     * @return NULL 인지 여부
     */
    public static boolean isNullObject(Object obj) {

        if (obj == null) {
            return true;
        }

        return false;
    }

    /**
     * 개월수 구하기 - (과거일자~현재일자) 기준
     * <p>
     * ex)근무개월수
     *
     * @param date
     * @return
     */
    public int getMonthCnt(String date) {
        int totalMonth = 0;
        try {
            if (date.length() < 8)
                return totalMonth;
            if (date.length() < 8)
                return totalMonth;

            // 과거일자
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(4, 6));
            int day = Integer.parseInt(date.substring(6, 8));

            int workYear = 0;
            int workMonth = 0;

            Calendar cal = Calendar.getInstance();
            int curMonth = cal.get(Calendar.MONTH) + 1; // 0번이 January 임
            int curYear = cal.get(Calendar.YEAR);
            int curDay = cal.get(Calendar.DAY_OF_MONTH);

            if (month > curMonth) {
                workYear = curYear - year - 1;
                workMonth = 12 - month + curMonth;
            } else if (month < curMonth) {
                workYear = curYear - year;
                workMonth = curMonth - month;
            } else if (month == curMonth) {
                workYear = curYear - year;
            }

            if (day > curDay) {
                totalMonth = workYear * 12 + workMonth - 1;
            } else {
                totalMonth = workYear * 12 + workMonth;
            }
        } catch (Exception ex) {
        }

        return totalMonth;
    }

    /**
     * 개월수 구하기 - (과거일자~기준일자) 기준
     * <p>
     * ex)근무개월수
     *
     * @param befDate
     * @param aftDate
     * @return
     */
    public int getMonthCnt(String befDate, String aftDate) {
        int totalMonth = 0;
        try {
            if (befDate.length() < 8)
                return totalMonth;
            if (aftDate.length() < 8)
                return totalMonth;

            // 과거일자
            int year = Integer.parseInt(befDate.substring(0, 4));
            int month = Integer.parseInt(befDate.substring(4, 6));
            int day = Integer.parseInt(befDate.substring(6, 8));

            int workYear = 0;
            int workMonth = 0;

            int aftYear = Integer.parseInt(aftDate.substring(0, 4));
            int aftMonth = Integer.parseInt(aftDate.substring(4, 6));
            int aftDay = Integer.parseInt(aftDate.substring(6, 8));

            if (month > aftMonth) {
                workYear = aftYear - year - 1;
                workMonth = 12 - month + aftMonth;
            } else if (month < aftMonth) {
                workYear = aftYear - year;
                workMonth = aftMonth - month;
            } else if (month == aftMonth) {
                workYear = aftYear - year;
            }

            if (day > aftDay) {
                totalMonth = workYear * 12 + workMonth - 1;
            } else {
                totalMonth = workYear * 12 + workMonth;
            }

        } catch (Exception ex) {
        }

        return totalMonth;
    }

    /**
     * 자릿수만큼 숫자 문자형 리턴
     *
     * @param nUnit : 자릿수
     * @return
     */
    public String getRandomStringNumber(int nUnit) {
        StringBuffer strRNumber = new StringBuffer();
        SecureRandom secRandom = new SecureRandom();
        for (int i = 0; i < nUnit; i++)
            strRNumber.append(secRandom.nextInt(10));

        return strRNumber.toString();
    }

    /**
     * Map의 전체 key에 대해 value에 '-', ',', whitespace character 을 제거
     *
     * @param param : '-', ',', whitespace character 을 제거할 Map
     * @return '-', ',', 공백 을 제거한 Map
     */
    public static Map<String, Object> removeDelmValMap(Map<String, Object> param) {
        return StringUtil.removeDelmValMap(param, null);
    }

    /**
     * Map의 value에 '-', ',', whitespace character 을 제거
     *
     * @param param : '-', ',', whitespace character 을 제거할 Map
     * @param keys  : null 또는 길이0 일 경우, 전체대상으로 '-', ',', whitespace character 을 제거 그
     *              이외는 입력된 key만 대상으로 '-', ',', whitespace character 을 제거
     * @return '-', ',', 공백 을 제거한 Map
     */
    public static Map<String, Object> removeDelmValMap(Map<String, Object> param, String[] keys) {
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        String regx = "[-,\\s]";
        String paramVal = "";

        // Map에 데이터가 존재하는지 체크
        if (param == null || param.isEmpty()) {
            return param;
        }

        Object[] objKey = param.keySet().toArray();
        int i = 0, j = 0;

        // 전체를 대상으로 제거작업
        if (keys == null || keys.length == 0) {
            for (i = 0; i < objKey.length; i++) {
                paramVal = ((String) param.get(objKey[i])).replaceAll(regx, "");
                rtnMap.put(objKey[i].toString(), paramVal);
            }
        }
        // 특정 keys 대상으로 제거작업
        else {
            for (i = 0; i < objKey.length; i++) {
                rtnMap.put(objKey[i].toString(), param.get(objKey[i]));
                for (j = 0; j < keys.length; j++) {
                    if (objKey[i].equals(keys[j])) {
                        paramVal = ((String) param.get(keys[j])).replaceAll(regx, "");
                        rtnMap.put(keys[j], paramVal);
                        break;
                    }
                }
            }
        }
        return rtnMap;
    }

    /**
     * long형 금액을 천단위 표기된 문자열로 변환
     *
     * @param amt
     * @return Usage : StringUtil.set1000Mark(1000000)
     */
    public static String set1000Mark(long amt) {
        NumberFormat nf = NumberFormat.getInstance();
        return nf.format(amt);
    }

    /**
     * 정수형 문자열로 표기된 금액을 천단위 표기된 문자열로 변환
     *
     * @param amt
     * @return Usage : StringUtil.set1000Mark("1000000")
     */
    public static String set1000Mark(String amt) {
        NumberFormat nf = NumberFormat.getInstance();
        return nf.format(Long.parseLong(amt));
    }

    /**
     * 실수형 문자열로 표기된 금액을 천단위 표기된 문자열로 변환(소수점이하 numDigit자리까지 표기, 나머지는 버림)
     *
     * @param amt
     * @param numDigit 소수점이하 자리수(나머지는 버림처리)
     * @return Usage : StringUtil.setPointFloor("1000000.02999", 2)
     */
    public static String setPointFloor(String amt, int numDigit) {
        return setPointFloor(Double.parseDouble(StringUtils.defaultIfEmpty(amt, "0")), numDigit);
    }

    /**
     * double형을 소수점이하 numDigit자리로 절사하여 문자열로 변환(소수점이하 numDigit자리까지 표기, 나머지는 버림)
     *
     * @param amt
     * @param numDigit 소수점이하 자리수(나머지는 버림처리)
     * @return Usage : StringUtil.setPointFloor(1000000.02999, 2)
     */
    public static String setPointFloor(double amt, int numDigit) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(numDigit); // 소수점 이하 자리수

        return Double.toString(Math.floor(amt * Math.pow(10, numDigit)) / Math.pow(10, numDigit));

    }

    /**
     * 실수형 문자열로 표기된 금액을 천단위 표기된 문자열로 변환(소수점이하 numDigit자리까지 표기, 나머지는 버림)
     *
     * @param amt
     * @param numDigit 소수점이하 자리수(나머지는 버림처리)
     * @return Usage : StringUtil.set1000MarkReal("1000000.02", 2)
     */
    public static String set1000MarkReal(String amt, int numDigit) {
        return set1000MarkReal(Double.parseDouble(StringUtils.defaultIfEmpty(amt, "0")), numDigit);
    }

    /**
     * double형 금액을 천단위 표기된 문자열로 변환(소수점이하 numDigit자리까지 표기, 나머지는 버림)
     *
     * @param amt
     * @param numDigit 소수점이하 자리수(나머지는 버림처리)
     * @return Usage : StringUtil.set1000MarkReal(1000000.02, 2)
     */
    public static String set1000MarkReal(double amt, int numDigit) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(numDigit); // 소수점 이하 자리수

        return nf.format(Math.floor(amt * Math.pow(10, numDigit)) / Math.pow(10, numDigit));
    }

    /**
     * Map의 전체 key에 대해 value에 천단위 표기 설정
     *
     * @param param : 천단위 표기할 Map
     * @return 천단위 표기한 Map
     * <p>
     * Usage : StringUtil.set1000MarkMap(rsltMap)
     */
    public static Map<String, String> set1000MarkMap(Map<String, String> param) {
        return StringUtil.set1000MarkMap(param, null);
    }

    /**
     * Map의 value에 천단위 표기 설정
     *
     * @param param : 천단위 표기할 Map
     * @param keys  : null 또는 길이0 일 경우, 전체대상으로 천단위 표기 설정 그 이외는 입력된 key만 대상으로 천단위 표기
     *              설정
     * @return 천단위 표기 설정
     * <p>
     * Usage : StringUtil.set1000MarkMap(rsltMap, new String[] {"bill_amt",
     * "reg_unknown_amt", "pay_tot_amt_rpmt"})
     */
    public static Map<String, String> set1000MarkMap(Map<String, String> param, String[] keys) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        String paramVal = "";
        int numDigit = 2; // 소수점이하 자릿수

        // Map에 데이터가 존재하는지 체크
        if (param == null || param.isEmpty()) {
            return param;
        }

        Object[] objKey = param.keySet().toArray();
        int i = 0;
        int j = 0;

        if (keys == null || keys.length == 0) {
            // 전체를 대상으로 제거작업
            for (i = 0; i < objKey.length; i++) {
                paramVal = set1000MarkReal((String) param.get(objKey[i]), numDigit);
                rtnMap.put(objKey[i].toString(), paramVal);
            }

        } else {
            // 특정 keys 대상으로 제거작업
            for (i = 0; i < objKey.length; i++) {
                rtnMap.put(objKey[i].toString(), param.get(objKey[i]));
                for (j = 0; j < keys.length; j++) {
                    if (objKey[i].equals(keys[j])) {
                        paramVal = set1000MarkReal((String) param.get(objKey[i]), numDigit);
                        rtnMap.put(keys[j], paramVal);
                        break;
                    }
                }
            }
        }
        return rtnMap;
    }

    /**
     * 청구금액 - 대출수납금액 >= 0 인 부족금액 계산 청구금액 - 대출수납금액 < 0 이면 0으로 부족금액 처리
     *
     * @param amt1 청구금액
     * @param amt2 대출수납금액
     * @return Usage : StringUtil.calcFewAmt("15000.01", "10000.01")
     */
    public static String calcFewAmt(String amt1, String amt2) {
        String rsltAmt = "0";
        if (isRealNumber(amt1) && isRealNumber(amt2)) {
            double fewAmt = Double.parseDouble(amt1) - Double.parseDouble(amt2); // 청구금액 - 대출수납금액
            if (fewAmt >= 0D) {
                rsltAmt = Double.toString(fewAmt);// 부족금액
            }
        }
        return rsltAmt;
    }

    /**
     * 청구금액 - 대출수납금액 < 0 인 초과월불입금액 계산(음수는 양수로) 청구금액 - 대출수납금액 >= 0 이면 0으로 초과월불입금액 처리
     *
     * @param amt1 청구금액
     * @param amt2 대출수납금액
     * @return Usage : StringUtil.calcOverAmt("15000.01", "10000.01")
     */
    public static String calcOverAmt(String amt1, String amt2) {
        String rsltAmt = "0";
        if (isRealNumber(amt1) && isRealNumber(amt2)) {
            double overAmt = Double.parseDouble(amt1) - Double.parseDouble(amt2); // 청구금액 - 대출수납금액
            if (overAmt < 0D) {
                rsltAmt = Double.toString((overAmt * -1));// 초과금액
            }
        }
        return rsltAmt;
    }

    /**
     * Map 의 KEY가 pfxKey로 시작하는 value의 합계를 구하는 함수
     *
     * @param param
     * @param pfxKey
     * @return StringUtil.calcSumAmt(rsltMap, " bill_ ")
     */
    public static String calcSumAmt(Map<String, String> param, String pfxKey) {
        Object[] objKey = param.keySet().toArray();
        String key = "";
        BigDecimal sum = new BigDecimal("0");
        int i = 0;
        for (i = 0; i < objKey.length; i++) {
            key = objKey[i].toString();
            if (key.startsWith(pfxKey)) {
                BigDecimal amt = new BigDecimal((String) param.get(key));
                sum = sum.add(amt);
            }
        }
        return sum.toString();
    }

    /**
     * 금액 합산
     *
     * @param amt1 계산중도상환
     * @param amt2 청구(계산)금액
     * @return Usage : StringUtil.calcPlusAmt("15000.01", "10000.01")
     */
    public static String calcPlusAmt(String amt1, String amt2) {
        String rsltAmt = "0";
        if (isRealNumber(amt1) && isRealNumber(amt2)) {
            double pymnAmt = Double.parseDouble(amt1) + Double.parseDouble(amt2);
            rsltAmt = Double.toString(pymnAmt);
        }
        return rsltAmt;
    }

    /**
     * null or empty string -> 0
     *
     * @param param
     * @param key
     * @return
     */
    public static int getIntValueInMap(Map<String, Object> param, String key) {
        if (param.get(key) instanceof BigDecimal) {
            return ((BigDecimal) param.get(key)).intValue();
        } else {
            return param.get(key) == null || param.get(key).equals("") ? 0 : Integer.parseInt((String) param.get(key));
        }
    }

    /**
     * null or empty string -> 0.0
     *
     * @param param
     * @param key
     * @return
     */
    public static double getDoubleValueInMap(Map<String, Object> param, String key) {
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

    /**
     * null String -> empty string
     *
     * @param param
     * @param key
     * @return
     */
    public static String getStringInMap(Map<String, Object> param, String key) {
        return getStringInMap(param, key, "");
    }

    /**
     * @param unicode
     * @return
     * @desc : 유니코드 -> 한글
     * @author : Kim Min Jae
     * @creatDate : 2021. 3. 15. 오후 7:49:16
     */
    public static String uniDecode(String unicode) {

        try {
            StringBuffer tmp = new StringBuffer();
            tmp.ensureCapacity(unicode.length());
            int lastPos = 0, pos = 0;
            char ch;
            while (lastPos < unicode.length()) {
                pos = unicode.indexOf("%", lastPos);
                if (pos == lastPos) {
                    if (unicode.charAt(pos + 1) == 'u') {
                        ch = (char) Integer.parseInt(unicode.substring(pos + 2, pos + 6), 16);
                        tmp.append(ch);
                        lastPos = pos + 6;
                    } else {
                        ch = (char) Integer.parseInt(unicode.substring(pos + 1, pos + 3), 16);
                        tmp.append(ch);
                        lastPos = pos + 3;
                    }
                } else {
                    if (pos == -1) {
                        tmp.append(unicode.substring(lastPos));
                        lastPos = unicode.length();
                    } else {
                        tmp.append(unicode.substring(lastPos, pos));
                        lastPos = pos;
                    }
                }
            }
            return tmp.toString();

        } catch (Exception e) {
            return unicode;
        }
    }

    /**
     * @param unicode
     * @return
     * @throws Exception
     * @desc : 한글 -> 유니코드
     * @author : Kim Min Jae
     * @creatDate : 2021. 3. 15. 오후 7:49:42
     */
    public static String uniEncode(String unicode) throws Exception {
        int i;
        char j;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(unicode.length() * 6);
        for (i = 0; i < unicode.length(); i++) {
            j = unicode.charAt(i);
            if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
                tmp.append(j);
            else if (j < 256) {
                tmp.append("%");
                if (j < 16)
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            } else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();

    }

    /**
     * @param id
     * @param length
     * @return
     * @desc : mask id
     * @author : Kim Min Jae
     * @creatDate : 2021. 3. 15. 오후 7:49:54
     */
    public static String maskId(String id, int length) {

        int lengthId = id.length();
        String k = "";
        if (lengthId < length)
            length = lengthId;
        for (int i = 0; i < lengthId - length; i++)
            k = k + "*";
        //log.debug("id before:" + id);
        id = id.substring(0, length) + k;
        //log.debug("id after:" + id);
        return id;
    }

    /**
     * 만나이 계산
     *
     * @param idNo : 주민등록번호 13자리 (예: "999999-1234567" or "9999991234567")
     * @return int 만나이
     */
    public static int calculateManAge(String idNo) {

        idNo = idNo.replaceAll("-", ""); // '-' 제거
        if (idNo.length() < 7) { // 7자리 보다 작으면
            return 0;
        }

        String today = ""; // 오늘 날짜
        int manAge = 0; // 만 나이

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);

        today = formatter.format(new Date()); // 시스템 날짜를 가져와서 yyyyMMdd 형태로 변환

        // today yyyyMMdd
        int todayYear = Integer.parseInt(today.substring(0, 4));
        int todayMonth = Integer.parseInt(today.substring(4, 6));
        int todayDay = Integer.parseInt(today.substring(6, 8));

        int ssnYear = Integer.parseInt(idNo.substring(0, 2));
        int ssnMonth = Integer.parseInt(idNo.substring(2, 4));
        int ssnDay = Integer.parseInt(idNo.substring(4, 6));

        if (idNo.charAt(6) == '0' || idNo.charAt(6) == '9') {
            ssnYear += 1800;
        } else if (idNo.charAt(6) == '1' || idNo.charAt(6) == '2' || idNo.charAt(6) == '5' || idNo.charAt(6) == '6') {
            ssnYear += 1900;
        } else { // 3, 4, 7, 8
            ssnYear += 2000;
        }

        manAge = todayYear - ssnYear;

        if (todayMonth < ssnMonth) { // 생년월일 "월"이 지났는지 체크
            manAge--;
        } else if (todayMonth == ssnMonth) { // 생년월일 "일"이 지났는지 체크
            if (todayDay < ssnDay) {
                manAge--; // 생일 안지났으면 (만나이 - 1)
            }
        }

        return manAge;
    }

    /**
     * 주민등록번호로 생년월일 구하기
     *
     * @param jno : 주민등록번호 13자리 (예: "9999991234567")
     * @return
     */
    public static String calcyyymmdd(String jno) {

        jno = jno.replaceAll("-", ""); // '-' 제거
        String yyyymmdd = ""; //

        String year = jno.substring(6, 7);

        if (year.equals("1") || year.equals("2")) { // 1900년대생 남여
            yyyymmdd = "19" + jno.substring(0, 6);
        } else if (year.equals("3") || year.equals("4")) { // 2000년대생 남여
            yyyymmdd = "00" + jno.substring(0, 6);
        }
        return yyyymmdd;

    }

    public static String euckr_to_utf8(String dec) {

        String rtCtr = "";
        String addstr = "";
        try {
            byte[] euckrStringBuffer = dec.getBytes(Charset.forName("euc-kr"));

            //log.debug("euc-kr - length : " + euckrStringBuffer.length);
            String decodedFromEucKr = new String(euckrStringBuffer, "euc-kr");
            //log.debug("String from euc-kr : " + decodedFromEucKr);

            // String 을 utf-8 로 인코딩.

            byte[] utf8StringBuffer = decodedFromEucKr.getBytes("utf-8");

            //log.debug("utf-8 - length : " + utf8StringBuffer.length);
            rtCtr = new String(utf8StringBuffer, "utf-8");
            //log.debug("String from utf-8 : " + rtCtr);

            if (utf8StringBuffer.length - euckrStringBuffer.length > 0) {
                for (int i = 0; i < utf8StringBuffer.length - euckrStringBuffer.length; i++) {
                    addstr += " ";
                }
            }

        } catch (Exception e) {

        }

        return rtCtr + addstr;
    }

    public static String stringCheck(String str) {
        if (str == null || str.equals("")) return "";

        str = str.replaceAll("'", "''");
        str = str.replaceAll("--", "__");
        str = str.replaceAll(";", "|");
        str = str.replaceAll("%", "");
        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");

        return str;
    }

    /**
     * <p>str문자열이 null 혹은 "" 일 경우 ""를 반환한다.</p>
     *
     * <p>그렇지 않은 경우에는 str문자열을 반환한다.</p>
     *
     * <pre>
     * StringUtil.nvl(null) = ""
     * StringUtil.nvl("123") = "123"
     * </pre>
     *
     * @param str 문자열
     * @return 문자열
     */
    public static String nvl(String str) {
        return nvl(str, "");
    }

    public static String nvl(Object str) {
        return nvl(str, "");
    }

    /**
     * <p>str문자열이 null 혹은 "" 일 경우 value를 반환한다.</p>
     *
     * <p>그렇지 않은 경우에는 str문자열을 반환한다.</p>
     *
     * <pre>
     * StringUtil.nvl(null, "") = ""
     * StringUtil.nvl("", "abc") = "abc"
     * StringUtil.nvl("123", "") = "123"
     * </pre>
     *
     * @param paramString1 문자열
     * @param paramString2 str이 null이거나 ""일 경우 대체할 문자
     * @return 문자열
     */
    public static String nvl(String paramString1, String paramString2) {
        String str = "";
        try {
            if ((paramString1 == null) || (paramString1.trim().length() == 0) || (paramString1.equals("null")) || (paramString1.equals(""))) {
                str = paramString2;
            } else {
                str = paramString1;
            }
        } catch (Exception localException) {
            str = paramString2;
        }
        return str;
    }

    public static String nvl(Object obj, String defalutStr) {
        String str = "";
        try {
            if (obj == null || String.valueOf(obj).equals("null") || String.valueOf(obj).equals("")) {
                str = defalutStr;
            } else {
                str = obj.toString().trim();
            }
        } catch (Exception e) {
            str = defalutStr;
        }
        return str;
    }

    // 뱅킹비밀번호 - 영문, 숫자, 특수문자('~'제외)의 조합으로 8~12자리
    public static String isAlphaNumSpcl(String pwd) {
        String result = "1";
        String num = "[0-9]";
        String eng = "[A-Za-z]";
        String spc = "[`!@#$%^&*(),.?/;:_+=<>{}\'\"]";

        Pattern patternNum = Pattern.compile(num);
        Pattern patternEng = Pattern.compile(eng);
        Pattern patternSpc = Pattern.compile(spc);

        if (patternNum.matcher(pwd).find() && patternEng.matcher(pwd).find() && patternSpc.matcher(pwd).find()) {
            result = "0";
        }

        return result;
    }

    //연속된숫자 (3자리)
    public static String continuePwd(String pwd) throws Exception {
        char[] s = pwd.toCharArray();
        char chr0, chr1, chr2;

        for (int i = 0; i < s.length - 2; i++) {
            chr0 = s[i];
            chr1 = s[i + 1];
            chr2 = s[i + 2];

            if (chr0 == chr1 - 1 && chr0 == chr2 - 2) {
                return "1";
            }
            if (chr0 == chr1 + 1 && chr0 == chr2 + 2) {
                return "1";
            }
        }

        return "0";
    }

    //동일숫자 3회반복
    public static String samePwd(String pwd) {
        String pattern = "(.)\\1\\1";
        Matcher match;
        match = Pattern.compile(pattern).matcher(pwd);
        if (match.find()) {
            return "1";
        }

        return "0";
    }

    //아이디와 동일 문자 4자리 체크
    public static boolean sameId(String pwd, String id) {
        for (int i = 0; i < pwd.length() - 3; i++) {
            if (id.contains(pwd.substring(i, i + 4))) {
                return true;
            }
        }

        return false;
    }

    //6자리 동일 체크
    public static boolean sameId2(String pwd, String id) {
        for (int i = 0; i < pwd.length() - 5; i++) {
            if (id.contains(pwd.substring(i, i + 6))) {
                return true;
            }
        }

        return false;
    }

    public static String substringByByteLength(String str, int beginIndex, int byteLength) throws Exception {
        return substringByByteLength(str, beginIndex, byteLength, null);
    }

    public static String substringByByteLength(String str, int beginIndex, int byteLength, String encoding) throws Exception {
        if (encoding == null) {
            encoding = "UTF-8";
        }

        byte[] bytes = str.getBytes(encoding);
        int length = byteLength;
        if (bytes.length < length) {
            length = bytes.length;
        }

        return new String(bytes, beginIndex, length, encoding);
    }

    public static String exceptionToString(Exception e) throws Exception {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));

        return substringByByteLength(errors.toString(), 0, 3000);
    }

    /**
     * <p>str이 null 이거나 ""일 경우 판단한다.</p>
     *
     * <pre>
     * </pre>
     *
     * @param str 문자열
     * @return null 이거나 "" 여부
     */
    static public boolean isEmpty(Object str) {

        if ((str != null) && !("".equals(str))) {
            return false;
        } else {
            return true;
        }
    }

    static public String lpad(int number) {
        return lpad(number, 2);
    }

    public static String lpad(int number, int length) {
        return lpad(number, length, '0');
    }

    public static String lpad(BigDecimal number, int length) {
        return lpad(number.toString(), length, '0');
    }

    public static String lpad(long number, int length, char padChar) {
        return lpad(String.valueOf(number), length, padChar);
    }

    public static String lpad(int number, int length, char padChar) {
        return lpad(String.valueOf(number), length, padChar);
    }

    public static String lpad(String input, int length) {
        return lpad(input, length, ' ');
    }

    public static String lpadByByte(String input, int byteLength, String encoding) throws Exception {
        return lpadByByte(input, byteLength, ' ', encoding);
    }

    public static String lpadByByte(String input, int byteLength, char padChar, String encoding) throws Exception {
        return padByByte(input, byteLength, String.valueOf(padChar), false, encoding);
    }

    public static String lpad(String input, int length, char padChar) {
        return pad(input, length, padChar, false);
    }

    public static String lpad(String input, int length, String padStr) {
        return pad(input, length, padStr, false);
    }

    public static String pad(String input, int length, char padChar, boolean rpad) {
        return pad(input, length, String.valueOf(padChar), rpad);
    }

    public static String pad(String input, int length, String padStr, boolean rpad) {
        // Create a buffer with an initial capacity set
        StringBuffer buf = new StringBuffer(length);

        // Determine the number of characters to be padded
        int padCount = length - (input != null ? input.length() : 0);

        // Start with the input, if we are rpad-ing
        if (rpad && input != null) {
            buf.append(input);
        }

        // Append with the pad characters
        for (int i = 0; i < padCount; i++) {
            buf.append(padStr);
        }

        // End with the input, if we are lpad-ing
        if (!rpad && input != null) {
            buf.append(input);
        }
        return buf.length() > length ? buf.substring(0, length) : buf.toString();
    }

    public static String padByByte(String input, int byteLength, String padStr, boolean rpad, String encoding) throws Exception {

        int totalByteLength = getByteLength(input, encoding);

        // Create a buffer with an initial capacity set
        StringBuffer buf = new StringBuffer();

        // Determine the number of characters to be padded
        int padCount = byteLength - (input != null ? totalByteLength : 0);

        // Start with the input, if we are rpad-ing
        if (rpad && input != null) {
            buf.append(input);
        }

        // Append with the pad characters
        for (int i = 0; i < padCount; i++) {
            buf.append(padStr);
        }

        // End with the input, if we are lpad-ing
        if (!rpad && input != null) {
            buf.append(input);
        }

        int appendedByteLength = getByteLength(buf.toString(), encoding);

        return appendedByteLength > byteLength ?
                substringByByteLength(buf.toString(), 0, byteLength, encoding)
                : buf.toString();
    }

    public static int getByteLength(String str) {
        return getByteLength(str, "UTF-8");
    }

    public static int getByteLength(String str, String encoding) {
        if (str == null || "".equals(str)) {
            return 0;
        } else {
            try {
                byte srcarr[] = str.getBytes(encoding);
                return srcarr.length;
            } catch (Exception e) {
                return str.getBytes().length;
            }
        }
    }
}
