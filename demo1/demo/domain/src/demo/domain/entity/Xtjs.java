package demo.domain.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统角色实体类
 */
@Entity
@Table(name = "T_XT_JS")
public class Xtjs {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_XT_JS", allocationSize = 1, sequenceName = "SEQ_XT_JS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_XT_JS")
    private Long id;

    /**
     * 角色名称
     */
    @Column(name = "JSMC")
    private String jsmc;

    /**
     * 角色代码
     */
    @Column(name = "JSDM")
    private String jsdm;

    @Transient
    private String jscdIds;

    /**
     * 操作时间
     */
    @Column(name = "CZSJ")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date czsj;

    /**
     * 操作人ID
     */
    @Column(name = "CZR_ID")
    private Long czrId;

    /**
     * 操作标识
     */
    @Column(name = "CZBS")
    private String czbs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJsmc() {
        return jsmc;
    }

    public void setJsmc(String jsmc) {
        this.jsmc = jsmc;
    }

    public String getJsdm() {
        return jsdm;
    }

    public void setJsdm(String jsdm) {
        this.jsdm = jsdm;
    }

    public String getJscdIds() {
        return jscdIds;
    }

    public void setJscdIds(String jscdIds) {
        this.jscdIds = jscdIds;
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public Long getCzrId() {
        return czrId;
    }

    public void setCzrId(Long czrId) {
        this.czrId = czrId;
    }

    public String getCzbs() {
        return czbs;
    }

    public void setCzbs(String czbs) {
        this.czbs = czbs;
    }
}
