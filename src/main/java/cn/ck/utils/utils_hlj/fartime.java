package cn.ck.utils.utils_hlj;

import java.util.Date;

/**
 * 计算过去的某个时间，距离现在有多久
 * 超过24小时显示几天前
 * 超过60分钟显示几小时前
 * 否则显示几分钟前
 */

public class fartime {

    public String far(Date date){
//        Date date1 = j2.getJobCreattime();
        Date d2 = new Date();
//            两个日期相减
        long cha = d2.getTime() - date.getTime();
//            拆分天数，小时，分钟
        long day = cha / (24 * 60 * 60 * 1000);
        long hour = (cha / (60 * 60 * 1000) - day * 24);
        long min = ((cha / (60 * 1000)) - day * 24 * 60 - hour * 60);
        String time = "出错";
        int a = 0;
        //显示天
        if (day > 0) {
            a = (int) day;
            time = a + "天前";
        } else if (day == 0) {
//                显示小时
            if (hour > 0) {
                a = (int) hour;
                time = a + "小时前";
            }
            //显示分钟
            else if (hour == 0) {
                if (min > 0) {
                    a = (int) min;
                    time = a + "分钟前";
                }
                if(min==0){
                    time = "1分钟前";
                }
            }
        }
        return time;
    }
}
