package com.common.ats.SharedTestData;

import com.common.ats.Assert.AtsAssertUtils;
import com.common.ats.Common.Compare;
import com.common.ats.Common.CsvParser;
import com.common.ats.Common.StringUtils;
import com.common.ats.DBUtils.DbOperationUtils.DbSqlCommand;
import com.common.ats.LoggerUtils.TcRunLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class TestDataInitHanderImpl implements TestDataInitHander {
    public void dbInsert(String[] files, String[] tableNames) {
        for (int i = 0; i < files.length; i++) {
            dbInsert(files[i], tableNames[i]);
        }
    }

    private void dbInsert(String filePath, String tableName) {
        try {
            BufferedReader br = loadCsvTestData(filePath);
            DbSqlCommand.dbInsert(tableName, jointSQL(tableName, br));
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedReader loadCsvTestData(String filePath) {
        try {
            return new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filePath), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String jointSQL(String tableName, BufferedReader br) throws IOException {
        StringBuffer oStringBuffer = new StringBuffer("INSERT INTO ").append(tableName);
        String preSql = null;
        String line = br.readLine();
        if (line != null) {
            StringTokenizer st = new StringTokenizer(line, ",");
            oStringBuffer.append(" (");
            while (st.hasMoreTokens()) {
                oStringBuffer.append(st.nextToken().replaceAll("\"", StringUtils.EMPTY_STRING)).append(",");
            }
            preSql = oStringBuffer.substring(0, oStringBuffer.length() - 1) + ")";
        }
        StringBuffer sqlBuffer = new StringBuffer();
        while (true) {
            String line2 = br.readLine();
            if (line2 == null) {
                String sql = sqlBuffer.substring(0, sqlBuffer.length() - 1) + ")";
                TcRunLog.info("当前生成的SQL语句是: \r\n -------->>>  " + sql + "\n");
                return sql;
            }
            sqlBuffer.append(preSql).append(" VALUES (");
            StringTokenizer st2 = new StringTokenizer(line2, ",");
            while (st2.hasMoreTokens()) {
                sqlBuffer.append(st2.nextToken().replaceAll("\"", "'")).append(",");
            }
        }
    }

    public int[] dbUpdate(String[] sqls, String[] tableNames) {
        int[] updateRes = new int[sqls.length];
        for (int i = 0; i < sqls.length; i++) {
            int rs = DbSqlCommand.dbUpdate(tableNames[i], sqls[i]);
            if (rs == 0) {
                TcRunLog.info("第 【" + i + "】个SQL语句: " + sqls[i] + "  Update动作执行失败。");
            }
            updateRes[i] = rs;
        }
        return updateRes;
    }

    public List<List<Map<String, String>>> dbSelect(String[] sqls, String[] tableNames) {
        List<List<Map<String, String>>> oList = new ArrayList<>();
        for (int i = 0; i < sqls.length; i++) {
            oList.add(DbSqlCommand.dbSelect(tableNames[i], sqls[i]));
        }
        return oList;
    }

    public List<ResultSet> dbSelectResultSet(String[] sqls, String[] tableNames) {
        List<ResultSet> oList = new ArrayList<>();
        for (int i = 0; i < sqls.length; i++) {
            oList.add(DbSqlCommand.select(tableNames[i], sqls[i]));
        }
        return oList;
    }

    public boolean dbCheck(String[] sqls, String[] tableNames, String[] files) {
        List<List<Map<String, String>>> trueRes = dbSelect(sqls, tableNames);
        List<List<Map<String, String>>> expectRes = getCsvData(files);
        if (trueRes != expectRes) {
            return Compare.compare(trueRes, expectRes);
        }
        AtsAssertUtils.assertEquals("数据库查询出来的结果", (Object) 1, (Object) 1);
        return true;
    }

    public List<List<Map<String, String>>> getCsvData(String[] files) {
        List<List<Map<String, String>>> list = new ArrayList<>();
        try {
            for (String filePath : files) {
                getCsvData(list, filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void getCsvData(List<List<Map<String, String>>> list, String filePath) throws IOException {
        List<Map<String, String>> oList = new ArrayList<>();
        BufferedReader br = loadCsvTestData(filePath);
        String[] temp = null;
        String line = br.readLine();
        if (line != null) {
            temp = line.replaceAll("\"", StringUtils.EMPTY_STRING).split(",");
        }
        while (true) {
            String line2 = br.readLine();
            if (line2 == null) {
                list.add(oList);
                return;
            }
            Map<String, String> oMap = new HashMap<>();
            CsvParser.matchCsvReadLine(line2, oMap, temp);
            oList.add(oMap);
        }
    }

    public boolean dbCheckMap(Map<String, String> oMap, String fileString) {
        return Compare.comparMap(oMap, getCsvData(fileString)).booleanValue();
    }

    private Map<String, String> getCsvData(String fileString) {
        Map<String, String> oMap = new HashMap<>();
        try {
            BufferedReader br = loadCsvTestData(fileString);
            String[] temp = null;
            String line = br.readLine();
            if (line != null) {
                temp = line.replaceAll("\"", StringUtils.EMPTY_STRING).split(",");
            }
            while (true) {
                String line2 = br.readLine();
                if (line2 == null) {
                    break;
                }
                CsvParser.matchCsvReadLine(line2, oMap, temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return oMap;
    }
}
