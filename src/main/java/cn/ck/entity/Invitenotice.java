package cn.ck.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2018-10-19
 */
public class Invitenotice implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer innoId;
    private Integer innoProid;
    private Date innoTime;
    private String innoForeid;
    private String innoState;


    public Integer getInnoId() {
        return innoId;
    }

    public void setInnoId(Integer innoId) {
        this.innoId = innoId;
    }

    public Integer getInnoProid() {
        return innoProid;
    }

    public void setInnoProid(Integer innoProid) {
        this.innoProid = innoProid;
    }

    public Date getInnoTime() {
        return innoTime;
    }

    public void setInnoTime(Date innoTime) {
        this.innoTime = innoTime;
    }

    public String getInnoForeid() {
        return innoForeid;
    }

    public void setInnoForeid(String innoForeid) {
        this.innoForeid = innoForeid;
    }

    public String getInnoState() {
        return innoState;
    }

    public void setInnoState(String innoState) {
        this.innoState = innoState;
    }

    @Override
    public String toString() {
        return "Invitenotice{" +
        ", innoId=" + innoId +
        ", innoProid=" + innoProid +
        ", innoTime=" + innoTime +
        ", innoForeid=" + innoForeid +
        ", innoState=" + innoState +
        "}";
    }
}
