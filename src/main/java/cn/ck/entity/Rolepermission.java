package cn.ck.entity;

import com.baomidou.mybatisplus.enums.IdType;
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
public class Rolepermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "rp_id", type = IdType.AUTO)
    private Integer rpId;
    private Integer rpRoid;
    private Integer rpPerid;


    public Integer getRpId() {
        return rpId;
    }

    public void setRpId(Integer rpId) {
        this.rpId = rpId;
    }

    public Integer getRpRoid() {
        return rpRoid;
    }

    public void setRpRoid(Integer rpRoid) {
        this.rpRoid = rpRoid;
    }

    public Integer getRpPerid() {
        return rpPerid;
    }

    public void setRpPerid(Integer rpPerid) {
        this.rpPerid = rpPerid;
    }

    @Override
    public String toString() {
        return "Rolepermission{" +
        ", rpId=" + rpId +
        ", rpRoid=" + rpRoid +
        ", rpPerid=" + rpPerid +
        "}";
    }
}
