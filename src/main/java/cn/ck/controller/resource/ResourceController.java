package cn.ck.controller.resource;

import cn.ck.controller.AbstractController;
import cn.ck.entity.*;
import cn.ck.entity.bean.DanmuJson;
import cn.ck.service.*;
import cn.ck.utils.JsonUtils;
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
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/resource")
public class ResourceController extends AbstractController {

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private DanmuService danmuService;
    @Autowired
    private CollectresService collectresService;
    @Autowired
    private LikeresService likeresService;
    @Autowired
    private UsersService usersService;

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

        //PageHelper方式分页
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

        //PageHelper方式分页
        PageHelper.startPage(curPage, 8);
        List<Resource> resources = resourceService.getSuggestPage(keyword);
        PageInfo<Resource> resourcePageInfo = new PageInfo<>(resources);

        return ResponseBo.ok().put("result", resourcePageInfo);
    }

    /**
     * 视频播放界面
     * @return
     */
    @RequestMapping("player/{resId}")
    public String player(@PathVariable("resId")Integer resId){
        return "resource/resource_player";
    }

    /**
     * 当前播放视频的详细信息
     * 当前播放视频的收藏人数
     * 若用户已登录，是否已经收藏
     * @param resId 视频id
     * @return
     */
    @RequestMapping("getCurRes")
    @ResponseBody
    public ResponseBo getCurRes(Integer resId) throws Exception {
        if(resId == null) return ResponseBo.error("错误视频ID");

        boolean isLogined = false, isCollected = false, isLiked = false;
        if(getUser() != null){
            isLogined = true;
            isCollected = collectresService.isCollected(getUser().getAllId(), resId);
            isLiked = likeresService.isLiked(getUser().getAllId(), resId);
        }
        int collectedNum = collectresService.getCollectedNumByRes(resId);
        return ResponseBo.ok()
                .put("curRes", resourceService.selectById(resId))
                .put("collectedNum", collectedNum)
                .put("isLogined", isLogined)
                .put("isCollected", isCollected)
                .put("isLiked", isLiked);
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
    @RequestMapping(value ="/getFileSrc/{resId}",method = RequestMethod.GET)
    @ResponseBody
    public void getFileSrc(@PathVariable("resId")Integer resId, HttpServletRequest request , HttpServletResponse response) throws IOException {
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

    /**
     * 获取弹幕列表
     * @param resId
     * @return
     * @throws Exception
     */
    @RequestMapping("getDanmuList")
    @ResponseBody
    public ResponseBo getDanmuList(Integer resId) throws Exception {
        PageHelper.startPage(1, 300);
        List<DanmuJson> danmuJsons = danmuService.selectDanmuJsonByResId(resId);
        List<DanmuJson> danmus = danmuService.sortListByTime(danmuJsons);
        return ResponseBo.ok().put("danmus", danmus);
    }

    /**
     * 发送弹幕
     * @param resId
     * @param danmu
     * @return
     * @throws Exception
     */
    @RequestMapping("putDanmu/{resId}")
    @ResponseBody
    public ResponseBo putDanmu(@PathVariable("resId")Integer resId, String danmu) throws Exception {
        DanmuJson danmuJson = JsonUtils.json2obj(danmu, DanmuJson.class);
        danmuJson.setDmResource(resId);
        danmuService.insertDanmuFromJson(danmuJson);
        return ResponseBo.ok();
    }

    @RequestMapping("likeRes/{resId}")
    @ResponseBody
    public ResponseBo likeResource(@PathVariable("resId")Integer resId){
        Alluser alluser = getUser();
        if(alluser == null) return ResponseBo.error("未登录");
        //取出视频记录
        Resource resource = resourceService.selectById(resId);
        if(resource == null) return ResponseBo.error("视频错误");
        //插入点赞记录
        Likeres likeres = new Likeres();
        likeres.setLikrRes(resId);
        likeres.setLikrUser(alluser.getAllId());
        likeresService.insert(likeres);
        //更新视频点赞人数
        int count = likeresService.getLikedNumByRes(resId);
        resource.setResLikenum(count);
        resourceService.updateById(resource);

        return ResponseBo.ok();
    }

    @RequestMapping("collectRes/{resId}")
    @ResponseBody
    public ResponseBo collectResource(@PathVariable("resId")Integer resId){
        Alluser alluser = getUser();
        if(alluser == null) return ResponseBo.error("未登录");

        Users users = usersService.selectById(alluser.getAllId());
        if(users == null) return ResponseBo.error("您不是普通用户不能收藏");

        //取出视频记录
        Resource resource = resourceService.selectById(resId);
        if(resource == null) return ResponseBo.error("视频错误");

        //插入收藏记录
        Collectres collectres = new Collectres();
        collectres.setColrRes(resId);
        collectres.setColrTime(new Date());
        collectres.setColrUsers(alluser.getAllId());
        collectresService.insert(collectres);

        return ResponseBo.ok();
    }

}
