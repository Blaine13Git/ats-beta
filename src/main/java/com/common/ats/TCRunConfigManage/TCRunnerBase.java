package com.common.ats.TCRunConfigManage;

import com.common.ats.Common.StringUtils;
import com.common.ats.CsvProviderUtils.CsvDataProvideBase.CsvDataProvideBase;
import org.testng.annotations.Listeners;

@Listeners({TCRunningListener.class})
public class TCRunnerBase extends CsvDataProvideBase {
    public TCRunnerBase() {
        super(StringUtils.EMPTY_STRING);
    }
}
