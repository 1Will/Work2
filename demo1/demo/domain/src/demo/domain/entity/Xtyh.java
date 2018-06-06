package demo.domain.entity;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wyl on 2017/12/21.
 */
@Entity
@Table(name = "T_XT_YH")
public class Xtyh {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_XT_YH", allocationSize = 1, sequenceName = "SEQ_XT_YH")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_XT_YH")
    private Long id;
    /**
     * 登录名
     */
    @Column(name="DLM")
    private String dlm;
    /**
     * 密码
     */
    @Column(name="MM")
    private String mm;
    /**
     * 姓名
     */
    @Column(name="XM")
    private String xm;
    /**
     * 性别
     */
    @Column(name="XB_NO")
    private String xb_no;
    /**
     * 机构ID
     */
    @Column(name="JG_ID")
    private Long jg_id;
    /**
     * 机构ID
     */
    @Formula("(SELECT JG.JGMC FROM T_XT_JG JG WHERE JG.CZBS <> '3' AND JG.ID = JG_ID)")
    private String jgmc;

    /**
     * 联系电话
     */
    @Column(name="LXDH")
    private String lxdh;
    /**
     * 身份证号码
     */
    @Column(name="SFZHM")
    private String sfzhm;
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

    public Long getJg_id() {
        return jg_id;
    }

    public void setJg_id(Long jg_id) {
        this.jg_id = jg_id;
    }

    public String getDlm() {
        return dlm;
    }

    public void setDlm(String dlm) {
        this.dlm = dlm;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getXb_no() {
        return xb_no;
    }

    public void setXb_no(String xb_no) {
        this.xb_no = xb_no;
    }

    public String getJgmc() {
        return jgmc;
    }

    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getSfzhm() {
        return sfzhm;
    }

    public void setSfzhm(String sfzhm) {
        this.sfzhm = sfzhm;
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
