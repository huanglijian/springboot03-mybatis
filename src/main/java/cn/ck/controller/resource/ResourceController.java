package cn.ck.controller.resource;

import cn.ck.controller.AbstractController;
import cn.ck.entity.Resource;
import cn.ck.service.AlluserService;
import cn.ck.service.DanmuService;
import cn.ck.service.ResourceService;
import cn.ck.utils.ResponseBo;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("/resource")
public class ResourceController extends AbstractController {

    @Autowired
    private AlluserService alluserService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private DanmuService danmuService;

    /**
     * 创客讲堂首页跳转controller
     * @return
     */
    @RequestMapping("home")
    public String home(){
        return "resource/resource_video";
    }


    /**
     * 创客讲堂热门视频
     * @return
     */
    @RequestMapping("hotResource")
    @ResponseBody
    public ResponseBo getHotResource(){
        Page<Resource> resourcePage = resourceService.getMostLikeResPage(new Page<Resource>(1, 8));
        return ResponseBo.ok().put("hotResource", resourcePage.getRecords());
    }

    /**
     * 创客讲堂推荐视频
     * @return
     */
    @RequestMapping("recommendResource")
    @ResponseBody
    public ResponseBo getRecommendResource(){
        Page<Resource> resourcePage = resourceService.getMostLikeResPage(new Page<Resource>(1, 8));
        return ResponseBo.ok().put("recommendResource", resourcePage.getRecords());
    }

    /**
     * 搜索建议
     * @return
     */
    @RequestMapping("searchSuggest")
    @ResponseBody
    public ResponseBo getSearchSuggest(@RequestParam("key")String keyword){
        Page<Resource> page = resourceService.getSuggestPage(new Page<Resource>(1, 10), keyword);
        return  ResponseBo.ok().put("resource", page.getRecords());
    }

    /**
     * 搜索结果页面
     * @param keyword
     * @return
     */
    @RequestMapping("searchResult")
    public String getSearchResult(@RequestParam("key")String keyword, @RequestParam("page")Integer page){
        return "resource/res_search_results";
    }

    @RequestMapping("doSearch/{key}/{page}")
    @ResponseBody
    public ResponseBo doSearch(@PathVariable("key")String keyword, @PathVariable("page")Integer curPage){
        Page<Resource> page = resourceService.getSuggestPage(new Page<Resource>(curPage, 16), keyword);
        return ResponseBo.ok().put("result", page);
    }

    /**
     * 视频播放页
     * @return
     */
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
