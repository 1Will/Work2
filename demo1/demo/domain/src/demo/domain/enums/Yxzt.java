package demo.domain.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 有效状态枚举类
 */
public enum Yxzt {
    WX("无效", "0"), YX("有效", "1"), TY("停用", "2");

    private String name;
    private String index;

    Yxzt(String name, String index) {
        this.name = name;
        this.index = index;
    }

    public static Yxzt indexOf(String index) {
        for (Yxzt c : Yxzt.values()) {
            if (c.getIndex().equals(index)) {
                return c;
            }
        }
        return null;
    }

    public static Map<String, String> initMap(){
        Map<String, String> map = new LinkedHashMap<String, String>();
        for(Yxzt o : Yxzt.values()){
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