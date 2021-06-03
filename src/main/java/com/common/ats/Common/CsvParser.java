package com.common.ats.Common;

import com.common.ats.LoggerUtils.TcRunLog;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CsvParser {
    private static Pattern pattern = Pattern.compile(",?\"([^\"]*)\"|[^,]*?,");

    public static void matchCsvReadLine(String oReadLine, Map<String, String> map, String[] temp) {
        boolean z;
        Matcher m = getMatcher(oReadLine);
        boolean found = false;
        int i = 0;
        while (m.find()) {
            found = true;
            String A = m.group();
            if (StringUtils.equals(A.substring(0, 1), "\"") && A.length() != 1) {
                A = A.replace("\"", StringUtils.EMPTY_STRING).trim();
            }
            if (A.endsWith(",")) {
                A = A.substring(0, A.length() - 1);
            }
            boolean isEmpty = A.isEmpty();
            if (A == StringUtils.EMPTY_STRING) {
                z = true;
            } else {
                z = false;
            }
            if (!z && !isEmpty) {
                map.put(temp[i], A);
                i++;
            }
        }
        if (!found) {
            TcRunLog.error("CSV数据正则匹配错误-----错误-------");
        }
    }

    private static Matcher getMatcher(String sequence) {
        return pattern.matcher(sequence);
    }
}
