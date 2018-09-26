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
 * @since 2018-09-26
 */
public class Collectpj implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "colp_id", type = IdType.AUTO)
    private Integer colpId;
    private Date colpTime;
    private String colpUser;
    private Integer colpPjid;


    public Integer getColpId() {
        return colpId;
    }

    public void setColpId(Integer colpId) {
        this.colpId = colpId;
    }

    public Date getColpTime() {
        return colpTime;
    }

    public void setColpTime(Date colpTime) {
        this.colpTime = colpTime;
    }

    public String getColpUser() {
        return colpUser;
    }

    public void setColpUser(String colpUser) {
        this.colpUser = colpUser;
    }

    public Integer getColpPjid() {
        return colpPjid;
    }

    public void setColpPjid(Integer colpPjid) {
        this.colpPjid = colpPjid;
    }

    @Override
    public String toString() {
        return "Collectpj{" +
        ", colpId=" + colpId +
        ", colpTime=" + colpTime +
        ", colpUser=" + colpUser +
        ", colpPjid=" + colpPjid +
        "}";
    }
}
