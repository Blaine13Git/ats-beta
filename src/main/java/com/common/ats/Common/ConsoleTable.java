package com.common.ats.Common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ConsoleTable {
    private static int margin = 2;
    private int column;
    private int[] columnLen;
    private int rowLen = 20;
    private List<List<?>> rows = new ArrayList();

    public ConsoleTable(int column2, boolean printHeader) {
        this.column = column2;
        this.columnLen = new int[column2];
    }

    public void appendRow() {
        this.rows.add(new ArrayList(this.column));
    }

    public static int max(int[] values) {
        int max = values[0];
        for (int v : values) {
            if (max < v) {
                max = v;
            }
        }
        return max;
    }

    public ConsoleTable appendColumn(Object value) {
        if (value == null) {
            value = "NULL";
        }
        List row = this.rows.get(this.rows.size() - 1);
        row.add(value);
        int len = value.toString().getBytes().length;
        if (this.columnLen[row.size() - 1] < len) {
            this.columnLen[row.size() - 1] = len;
        }
        for (int i = 0; i < this.columnLen.length - 1; i++) {
            this.columnLen[i] = max(this.columnLen);
        }
        return this;
    }

    public String toString() {
        int x;
        StringBuilder buf = new StringBuilder();
        int le = (margin * this.column) + (this.rowLen * this.rows.get(0).size()) + (this.column - 1);
        buf.append("|").append(printChar('-', le)).append("|\r\n");
        for (int ii = 0; ii < this.rows.size(); ii++) {
            List<?> row = this.rows.get(ii);
            for (int i = 0; i < this.column; i++) {
                String o = StringUtils.EMPTY_STRING;
                if (i < row.size()) {
                    o = row.get(i).toString();
                }
                int lenValue = o.getBytes().length;
                if (isChineseChar(o)) {
                    x = lenValue / 2;
                } else {
                    x = o.length();
                }
                if (x >= 15) {
                    o = String.valueOf(o.toString().substring(0, 12)) + "...";
                    x = o.toString().getBytes().length;
                }
                buf.append('|').append(printChar((char) 0, margin)).append(o);
                buf.append(printChar((char) 0, (this.rowLen - margin) - x));
            }
            buf.append("|\r\n");
            buf.append("|").append(printChar('-', le)).append("|\r\n");
        }
        return buf.toString();
    }

    private String printChar(char c, int len) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < len; i++) {
            buf.append(c);
        }
        return buf.toString();
    }

    public static boolean isChineseChar(String str) {
        if (Pattern.compile("[一-龥]").matcher(str).find()) {
            return true;
        }
        return false;
    }
}
