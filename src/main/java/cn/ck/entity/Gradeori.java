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
 * @since 2018-10-18
 */
public class Gradeori implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "grao_id", type = IdType.AUTO)
    private Integer graoId;
    private String graoUser;
    private Integer graoOri;
    private Integer garoSco;


    public Integer getGraoId() {
        return graoId;
    }

    public void setGraoId(Integer graoId) {
        this.graoId = graoId;
    }

    public String getGraoUser() {
        return graoUser;
    }

    public void setGraoUser(String graoUser) {
        this.graoUser = graoUser;
    }

    public Integer getGraoOri() {
        return graoOri;
    }

    public void setGraoOri(Integer graoOri) {
        this.graoOri = graoOri;
    }

    public Integer getGaroSco() {
        return garoSco;
    }

    public void setGaroSco(Integer garoSco) {
        this.garoSco = garoSco;
    }

    @Override
    public String toString() {
        return "Gradeori{" +
        ", graoId=" + graoId +
        ", graoUser=" + graoUser +
        ", graoOri=" + graoOri +
        ", garoSco=" + garoSco +
        "}";
    }
}
