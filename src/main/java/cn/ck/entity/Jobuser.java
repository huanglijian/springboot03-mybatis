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
public class Jobuser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ju_id", type = IdType.AUTO)
    private Integer juId;
    private Date juTime;
    private String juFile;
    private String juState;
    private String juUsers;
    private Integer juJobs;


    public Integer getJuId() {
        return juId;
    }

    public void setJuId(Integer juId) {
        this.juId = juId;
    }

    public Date getJuTime() {
        return juTime;
    }

    public void setJuTime(Date juTime) {
        this.juTime = juTime;
    }

    public String getJuFile() {
        return juFile;
    }

    public void setJuFile(String juFile) {
        this.juFile = juFile;
    }

    public String getJuState() {
        return juState;
    }

    public void setJuState(String juState) {
        this.juState = juState;
    }

    public String getJuUsers() {
        return juUsers;
    }

    public void setJuUsers(String juUsers) {
        this.juUsers = juUsers;
    }

    public Integer getJuJobs() {
        return juJobs;
    }

    public void setJuJobs(Integer juJobs) {
        this.juJobs = juJobs;
    }

    @Override
    public String toString() {
        return "Jobuser{" +
        ", juId=" + juId +
        ", juTime=" + juTime +
        ", juFile=" + juFile +
        ", juState=" + juState +
        ", juUsers=" + juUsers +
        ", juJobs=" + juJobs +
        "}";
    }
}
