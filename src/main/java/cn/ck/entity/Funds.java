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
public class Funds implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fundId;
    private Date fundDatetime;
    private Double fundMoney;
    private String fundType;
    private String fundIncome;
    private String fundOutlay;
    private String fundRemark;


    public String getFundId() {
        return fundId;
    }

    public void setFundId(String fundId) {
        this.fundId = fundId;
    }

    public Date getFundDatetime() {
        return fundDatetime;
    }

    public void setFundDatetime(Date fundDatetime) {
        this.fundDatetime = fundDatetime;
    }

    public Double getFundMoney() {
        return fundMoney;
    }

    public void setFundMoney(Double fundMoney) {
        this.fundMoney = fundMoney;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getFundIncome() {
        return fundIncome;
    }

    public void setFundIncome(String fundIncome) {
        this.fundIncome = fundIncome;
    }

    public String getFundOutlay() {
        return fundOutlay;
    }

    public void setFundOutlay(String fundOutlay) {
        this.fundOutlay = fundOutlay;
    }

    public String getFundRemark() {
        return fundRemark;
    }

    public void setFundRemark(String fundRemark) {
        this.fundRemark = fundRemark;
    }

    @Override
    public String toString() {
        return "Funds{" +
        ", fundId=" + fundId +
        ", fundDatetime=" + fundDatetime +
        ", fundMoney=" + fundMoney +
        ", fundType=" + fundType +
        ", fundIncome=" + fundIncome +
        ", fundOutlay=" + fundOutlay +
        ", fundRemark=" + fundRemark +
        "}";
    }
}
