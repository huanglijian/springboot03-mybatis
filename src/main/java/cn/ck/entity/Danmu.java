package cn.ck.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2018-09-19
 */
public class Danmu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dm_id", type = IdType.AUTO)
    private Integer dmId;
    private String dmText;
    private String dmColor;
    private Integer dmPosition;
    private Integer dmTime;
    private Date dmSendtime;
    private Integer dmResource;


    public Integer getDmId() {
        return dmId;
    }

    public void setDmId(Integer dmId) {
        this.dmId = dmId;
    }

    public String getDmText() {
        return dmText;
    }

    public void setDmText(String dmText) {
        this.dmText = dmText;
    }

    public String getDmColor() {
        return dmColor;
    }

    public void setDmColor(String dmColor) {
        this.dmColor = dmColor;
    }

    public Integer getDmPosition() {
        return dmPosition;
    }

    public void setDmPosition(Integer dmPosition) {
        this.dmPosition = dmPosition;
    }

    public Integer getDmTime() {
        return dmTime;
    }

    public void setDmTime(Integer dmTime) {
        this.dmTime = dmTime;
    }

    public Date getDmSendtime() {
        return dmSendtime;
    }

    public void setDmSendtime(Date dmSendtime) {
        this.dmSendtime = dmSendtime;
    }

    public Integer getDmResource() {
        return dmResource;
    }

    public void setDmResource(Integer dmResource) {
        this.dmResource = dmResource;
    }

    @Override
    public String toString() {
        return "Danmu{" +
        ", dmId=" + dmId +
        ", dmText=" + dmText +
        ", dmColor=" + dmColor +
        ", dmPosition=" + dmPosition +
        ", dmTime=" + dmTime +
        ", dmSendtime=" + dmSendtime +
        ", dmResource=" + dmResource +
        "}";
    }
}
