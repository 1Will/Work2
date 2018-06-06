package demo.domain.entity;

/**
 * Created by czg on 2017/10/19.
 */


import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


/**
 * 机构实体类
 */
@Entity
@Table(name="T_XT_JG")
public class Xtjg {
    @Id
    @Column(name="ID")
    @SequenceGenerator(name = "SEQ_XT_JG", allocationSize = 1, sequenceName = "SEQ_XT_JG")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_XT_JG")
    private Long id;

    /**
     * 机构代码
     */
    @Column(name="JGDM")
    private String jgdm;

    /**
     * 机构名称
     */
    @Column(name="JGMC")
    private String jgmc;

    /**
     * 电话号码
     */
    @Column(name="DHHM")
    private String dhhm;

    /**
     * 上级机构id
     */
    @Column(name="SJJG_ID")
    private Long sjjg_id;

    /**
     * 上级机构名称
     */
    @Formula("(SELECT T.JGMC FROM T_XT_JG T WHERE T.ID = SJJG_ID)")
    private String sjjgmc;

    /**
     * 机构类型
     */
    @Column(name="JGLX")
    private String jglx;

    /**
     * 操作时间
     */
    @Column(name = "CZSJ")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date czsj;

    /**
     *操作人id
     */
    @Column(name="CZR_ID")
    private Long czr_id;

    /**
     * 操作标示
     */
    @Column(name="CZBS")
    private String czbs;

    /**
     * 排序号
     */
    @Column(name="PXH")
    private Long pxh;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJgdm() {
        return jgdm;
    }

    public void setJgdm(String jgdm) {
        this.jgdm = jgdm;
    }

    public String getJgmc() {
        return jgmc;
    }

    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    public String getDhhm() {
        return dhhm;
    }

    public void setDhhm(String dhhm) {
        this.dhhm = dhhm;
    }

    public Long getSjjg_id() {
        return sjjg_id;
    }

    public void setSjjg_id(Long sjjg_id) {
        this.sjjg_id = sjjg_id;
    }

    public String getJglx() {
        return jglx;
    }

    public void setJglx(String jglx) {
        this.jglx = jglx;
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public Long getCzr_id() {
        return czr_id;
    }

    public void setCzr_id(Long czr_id) {
        this.czr_id = czr_id;
    }

    public String getCzbs() {
        return czbs;
    }

    public void setCzbs(String czbs) {
        this.czbs = czbs;
    }

    public Long getPxh() {
        return pxh;
    }

    public void setPxh(Long pxh) {
        this.pxh = pxh;
    }


    public String getSjjgmc() {
        return sjjgmc;
    }

    public void setSjjgmc(String sjjgmc) {
        this.sjjgmc = sjjgmc;
    }
}
