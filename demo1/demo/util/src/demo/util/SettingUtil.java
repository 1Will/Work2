package demo.util;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 属性集工具类
 */
public class SettingUtil {
    public static Map<String, String> settingMap = new HashMap<String, String>();
    private static final String settingPath = "/config/config.properties";
    private static Properties properties = new Properties();
    private static InputStreamReader inReader = null;
    private static InputStream in = null;

    static {
        in = new SettingUtil().getClass().getResourceAsStream(settingPath);
        load();
    }

    public static void reload(HttpServletRequest request){
        try {
            in = new FileInputStream(new File(request.getSession().getServletContext().getRealPath("WEB-INF/classes") + settingPath));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        load();
    }

    private static void load(){
        try {
            properties.clear();
            inReader = new InputStreamReader(in, "UTF-8");
            properties.load(inReader);
            if(properties != null && properties.size() > 0){
                settingMap.clear();
                for(Map.Entry<Object, Object> en : properties.entrySet()){
                    settingMap.put((String)en.getKey(), en.getValue() == null ? "" : (String)en.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if(in != null){
                    in.close();
                }
                if(inReader != null){
                    inReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}