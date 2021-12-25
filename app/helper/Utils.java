package helper;

import models.ConfigServer;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static String getCurrentTime() {
        return new Timestamp(System.currentTimeMillis()).toString();
    }

    public static long getCurrentMiniseconds() {
        return System.currentTimeMillis();
    }

    public static Timestamp getTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static long getTimeMillisStartOfDay() {
        Date date = new Date();
        Long time = date.getTime() / 1000;
        return (time / 86400) * 86400 - 25200;
    }

    public static void debug(Object obj) {
        System.out.print("\n" + obj + "\n");
    }

    public static ConfigServer getConfigServer() {
        return ConfigServer.find.byId(1);
    }

    // 3 dấu ### bên phải là nhóm 3 số thì phân tách
    private static NumberFormat formatter = new DecimalFormat("###,###");

    /**
     * Định dạng tiền có dấu chấm
     */
    public static String formatMoney(long money) {
        return formatter.format(money);
    }

    public static boolean checkTimestampNhoHonTimeCurrentDay(Timestamp timeCheck) {
        Timestamp timeCurrent = new Timestamp(System.currentTimeMillis());
        long timeCk = getYearMonthDayGhepChuoiToLong(timeCheck);
        long timeC = getYearMonthDayGhepChuoiToLong(timeCurrent);
        return timeCk < timeC;
    }

    public static long getYearMonthDayGhepChuoiToLong(Timestamp timeConvert) {
        return Long.parseLong(timeConvert.toString().split(" ")[0].replaceAll("-", ""));
    }

    public static Timestamp getTimestamp(long miliseconds) {
        return new Timestamp(miliseconds);
    }

    private static long getTotalMiliSecondsOnDay() {
        return 24L * 60L * 60L * 1000L;
    }

    public static long getTotalSecondsPerDay() {
        return 24L * 60L * 60L;
    }

    public static long getTimestampSauNgay(int day) {
        return getTimestamp(getCurrentMiniseconds() - (day * getTotalMiliSecondsOnDay())).getTime();
    }

    public static long convertTimeCurrentMiliSecondsToTimeCurrentSeconds(long currentMiliSeconds) {
        return currentMiliSeconds / 1000L;
    }

    public static long convertTimeCurrentSecondsToTimeCurrentMiliSeconds(long currentSeconds) {
        return currentSeconds * 1000L;
    }

    public static int getTypeAccountBenNodeJs(int level) {
        int type = 0;
        if (level == S.LEVEL_KTV) {
            type = S.TYPE_ACCOUNT_KTV;
        } else if (level == S.LEVEL_CTV) {
            type = S.LEVEL_CTV;
        }
        return type;
    }

    public static long getTimeMiniSecondsCachTimePlay(int _timeHenGio) {
        int timeHenGio = Math.abs(_timeHenGio);
        long timeWait = 0L;
        Calendar c = Calendar.getInstance();
        int hour24 = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        int seconds = c.get(Calendar.SECOND);
        int hourWait = 0;
        if (timeHenGio > 11) {
            if (timeHenGio < hour24) {
                hourWait = 24 - hour24 - timeHenGio;
            } else {
                hourWait = timeHenGio - hour24;
            }
        } else {
            hourWait = 24 - hour24 + timeHenGio;
        }
        // System.err.println(hourWait);
        timeWait = hourWait * 60L * 60L * 1000L - (60 - minutes) * 60L * 1000L - (60 - seconds) * 1000L;
        // System.out.println(((float) timeWait /
        // 3600000F));System.out.println("-----");
        return timeWait;
    }

    public static long getTimeStartOfDay(){
        Date today = new Date();
        long ts_now = today.getTime();
        Calendar cal = Calendar.getInstance();
        long milisSinceMidnight = (cal.get(Calendar.HOUR_OF_DAY)*60*60*1000) + (cal.get(Calendar.MINUTE)*60*1000) + (cal.get(Calendar.SECOND)*1000) + (cal.get(Calendar.MILLISECOND));
        return ts_now - milisSinceMidnight;
    }

    public static String timeStampToString(long timestamp){
        Date date = new Date(timestamp);
        SimpleDateFormat dt = new SimpleDateFormat("HH:mm dd/MM/YYYY");
        return dt.format(date);
    }

    public static String getInfoPositionMap(double latitude, double longitude, String position) {
        return latitude + S.TACH_SAO + longitude + S.TACH_SAO + position;
    }

//    public static String genOtp(){
//        Date date = new Date();
//        Long startTime = date.getTime();
//
//    }
}
