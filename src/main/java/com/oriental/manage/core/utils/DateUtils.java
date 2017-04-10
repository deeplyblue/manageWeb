package com.oriental.manage.core.utils;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lupf on 2016/4/27.
 */
public class DateUtils {

    /**
     * 默认日期格式
     */
    public static final String DATESHOWFORMAT = "yyyy-MM-dd";
    public static final String DATESPRITFORMAT = "yyyy/MM/dd";
    public static final String DATETIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATESHORTFORMAT = "yyyyMMdd";
    public static final String DATEMONTHFORMAT = "yyyyMM";
    //    public static final String DATETIMECFORMAT = "yyyyMMddHHssmm";
    public static final String DATETIMESFORMAT = "yyyyMMddHHmmss";
    /** 日期时间格式：yyyyMMddHHmmss*/
    public static final String fullPattern = "yyyyMMddHHmmss";
    /**
     * 无秒
     */
    public static final String full_pattern = "yyyyMMddHHmm";

    public static Date parse(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATESHORTFORMAT);
        return sdf.parse(date);
    }

    public static Date parse(String date, String datetimesformat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(datetimesformat);
        return sdf.parse(date);
    }

    public static Date parse(Date date, String pattern) throws ParseException {
        if(null == date){
            return null;
        }
        if(org.apache.commons.lang.StringUtils.isEmpty(pattern)){
            pattern= DATESHORTFORMAT;
        }
        String dateStr = DateFormatUtils.format(date, pattern);
        return parse(dateStr, pattern);
    }

    public static String format(Date date, String pattern) {
        return date == null ? null : (new DateTime(date)).toString(pattern);
    }

    public static DateTime parseTime(String date, String pattern) {
        if (StringUtils.isBlank(date)) return null;
        if (fullPattern.equals(pattern)) { // 这段逻辑整合自 gateway-bank 中的 DateUtil
            date = StringUtils.rightPad(date, 14, '0');
        }
        return DateTimeFormat.forPattern(pattern).parseDateTime(date);
    }

    /**
     * 得到今天指定格式的日期
     *
     * @param format 指定格式
     * @return 当天日期
     */
    public static String getToDay(String format) {
        if (StringUtils.isEmpty(format)) {
            format = DATESHORTFORMAT;
        }
        DateTime dt = new DateTime();
        return dt.toString(format);
    }

    /**
     * 日期计算
     * @param date 需要计算的日期
     * @param day 需要往前(负数)或往后(正数)的天数
     * @param inPattern 输入时间的格式
     * @param outPattern 输出时间的格式
     * @return 按照输出时间格式进行格式化后的时间
     */
    public static String computeDate(String date,int day,String inPattern,String outPattern){
        try{
            DateTime dateTime = parseTime(date, inPattern);
            dateTime=dateTime.minusDays(0-day);
            return format(dateTime.toDate(),outPattern);
            //输入时间转换错误时返回空值 避免因时间转换错误而导致原流程无法继续执行
        }catch (Exception e){

            return null;
        }
    }

    public static String now() {
        return new DateTime().toString(DATETIMESFORMAT);
    }
}
