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
public class Studio implements Serializable {

    private static final long serialVersionUID = 1L;

    private String stuId;
    private Date stuCreattime;
    private String stuName;
    private Integer stuProjectnum;
    private Double stuGrade;
    private Integer stuMembernum;
    private String stuIntro;
    private String stuImg;
    private String stuField;
    private String stuTag;
    private String stuProvince;
    private String stuCity;
    private String stuArea;
    private Double stuIncome;
    private String stuCreatid;


    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public Date getStuCreattime() {
        return stuCreattime;
    }

    public void setStuCreattime(Date stuCreattime) {
        this.stuCreattime = stuCreattime;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Integer getStuProjectnum() {
        return stuProjectnum;
    }

    public void setStuProjectnum(Integer stuProjectnum) {
        this.stuProjectnum = stuProjectnum;
    }

    public Double getStuGrade() {
        return stuGrade;
    }

    public void setStuGrade(Double stuGrade) {
        this.stuGrade = stuGrade;
    }

    public Integer getStuMembernum() {
        return stuMembernum;
    }

    public void setStuMembernum(Integer stuMembernum) {
        this.stuMembernum = stuMembernum;
    }

    public String getStuIntro() {
        return stuIntro;
    }

    public void setStuIntro(String stuIntro) {
        this.stuIntro = stuIntro;
    }

    public String getStuImg() {
        return stuImg;
    }

    public void setStuImg(String stuImg) {
        this.stuImg = stuImg;
    }

    public String getStuField() {
        return stuField;
    }

    public void setStuField(String stuField) {
        this.stuField = stuField;
    }

    public String getStuTag() {
        return stuTag;
    }

    public void setStuTag(String stuTag) {
        this.stuTag = stuTag;
    }

    public String getStuProvince() {
        return stuProvince;
    }

    public void setStuProvince(String stuProvince) {
        this.stuProvince = stuProvince;
    }

    public String getStuCity() {
        return stuCity;
    }

    public void setStuCity(String stuCity) {
        this.stuCity = stuCity;
    }

    public String getStuArea() {
        return stuArea;
    }

    public void setStuArea(String stuArea) {
        this.stuArea = stuArea;
    }

    public Double getStuIncome() {
        return stuIncome;
    }

    public void setStuIncome(Double stuIncome) {
        this.stuIncome = stuIncome;
    }

    public String getStuCreatid() {
        return stuCreatid;
    }

    public void setStuCreatid(String stuCreatid) {
        this.stuCreatid = stuCreatid;
    }

    @Override
    public String toString() {
        return "Studio{" +
        ", stuId=" + stuId +
        ", stuCreattime=" + stuCreattime +
        ", stuName=" + stuName +
        ", stuProjectnum=" + stuProjectnum +
        ", stuGrade=" + stuGrade +
        ", stuMembernum=" + stuMembernum +
        ", stuIntro=" + stuIntro +
        ", stuImg=" + stuImg +
        ", stuField=" + stuField +
        ", stuTag=" + stuTag +
        ", stuProvince=" + stuProvince +
        ", stuCity=" + stuCity +
        ", stuArea=" + stuArea +
        ", stuIncome=" + stuIncome +
        ", stuCreatid=" + stuCreatid +
        "}";
    }
}
