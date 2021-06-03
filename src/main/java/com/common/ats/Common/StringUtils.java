package com.common.ats.Common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class StringUtils {
    public static final String EMPTY_STRING = "";

    public static String replaceSubString(String originalValue, String replacement, int start, int end) {
        if (isBlank(originalValue) || start < 0 || start > originalValue.length() || end < 0 || end > originalValue.length() || end <= start) {
            return originalValue;
        }
        String preStr = originalValue.substring(0, start);
        return String.valueOf(preStr) + replacement + originalValue.substring(end, originalValue.length());
    }

    public static String amountFormat(String amount) {
        if (amount == null || amount.equals("0")) {
            return "0";
        }
        return String.valueOf(amount) + "00";
    }

    public static String stringFormat(String str) {
        if (str.equals("null") || str.equals(EMPTY_STRING)) {
            return "0";
        }
        return str;
    }

    public static String stringNullFormat(String str) {
        if (str.equals("null") || str.equals(EMPTY_STRING)) {
            return null;
        }
        return str;
    }

    public static String amountSysFormat(String amount) {
        if (amount == null || amount.equals("0")) {
            return "0.00";
        }
        if (amount.length() < 3) {
            return "0." + amount;
        }
        return String.valueOf(amount.substring(0, amount.length() - 2)) + "." + amount.substring(amount.length() - 2, amount.length());
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isBlankOrNull(String str) {
        int strLen;
        if (str == null || str.equals("null") || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String stringFirstCharToUpperCase(String str) {
        if (str == null || str.length() < 1) {
            return str;
        }
        String strt1 = str.substring(0, 1).toUpperCase();
        return String.valueOf(strt1) + str.substring(1);
    }

    public static String stringFirstCharToLowerCase(String str) {
        if (str == null || str.length() < 1) {
            return str;
        }
        String strt1 = str.substring(0, 1).toLowerCase();
        return String.valueOf(strt1) + str.substring(1);
    }

    public static List<Integer> convertColumnRegex2Columns(String columnRegex) {
        int to;
        List<Integer> listColumns = new ArrayList<>();
        if (isBlank(columnRegex) || !columnRegex.matches("^((\\d+)|(\\d+):(\\d+))(,((\\d+)|(\\d+):(\\d+)))*$")) {
            return listColumns;
        }
        String[] columnStrings = columnRegex.split(",");
        for (String split : columnStrings) {
            String[] columns = split.split(":");
            if (1 == columns.length) {
                listColumns.add(Integer.valueOf(columns[0]));
            } else if (2 != columns.length) {
                return new ArrayList<>();
            } else {
                int from = Integer.valueOf(columns[0]).intValue();
                int temp = Integer.valueOf(columns[1]).intValue();
                if (from > temp) {
                    to = from;
                    from = temp;
                } else {
                    to = temp;
                }
                for (int j = from; j <= to; j++) {
                    listColumns.add(Integer.valueOf(j));
                }
            }
        }
        Collections.sort(listColumns, new IntegerCompare((IntegerCompare) null));
        return listColumns;
    }

    private static class IntegerCompare implements Comparator<Object> {
        private IntegerCompare() {
        }

        /* synthetic */ IntegerCompare(IntegerCompare integerCompare) {
            this();
        }

        public int compare(Object o1, Object o2) {
            return ((Integer) o1).intValue() - ((Integer) o2).intValue();
        }
    }

    public static String getTradeNo(String url) {
        if (url == null) {
            return EMPTY_STRING;
        }
        String[] tr = url.split("=");
        return tr[tr.length - 1];
    }

    public static boolean isNull(String str) {
        return str == null;
    }

    public static String getRandomId() {
        return String.valueOf(DateUtil.getLongDateString()) + "-" + new Random().nextInt(1000);
    }

    public static List<Map<String, String>> getParamList(String path) throws IOException {
        List<Map<String, String>> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new StringUtils().getClass().getClassLoader().getResourceAsStream(path)));
        String[] keys = null;
        int count = 0;
        while (isNotBlank(new BufferedReader(new InputStreamReader(new StringUtils().getClass().getClassLoader().getResourceAsStream(path))).readLine())) {
            try {
                count++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        HashMap[] hashMapArr = new HashMap[(count - 1)];
        String line1 = br.readLine();
        if (isNotBlank(line1)) {
            keys = line1.split(",");
        }
        int t = 0;
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            Map<String, String> map = new HashMap<>();
            String[] strs = line.split(",");
            if (strs.length == keys.length) {
                for (int i = 0; i < keys.length; i++) {
                    map.put(keys[i], strs[i]);
                }
            }
            hashMapArr[t] = (HashMap) map;
            list.add(hashMapArr[t]);
            t++;
        }
        return list;
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean compareLarger(String arg1, String arg2) {
        if (Integer.parseInt(arg1) > Integer.parseInt(arg2)) {
            return true;
        }
        return false;
    }

    public static boolean equals(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }
        return str1.equals(str2);
    }

    public static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String substringAfter(String str, String separator) {
        int pos;
        if (str == null || str.length() == 0) {
            return str;
        }
        if (separator == null || (pos = str.indexOf(separator)) == -1) {
            return EMPTY_STRING;
        }
        return str.substring(separator.length() + pos);
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }
        return str1.equalsIgnoreCase(str2);
    }

    public static String toUpperCase(String str) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase();
    }

    public static String toLowerCase(String str) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase();
    }
}
