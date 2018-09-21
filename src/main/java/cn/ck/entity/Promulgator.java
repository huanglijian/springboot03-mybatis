package cn.ck.entity;

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
public class Promulgator implements Serializable {

    private static final long serialVersionUID = 1L;

    private String promId;
    private String promName;
    private String promPhone;
    private String promAbipay;
    private String promPaypwd;
    private Date promLogintime;
    private String promIntro;
    private String promImg;


    public String getPromId() {
        return promId;
    }

    public void setPromId(String promId) {
        this.promId = promId;
    }

    public String getPromName() {
        return promName;
    }

    public void setPromName(String promName) {
        this.promName = promName;
    }

    public String getPromPhone() {
        return promPhone;
    }

    public void setPromPhone(String promPhone) {
        this.promPhone = promPhone;
    }

    public String getPromAbipay() {
        return promAbipay;
    }

    public void setPromAbipay(String promAbipay) {
        this.promAbipay = promAbipay;
    }

    public String getPromPaypwd() {
        return promPaypwd;
    }

    public void setPromPaypwd(String promPaypwd) {
        this.promPaypwd = promPaypwd;
    }

    public Date getPromLogintime() {
        return promLogintime;
    }

    public void setPromLogintime(Date promLogintime) {
        this.promLogintime = promLogintime;
    }

    public String getPromIntro() {
        return promIntro;
    }

    public void setPromIntro(String promIntro) {
        this.promIntro = promIntro;
    }

    public String getPromImg() {
        return promImg;
    }

    public void setPromImg(String promImg) {
        this.promImg = promImg;
    }

    @Override
    public String toString() {
        return "Promulgator{" +
        ", promId=" + promId +
        ", promName=" + promName +
        ", promPhone=" + promPhone +
        ", promAbipay=" + promAbipay +
        ", promPaypwd=" + promPaypwd +
        ", promLogintime=" + promLogintime +
        ", promIntro=" + promIntro +
        ", promImg=" + promImg +
        "}";
    }
}
