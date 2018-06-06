package demo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公用工具类
 */
public class StringUtil {
    /**
     * 判断是否是空串
     */
    public static boolean isNotNull(String str) {
        if (str != null && !"".equals(str.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 判断为空
     *
     * @param str
     * @return
     */
    public static boolean isNull(Object str) {
        if (str == null || str.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是空串
     */
    public static boolean isNotNull(Object str) {
        if (str != null && !"".equals(str.toString().trim())) {
            return true;
        }
        return false;
    }

    /**
     * 判断数组是否为空
     */
    public static boolean arrayIsNotNull(Object[] array) {
        if (array != null && array.length > 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        String regex = "[0-9]*";
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 特殊字符转义，用于Lucene检索词处理
     *
     * @param input
     * @return
     */
    public static String transformLuceneMetacharactor(String input) {
        StringBuffer sb = new StringBuffer();
        String regex = "[+\\-&|!(){}\\[\\]^\"~*?:(\\)]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matcher.appendReplacement(sb, "\\\\" + matcher.group());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * Clob转String
     *
     * @param clob
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public static String clobToString(Clob clob) throws SQLException, IOException {
        String reString;
        Reader is = clob.getCharacterStream();// 得到流
        BufferedReader br = new BufferedReader(is);
        String s = br.readLine();
        StringBuffer sb = new StringBuffer();
        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
            sb.append(s);
            s = br.readLine();
        }
        reString = sb.toString();
        return reString;
    }

}