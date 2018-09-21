package cn.ck.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "noti_id", type = IdType.AUTO)
    private Integer notiId;
    private String notiMsg;
    private String notiState;
    private Date notiTime;
    private String notiForeid;


    public Integer getNotiId() {
        return notiId;
    }

    public void setNotiId(Integer notiId) {
        this.notiId = notiId;
    }

    public String getNotiMsg() {
        return notiMsg;
    }

    public void setNotiMsg(String notiMsg) {
        this.notiMsg = notiMsg;
    }

    public String getNotiState() {
        return notiState;
    }

    public void setNotiState(String notiState) {
        this.notiState = notiState;
    }

    public Date getNotiTime() {
        return notiTime;
    }

    public void setNotiTime(Date notiTime) {
        this.notiTime = notiTime;
    }

    public String getNotiForeid() {
        return notiForeid;
    }

    public void setNotiForeid(String notiForeid) {
        this.notiForeid = notiForeid;
    }

    @Override
    public String toString() {
        return "Notice{" +
        ", notiId=" + notiId +
        ", notiMsg=" + notiMsg +
        ", notiState=" + notiState +
        ", notiTime=" + notiTime +
        ", notiForeid=" + notiForeid +
        "}";
    }
}
