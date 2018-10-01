package cn.ck.entity.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

public class DanmuJson {

    private static final long serialVersionUID = 1L;

//    text——弹幕文本内容。
//    color——弹幕颜色。
//    position——弹幕位置 0为滚动 1 为顶部 2为底部
//    size——弹幕文字大小。 0为小字 1为大字
//    time——弹幕所出现的时间。 单位为分秒（十分之一秒）
//    isnew——当出现该属性时（属性值可为任意），会认为这是用户新发的弹幕，从而弹幕在显示的时候会有边框。
    @TableId(value = "dm_id", type = IdType.AUTO)
    private Integer Id;
    private String text;
    private String color;
    private Integer position;
    private Integer time;
    private Date sendtime;
    private Integer dmResource;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public Integer getDmResource() {
        return dmResource;
    }

    public void setDmResource(Integer dmResource) {
        this.dmResource = dmResource;
    }

    @Override
    public String toString() {
        return "DanmuJson{" +
                "Id=" + Id +
                ", text='" + text + '\'' +
                ", color='" + color + '\'' +
                ", position=" + position +
                ", time=" + time +
                ", sendtime=" + sendtime +
                ", dmResource=" + dmResource +
                '}';
    }
}
