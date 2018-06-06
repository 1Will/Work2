package demo.domain.entity;

import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 社区管理实体类
 */
@Entity
@Table(name = "T_XT_SQ")
public class Xtsq {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_XT_SQ", allocationSize = 1, sequenceName = "SEQ_XT_SQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_XT_SQ")
    private Long id;

    /**
     * 社区名称
     */
    @Column(name = "SQMC")
    private String sqmc;

    /**
     * 机构id
     */
    @Formula("(SELECT JG.ID\n" +
            "  FROM T_XT_JG JG\n" +
            " INNER JOIN T_XT_SQJG SQJG\n" +
            "    ON JG.ID = SQJG.JG_ID\n" +
            " INNER JOIN T_XT_SQ SQ\n" +
            "    ON SQJG.SQ_ID = SQ.ID\n" +
            " WHERE JG.CZBS <> 3\n" +
            "   AND SQ.ID = ID)")
    private Long jgId;

    /**
     * 机构名称
     */
    @Formula("(SELECT JG.JGMC\n" +
            "  FROM T_XT_JG JG\n" +
            " INNER JOIN T_XT_SQJG SQJG\n" +
            "    ON JG.ID = SQJG.JG_ID\n" +
            " INNER JOIN T_XT_SQ SQ\n" +
            "    ON SQJG.SQ_ID = SQ.ID\n" +
            " WHERE JG.CZBS <> 3\n" +
            "   AND SQ.ID = ID)")
    private String jgmc;

    /**
     * 照片名称
     */
    @Column(name = "ZPMC")
    private String zpmc;

    /**
     * 存储路径
     */
    @Column(name = "CCLJ")
    private String cclj;

    /**
     * 操作标识
     */
    @Column(name = "CZBS")
    private String czbs;


    /**
     * 操作时间
     */
    @Column(name = "CZSJ")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date czsj;


    /**
     * 操作用户
     */
    @Column(name = "CZYH_ID")
    private Long czyhId;


    public void setId(Long id) {
        this.id = id;
    }

    public void setSqmc(String sqmc) {
        this.sqmc = sqmc;
    }

    public void setZpmc(String zpmc) {
        this.zpmc = zpmc;
    }

    public void setCclj(String cclj) {
        this.cclj = cclj;
    }

    public void setCzbs(String czbs) {
        this.czbs = czbs;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public void setCzyhId(Long czyhId) {
        this.czyhId = czyhId;
    }

    public Long getId() {
        return id;
    }

    public String getSqmc() {
        return sqmc;
    }

    public String getZpmc() {
        return zpmc;
    }

    public String getCclj() {
        return cclj;
    }

    public String getCzbs() {
        return czbs;
    }

    public Date getCzsj() {
        return czsj;
    }

    public Long getCzyhId() {
        return czyhId;
    }

    public Long getJgId() {
        return jgId;
    }

    public void setJgId(Long jgId) {
        this.jgId = jgId;
    }

    public String getJgmc() {
        return jgmc;
    }

    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }
}