package demo.domain.entity;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Date;

/**
 * @author xm
 */
@Entity
@Table(name = "T_XT_CD")
public class Xtcd {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_XT_CD", allocationSize = 1, sequenceName = "SEQ_XT_CD")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_XT_CD")
    private Long id;

    //菜单名称
    @Column(name = "CDMC")
    private String cdmc;

    //上级菜单
    @Column(name = "SJCD_ID")
    private Long sjcdId;

    //菜单url
    @Column(name = "URL")
    private String url;

    //显示图片
    @Column(name = "XSTP")
    private String xstp;

    //所属系统(0:外网，1，内网)
    @Column(name = "SSXT")
    private String ssxt;

    //操作时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CZSJ")
    private Date czsj;

    //操作人
    @Column(name = "CZR_ID")
    private Long czrId;

    //操作标识
    @Column(name = "CZBS")
    private String czbs;

    //排序号
    @Column(name = "PXH")
    private Long pxh;

    //上级菜单
    @Formula("(SELECT O.CDMC FROM T_XT_CD O WHERE O.ID = SJCD_ID AND O.CZBS<>'3' )")
    private String sjcdmc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCdmc() {
        return cdmc;
    }

    public void setCdmc(String cdmc) {
        this.cdmc = cdmc;
    }

    public Long getSjcdId() {
        return sjcdId;
    }

    public void setSjcdId(Long sjcdId) {
        this.sjcdId = sjcdId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getXstp() {
        return xstp;
    }

    public void setXstp(String xstp) {
        this.xstp = xstp;
    }

    public String getSsxt() {
        return ssxt;
    }

    public void setSsxt(String ssxt) {
        this.ssxt = ssxt;
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

    public Long getPxh() {
        return pxh;
    }

    public void setPxh(Long pxh) {
        this.pxh = pxh;
    }

    public String getSjcdmc() {
        return sjcdmc;
    }

    public void setSjcdmc(String sjcdmc) {
        this.sjcdmc = sjcdmc;
    }
}
