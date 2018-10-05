package cn.ck.controller.loginRegisted;

import cn.ck.controller.AbstractController;
import cn.ck.entity.*;
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
    private ProjectService projectService;
    @Autowired
    private ResourceService resourceService;

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

        String imgpath = "";
        if(user.getAllType().equals("发布者")){
            Promulgator promulgator = promulgatorService.selectById(loginUser.getAllId());
            imgpath = promulgator.getPromImg();
        }
        else{
            Users users = usersService.selectById(loginUser.getAllId());
            imgpath = users.getUserImg();
        }
        return ResponseBo.ok().put("loginUser", user).put("img", imgpath);
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
     * 按照标签取出数据
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
}
