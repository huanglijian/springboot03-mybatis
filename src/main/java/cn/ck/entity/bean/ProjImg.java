package cn.ck.entity.bean;

import cn.ck.entity.Project;

/**
 * @author 马圳彬
 * @version 1.0
 * @description chuangke
 * @date 2018/10/11 20:09
 **/
public class ProjImg {
    private Project project;
    private String projImgpath;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getProjImgpath() {
        return projImgpath;
    }

    public void setProjImgpath(String projImgpath) {
        this.projImgpath = projImgpath;
    }

    @Override
    public String toString() {
        return "ProjImg{" +
                "project=" + project +
                ", projImgpath='" + projImgpath + '\'' +
                '}';
    }
}
