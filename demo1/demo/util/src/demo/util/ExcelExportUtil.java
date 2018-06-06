package demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * EXCEL??????
 */
public class ExcelExportUtil {
    /**
     * EXCEL????
     *
     * @param fileName ???????
     * @param fileData ???????
     * @param response ???????
     */
    public static void excelExport(String fileName, String fileData, HttpServletResponse response) {
        OutputStream out = null;
        try {
            //EXCEL????
            Workbook workbook = new HSSFWorkbook();
            //???岿??
            Font headFont = getFont(workbook, 12, Font.BOLDWEIGHT_BOLD);//??????????
            Font bodyFont = getFont(workbook, 11, Font.BOLDWEIGHT_NORMAL);//????????????
            //???????
            CellStyle bodyCss = null;//????????
            CellStyle headCss = getCellStyle(workbook, headFont, CellStyle.ALIGN_CENTER);//?????????
            //????????
            fileName = fileName + ".xls";//?????
            //???????
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> datas = mapper.readValue(fileData, List.class);
            // ???????
            for (Map<String, Object> map : datas) {
                //???????
                String sheetName = map.get("sheetName").toString();//SHEET??
                int height = Integer.parseInt(map.get("height").toString());//??????
                int columns = Integer.parseInt(map.get("columns").toString());//???????
                List<Integer> widths = (List<Integer>) map.get("widths");//????п??
                List<String> headers = (List<String>) map.get("headers");//?????????
                List<List<String>> rows = (List<List<String>>) map.get("rows");//?б?????
                List<List<Integer>> colors = (List<List<Integer>>) map.get("colors");//?????????
                List<String> cellTypes = (List<String>) map.get("cellTypes");//???????
                //????SHEET
                Sheet sheet = workbook.createSheet(sheetName);
                //???????
                Cell cell = null;
                Row row = sheet.createRow(0);
                row.setHeight((short) height);
                for (int i = 0; i < headers.size(); i++) {
                    cell = row.createCell(i);
                    sheet.setColumnWidth(i, widths.get(i));
                    cell.setCellStyle(headCss);
                    cell.setCellValue(headers.get(i));
                }
                //???????
                if (rows != null && !rows.isEmpty()) {
                    int j = 0;
                    for (List<String> ds : rows) {
                        if (ds != null && !ds.isEmpty()) {
                            row = sheet.createRow(j + 1);
                            row.setHeight((short) 300);
                            for (int k = 0; k < columns; k++) {
                                cell = row.createCell(k);
                                if ("number".equals(cellTypes.get(k))) {
                                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                                    cell.setCellValue(Long.valueOf(String.valueOf(ds.get(k))));
                                    bodyCss = getCellStyle(workbook, bodyFont, CellStyle.ALIGN_RIGHT);
                                } else if("double".equals(cellTypes.get(k))){
                                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                                    cell.setCellValue(Double.valueOf(String.valueOf(ds.get(k))));
                                    bodyCss = getCellStyle(workbook, bodyFont, CellStyle.ALIGN_RIGHT);
                                } else if("date".equals(cellTypes.get(k))){
                                    cell.setCellType(Cell.CELL_TYPE_STRING);
                                    cell.setCellValue(String.valueOf(ds.get(k)));
                                    bodyCss = getCellStyle(workbook, bodyFont, CellStyle.ALIGN_CENTER);
                                }else {
                                    cell.setCellType(Cell.CELL_TYPE_STRING);
                                    cell.setCellValue(String.valueOf(ds.get(k)));
                                    bodyCss = getCellStyle(workbook, bodyFont, CellStyle.ALIGN_LEFT);
                                }
                                if (colors != null && !colors.isEmpty() && colors.get(j).get(k) != 0) {
                                    bodyCss.setAlignment(CellStyle.ALIGN_CENTER);
                                    bodyCss.setFillPattern(CellStyle.SOLID_FOREGROUND);
                                    bodyCss.setFillForegroundColor(colors.get(j).get(k).shortValue());
                                }
                                cell.setCellStyle(bodyCss);
                            }
                            j++;
                        }
                    }
                }
            }
            //????????????????
            response.reset();
            response.setContentType("applicationnd.ms-excel;charset=UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    //???????
    private static Font getFont(Workbook workbook, Integer fontHeight, Short boldWeight) {
        Font font = workbook.createFont();//????????
        font.setFontHeightInPoints(fontHeight.shortValue());//??????????
        font.setBoldweight(boldWeight);//??????????????
        font.setFontName("????");//????????????
        return font;
    }

    //???????????
    private static CellStyle getCellStyle(Workbook workbook, Font font, Short alignment) {
        CellStyle cellStyle = workbook.createCellStyle();//????????????
        cellStyle.setFont(font);//????????
        cellStyle.setWrapText(true);//???????????
        cellStyle.setAlignment(alignment);//??????????????
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//???????????
        return cellStyle;
    }
}