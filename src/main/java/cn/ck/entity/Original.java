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
public class Original implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "orig_id", type = IdType.AUTO)
    private Integer origId;
    private String origName;
    private String origIntro;
    private String origType;
    private String origTag;
    private Date origUploadtime;
    private String origPath;
    private Double origGrade;
    private String origUsers;


    public Integer getOrigId() {
        return origId;
    }

    public void setOrigId(Integer origId) {
        this.origId = origId;
    }

    public String getOrigName() {
        return origName;
    }

    public void setOrigName(String origName) {
        this.origName = origName;
    }

    public String getOrigIntro() {
        return origIntro;
    }

    public void setOrigIntro(String origIntro) {
        this.origIntro = origIntro;
    }

    public String getOrigType() {
        return origType;
    }

    public void setOrigType(String origType) {
        this.origType = origType;
    }

    public String getOrigTag() {
        return origTag;
    }

    public void setOrigTag(String origTag) {
        this.origTag = origTag;
    }

    public Date getOrigUploadtime() {
        return origUploadtime;
    }

    public void setOrigUploadtime(Date origUploadtime) {
        this.origUploadtime = origUploadtime;
    }

    public String getOrigPath() {
        return origPath;
    }

    public void setOrigPath(String origPath) {
        this.origPath = origPath;
    }

    public Double getOrigGrade() {
        return origGrade;
    }

    public void setOrigGrade(Double origGrade) {
        this.origGrade = origGrade;
    }

    public String getOrigUsers() {
        return origUsers;
    }

    public void setOrigUsers(String origUsers) {
        this.origUsers = origUsers;
    }

    @Override
    public String toString() {
        return "Original{" +
        ", origId=" + origId +
        ", origName=" + origName +
        ", origIntro=" + origIntro +
        ", origType=" + origType +
        ", origTag=" + origTag +
        ", origUploadtime=" + origUploadtime +
        ", origPath=" + origPath +
        ", origGrade=" + origGrade +
        ", origUsers=" + origUsers +
        "}";
    }
}
