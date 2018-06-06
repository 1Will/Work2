package demo.domain.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wcy
 * @date ${date}
 * $END
 */
@Entity
@Table(name = "T_XT_FJ")
public class Xtfj {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_XT_FJ", allocationSize = 1, sequenceName = "SEQ_XT_FJ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_XT_FJ")
    private Long id;

    /**
     * 发布信息ID
     */
    @Column(name = "XXFB_ID")
    private Long xxfbId;

    /**
     * 附件名称
     */
    @Column(name = "FJMC")
    private String fjmc;

    /**
     * 附件大小
     */
    @Column(name = "FJDX")
    private Long fjdx;

    /**
     * 存储路径
     */
    @Column(name = "CCLJ")
    private String cclj;

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

    /**
     * 附件
     */
    @Column(name = "ANNEX")
    private byte[] annex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getXxfbId() {
        return xxfbId;
    }

    public void setXxfbId(Long xxfbId) {
        this.xxfbId = xxfbId;
    }

    public String getFjmc() {
        return fjmc;
    }

    public void setFjmc(String fjmc) {
        this.fjmc = fjmc;
    }

    public Long getFjdx() {
        return fjdx;
    }

    public void setFjdx(Long fjdx) {
        this.fjdx = fjdx;
    }

    public String getCclj() {
        return cclj;
    }

    public void setCclj(String cclj) {
        this.cclj = cclj;
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

    public byte[] getAnnex() {
        return annex;
    }

    public void setAnnex(byte[] annex) {
        this.annex = annex;
    }
}
