package demo.domain.enums;

/**
 * 数据信息的操作标识枚举类
 */
public enum Czbs {
    XZ("新增", "1"), XG("修改", "2"), SC("删除", "3");

    private String name;
    private String index;

    Czbs(String name, String index) {
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