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
public class Jobs implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "job_id", type = IdType.AUTO)
    private Integer jobId;
    private Date jobCreattime;
    private String jobMoney;
    private String jobIntro;
    private String jobRequire;
    private String jobState;
    private Integer jobNum;
    private String jobType;
    private String jobStudio;


    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Date getJobCreattime() {
        return jobCreattime;
    }

    public void setJobCreattime(Date jobCreattime) {
        this.jobCreattime = jobCreattime;
    }

    public String getJobMoney() {
        return jobMoney;
    }

    public void setJobMoney(String jobMoney) {
        this.jobMoney = jobMoney;
    }

    public String getJobIntro() {
        return jobIntro;
    }

    public void setJobIntro(String jobIntro) {
        this.jobIntro = jobIntro;
    }

    public String getJobRequire() {
        return jobRequire;
    }

    public void setJobRequire(String jobRequire) {
        this.jobRequire = jobRequire;
    }

    public String getJobState() {
        return jobState;
    }

    public void setJobState(String jobState) {
        this.jobState = jobState;
    }

    public Integer getJobNum() {
        return jobNum;
    }

    public void setJobNum(Integer jobNum) {
        this.jobNum = jobNum;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobStudio() {
        return jobStudio;
    }

    public void setJobStudio(String jobStudio) {
        this.jobStudio = jobStudio;
    }

    @Override
    public String toString() {
        return "Jobs{" +
        ", jobId=" + jobId +
        ", jobCreattime=" + jobCreattime +
        ", jobMoney=" + jobMoney +
        ", jobIntro=" + jobIntro +
        ", jobRequire=" + jobRequire +
        ", jobState=" + jobState +
        ", jobNum=" + jobNum +
        ", jobType=" + jobType +
        ", jobStudio=" + jobStudio +
        "}";
    }
}
