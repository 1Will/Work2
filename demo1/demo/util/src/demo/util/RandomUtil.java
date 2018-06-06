package demo.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 */
public class RandomUtil {
    /**
     * 获取随机数的rownum数
     * @param
     * @return
     */
    public static Integer[] getRandom(Integer sum,Integer no){
        List<Integer> reList = new ArrayList<Integer>();
        int[] num = new int[sum];
        boolean[] flag = new boolean[sum];
        int count = 0;
        for (int i = 0; i < sum; i++)
        {
            num[i] = i + 1;
            flag[i] = true;
        }
        while (count != no) {
            int k = (int) (Math.random() * sum);
            if (flag[k]) {
                reList.add(num[k]);
                flag[k] = false;
                count++;
            }
        }
        Integer[] res = new Integer[reList.size()];
        res = (Integer[])reList.toArray(res);
        return res;
    }
}
