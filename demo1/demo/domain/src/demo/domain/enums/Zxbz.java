package demo.domain.enums;

/**
 * 请求响应的执行标识枚举类
 */
public enum Zxbz {
    CG("成功", "1"), SB("失败", "2"), YC("异常", "3");

    private String name;
    private String index;

    Zxbz(String name, String index) {
        this.name = name;
        this.index = index;
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