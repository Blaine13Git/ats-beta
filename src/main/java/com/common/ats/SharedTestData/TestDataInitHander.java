package com.common.ats.SharedTestData;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface TestDataInitHander {
    boolean dbCheck(String[] strArr, String[] strArr2, String[] strArr3);

    boolean dbCheckMap(Map<String, String> map, String str);

    void dbInsert(String[] strArr, String[] strArr2);

    List<List<Map<String, String>>> dbSelect(String[] strArr, String[] strArr2);

    List<ResultSet> dbSelectResultSet(String[] strArr, String[] strArr2);

    int[] dbUpdate(String[] strArr, String[] strArr2);
}
