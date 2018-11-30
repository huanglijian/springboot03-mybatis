package com.neu.bean;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class Publish {
    private Integer pubId;

    private String pubTheme;

    private String pubScene;

    private String pubIntro;

    private Date pubDate;

    private Integer pubWatchnum;

    private Integer pubState;

    private String pubUser;

    public Integer getPubId() {
        return pubId;
    }

    public void setPubId(Integer pubId) {
        this.pubId = pubId;
    }

    public String getPubTheme() {
        return pubTheme;
    }

    public void setPubTheme(String pubTheme) {
        this.pubTheme = pubTheme == null ? null : pubTheme.trim();
    }

    public String getPubScene() {
        return pubScene;
    }

    public void setPubScene(String pubScene) {
        this.pubScene = pubScene == null ? null : pubScene.trim();
    }

    public String getPubIntro() {
        return pubIntro;
    }

    public void setPubIntro(String pubIntro) {
        this.pubIntro = pubIntro == null ? null : pubIntro.trim();
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public Integer getPubWatchnum() {
        return pubWatchnum;
    }

    public void setPubWatchnum(Integer pubWatchnum) {
        this.pubWatchnum = pubWatchnum;
    }

    public Integer getPubState() {
        return pubState;
    }

    public void setPubState(Integer pubState) {
        this.pubState = pubState;
    }

    public String getPubUser() {
        return pubUser;
    }

    public void setPubUser(String pubUser) {
        this.pubUser = pubUser == null ? null : pubUser.trim();
    }

    @Override
    public String toString() {
        return "Publish{" +
                "pubId=" + pubId +
                ", pubTheme='" + pubTheme + '\'' +
                ", pubScene='" + pubScene + '\'' +
                ", pubIntro='" + pubIntro + '\'' +
                ", pubDate=" + pubDate +
                ", pubWatchnum=" + pubWatchnum +
                ", pubState=" + pubState +
                ", pubUser='" + pubUser + '\'' +
                '}';
    }
}