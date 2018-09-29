package cn.ck.controller.promcenter;


import cn.ck.controller.AbstractController;
import cn.ck.controller.FileController;
import cn.ck.entity.*;
import cn.ck.service.AccountService;
import cn.ck.service.ProjectService;
import cn.ck.service.PromulgatorService;
import cn.ck.service.StudioService;
import cn.ck.utils.ConstCofig;
import cn.ck.utils.ResponseBo;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/promcenter")
public class AccountController {
    @Autowired
    PromulgatorService promulgatorService;
    @Autowired
    AccountService accountService;
    @Autowired
    ProjectService projectService;
    @Autowired
    StudioService studioService;

    /**
     * 发布者账户页面渲染
     * @return
     */
    @PostMapping("/account")
    @ResponseBody
    public ResponseBo account() {
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        Promulgator promulgator=promulgatorService.selectID(user.getAllId());
        Account account=accountService.selectOne(new EntityWrapper<Account>().eq("acc_foreid", user.getAllId()));
        //项目总数
        int allnum=projectService.selectCount(new EntityWrapper<Project>().eq("proj_prom",user.getAllId()));
        //完工项目数量
        int finishnum=projectService.selectCount(new EntityWrapper<Project>().eq("proj_prom",user.getAllId()).eq("proj_state","开发完成"));
        //开发中项目数量
        int beingnum=projectService.selectCount(new EntityWrapper<Project>().eq("proj_prom",user.getAllId()).eq("proj_state","开发中"));
        //失败项目数量
        int failnum=projectService.selectCount(new EntityWrapper<Project>().eq("proj_prom",user.getAllId()).eq("proj_state","项目中止"));
        //中止项目数量
        int pausenum=projectService.selectCount(new EntityWrapper<Project>().eq("proj_prom",user.getAllId()).eq("proj_state","发布者中止").eq("proj_state","承接方中止"));
        //竞标中项目
        int bidnum=projectService.selectCount(new EntityWrapper<Project>().eq("proj_prom",user.getAllId()).eq("proj_state","竞标中"));

        Set<String> set = new HashSet<>();
        set.add("proj_starttime");
        List<Project> projectList=projectService.selectList(new EntityWrapper<Project>().eq("proj_prom",user.getAllId()).eq("proj_state","承接方中止").or().eq("proj_state","开发中").or().eq("proj_state","发布者中止").orderDesc(set));
        for (Project project:projectList) {
            Studio studio=studioService.selectById(project.getProjStudio());
            String stuname=studio.getStuName();
            project.setProjStudio(stuname);
        }

        return ResponseBo.ok().put("prom",promulgator).put("account",account).put("project",projectList)
                .put("allnum",allnum).put("finishnum",finishnum).put("beingnum",beingnum).put("failnum",failnum)
                .put("pausenum",pausenum).put("bidnum",bidnum);
    }

    /**
     * 获取登录用户信息，用于用户头像更新界面渲染
     * @return
     */
    @GetMapping("/getpromimg")
    @ResponseBody
    public ResponseBo getpromimg(){
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        Promulgator promulgator=promulgatorService.selectID(user.getAllId());
        return ResponseBo.ok().put("prom",promulgator);
    }

    /**
     * 渲染图片显示
     * @param fileName
     * @param response
     */
    @RequestMapping("/previewsrc/{fileName}")
    public void previewsrc(@PathVariable("fileName") String fileName, HttpServletResponse response){
        //将文件名分割成 文件名 和 格式
        //“ . " 需要用两次转义
        String [] path = fileName.split("\\.");
        //获取服务器中的文件
        File imgFile = new File(ConstCofig.RootPath + ConstCofig.PromImg + path[0] + "." + path[1]);
        //输出到页面
        FileController.responseFile(response, imgFile);
    }

    /**
     * 更换头像
     * Base64位编码的图片进行解码，并保存到指定目录
     * @param dataURL Base64位编码的图片
     * @return
     * @throws IOException
     */
  @PostMapping("/updatepromimg")
  @ResponseBody
  public ResponseBo decodeBase64DataURLToImage(@RequestParam("dataURL") String dataURL) throws IOException {
      // 将dataURL开头的非base64字符删除
      String base64 = dataURL.substring(dataURL.indexOf(",") + 1);
      String path="E:\\ChuangKeFile\\PromImg\\";
      String imgName= UUID.randomUUID()+".png";
      FileOutputStream write = new FileOutputStream(new File(path + imgName));
      byte[] decoderBytes = Base64.getDecoder().decode(base64);
      write.write(decoderBytes);
      write.close();

      //将路径更新至数据库
      Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
      Promulgator promulgator=promulgatorService.selectID(user.getAllId());
      promulgator.setPromImg(imgName);
      promulgatorService.updateAllColumnById(promulgator);
      return ResponseBo.ok();
  }


}
