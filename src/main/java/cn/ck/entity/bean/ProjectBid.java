package cn.ck.entity.bean;

import cn.ck.entity.Project;

public class ProjectBid {
    private Project project;
    private String creatdate;
    private String startdate;
    private String enddate;
    private String bidday;//竞标剩余天数
    private String bidnum;//竞标人数

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getCreatdate() {
        return creatdate;
    }

    public void setCreatdate(String creatdate) {
        this.creatdate = creatdate;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getBidday() {
        return bidday;
    }

    public void setBidday(String bidday) {
        this.bidday = bidday;
    }

    public String getBidnum() {
        return bidnum;
    }

    public void setBidnum(String bidnum) {
        this.bidnum = bidnum;
    }

    @Override
    public String toString() {
        return "ProjectBid{" +
                "project=" + project +
                ", creatdate='" + creatdate + '\'' +
                ", startdate='" + startdate + '\'' +
                ", enddate='" + enddate + '\'' +
                ", bidday='" + bidday + '\'' +
                ", bidnum='" + bidnum + '\'' +
                '}';
    }
}
