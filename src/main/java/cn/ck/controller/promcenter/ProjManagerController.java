package cn.ck.controller.promcenter;

import cn.ck.entity.Notice;
import cn.ck.entity.Project;
import cn.ck.service.NoticeService;
import cn.ck.service.ProjectService;
import cn.ck.utils.NoticeInsert;
import cn.ck.utils.ResponseBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/promcenter")
public class ProjManagerController {
    @Autowired
    ProjectService projectService;
    @Autowired
    NoticeService noticeService;

    /**
     * 发布者主动发起项目暂停
     * @param id
     * @return
     */
    @PostMapping("/prompauseproj/{id}")
    @ResponseBody
    public ResponseBo prompauseproj(@PathVariable("id") String id){
        Project project=projectService.selectById(id);
       if(project.getProjState().equals("开发中")){
           project.setProjState("发布者中止");
           projectService.updateAllColumnById(project);
           //发送通知信息
           String nowdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
           String stumessage="您好，您正在开发的项目"+project.getProjName()+"已在"+nowdate+"被甲方发起项目中止,如需查看详细信息，请查看项目管理相关功能";
           String prommessage="您好，您正在开发的项目"+project.getProjName()+"已在"+nowdate+"发起项目中止,请等待乙方的确认，如需查看详细信息，请查看项目管理相关功能";
           Notice notice1= NoticeInsert.insertNotice(stumessage,project.getProjStudio());
           noticeService.insertAllColumn(notice1);
           Notice notice2= NoticeInsert.insertNotice(prommessage,project.getProjProm());
           noticeService.insertAllColumn(notice2);

           return ResponseBo.ok();
       }else{
           return ResponseBo.ok().put("code","1");
       }
    }

    /**
     * 发布者确认承接方发起的项目暂停
     * @param id
     * @return
     */
    @PostMapping("/promaffirm/{id}")
    @ResponseBody
    public ResponseBo promaffirm(@PathVariable("id") String id){
        Project project=projectService.selectById(id);
        if(project.getProjState().equals("承接方中止")){
            project.setProjState("项目中止");
            projectService.updateAllColumnById(project);
            //发送通知信息
            String nowdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String stumessage="您好，您发起中止的项目"+project.getProjName()+"已在"+nowdate+"被甲方确认中止,如需查看详细信息，请查看项目管理相关功能";
            String prommessage="您好，您已确认乙方发起的项目中止操作，"+project.getProjName()+"已在"+nowdate+"中止,如需查看详细信息，请查看项目管理相关功能";
            Notice notice1= NoticeInsert.insertNotice(stumessage,project.getProjStudio());
            noticeService.insertAllColumn(notice1);
            Notice notice2= NoticeInsert.insertNotice(prommessage,project.getProjProm());
            noticeService.insertAllColumn(notice2);

            return ResponseBo.ok();
        }else{
            return ResponseBo.ok().put("code","1");
        }
    }

    /**
     * 发布者取消中止项目
     * @param id
     * @return
     */
    @PostMapping("/promcancel/{id}")
    @ResponseBody
    public ResponseBo promcancel(@PathVariable("id") String id){
        Project project=projectService.selectById(id);
        if(project.getProjState().equals("发布者中止")){
            project.setProjState("开发中");
            projectService.updateAllColumnById(project);
            //发送通知信息
            String nowdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String stumessage="您好，甲方发起的中止项目操作已在"+nowdate+"被甲方取消中止,项目"+project.getProjName()+"现可继续开发，如需查看详细信息，请查看项目管理相关功能";
            String prommessage="您好，您已取消项目中止操作，"+project.getProjName()+"已在"+nowdate+"取消中止,现可继续开发，如需查看详细信息，请查看项目管理相关功能";
            Notice notice1= NoticeInsert.insertNotice(stumessage,project.getProjStudio());
            noticeService.insertAllColumn(notice1);
            Notice notice2= NoticeInsert.insertNotice(prommessage,project.getProjProm());
            noticeService.insertAllColumn(notice2);

            return ResponseBo.ok();
        }else{
            return ResponseBo.ok().put("code","1");
        }
    }
}
