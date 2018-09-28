package cn.ck.utils;

import cn.ck.entity.Notice;

import java.util.Date;

public class NoticeInsert {

    /**
     * 新增通知内容
     * @param message 通知内容
     * @param id 通知对象
     * @return notice实体类
     */
    public static Notice insertNotice(String message,String id){
        Notice notice=new Notice();
        notice.setNotiTime(new Date());
        notice.setNotiMsg(message);
        notice.setNotiForeid(id);
        notice.setNotiState("否");
        return notice;
    }
}
