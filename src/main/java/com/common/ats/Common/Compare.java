package com.common.ats.Common;

import com.common.ats.Assert.AtsAssertUtils;
import java.util.List;
import java.util.Map;

public class Compare {
    public static boolean compare(List<List<Map<String, String>>> trueRes, List<List<Map<String, String>>> expectRes) {
        Boolean flag = false;
        for (int i = 0; i < trueRes.size(); i++) {
            for (int j = 0; j < trueRes.get(i).size(); j++) {
                flag = comparMap((Map) trueRes.get(i).get(j), (Map) expectRes.get(i).get(j));
            }
        }
        return flag.booleanValue();
    }

    public static Boolean comparMap(Map<String, String> map1, Map<String, String> map2) {
        boolean flag = true;
        for (Object key : map2.keySet()) {
            if (!map1.containsKey(key)) {
                AtsAssertUtils.assertEquals("预期结果中的字段：【" + key + "],在数据库中查询不到,请检查确认信息.", (Object) 2, (Object) 1);
            } else if (!StringUtils.equals(map1.get(key), map2.get(key))) {
                flag = false;
                AtsAssertUtils.assertEquals(key + "的值和数据库中不一致。", map1.get(key), map2.get(key));
            }
        }
        return Boolean.valueOf(flag);
    }
}
