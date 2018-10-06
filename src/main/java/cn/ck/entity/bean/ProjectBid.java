package cn.ck.entity.bean;

import cn.ck.entity.Bidding;
import cn.ck.entity.Project;
import cn.ck.entity.Studio;

public class ProjectBid {
    private Project project;
    private Bidding bidding;
    private Studio studio;
    private String creatdate;//
    private String startdate;//
    private String enddate;//
    private Integer bidday;//竞标剩余天数
    private Integer bidnum;//竞标人数
    private Integer developday;//开发剩余天数


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

    public Integer getBidday() {
        return bidday;
    }

    public void setBidday(Integer bidday) {
        this.bidday = bidday;
    }

    public Integer getBidnum() {
        return bidnum;
    }

    public void setBidnum(Integer bidnum) {
        this.bidnum = bidnum;
    }

    public Bidding getBidding() {
        return bidding;
    }

    public void setBidding(Bidding bidding) {
        this.bidding = bidding;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public Integer getDevelopday() {
        return developday;
    }

    public void setDevelopday(Integer developday) {
        this.developday = developday;
    }


    @Override
    public String toString() {
        return "ProjectBid{" +
                "project=" + project +
                ", bidding=" + bidding +
                ", studio=" + studio +
                ", creatdate='" + creatdate + '\'' +
                ", startdate='" + startdate + '\'' +
                ", enddate='" + enddate + '\'' +
                ", bidday=" + bidday +
                ", bidnum=" + bidnum +
                ", developday=" + developday +
                '}';
    }
}
