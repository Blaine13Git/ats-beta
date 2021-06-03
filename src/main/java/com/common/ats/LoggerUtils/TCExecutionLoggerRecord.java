package com.common.ats.LoggerUtils;

import java.util.Arrays;
import org.testng.ITestContext;
import org.testng.ITestResult;

public class TCExecutionLoggerRecord {
    static String sta = ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
    static String str = "######################################################################################################";

    public static void initTcRecord(ITestContext context, int para) {
        if (para == 0) {
            TcRunLog.info(str);
            TcRunLog.info("用例执行环境初始化开始....");
            TcRunLog.info("测试类: " + Arrays.toString(context.getAllTestMethods()).split("instance:")[1].split("@")[0]);
            TcRunLog.info(String.valueOf(str) + "\r\n");
            return;
        }
        TcRunLog.info("用例执行环境初始化结束....");
    }

    public static void startTcRecord(ITestResult result) {
        initTcRecord(result.getTestContext(), 0);
        TcRunLog.info("\r\n" + sta);
        TcRunLog.info(">>>>>>>>>>>>>>>>>>>>>>开始执行" + result.getTestContext().getName() + "下的测试用例: 【 " + result.getName() + " 】<<<<<<<<<<<<<<<<<<<<<");
        TcRunLog.info(String.valueOf(sta) + "\r\n");
    }

    public static void endSuccTcRecord(ITestResult result) {
        System.out.println("\n");
        TcRunLog.info(sta);
        TcRunLog.info("测试用例:【" + result.getName() + "】成功....");
        TcRunLog.info(String.valueOf(sta) + "\r\n");
    }

    public static void endFailTcRecord(ITestResult result) {
        System.out.println("\n");
        TcRunLog.info(sta);
        TcRunLog.error("测试用例:【" + result.getName() + "】执行失败....");
        TcRunLog.error("失败信息1:" + result.getThrowable().getMessage());
        TcRunLog.error("失败信息2:\r\n");
        result.getThrowable().printStackTrace();
        TcRunLog.info(String.valueOf(sta) + "\r\n");
        System.out.println("\n");
    }

    public static void endSkipTcRecord(ITestResult result) {
        System.out.println("\n");
        TcRunLog.info(sta);
        TcRunLog.info("测试用例:【" + result.getName() + "】跳过....");
        TcRunLog.info(String.valueOf(sta) + "\r\n");
    }

    public static void endTcRecord(ITestContext context) {
        TcRunLog.info(str);
        TcRunLog.info("测试用例执行结束...." + context.getName());
        TcRunLog.info("失败的测试用例：" + context.getFailedTests().getAllMethods());
        TcRunLog.info(String.valueOf(str) + "\r\n");
    }
}
