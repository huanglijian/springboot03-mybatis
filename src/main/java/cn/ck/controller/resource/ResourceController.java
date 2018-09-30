package cn.ck.controller.resource;

import cn.ck.controller.AbstractController;
import cn.ck.entity.Resource;
import cn.ck.service.AlluserService;
import cn.ck.service.DanmuService;
import cn.ck.service.ResourceService;
import cn.ck.utils.ResponseBo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;


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
        //MP方式分页
//        Page<Resource> resourcePage = resourceService.getMostLikeResPage(new Page<Resource>(1, 8));

        //PageHelper方式分页
        PageHelper.startPage(1, 8);
        List<Resource> resources = resourceService.getMostLikeResPage();
        PageInfo<Resource> pageInfo = new PageInfo<>(resources);

        return ResponseBo.ok().put("hotResource", pageInfo.getList());
    }

    /**
     * 创客讲堂推荐视频
     * @return
     */
    @RequestMapping("recommendResource")
    @ResponseBody
    public ResponseBo getRecommendResource(){
        //MP方式分页
//        Page<Resource> resourcePage = resourceService.getMostLikeResPage(new Page<Resource>(1, 8));

        //PageHelper方式分页
        PageHelper.startPage(1, 8);
        List<Resource> resources = resourceService.getLatestResPage();
        PageInfo<Resource> pageInfo = new PageInfo<>(resources);

        return ResponseBo.ok().put("recommendResource", pageInfo.getList());
    }

    /**
     * 搜索建议
     * @return
     */
    @RequestMapping("searchSuggest")
    @ResponseBody
    public ResponseBo getSearchSuggest(@RequestParam("key")String keyword){
        //MP方式分页
//        Page<Resource> page = resourceService.getSuggestPage(new Page<Resource>(1, 10), keyword);

        PageHelper.startPage(1, 10);
        List<Resource> resources = resourceService.selectList(new EntityWrapper<>());
        PageInfo<Resource> pageInfo = new PageInfo<>(resources);

        return  ResponseBo.ok().put("resource", pageInfo.getList());
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

    /**
     * 搜索结果
     * @param keyword 搜索的关键字
     * @param curPage 页数
     * @return
     */
    @RequestMapping("doSearch/{key}/{page}")
    @ResponseBody
    public ResponseBo doSearch(@PathVariable("key")String keyword, @PathVariable("page")Integer curPage){
        //MP方式分页
//        Page<Resource> page = resourceService.getSuggestPage(new Page<Resource>(curPage, 2), keyword);

        PageHelper.startPage(curPage, 8);
        List<Resource> resources = resourceService.getSuggestPage(keyword);
        PageInfo<Resource> resourcePageInfo = new PageInfo<>(resources);

        return ResponseBo.ok().put("result", resourcePageInfo);
    }

    /**
     * 视频播放界面
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

    /**
     * 二进制流输出视频
     * @param request
     * @param response
     * @throws IOException
     */
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
