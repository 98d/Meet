package com.example.framework.utils;

public class TimeUtils {

    /**
     * 时间转化类
     * 转换毫秒格式 HH:mm:ss
     * @param ms
     */
    public static String formatDuring(long ms) {
        long hours = (ms % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (ms % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (ms % (1000 * 60)) / 1000;

        String h = hours + "";
        if (hours < 10){
            h = "0" + h;
        }

        String m = hours + "";
        if (minutes < 10){
            m = "0" + m;
        }

        String s = hours + "";
        if (seconds < 10){
            s = "0" + s;
        }
        return h + ":" + m + ":" + s;
    }
}
