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
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String userName;
    private String userPhone;
    private String userSex;
    private Integer userAge;
    private String userIntro;
    private String userAbipay;
    private Date userLogintime;
    private String userImg;
    private String userStudio;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserIntro() {
        return userIntro;
    }

    public void setUserIntro(String userIntro) {
        this.userIntro = userIntro;
    }

    public String getUserAbipay() {
        return userAbipay;
    }

    public void setUserAbipay(String userAbipay) {
        this.userAbipay = userAbipay;
    }

    public Date getUserLogintime() {
        return userLogintime;
    }

    public void setUserLogintime(Date userLogintime) {
        this.userLogintime = userLogintime;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserStudio() {
        return userStudio;
    }

    public void setUserStudio(String userStudio) {
        this.userStudio = userStudio;
    }

    @Override
    public String toString() {
        return "Users{" +
        ", userId=" + userId +
        ", userName=" + userName +
        ", userPhone=" + userPhone +
        ", userSex=" + userSex +
        ", userAge=" + userAge +
        ", userIntro=" + userIntro +
        ", userAbipay=" + userAbipay +
        ", userLogintime=" + userLogintime +
        ", userImg=" + userImg +
        ", userStudio=" + userStudio +
        "}";
    }
}
