package demo.util;

import java.io.Reader;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 公用工具类
 */
public class CommonUtil {
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
     */
    public static boolean isNull(Object str) {
        return str == null || str.equals("");
    }

    /**
     * 判断是否是空串
     */
    public static boolean isNotNull(Object obj) {
        if (obj != null && !"".equals(obj.toString().trim())) {
            return true;
        }
        return false;
    }

    /**
     * 判断数组是否为空
     */
    public static boolean isNotNull(Object[] objs) {
        if (objs != null && objs.length > 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为数字
     */
    public static boolean isNumber(Object obj) {
        if (obj != null && !"".equals(obj.toString().trim())) {
            return obj.toString().trim().matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
        }
        return false;
    }

    /**
     * CLOB转字符串
     */
    public static String clobToStr(Object obj) {
        Reader reader = null;
        try {
            if (obj != null && obj instanceof Clob) {
                Clob clob = (Clob) obj;
                reader = clob.getCharacterStream();
                char[] chars = new char[(int) clob.length()];
                reader.read(chars);
                return new String(chars);
            } else if (obj != null && obj instanceof String) {
                return obj.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    //获取当前系统时间
    public static String getTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format((new Date())).trim();
    }
}