package cn.ck.controller.promcenter;

import cn.ck.entity.Project;
import cn.ck.service.ProjectService;
import cn.ck.utils.ResponseBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/promcenter")
public class ProjManagerController {
    @Autowired
    ProjectService projectService;


    @PostMapping("/prompauseproj/{id}")
    @ResponseBody
    public ResponseBo prompauseproj(@PathVariable("id") String id, Model model){
        System.out.println(id);
        Project project=projectService.selectById(id);
//        project.setProjState("发布者中止");
//        projectService.updateAllColumnById(project);
//        System.out.println(project);
       if(project.getProjState().equals("开发中")){
           project.setProjState("发布者中止");
           projectService.updateAllColumnById(project);
           System.out.println(project);
           return ResponseBo.ok();
       }else{
           return ResponseBo.ok().put("code","1");
       }
    }
}
