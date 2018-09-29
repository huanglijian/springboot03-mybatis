package cn.ck.entity.bean;

import cn.ck.entity.Jobs;
import cn.ck.entity.Jobuser;
import cn.ck.entity.Users;

import java.util.Date;

/**
 * @author 马圳彬
 * @version 1.0
 * @description 创客
 * @date 2018/9/28 11:52
 **/
public class JobUsers {
    private Jobs jobs;
    private Jobuser jobuser;
    private Users users;

    private String jobName;
    private String userName;
    private String userImg;
    private Date userEntrytime;

    public Jobs getJobs() {
        return jobs;
    }

    public void setJobs(Jobs jobs) {
        this.jobs = jobs;
    }

    public Jobuser getJobuser() {
        return jobuser;
    }

    public void setJobuser(Jobuser jobuser) {
        this.jobuser = jobuser;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public Date getUserEntrytime() {
        return userEntrytime;
    }

    public void setUserEntrytime(Date userEntrytime) {
        this.userEntrytime = userEntrytime;
    }

    @Override
    public String toString() {
        return "JobUsers{" +
                "jobs=" + jobs +
                ", jobuser=" + jobuser +
                ", users=" + users +
                ", jobName='" + jobName + '\'' +
                ", userName='" + userName + '\'' +
                ", userImg='" + userImg + '\'' +
                ", userEntrytime=" + userEntrytime +
                '}';
    }
}
