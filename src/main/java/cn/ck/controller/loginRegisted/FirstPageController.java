package cn.ck.controller.loginRegisted;

import cn.ck.controller.AbstractController;
import cn.ck.entity.*;
import cn.ck.entity.bean.ProjectBid;
import cn.ck.service.*;
import cn.ck.utils.ResponseBo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 主页控制器
 */
@Controller
@RequestMapping("/home")
public class FirstPageController extends AbstractController {

    @Autowired
    private AlluserService alluserService;
    @Autowired
    private PromulgatorService promulgatorService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private  StudioService studioService;
    @Autowired
    private  BiddingService biddingService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private JobsService jobsService;
    @Autowired
    private OriginalService originalService;

    /**
     * 将当前登录用户的信息输出到页面
     * @return
     */
    @RequestMapping("getLoginUser")
    @ResponseBody
    public ResponseBo getLoginUser(){
        Alluser loginUser = getUser();
        if(loginUser == null)
            return ResponseBo.error(404, "没有登录");

        Alluser user = new Alluser();
        user.setAllId(loginUser.getAllId());
        user.setAllEmail(loginUser.getAllEmail());
        user.setAllType(loginUser.getAllType());

        String imgpath, centerUrl;;
        boolean isPromul = false;
        if(user.getAllType().equals("发布者")){
            Promulgator promulgator = promulgatorService.selectById(loginUser.getAllId());
            //发布者的头像路径
            imgpath = promulgator.getPromImg();
            if(imgpath != null && !imgpath.equals(""))
                imgpath = "/promcenter/previewsrc/" + imgpath;
            //是否是发布者
            isPromul = true;
            //个人中心链接
            centerUrl = "/pcjump/account";
        }
        else{
            Users users = usersService.selectById(loginUser.getAllId());
            //user的头像路径
            imgpath = users.getUserImg();
            if(imgpath != null && !imgpath.equals(""))
                imgpath = "/file/showImg/" + imgpath;
            //是否是发布者
            isPromul = false;
            //个人中心链接
            centerUrl = "/user/userInfo";
        }

        return ResponseBo.ok().put("loginUser", user)
                        .put("img", imgpath)
                        .put("isPromul", isPromul)
                        .put("centerUrl", centerUrl);
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
        List<ProjectBid> suggest = biddingService.selectSuggestProj(keyword);
        PageInfo pageInfo = new PageInfo<ProjectBid>(suggest);

        return  ResponseBo.ok().put("suggestProj", pageInfo.getList());
    }

    /**
     * 搜索结果页面
     * @param keyword
     * @return
     */
    @RequestMapping("searchResult")
    public String getSearchResult(@RequestParam("key")String keyword, @RequestParam("page")Integer page){
        return "home_search_results";
    }

    /**
     * 搜索结果
     * @param keyword 搜索的关键字
     * @param curPage 页数
     * @return
     */
    @RequestMapping("doSearch")
    @ResponseBody
    public ResponseBo doSearch(@RequestParam("keyword")String keyword, @RequestParam("curPage")Integer curPage){
        //MP方式分页
//        Page<Resource> page = resourceService.getSuggestPage(new Page<Resource>(curPage, 2), keyword);

        //PageHelper方式分页
        PageHelper.startPage(curPage, 8);
        List<ProjectBid> list = biddingService.selectSuggestProj(keyword);
        PageInfo<ProjectBid> pageInfo = new PageInfo<>(list);

        return ResponseBo.ok().put("result", pageInfo);
    }

    /**
     * 取出推荐的项目
     * 分成4块
     * @return
     */
    @RequestMapping("getRecommendProj")
    @ResponseBody
    public ResponseBo getRecommendProj(){
        PageHelper.startPage(0, 40);
        PageInfo<ProjectBid> page = new PageInfo<ProjectBid>(biddingService.selectRecommendProj());
        List<ProjectBid> list = page.getList();
        int size = list.size();
        int pageSize = 10;
        List<ProjectBid> left = null, front = null, right = null, out = null;
        if(size <= pageSize)
            front = list;
        else if(size <= pageSize * 2){
            front = list.subList(0, pageSize-1);
            left = list.subList(pageSize, size-1);
        }
        else if(size <= pageSize * 3){
            front = list.subList(0, pageSize-1);
            left = list.subList(pageSize, pageSize*2-1);
            right = list.subList(pageSize * 2, size-1);
        }
        else if(size <= pageSize * 4){
            front = list.subList(0, pageSize-1);
            left = list.subList(pageSize, pageSize * 2 -1);
            right = list.subList(pageSize * 2, pageSize*3-1);
            out = list.subList(pageSize *3, size-1);
        }
        return ResponseBo.ok()
                .put("left", left)
                .put("right", right)
                .put("front", front)
                .put("out", out);
    }

    /**
     * 取出优秀的工作室
     * @return
     */
    @RequestMapping("getSuperStudio")
    @ResponseBody
    public ResponseBo getSuperStudio(){
        PageHelper.startPage(0, 8);
        List<Studio> studios = studioService.selectSuperStudio(2);
        PageInfo<Studio> pageInfo = new PageInfo<>(studios);
        return ResponseBo.ok().put("studios", pageInfo.getList());
    }

    /**
     * 按照标签取出视频数据
     * @param tag
     * @return
     */
    @RequestMapping("tagResource/{tag}")
    @ResponseBody
    public ResponseBo getResByTag(@PathVariable("tag")String tag){
        PageHelper.startPage(0, 8);
        List<Resource> resources = resourceService.getResByTag(tag);
        PageInfo<Resource> resourcePageInfo = new PageInfo<>(resources);

        return ResponseBo.ok().put("res", resourcePageInfo.getList());
    }

    /**
     * 取出最晚发布的职位
     * @return
     */
    @RequestMapping("getJobs")
    @ResponseBody
    public ResponseBo getJobs(){
        PageHelper.startPage(0, 10);
        List<Jobs> list = jobsService.selectLatestJobs();
        return ResponseBo.ok().put("jobs", list);
    }

    /**
     * 最高分的原创
     * @return
     */
    @RequestMapping("getOrigins")
    @ResponseBody
    public ResponseBo getOrigins(){
        PageHelper.startPage(0, 10);
        List<Original> list = originalService.selectList(new EntityWrapper<Original>().orderBy("orig_grade", false));
        return ResponseBo.ok().put("origins", list);
    }
}
