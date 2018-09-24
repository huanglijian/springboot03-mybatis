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
 * @since 2018-09-21
 */
public class Likeres implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "likr_id", type = IdType.AUTO)
    private Integer likrId;
    private String likrUser;
    private Integer likrRes;


    public Integer getLikrId() {
        return likrId;
    }

    public void setLikrId(Integer likrId) {
        this.likrId = likrId;
    }

    public String getLikrUser() {
        return likrUser;
    }

    public void setLikrUser(String likrUser) {
        this.likrUser = likrUser;
    }

    public Integer getLikrRes() {
        return likrRes;
    }

    public void setLikrRes(Integer likrRes) {
        this.likrRes = likrRes;
    }

    @Override
    public String toString() {
        return "Likeres{" +
        ", likrId=" + likrId +
        ", likrUser=" + likrUser +
        ", likrRes=" + likrRes +
        "}";
    }
}
