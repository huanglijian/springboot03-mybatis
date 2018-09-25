package cn.ck.controller.promcenter;

import cn.ck.utils.FileController;
import cn.ck.entity.Alluser;
import cn.ck.entity.Project;
import cn.ck.service.ProjectService;
import cn.ck.utils.DateUtils;
import cn.ck.utils.ResponseBo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/promcenter")
public class ProjectController {
    @Autowired
    ProjectService projectService;


    @PostMapping("/projectcreat")
    @ResponseBody
    public ResponseBo projectcreat(HttpServletRequest request, @RequestParam("img") MultipartFile img, @RequestParam("file") MultipartFile file, Project project){

        if(!file.isEmpty()){
            String filepath="E:/ck/project/file";
            String fileurl=FileController.fileupload(file,filepath);
            project.setProjFile(fileurl);
        }

        String imgpath = "E:/ck/project/img";
        String imgurl=FileController.fileupload(img,imgpath);
        project.setProjImg(imgurl);
//        System.out.println(imgurl);

        Date date = new Date();//获得系统时间
        String nowTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
        Timestamp projcreattime = Timestamp.valueOf(nowTime);//把时间转换
        project.setProjCreattime(projcreattime);

        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        project.setProjProm(user.getAllId());

        project.setProjTag(request.getParameter("label"));
        project.setProjState("竞标中");

        System.out.println(project);

        if(projectService.insertAllColumn(project)){
            return ResponseBo.ok("发布成功");
        }else{
            return ResponseBo.error("发布失败");
        }

    }

    @PostMapping("/projectbid")
    @ResponseBody
    public Map<String,Object> projectbid(){
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        Map<String,Object> bidmap=new HashMap<>();
        Project project=new Project();
       //匹配当前时间，更改目前项目竞标状态
        List<Project> proBidding=projectService.projBidTimefalse(user.getAllId());
        for (Project project1:proBidding) {
            project1.setProjState("竞标结束");
        }
        if(proBidding.size()!=0){
            projectService.updateAllColumnBatchById(proBidding);
        }
        //查询更新后竞标中的项目
        List<Project> proBiddinglist=projectService.projBidTimetrue(user.getAllId());
        for (Project project2:proBiddinglist) {
            String date=DateUtils.format(project2.getProjCreattime(),DateUtils.DATE_TIME_PATTERN);
            project2.getProjCreattime();
        }

        return bidmap;
    }
}
