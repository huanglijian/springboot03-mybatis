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
public class Collectres implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "colr_id", type = IdType.AUTO)
    private Integer colrId;
    private Date colrTime;
    private String colrUsers;
    private Integer colrRes;


    public Integer getColrId() {
        return colrId;
    }

    public void setColrId(Integer colrId) {
        this.colrId = colrId;
    }

    public Date getColrTime() {
        return colrTime;
    }

    public void setColrTime(Date colrTime) {
        this.colrTime = colrTime;
    }

    public String getColrUsers() {
        return colrUsers;
    }

    public void setColrUsers(String colrUsers) {
        this.colrUsers = colrUsers;
    }

    public Integer getColrRes() {
        return colrRes;
    }

    public void setColrRes(Integer colrRes) {
        this.colrRes = colrRes;
    }

    @Override
    public String toString() {
        return "Collectres{" +
        ", colrId=" + colrId +
        ", colrTime=" + colrTime +
        ", colrUsers=" + colrUsers +
        ", colrRes=" + colrRes +
        "}";
    }
}
