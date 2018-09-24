package cn.ck.controller.promcenter;

import cn.ck.controller.common.FileController;
import cn.ck.entity.Alluser;
import cn.ck.entity.Project;
import cn.ck.service.ProjectService;
import cn.ck.service.PromulgatorService;
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

@Controller
@RequestMapping("/promcenter")
public class ProjectController {
    @Autowired
    ProjectService projectService;


    @PostMapping("/projectcreat")
    @ResponseBody
    public ResponseBo projectcreat(HttpServletRequest request, @RequestParam("img") MultipartFile img, @RequestParam("file") MultipartFile file, Project project){
        FileController fileupload=new FileController();

        if(!file.isEmpty()){
            String filepath="G:/ck/project/file";
            String fileurl=fileupload.fileupload(file,filepath);
            project.setProjFile(fileurl);
        }

        String imgpath = "G:/ck/project/img";
        String imgurl=fileupload.fileupload(img,imgpath);
        project.setProjImg(imgurl);
//        System.out.println(imgurl);

        Date date = new Date();//获得系统时间
        String nowTime = new SimpleDateFormat("yyyy-MM-dd").format(date);
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
}
