package com.common.ats.Common;

import com.common.ats.LoggerUtils.TcRunLog;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
    public static final long ONE_DAY_MILL_SECONDS = 86400000;
    public static final long ONE_DAY_SECONDS = 86400;
    public static final String chineseDtFormat = "yyyy年MM月dd日";
    public static final String longFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String monthFormat = "yyyy-MM";
    public static final String newFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String noSecondFormat = "yyyy-MM-dd HH:mm";
    public static final String shortFormat = "yyyy-MM-dd";
    public static final String timeFormat = "HH:mm:ss";
    public static final String webFormat = "yyyy-MM-dd";

    public static String getLongDateString() {
        return getNewDateFormat(longFormat).format(new Date());
    }

    public static DateFormat getNewDateFormat(String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        df.setLenient(false);
        return df;
    }

    public static String format(Date date, String format) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static Date parseDateNoTime(String sDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(shortFormat);
        if (sDate == null || sDate.length() < shortFormat.length()) {
            throw new ParseException("length too little", 0);
        } else if (StringUtils.isNumeric(sDate)) {
            return dateFormat.parse(sDate);
        } else {
            throw new ParseException("not all digit", 0);
        }
    }

    public static Date parseDateNoTime(String sDate, String format) throws ParseException {
        if (StringUtils.isBlank(format)) {
            throw new ParseException("Null format. ", 0);
        }
        DateFormat dateFormat = new SimpleDateFormat(format);
        if (sDate != null && sDate.length() >= format.length()) {
            return dateFormat.parse(sDate);
        }
        throw new ParseException("length too little", 0);
    }

    public static Date parseDateNoTimeWithDelimit(String sDate, String delimit) throws ParseException {
        String sDate2 = sDate.replaceAll(delimit, StringUtils.EMPTY_STRING);
        DateFormat dateFormat = new SimpleDateFormat(shortFormat);
        if (sDate2 != null && sDate2.length() == shortFormat.length()) {
            return dateFormat.parse(sDate2);
        }
        throw new ParseException("length not match", 0);
    }

    public static Date parseDateLongFormat(String sDate) {
        DateFormat dateFormat = new SimpleDateFormat(longFormat);
        Date d = null;
        if (sDate != null && sDate.length() == longFormat.length()) {
            try {
                d = dateFormat.parse(sDate);
            } catch (ParseException e) {
                return null;
            }
        }
        Date date = d;
        return d;
    }

    public static Date parseDateNewFormat(String sDate) {
        DateFormat dateFormat = new SimpleDateFormat(newFormat);
        Date d = null;
        dateFormat.setLenient(false);
        if (sDate != null && sDate.length() == newFormat.length()) {
            try {
                d = dateFormat.parse(sDate);
            } catch (ParseException e) {
                return null;
            }
        }
        Date date = d;
        return d;
    }

    public static Date addHours(Date date, long hours) {
        return addMinutes(date, 60 * hours);
    }

    public static Date addMinutes(Date date, long minutes) {
        return addSeconds(date, 60 * minutes);
    }

    public static Date addSeconds(Date date1, long secs) {
        return new Date(date1.getTime() + (1000 * secs));
    }

    public static boolean isValidHour(String hourStr) {
        int hour;
        if (StringUtils.isEmpty(hourStr) || !StringUtils.isNumeric(hourStr) || (hour = new Integer(hourStr).intValue()) < 0 || hour > 23) {
            return false;
        }
        return true;
    }

    public static boolean isValidMinuteOrSecond(String str) {
        int hour;
        if (StringUtils.isEmpty(str) || !StringUtils.isNumeric(str) || (hour = new Integer(str).intValue()) < 0 || hour > 59) {
            return false;
        }
        return true;
    }

    public static Date addDays(Date date1, long days) {
        return addSeconds(date1, ONE_DAY_SECONDS * days);
    }

    public static String getTomorrowDateString(String sDate) throws ParseException {
        return getDateString(addSeconds(parseDateNoTime(sDate), ONE_DAY_SECONDS));
    }

    public static String getLongDateString(Date date) {
        return getDateString(date, new SimpleDateFormat(longFormat));
    }

    public static String getNewFormatDateString(Date date) {
        return getDateString(date, new SimpleDateFormat(newFormat));
    }

    public static String getDateString(Date date, DateFormat dateFormat) {
        if (date == null || dateFormat == null) {
            return null;
        }
        return dateFormat.format(date);
    }

    public static String getYesterDayDateString(String sDate) throws ParseException {
        return getDateString(addSeconds(parseDateNoTime(sDate), -86400));
    }

    public static String getDateString(Date date) {
        return getNewDateFormat(shortFormat).format(date);
    }

    public static String getWebDateString(Date date) {
        return getDateString(date, getNewDateFormat(webFormat));
    }

    public static String getChineseDateString(Date date) {
        return getDateString(date, getNewDateFormat(chineseDtFormat));
    }

    public static String getTodayString() {
        return getDateString(new Date(), getNewDateFormat(shortFormat));
    }

    public static String getTimeString(Date date) {
        return getDateString(date, getNewDateFormat(timeFormat));
    }

    public static String getBeforeDayString(int days) {
        return getDateString(new Date(System.currentTimeMillis() - (ONE_DAY_MILL_SECONDS * ((long) days))), getNewDateFormat(shortFormat));
    }

    public static long getDiffSeconds(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();
        sysDate.setTime(one);
        Calendar failDate = new GregorianCalendar();
        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / 1000;
    }

    public static long getDiffMinutes(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();
        sysDate.setTime(one);
        Calendar failDate = new GregorianCalendar();
        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / 60000;
    }

    public static long getDiffDays(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();
        sysDate.setTime(one);
        Calendar failDate = new GregorianCalendar();
        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / ONE_DAY_MILL_SECONDS;
    }

    public static String getBeforeDayString(String dateString, int days) {
        Date date;
        DateFormat df = getNewDateFormat(shortFormat);
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            date = new Date();
        }
        return df.format(new Date(date.getTime() - (ONE_DAY_MILL_SECONDS * ((long) days))));
    }

    public static boolean isValidShortDateFormat(String strDate) {
        if (strDate.length() != shortFormat.length()) {
            return false;
        }
        try {
            Integer.parseInt(strDate);
            try {
                getNewDateFormat(shortFormat).parse(strDate);
                return true;
            } catch (ParseException e) {
                return false;
            }
        } catch (Exception e2) {
            return false;
        }
    }

    public static boolean isValidShortDateFormat(String strDate, String delimiter) {
        return isValidShortDateFormat(strDate.replaceAll(delimiter, StringUtils.EMPTY_STRING));
    }

    public static boolean isValidLongDateFormat(String strDate) {
        if (strDate.length() != longFormat.length()) {
            return false;
        }
        try {
            Long.parseLong(strDate);
            try {
                getNewDateFormat(longFormat).parse(strDate);
                return true;
            } catch (ParseException e) {
                return false;
            }
        } catch (Exception e2) {
            return false;
        }
    }

    public static boolean isValidLongDateFormat(String strDate, String delimiter) {
        return isValidLongDateFormat(strDate.replaceAll(delimiter, StringUtils.EMPTY_STRING));
    }

    public static String getShortDateString(String strDate) {
        return getShortDateString(strDate, "-|/");
    }

    public static String getShortDateString(String strDate, String delimiter) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }
        String temp = strDate.replaceAll(delimiter, StringUtils.EMPTY_STRING);
        if (!isValidShortDateFormat(temp)) {
            return null;
        }
        return temp;
    }

    public static String getShortFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(5, 1);
        return getNewDateFormat(shortFormat).format(cal.getTime());
    }

    public static String getWebTodayString() {
        return getNewDateFormat(webFormat).format(new Date());
    }

    public static String getWebFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(5, 1);
        return getNewDateFormat(webFormat).format(cal.getTime());
    }

    public static String convert(String dateString, DateFormat formatIn, DateFormat formatOut) {
        try {
            return formatOut.format(formatIn.parse(dateString));
        } catch (ParseException e) {
            TcRunLog.debug("convert() --- orign date error: " + dateString);
            return StringUtils.EMPTY_STRING;
        }
    }

    public static String convert2WebFormat(String dateString) {
        return convert(dateString, getNewDateFormat(shortFormat), getNewDateFormat(webFormat));
    }

    public static String convert2ChineseDtFormat(String dateString) {
        return convert(dateString, getNewDateFormat(shortFormat), getNewDateFormat(chineseDtFormat));
    }

    public static String convertFromWebFormat(String dateString) {
        return convert(dateString, getNewDateFormat(webFormat), getNewDateFormat(shortFormat));
    }

    public static boolean webDateNotLessThan(String date1, String date2) {
        return dateNotLessThan(date1, date2, getNewDateFormat(webFormat));
    }

    public static boolean dateNotLessThan(String date1, String date2, DateFormat format) {
        try {
            if (format.parse(date1).before(format.parse(date2))) {
                return false;
            }
            return true;
        } catch (ParseException e) {
            TcRunLog.debug("dateNotLessThan() --- ParseException(" + date1 + ", " + date2 + ")");
            return false;
        }
    }

    public static String getEmailDate(Date today) {
        return new SimpleDateFormat("yyyy��MM��dd��HH:mm:ss").format(today);
    }

    public static String getSmsDate(Date today) {
        return new SimpleDateFormat("MM��dd��HH:mm").format(today);
    }

    public static String formatTimeRange(Date startDate, Date endDate, String format) {
        if (endDate == null || startDate == null) {
            return null;
        }
        long range = endDate.getTime() - startDate.getTime();
        long day = range / ONE_DAY_MILL_SECONDS;
        long hour = (range % ONE_DAY_MILL_SECONDS) / 3600000;
        long minute = (range % 3600000) / 60000;
        if (range < 0) {
            day = 0;
            hour = 0;
            minute = 0;
        }
        return format.replaceAll("dd", String.valueOf(day)).replaceAll("hh", String.valueOf(hour)).replaceAll("mm", String.valueOf(minute));
    }

    public static String formatMonth(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(monthFormat).format(date);
    }

    public static Date getBeforeDate() {
        return new Date(new Date().getTime() - ONE_DAY_MILL_SECONDS);
    }

    public static Date getDayBegin(Date date) {
        DateFormat df = new SimpleDateFormat(shortFormat);
        df.setLenient(false);
        try {
            return df.parse(df.format(date));
        } catch (ParseException e) {
            return date;
        }
    }

    public static boolean dateLessThanNowAddMin(Date date, long min) {
        return addMinutes(date, min).before(new Date());
    }

    public static boolean isBeforeNow(Date date) {
        if (date != null && date.compareTo(new Date()) < 0) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isBeforeNow(new Date()));
    }

    public static Date parseNoSecondFormat(String sDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(noSecondFormat);
        if (sDate == null || sDate.length() < noSecondFormat.length()) {
            throw new ParseException("length too little", 0);
        } else if (StringUtils.isNumeric(sDate)) {
            return dateFormat.parse(sDate);
        } else {
            throw new ParseException("not all digit", 0);
        }
    }
}
