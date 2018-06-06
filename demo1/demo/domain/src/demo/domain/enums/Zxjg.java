package demo.domain.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 执行结果枚举类
 */
public enum Zxjg {
    PASS("通过", "0"), REJECTED("不通过", "1"), DISABLE("停用", "2"),
    VERIFIED("已核实", "3"), INVALID("无效", "4"), ERROR("错误", "5");

    private String name;
    private String index;

    Zxjg(String name, String index) {
        this.name = name;
        this.index = index;
    }

    public static Zxjg indexOf(String index) {
        for (Zxjg c : Zxjg.values()) {
            if (c.getIndex().equals(index)) {
                return c;
            }
        }
        return null;
    }

    public static Map<String, String> initMap() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        for (Zxjg o : Zxjg.values()) {
            map.put(o.getIndex(), o.getName());
        }
        return map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
