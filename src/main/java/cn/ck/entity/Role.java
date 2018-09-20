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
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ro_id", type = IdType.AUTO)
    private Integer roId;
    private String roName;
    private String roDescription;


    public Integer getRoId() {
        return roId;
    }

    public void setRoId(Integer roId) {
        this.roId = roId;
    }

    public String getRoName() {
        return roName;
    }

    public void setRoName(String roName) {
        this.roName = roName;
    }

    public String getRoDescription() {
        return roDescription;
    }

    public void setRoDescription(String roDescription) {
        this.roDescription = roDescription;
    }

    @Override
    public String toString() {
        return "Role{" +
        ", roId=" + roId +
        ", roName=" + roName +
        ", roDescription=" + roDescription +
        "}";
    }
}
