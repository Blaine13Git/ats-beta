package com.common.ats.TCRunConfigManage;

import com.common.ats.LoggerUtils.TCExecutionLoggerRecord;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TCRunningListener implements ITestListener {
    public void onTestStart(ITestResult result) {
        TCExecutionLoggerRecord.startTcRecord(result);
    }

    public void onTestSuccess(ITestResult result) {
        TCExecutionLoggerRecord.endSuccTcRecord(result);
    }

    public void onTestFailure(ITestResult result) {
        TCExecutionLoggerRecord.endFailTcRecord(result);
    }

    public void onTestSkipped(ITestResult result) {
        TCExecutionLoggerRecord.endSkipTcRecord(result);
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onStart(ITestContext context) {
    }

    public void onFinish(ITestContext context) {
        TCExecutionLoggerRecord.endTcRecord(context);
    }
}
