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
public class Bidding implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "bid_id", type = IdType.AUTO)
    private Integer bidId;
    private Date bidTime;
    private Date bidStarttime;
    private Date bidEndtime;
    private String bidState;
    private String bidScheme;
    private String bidCycle;
    private String bidMoney;
    private String bidPhone;
    private String bidEmail;
    private String bidStudio;
    private Integer bidProj;


    public Integer getBidId() {
        return bidId;
    }

    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    public Date getBidTime() {
        return bidTime;
    }

    public void setBidTime(Date bidTime) {
        this.bidTime = bidTime;
    }

    public Date getBidStarttime() {
        return bidStarttime;
    }

    public void setBidStarttime(Date bidStarttime) {
        this.bidStarttime = bidStarttime;
    }

    public Date getBidEndtime() {
        return bidEndtime;
    }

    public void setBidEndtime(Date bidEndtime) {
        this.bidEndtime = bidEndtime;
    }

    public String getBidState() {
        return bidState;
    }

    public void setBidState(String bidState) {
        this.bidState = bidState;
    }

    public String getBidScheme() {
        return bidScheme;
    }

    public void setBidScheme(String bidScheme) {
        this.bidScheme = bidScheme;
    }

    public String getBidCycle() {
        return bidCycle;
    }

    public void setBidCycle(String bidCycle) {
        this.bidCycle = bidCycle;
    }

    public String getBidMoney() {
        return bidMoney;
    }

    public void setBidMoney(String bidMoney) {
        this.bidMoney = bidMoney;
    }

    public String getBidPhone() {
        return bidPhone;
    }

    public void setBidPhone(String bidPhone) {
        this.bidPhone = bidPhone;
    }

    public String getBidEmail() {
        return bidEmail;
    }

    public void setBidEmail(String bidEmail) {
        this.bidEmail = bidEmail;
    }

    public String getBidStudio() {
        return bidStudio;
    }

    public void setBidStudio(String bidStudio) {
        this.bidStudio = bidStudio;
    }

    public Integer getBidProj() {
        return bidProj;
    }

    public void setBidProj(Integer bidProj) {
        this.bidProj = bidProj;
    }

    @Override
    public String toString() {
        return "Bidding{" +
        ", bidId=" + bidId +
        ", bidTime=" + bidTime +
        ", bidStarttime=" + bidStarttime +
        ", bidEndtime=" + bidEndtime +
        ", bidState=" + bidState +
        ", bidScheme=" + bidScheme +
        ", bidCycle=" + bidCycle +
        ", bidMoney=" + bidMoney +
        ", bidPhone=" + bidPhone +
        ", bidEmail=" + bidEmail +
        ", bidStudio=" + bidStudio +
        ", bidProj=" + bidProj +
        "}";
    }
}
