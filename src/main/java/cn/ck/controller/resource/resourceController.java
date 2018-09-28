package cn.ck.controller.resource;

import cn.ck.controller.AbstractController;
import cn.ck.entity.Resource;
import cn.ck.service.AlluserService;
import cn.ck.service.DanmuService;
import cn.ck.service.ResourceService;
import cn.ck.utils.ResponseBo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping("/resource")
public class resourceController extends AbstractController {

    @Autowired
    private AlluserService alluserService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private DanmuService danmuService;

    @RequestMapping("home")
    public String home(){
        return "resource/resource_video";
    }

    @RequestMapping("hotResource")
    @ResponseBody
    public ResponseBo getHotResource(){
        Page<Resource> resourcePage = resourceService.getMostLikeResPage(new Page<Resource>(1, 8));
        return ResponseBo.ok().put("hotResource", resourcePage.getRecords());
    }

    @RequestMapping("recommendResource")
    @ResponseBody
    public ResponseBo getRecommendResource(){
        Page<Resource> resourcePage = resourceService.getMostLikeResPage(new Page<Resource>(1, 8));
        return ResponseBo.ok().put("recommendResource", resourcePage.getRecords());
    }

    @RequestMapping("searchSuggest")
    @ResponseBody
    public ResponseBo getSearchSuggest(){
        Page<Resource> page = resourceService.selectPage(new Page<Resource>(1,100), new EntityWrapper<Resource>().orderBy("res_uploadtime"));
        return  ResponseBo.ok().put("resource", page.getRecords());
    }

    @RequestMapping("player")
    public String player(){
        return "resource/resource_player";
    }

    @RequestMapping("index")
    public String index(){
        return "resource/index";
    }

    @RequestMapping(value ="/getFileSrc",method = RequestMethod.GET)
    @ResponseBody
    public void getFileSrc(HttpServletRequest request , HttpServletResponse response) throws IOException {
        File file = new File("D:\\VsCode Work Space\\testVideo2.mp4");
        FileInputStream input = new FileInputStream(file);
        int i = input.available();
        byte[] bytes = new byte[i];
        input.read(bytes);
        response.setContentType("application/video");
        OutputStream output = response.getOutputStream();
        output.write(bytes);
        output.flush();
        output.close();
        input.close();
    }
}
