package demo.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 导出功能的实现
 */
public class ExportTool {
    private static ExportTool tool = new ExportTool();

    private static HSSFCellStyle setupStyle(HSSFWorkbook workbook) throws Exception {
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight((short) 10);
        style.setFont(font);
        style.setFillBackgroundColor(HSSFColor.WHITE.index);
        style.setFillForegroundColor((short) 1);
        return style;
    }

    //创建标题行
    private void createTitleRow(HSSFWorkbook workbook, HSSFRow row, String[] title) throws Exception {
        int length = title.length;

        for (int i = 0; i < length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(ExportTool.setupStyle(workbook));
        }
    }

    public static HSSFWorkbook export(List<Map<String, Object>> list, String[] excelHeader, String[] excelCloName, String sheetName) throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
        HSSFRow row = sheet.createRow((short) 0);
        sheet.setColumnWidth((short) 0, 2000);
        tool.createTitleRow(wb, row, excelHeader);
        int index = 1;
        for (int i = 0; i < list.size(); i++) {
            int colNum = 0;
            row = sheet.createRow(i + 1);
            Map<String, Object> rowDataMap = list.get(i);
            row.createCell(colNum++).setCellValue(index++);
            for (int j = 0; j < excelCloName.length; j++) {
                row.createCell(colNum++).setCellValue(rowDataMap.get(excelCloName[j]).toString());
                sheet.setColumnWidth((short) j + 1, 5500);
            }
        }
        return wb;
    }

    /**
     * 数据导出接口方法
     *
     * @param response     响应
     * @param excelHeader  表头名数组
     * @param excelCloName 列名数组(map的key)
     * @param list         导出数据map集合
     * @param sheetName    sheet名称
     * @param fileName     文件名称
     */
    public static void exportService(HttpServletResponse response, String[] excelHeader, String[] excelCloName, List<Map<String, Object>> list, String sheetName, String fileName) throws Exception {
        HSSFWorkbook wb = ExportTool.export(list, excelHeader, excelCloName, sheetName);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH/mm/ss");
        String fileNames = fileName + df.format(new Date()) + ".xls";
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileNames.getBytes("gb2312"), "ISO-8859-1"));
            OutputStream ouputStream;
            ouputStream = response.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
