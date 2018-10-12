package cn.ck.entity.bean;

import java.util.Date;

public class Adminoriginal {
    private Integer origId;
    private String origName;
    private String origIntro;
    private String origType;
    private String origTag;
    private Date origUploadtime;
    private String origPath;
    private Double origGrade;
    private String origUsers;
    private String userName;
    private int count;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
