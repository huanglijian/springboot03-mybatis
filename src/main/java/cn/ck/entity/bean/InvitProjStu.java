package cn.ck.entity.bean;

import cn.ck.entity.Invite;
import cn.ck.entity.Project;
import cn.ck.entity.Studio;

import javax.rmi.CORBA.Stub;

public class InvitProjStu extends Invite {
    private Project project;
    private Studio studio;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    @Override
    public String toString() {
        return "InvitProjStu{" +
                "project=" + project +
                ", studio=" + studio +
                '}';
    }
}
