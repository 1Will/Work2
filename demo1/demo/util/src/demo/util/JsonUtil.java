package demo.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import oracle.sql.CLOB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by icezs on 2015/12/13.
 */
public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(PageUtil page, String fields) {
        String json = "";
        if (fields == null || "".equals(fields)) {
            try {
                json = mapper.writeValueAsString(page);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            StringBuffer sb = new StringBuffer("{");
            sb.append("\"currentPage\":").append(page.getCurrentPage()).append(",")
                    .append("\"totalCount\":").append(page.getTotalCount()).append(",")
                    .append("\"pageCount\":").append(page.getPageCount()).append(",")
                    .append("\"results\":[");
            List resultList = page.getResults();
            int size = resultList.size();
            if (size > 0) {
                String[] fieldArray = fields.split(",");
                for (int i = 0; i < size; i++) {
                    Object[] dataArray = (Object[]) resultList.get(i);
                    sb.append("{");
                    int len = fieldArray.length;
                    for (int j = 0; j < len; j++) {
                        sb.append("\"").append(fieldArray[j].trim()).append("\":").append("\"");
                        if (dataArray[j] instanceof CLOB) {
                            sb.append(ClobToString((CLOB) dataArray[j]));
                        } else {
                            sb.append(dataArray[j]);
                        }
                        sb.append("\"");
                        if (j < len - 1) {
                            sb.append(",");
                        }
                    }
                    if (i < size - 1) {
                        sb.append("},");
                    } else {
                        sb.append("}");
                    }
                }
            }
            sb.append("]}");
            json = sb.toString();
        }
        System.out.println(json);
        return json;
    }

    public static String listToJson(List list) {
        String json = "";
        try {
            json = mapper.writeValueAsString(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(json);
        return json;
    }

    public static String objectToJson(Object object) {
        String json = "";
        try {
            json = mapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(json);
        return json;
    }

    public static <T> T readJson(String jsonStr, Class<?> collectionClass, Class<?>... elementClasses) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        return mapper.readValue(jsonStr, javaType);
    }

    public static String ClobToString(CLOB clob) {
        String reString = "";
        try {
            Reader is = clob.getCharacterStream();// 得到流
            BufferedReader br = new BufferedReader(is);
            String s = br.readLine();
            StringBuffer sb = new StringBuffer();
            while (s != null) {// 执行循环将字符串全部取出付值给 StringBuffer由StringBuffer转成STRING
                sb.append(s);
                s = br.readLine();
            }
            reString = sb.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reString;
    }
}
