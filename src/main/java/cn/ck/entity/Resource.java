package cn.ck.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2018-09-19
 */
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "res_id", type = IdType.AUTO)
    private Integer resId;
    private Date resUploadtime;
    private String resName;
    private String resIntro;
    private String resPath;
    private String resTag;
    private String resImg;
    private Integer resLikenum;
    private String resLong;


    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Date getResUploadtime() {
        return resUploadtime;
    }

    public void setResUploadtime(Date resUploadtime) {
        this.resUploadtime = resUploadtime;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResIntro() {
        return resIntro;
    }

    public void setResIntro(String resIntro) {
        this.resIntro = resIntro;
    }

    public String getResPath() {
        return resPath;
    }

    public void setResPath(String resPath) {
        this.resPath = resPath;
    }

    public String getResTag() {
        return resTag;
    }

    public void setResTag(String resTag) {
        this.resTag = resTag;
    }

    public String getResImg() {
        return resImg;
    }

    public void setResImg(String resImg) {
        this.resImg = resImg;
    }

    public Integer getResLikenum() {
        return resLikenum;
    }

    public void setResLikenum(Integer resLikenum) {
        this.resLikenum = resLikenum;
    }

    public String getResLong() {
        return resLong;
    }

    public void setResLong(String resLong) {
        this.resLong = resLong;
    }

    @Override
    public String toString() {
        return "Resource{" +
        ", resId=" + resId +
        ", resUploadtime=" + resUploadtime +
        ", resName=" + resName +
        ", resIntro=" + resIntro +
        ", resPath=" + resPath +
        ", resTag=" + resTag +
        ", resImg=" + resImg +
        ", resLikenum=" + resLikenum +
        ", resLong=" + resLong +
        "}";
    }
}
