package com.common.ats.Common;

import com.common.ats.LoggerUtils.TcRunLog;
import com.common.ats.PropertyUtils.ReadProperties.DefinePropertiesField;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrintXslFormat {
    public static void printResultSet(ResultSet oResultSet) {
        List row = new ArrayList();
        List<List> columnList = new ArrayList<>();
        try {
            ResultSetMetaData rsmd = oResultSet.getMetaData();
            int row_column = iteratorResult(oResultSet, row, columnList, 0, rsmd, rsmd.getColumnCount());
            int columnNumber = row.size() / row_column;
            ConsoleTable t = new ConsoleTable(columnNumber, true);
            t.appendRow();
            for (int i = 0; i < columnNumber; i++) {
                t.appendColumn(row.get(i));
            }
            for (int i2 = 0; i2 < row_column; i2++) {
                t.appendRow();
                for (int ii = 0; ii < columnNumber; ii++) {
                    t.appendColumn(columnList.get(i2).get(ii));
                }
            }
            TcRunLog.info("\r\n" + t);
        } catch (Exception e) {
            TcRunLog.error("将ResultSet转换成Map失败:转换出现异常.\r\n" + e);
        }
    }

    public static void printResultSetX(ResultSet oResultSet) {
        List row = new ArrayList();
        List<List> columnList = new ArrayList<>();
        try {
            ResultSetMetaData rsmd = oResultSet.getMetaData();
            int row_column = iteratorResult(oResultSet, row, columnList, 0, rsmd, rsmd.getColumnCount());
            int columnNumber = row.size() / row_column;
            ConsoleTable t = new ConsoleTable(row_column + 1, false);
            for (int i = 0; i < columnNumber; i++) {
                t.appendRow();
                t.appendColumn(row.get(i));
                for (int ii = 0; ii < row_column; ii++) {
                    t.appendColumn(columnList.get(ii).get(i));
                }
            }
            TcRunLog.info("\r\n" + t);
        } catch (Exception e) {
            TcRunLog.error("printResultSet>>>将ResultSet转换成Map失败:转换出现异常.\r\n");
            e.printStackTrace();
        }
    }

    private static int iteratorResult(ResultSet oResultSet, List row, List<List> columnList, int row_column, ResultSetMetaData rsmd, int count) throws SQLException {
        while (oResultSet.next()) {
            List column = new ArrayList();
            for (int i = 1; i <= count; i++) {
                String key = rsmd.getColumnLabel(i);
                String value = oResultSet.getString(i);
                row.add(key);
                column.add(value);
            }
            row_column++;
            columnList.add(column);
        }
        return row_column;
    }

    public static void oPrintPropertiesField(DefinePropertiesField oDefinePropertiesField) {
        String[] rs = DefinePropertiesField.getFiledName();
        ConsoleTable t = new ConsoleTable(rs.length, true);
        for (int i = 0; i < rs.length; i++) {
            String[] x = (String[]) oDefinePropertiesField.getFieldValueByName(rs[i]);
            t.appendRow();
            t.appendColumn(rs[i]);
            for (String appendColum : x) {
                t.appendColumn(appendColum);
            }
        }
        TcRunLog.debug("\r\n" + t);
    }

    public static void iteratesMap(Map<String, String> oMap) {
        ConsoleTable t = new ConsoleTable(2, false);
        for (Map.Entry<String, String> entry : oMap.entrySet()) {
            t.appendRow();
            t.appendColumn(entry.getKey()).appendColumn(entry.getValue());
        }
        TcRunLog.debug("\r\n" + t);
    }
}
