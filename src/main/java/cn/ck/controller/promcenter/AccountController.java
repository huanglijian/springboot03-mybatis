package cn.ck.controller.promcenter;


import cn.ck.controller.AbstractController;
import cn.ck.controller.FileController;
import cn.ck.entity.Account;
import cn.ck.entity.Alluser;
import cn.ck.entity.Project;
import cn.ck.entity.Promulgator;
import cn.ck.service.AccountService;
import cn.ck.service.ProjectService;
import cn.ck.service.PromulgatorService;
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
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/promcenter")
public class AccountController {
    @Autowired
    PromulgatorService promulgatorService;
    @Autowired
    AccountService accountService;
    @Autowired
    ProjectService projectService;



    @PostMapping("/account")
    @ResponseBody
    public Map<String,Object> account() {
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        Promulgator promulgator=new Promulgator();
        promulgator=promulgatorService.selectID(user.getAllId());
        Account account=new Account();
        account=accountService.selectOne(new EntityWrapper<Account>().eq("acc_foreid", user.getAllId()));
        Map<String,Object> prommap=new HashMap<String, Object>();
        prommap.put("num","1");
        prommap.put("prom",promulgator);
        prommap.put("account",account);
//        String json = JSON.toJSONString(prommap,true);
//        System.out.println(json);
//        System.out.println(prommap);
        return prommap;
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
     * @param dataURL
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
