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
 * @since 2018-09-28
 */
public class Invite implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "inv_id", type = IdType.AUTO)
    private Integer invId;
    private Date invCreattime;
    private Integer invProject;
    private String invStudio;


    public Integer getInvId() {
        return invId;
    }

    public void setInvId(Integer invId) {
        this.invId = invId;
    }

    public Date getInvCreattime() {
        return invCreattime;
    }

    public void setInvCreattime(Date invCreattime) {
        this.invCreattime = invCreattime;
    }

    public Integer getInvProject() {
        return invProject;
    }

    public void setInvProject(Integer invProject) {
        this.invProject = invProject;
    }

    public String getInvStudio() {
        return invStudio;
    }

    public void setInvStudio(String invStudio) {
        this.invStudio = invStudio;
    }

    @Override
    public String toString() {
        return "Invite{" +
        ", invId=" + invId +
        ", invCreattime=" + invCreattime +
        ", invProject=" + invProject +
        ", invStudio=" + invStudio +
        "}";
    }
}
