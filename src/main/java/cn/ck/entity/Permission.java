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
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "per_id", type = IdType.AUTO)
    private Integer perId;
    private String perUrl;
    private String perName;


    public Integer getPerId() {
        return perId;
    }

    public void setPerId(Integer perId) {
        this.perId = perId;
    }

    public String getPerUrl() {
        return perUrl;
    }

    public void setPerUrl(String perUrl) {
        this.perUrl = perUrl;
    }

    public String getPerName() {
        return perName;
    }

    public void setPerName(String perName) {
        this.perName = perName;
    }

    @Override
    public String toString() {
        return "Permission{" +
        ", perId=" + perId +
        ", perUrl=" + perUrl +
        ", perName=" + perName +
        "}";
    }
}
