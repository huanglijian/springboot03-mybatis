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
public class Collectori implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "colo_id", type = IdType.AUTO)
    private Integer coloId;
    private Date coloTime;
    private String coloUsers;
    private Integer coloOgi;


    public Integer getColoId() {
        return coloId;
    }

    public void setColoId(Integer coloId) {
        this.coloId = coloId;
    }

    public Date getColoTime() {
        return coloTime;
    }

    public void setColoTime(Date coloTime) {
        this.coloTime = coloTime;
    }

    public String getColoUsers() {
        return coloUsers;
    }

    public void setColoUsers(String coloUsers) {
        this.coloUsers = coloUsers;
    }

    public Integer getColoOgi() {
        return coloOgi;
    }

    public void setColoOgi(Integer coloOgi) {
        this.coloOgi = coloOgi;
    }

    @Override
    public String toString() {
        return "Collectori{" +
        ", coloId=" + coloId +
        ", coloTime=" + coloTime +
        ", coloUsers=" + coloUsers +
        ", coloOgi=" + coloOgi +
        "}";
    }
}
