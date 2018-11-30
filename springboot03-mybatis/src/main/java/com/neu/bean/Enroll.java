package com.neu.bean;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class Enroll {
    private Integer enrId;

    private String enrUser;

    private Integer enrPublish;

    private String enrNote;

    private Date enrDate;

    public Integer getEnrId() {
        return enrId;
    }

    public void setEnrId(Integer enrId) {
        this.enrId = enrId;
    }

    public String getEnrUser() {
        return enrUser;
    }

    public void setEnrUser(String enrUser) {
        this.enrUser = enrUser == null ? null : enrUser.trim();
    }

    public Integer getEnrPublish() {
        return enrPublish;
    }

    public void setEnrPublish(Integer enrPublish) {
        this.enrPublish = enrPublish;
    }

    public String getEnrNote() {
        return enrNote;
    }

    public void setEnrNote(String enrNote) {
        this.enrNote = enrNote == null ? null : enrNote.trim();
    }

    public Date getEnrDate() {
        return enrDate;
    }

    public void setEnrDate(Date enrDate) {
        this.enrDate = enrDate;
    }

    @Override
    public String toString() {
        return "Enroll{" +
                "enrId=" + enrId +
                ", enrUser='" + enrUser + '\'' +
                ", enrPublish=" + enrPublish +
                ", enrNote='" + enrNote + '\'' +
                ", enrDate=" + enrDate +
                '}';
    }
}