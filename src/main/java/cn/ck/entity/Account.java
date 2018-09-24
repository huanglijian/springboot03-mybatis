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
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "acc_id", type = IdType.AUTO)
    private Integer accId;
    private Double accMoney;
    private String accType;
    private String accForeid;


    public Integer getAccId() {
        return accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    public Double getAccMoney() {
        return accMoney;
    }

    public void setAccMoney(Double accMoney) {
        this.accMoney = accMoney;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getAccForeid() {
        return accForeid;
    }

    public void setAccForeid(String accForeid) {
        this.accForeid = accForeid;
    }

    @Override
    public String toString() {
        return "Account{" +
        ", accId=" + accId +
        ", accMoney=" + accMoney +
        ", accType=" + accType +
        ", accForeid=" + accForeid +
        "}";
    }
}
