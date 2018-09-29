package cn.ck.entity.bean;

import cn.ck.entity.Jobs;
import cn.ck.entity.Studio;

import java.util.Date;

public class JobsStudio {
    private Jobs jobs;
    private Studio studio;
    private String time;

    @Override
    public String toString() {
        return "JobsStudio{" +
                "jobs=" + jobs +
                ", studio=" + studio +
                ", time='" + time + '\'' +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Jobs getJobs() {
        return jobs;
    }

    public void setJobs(Jobs jobs) {
        this.jobs = jobs;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

}
