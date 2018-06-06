package demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author a
 * @date ${date}
 * $END
 */
public class ExcelBean {
    /**
     * 社区统计模块导出Excel，返回json
     * @param list
     * @return
     * @throws Exception
     */
    public static String getSqtjJson(List list) throws Exception{
        List newList = new ArrayList();
        Map<String, Object> map = new HashMap<>();
        String sheetName = "社区统计";
        int height = 500;
        int columns = 3;
        map.put("sheetName",sheetName);
        map.put("height",height);
        map.put("columns",columns);

        List<Integer> widths = new ArrayList<>();
        widths.add(3000);
        widths.add(10000);
        widths.add(3000);
        map.put("widths",widths);

        List<String> headers = new ArrayList<>();
        headers.add("序号");
        headers.add("机构名称");
        headers.add("社区数量");
        map.put("headers",headers);

        List<String> cellTypes = new ArrayList<>();
        cellTypes.add("number");
        cellTypes.add("");
        cellTypes.add("number");
        map.put("cellTypes",cellTypes);


        List<List<String>> rows = new ArrayList<List<String>>();
        if(list != null && list.size() >0){
            for(int i =0 ;i<list.size();i++){
                List<String> temp = new ArrayList<String>();
                temp.add((i+1)+"");
                temp.add(((Object[])list.get(i))[1].toString());
                temp.add(((Object[])list.get(i))[2].toString());
                rows.add(temp);
            }
        }
        map.put("rows",rows);
        newList.add(map);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(newList);

    }

}
