package demo.service.xtsq;

import demo.dao.xtjg.XtjgDao;
import demo.dao.xtsq.XtsqDao;
import demo.domain.entity.Xtsq;
import demo.domain.enums.Czbs;
import demo.domain.pojo.PageOption;
import demo.util.CommonUtil;
import demo.util.ExcelImportUtil;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 社区管理Service
 *
 * @author k
 */
@Service
public class XtsqService {

    /**
     * 社区管理dao
     */
    @Autowired
    private XtsqDao xtsqDao;

    /**
     * 机构dao
     */
    @Autowired
    private XtjgDao xtjgDao;

    /**
     * 获取社区列表
     *
     * @param option 分页参数
     * @param sqmc   社区名称
     * @param jgid   机构Id
     * @return List
     */
    public List getXtsqList(PageOption option, String sqmc, Long jgid) {
        return xtsqDao.getXtsqList(option, sqmc, jgid);
    }

    /**
     * 获取社区总数
     *
     * @param sqmc 社区名称
     * @param jgid 机构Id
     * @return 总数量
     */
    public int getXtsqTotal(String sqmc, Long jgid) {
        return xtsqDao.getXtsqTotal(sqmc, jgid);
    }

    /**
     * 将页面参数存入map
     *
     * @param option 分页
     * @param sqmc   社区名称
     * @param jgid   机构id
     * @return Map
     */
    public ModelMap getXtsqMap(PageOption option, String sqmc, Long jgid) {
        ModelMap map = new ModelMap();
        option.setTotal(getXtsqTotal(sqmc, jgid));
        map.put("xtsqList", getXtsqList(option, sqmc, jgid));
        map.put("option", option);
        return map;
    }


    /**
     * 通过id查找到社区
     *
     * @param id Xtsq的id
     * @return Xtsq
     */
    public Xtsq getXtsqById(Long id) {
        return xtsqDao.getObject(Xtsq.class, id);
    }

    /**
     * 保存或修改社区
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveXtsq(Xtsq xtsq, Long czyhid, Long jgid) throws Exception {
        if (CommonUtil.isNotNull(xtsq.getId())) {
            //修改
            xtsq.setCzbs(Czbs.XG.getIndex());
        } else {
            //新增
            xtsq.setCzbs(Czbs.XZ.getIndex());
        }
        xtsq.setCzyhId(czyhid);
        xtsq.setCzsj(new Date());
        xtsqDao.saveOrUpdate(xtsq);
        //删除所有关联机构，并重新保存
        xtsqDao.deleteSqJg(xtsq.getId());
        xtsqDao.saveSqJg(xtsq.getId(), jgid);
    }

    /**
     * 删除社区
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteXtsq(Long id, Long czyhId) throws Exception {
        //删除社区
        xtsqDao.deleteXtsq(id, czyhId);
        //删除社区机构
        xtsqDao.deleteSqJg(id);
    }

    /**
     * 社区导入
     */
    @Transactional(rollbackFor = Exception.class)
    public String importSq(MultipartFile file, HttpSession session, Long czyhId) throws Exception {
        HSSFWorkbook book = ExcelImportUtil.parseExcel(file.getInputStream());
        HSSFCellStyle style = ExcelImportUtil.setStyle(book);
        String msg;
        if (!CommonUtil.isNotNull(book)) {
            return "cw";
        } else {
            HSSFSheet sheet = book.getSheetAt(0);
            //跳过头2行
            int num = 2;
            //获取第一行信息
            int firstRow = sheet.getFirstRowNum();
            //获取最后一行信息
            int lastRow = sheet.getLastRowNum();
            //错误数量
            int eNum = 0;
            //成功数量
            int sNum = 0;
            for (int i = firstRow + num; i <= lastRow; i++) {
                HSSFRow row = sheet.getRow(i);
                //创建第二列，并设置列宽
                row.createCell(2);
                sheet.setColumnWidth(2, 40 * 256);
                StringBuilder err = new StringBuilder();
                Boolean flag = false;
                //获取两列对象
                HSSFCell sqmc = row.getCell(0);
                HSSFCell jgdm = row.getCell(1);
                //判断社区名称是否为空
                if (!CommonUtil.isNotNull(sqmc)) {
                    err.append("社区名称为空；");
                    flag = true;
                }
                //判断机构代码是否为空
                if (!CommonUtil.isNotNull(jgdm)) {
                    err.append("机构代码为空；");
                    flag = true;
                }
                Long jgId = 0L;
                //两列不为空时进行数据重复判断
                if (!flag) {
                    List jgList = xtjgDao.getXtjgList(null, jgdm.toString(), null);
                    if (jgList.size() > 0) {
                        //若有重复机构代码，取第一个
                        Map jg = (Map) jgList.get(0);
                        jgId = ((BigDecimal) jg.get("ID")).longValue();
                        int n = xtsqDao.getXtsqTotal(sqmc.toString(), jgId);
                        if (n > 0) {
                            err.append("数据重复；");
                            flag = true;
                        }
                    } else {
                        err.append("机构不存在；");
                        flag = true;
                    }
                }

                if (flag) {
                    row.getCell(2).setCellValue(err.toString());
                    row.getCell(2).setCellStyle(style);
                    eNum++;
                    continue;
                }
                Xtsq sq = new Xtsq();
                sq.setSqmc(sqmc.toString());
                saveXtsq(sq, czyhId, jgId);
                row.getCell(2).setCellValue("导入成功");
                sNum++;
            }
            if (eNum == 0) {
                msg = "导入成功";
            } else {
                msg = "成功导入" + sNum + "条数据，失败" + eNum + "条！";
            }
        }
        setBook(book, session);
        return msg;
    }


    /**
     * 修改后的excel写入服务器
     *
     * @param book    book
     * @param session session
     */
    private void setBook(HSSFWorkbook book, HttpSession session) throws Exception {
        FileOutputStream file;
        //获取临时表格目录
        try {
            String filePath = session.getServletContext().getRealPath("/upload/ImportSqResult.xls");
            file = new FileOutputStream(filePath);
            //写入临时表格
            book.write(file);
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
