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
public class Userrole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ur_id", type = IdType.AUTO)
    private Integer urId;
    private String urAllid;
    private Integer urRoid;


    public Integer getUrId() {
        return urId;
    }

    public void setUrId(Integer urId) {
        this.urId = urId;
    }

    public String getUrAllid() {
        return urAllid;
    }

    public void setUrAllid(String urAllid) {
        this.urAllid = urAllid;
    }

    public Integer getUrRoid() {
        return urRoid;
    }

    public void setUrRoid(Integer urRoid) {
        this.urRoid = urRoid;
    }

    @Override
    public String toString() {
        return "Userrole{" +
        ", urId=" + urId +
        ", urAllid=" + urAllid +
        ", urRoid=" + urRoid +
        "}";
    }
}
