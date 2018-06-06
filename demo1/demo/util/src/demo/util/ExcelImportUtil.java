package demo.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/12/17 0017.
 * <p>
 * excel导入数据工具类
 */
public class ExcelImportUtil {

    /**
     * 薪资发放模块导入excel文件
     *
     * @param fis 文件流，cxlx 查询类型，按类型去除表头
     * @return
     */
    public static List<List<String>> parseExcel(InputStream fis, String cxlx) {
        //接受excel数据的list集合
        List<List<String>> data = new ArrayList<List<String>>();
        //表头行数，设置固定值，文件流读取时去除
        Integer num = 2;
        //如果cxlx查询类型为1，excel表的表头行数为4行，此excel表有区别与其他表
        if ("1".equals(cxlx)) {
            num = 4;
        }
        //创建工作薄
        HSSFWorkbook book = null;
        try {
            book = new HSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Excle导入出错");
        }
        //获取sheet
        HSSFSheet sheet = book.getSheetAt(0);
        //获取第一行信息
        int firstRow = sheet.getFirstRowNum();
        //获取最后一行信息
        int lastRow = sheet.getLastRowNum();
        //除去表头
        for (int i = firstRow + num; i < lastRow + 1; i++) {
            List<String> list = new ArrayList<String>();
            //获取行
            HSSFRow row = sheet.getRow(i);
            //获取第一个单元格
            int firstCell = row.getFirstCellNum();
            //获取最后一个单元格
            int lastCell = row.getLastCellNum();
            //含有薪资信息的列数
            Integer columnsNum = 0;
            for (int j = firstCell; j < lastCell; j++) {
                if ("1".equals(cxlx) || "2".equals(cxlx) || "3".equals(cxlx) || "4".equals(cxlx) || "5".equals(cxlx) || "6".equals(cxlx)) {
                    if (j >= 0 && j < 4) {
                        HSSFCell cell = row.getCell(j);
                        //判断cell单元格是否为空
                        if (cell == null || "".equals(cell.toString().trim()) || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                            break;
                        }
                    }
                } else {
                    if (j >= 0 && j < 3) {
                        HSSFCell cell = row.getCell(j);
                        //判断cell单元格是否为空
                        if (cell == null || "".equals(cell.toString().trim()) || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                            break;
                        }
                    }
                }
                HSSFCell cell = row.getCell(j);
                //判断cell单元格是否为空
                if (cell == null || "".equals(cell.toString().trim()) || "0".equals(cell.toString().trim()) || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                    columnsNum++;
                    //如果为空，则重新创建此单元格
                    cell = row.createCell(j);
                    //设置默认值“0”
                    cell.setCellValue("0");
                }
                //数据类型转换成字符串类型
                if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                }
                String val = cell.getStringCellValue();
                //如果是表头则退出
                if (i == firstRow + 1) {
                    break;
                } else {
                    list.add(val);
                }
            }
            //读取完所有的列结束，退出
            if (columnsNum >= lastCell - 3) {
                break;
            }
            //如不是表头数据，则将数据加入集合中
            if (i != firstRow + 1) {
                //获取excel数据
                data.add(list);
            }
        }
        return data;
    }

    /**
     * 导入excel文件工具类
     *
     * @param fis 文件流
     * @return
     */
    public static HSSFWorkbook parseExcel(InputStream fis) {
        //创建工作薄
        HSSFWorkbook book = null;
        try {
            book = new HSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Excle导入出错");
        }
        return book;
    }

    /**
     * 设置样式
     *
     * @param book book
     * @return
     */
    public static HSSFCellStyle setStyle(HSSFWorkbook book) {
        // 样式对象
        HSSFCellStyle style = book.createCellStyle();
        // 垂直
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 水平
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //生成一个字体
        HSSFFont font = book.createFont();
        //HSSFColor.VIOLET.index //字体颜色
        font.setColor(HSSFColor.RED.index);
        //字体大小
        font.setFontHeightInPoints((short) 10);
        //字体增粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //把字体应用到当前的样式
        style.setFont(font);
        return style;
    }
}
