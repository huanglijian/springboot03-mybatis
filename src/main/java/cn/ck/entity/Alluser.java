package cn.ck.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2018-09-19
 */
public class Alluser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    private String allId;

    private String allType;
    private String allPwd;
    private String allSalt;
    private String allEmail;
    private String allState;


    public String getAllId() {
        return allId;
    }

    public void setAllId(String allId) {
        this.allId = allId;
    }

    public String getAllType() {
        return allType;
    }

    public void setAllType(String allType) {
        this.allType = allType;
    }

    public String getAllPwd() {
        return allPwd;
    }

    public void setAllPwd(String allPwd) {
        this.allPwd = allPwd;
    }

    public String getAllSalt() {
        return allSalt;
    }

    public void setAllSalt(String allSalt) {
        this.allSalt = allSalt;
    }

    public String getAllEmail() {
        return allEmail;
    }

    public void setAllEmail(String allEmail) {
        this.allEmail = allEmail;
    }

    public String getAllState() {
        return allState;
    }

    public void setAllState(String allState) {
        this.allState = allState;
    }

    @Override
    public String toString() {
        return "Alluser{" +
        ", allId=" + allId +
        ", allType=" + allType +
        ", allPwd=" + allPwd +
        ", allSalt=" + allSalt +
        ", allEmail=" + allEmail +
        ", allState=" + allState +
        "}";
    }
}
