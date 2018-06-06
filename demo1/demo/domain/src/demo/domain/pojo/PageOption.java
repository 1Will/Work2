package demo.domain.pojo;

/**
 * Created by wyl on 2017/12/19.
 */

/**
 * 分页参数
 */
public class PageOption {
    /**
     * 当前页
     */
    private Integer pageNo;
    /**
     * 每页数
     */
    private Integer pageSize;
    /**
     * 总数
     */
    private Integer total;
    /**
     * 当前页起始索引
     */
    private Integer offset;

    public Integer getPageNo() {
        if (total != null) {
            Integer totalPage = (total - 1) / getPageSize() + 1;
            if (pageNo > totalPage) {
                pageNo = totalPage;
            }
        }
        if (pageNo != null && pageSize != null) {
            this.offset = (pageNo - 1) * pageSize + 1;
        }
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
