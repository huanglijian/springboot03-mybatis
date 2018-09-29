package cn.ck.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2018-09-28
 */
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "proj_id", type = IdType.AUTO)
    private Integer projId;
    private String projName;
    private String projSecret;
    private String projType;
    private String projMoney;
    private Date projCreattime;
    private Date projStarttime;
    private Date projEndtime;
    private Integer projCycletime;
    private String projIntro;
    private String projFile;
    private String projFilename;
    private String projState;
    private String projTag;
    private String projImg;
    private String projStudio;
    private String projProm;


    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getProjSecret() {
        return projSecret;
    }

    public void setProjSecret(String projSecret) {
        this.projSecret = projSecret;
    }

    public String getProjType() {
        return projType;
    }

    public void setProjType(String projType) {
        this.projType = projType;
    }

    public String getProjMoney() {
        return projMoney;
    }

    public void setProjMoney(String projMoney) {
        this.projMoney = projMoney;
    }

    public Date getProjCreattime() {
        return projCreattime;
    }

    public void setProjCreattime(Date projCreattime) {
        this.projCreattime = projCreattime;
    }

    public Date getProjStarttime() {
        return projStarttime;
    }

    public void setProjStarttime(Date projStarttime) {
        this.projStarttime = projStarttime;
    }

    public Date getProjEndtime() {
        return projEndtime;
    }

    public void setProjEndtime(Date projEndtime) {
        this.projEndtime = projEndtime;
    }

    public Integer getProjCycletime() {
        return projCycletime;
    }

    public void setProjCycletime(Integer projCycletime) {
        this.projCycletime = projCycletime;
    }

    public String getProjIntro() {
        return projIntro;
    }

    public void setProjIntro(String projIntro) {
        this.projIntro = projIntro;
    }

    public String getProjFile() {
        return projFile;
    }

    public void setProjFile(String projFile) {
        this.projFile = projFile;
    }

    public String getProjFilename() {
        return projFilename;
    }

    public void setProjFilename(String projFilename) {
        this.projFilename = projFilename;
    }

    public String getProjState() {
        return projState;
    }

    public void setProjState(String projState) {
        this.projState = projState;
    }

    public String getProjTag() {
        return projTag;
    }

    public void setProjTag(String projTag) {
        this.projTag = projTag;
    }

    public String getProjImg() {
        return projImg;
    }

    public void setProjImg(String projImg) {
        this.projImg = projImg;
    }

    public String getProjStudio() {
        return projStudio;
    }

    public void setProjStudio(String projStudio) {
        this.projStudio = projStudio;
    }

    public String getProjProm() {
        return projProm;
    }

    public void setProjProm(String projProm) {
        this.projProm = projProm;
    }

    @Override
    public String toString() {
        return "Project{" +
        ", projId=" + projId +
        ", projName=" + projName +
        ", projSecret=" + projSecret +
        ", projType=" + projType +
        ", projMoney=" + projMoney +
        ", projCreattime=" + projCreattime +
        ", projStarttime=" + projStarttime +
        ", projEndtime=" + projEndtime +
        ", projCycletime=" + projCycletime +
        ", projIntro=" + projIntro +
        ", projFile=" + projFile +
        ", projFilename=" + projFilename +
        ", projState=" + projState +
        ", projTag=" + projTag +
        ", projImg=" + projImg +
        ", projStudio=" + projStudio +
        ", projProm=" + projProm +
        "}";
    }
}
