package demo.domain.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hangwenxin.
 */
@Entity
@Table(name = "T_XT_ZD")
public class Xtzd {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_XT_ZD", allocationSize = 1, sequenceName = "SEQ_XT_ZD")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_XT_ZD")
    private Long id;
    /**
     * 字典名称
     */
    @Column(name="ZDMC")
    private String zdmc;
    /**
     * 字典代码
     */
    @Column(name="ZDDM")
    private String zddm;
    /**
     * 上级字典
     */
    @Column(name="SJZD_ID")
    private Long sjzd_id;
    /**
     * 序号
     */
    @Column(name="XH")
    private Long xh;
    /**
     * 操作标志
     */
    @Column(name="CZBS")
    private String czbs;
    /**
     *操作人id
     */
    @Column(name="CZR_ID")
    private Long czr_id;
    /**
     * 操作时间
     */
    @Column(name = "CZSJ")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date czsj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZdmc() {
        return zdmc;
    }

    public void setZdmc(String zdmc) {
        this.zdmc = zdmc;
    }

    public String getZddm() {
        return zddm;
    }

    public void setZddm(String zddm) {
        this.zddm = zddm;
    }

    public Long getSjzd_id() {
        return sjzd_id;
    }

    public void setSjzd_id(Long sjzd_id) {
        this.sjzd_id = sjzd_id;
    }

    public Long getXh() {
        return xh;
    }

    public void setXh(Long xh) {
        this.xh = xh;
    }

    public String getCzbs() {
        return czbs;
    }

    public void setCzbs(String czbs) {
        this.czbs = czbs;
    }

    public Long getCzr_id() {
        return czr_id;
    }

    public void setCzr_id(Long czr_id) {
        this.czr_id = czr_id;
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }
}
