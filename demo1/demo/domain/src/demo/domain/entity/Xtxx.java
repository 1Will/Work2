package demo.domain.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @date ${date}
 * $END
 */
@Entity
@Table(name = "T_XT_XX")
public class Xtxx {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_XT_XX", allocationSize = 1, sequenceName = "SEQ_XT_XX")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_XT_XX")
    private Long id;

    /**
     * 信息标题
     */
    @Column(name = "XXBT")
    private String xxbt;

    /**
     * 关键字
     */
    @Column(name = "GJZ")
    private String gjz;

    /**
     * 是否置顶
     */
    @Column(name = "TOP")
    private Boolean top;

    /**
     * 内容
     */
    @Column(name = "NR")
    private String nr;

    /**
     * 发布人
     */
    @Column(name = "FBR_ID")
    private Long fbrId;

    /**
     * 发布时间
     */
    @Column(name = "FBSJ")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fbsj;

    /**
     * 发布单位
     */
    @Column(name = "FBDW_ID")
    private Long fbdwId;

    /**
     * 发布状态
     */
    @Column(name = "FBZT")
    private String fbzt;

    /**
     * 发布IP
     */
    @Column(name = "FBIP")
    private String fbIP;


    /**
     * 操作标志
     */
    @Column(name = "CZBS")
    private String czbs;

    /**
     * 操作用户
     */
    @Column(name = "CZYH_ID")
    private Long czyhId;

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

    public String getXxbt() {
        return xxbt;
    }

    public void setXxbt(String xxbt) {
        this.xxbt = xxbt;
    }

    public String getGjz() {
        return gjz;
    }

    public void setGjz(String gjz) {
        this.gjz = gjz;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public Long getFbrId() {
        return fbrId;
    }

    public void setFbrId(Long fbrId) {
        this.fbrId = fbrId;
    }

    public Date getFbsj() {
        return fbsj;
    }

    public void setFbsj(Date fbsj) {
        this.fbsj = fbsj;
    }

    public Long getFbdwId() {
        return fbdwId;
    }

    public void setFbdwId(Long fbdwId) {
        this.fbdwId = fbdwId;
    }

    public String getFbzt() {
        return fbzt;
    }

    public void setFbzt(String fbzt) {
        this.fbzt = fbzt;
    }

    public String getFbIP() {
        return fbIP;
    }

    public void setFbIP(String fbIP) {
        this.fbIP = fbIP;
    }

    public String getCzbs() {
        return czbs;
    }

    public void setCzbs(String czbs) {
        this.czbs = czbs;
    }

    public Long getCzyhId() {
        return czyhId;
    }

    public void setCzyhId(Long czyhId) {
        this.czyhId = czyhId;
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public Xtxx() {
    }

}
