package cn.ck.controller.admin;

import cn.ck.controller.AbstractController;
import cn.ck.entity.*;
import cn.ck.entity.bean.ResColNum;
import cn.ck.service.*;
import cn.ck.shiro.AuthorityManager;
import cn.ck.utils.EnityUtils;
import cn.ck.utils.ResponseBo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiresRoles("admin")
@RequestMapping("admin")
public class AdminAuthorityController extends AbstractController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AlluserService alluserService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private PromulgatorService promulgatorService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthorityManager authorityManager;
    @Autowired
    private StudioService studioService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private CollectresService collectresService;

    /**
     * 管理员信息页面
     * @return
     */
    @RequestMapping("adminInfo")
    public String adminInfo(){
        return "admin/admin_adminInfo";
    }

    /**
     * 管理员信息列表
     * @return
     */
    @RequestMapping("adminList")
    @ResponseBody
    public List<Map<String, Object>> getAdminMap(){
        List<Alluser> allusers = alluserService.selectList(new EntityWrapper<>());
        List<Admin> admins = adminService.selectList(new EntityWrapper<>());

        List<Map<String, Object>> list = new ArrayList<>();

        for(Admin u : admins){
            for(Alluser alluser : allusers){
                if(u.getAdminId().equals(alluser.getAllId())){
                    Map<String, Object> mid = new HashMap<>();

                    mid.putAll(EnityUtils.entityToMap(alluser));
                    mid.putAll(EnityUtils.entityToMap(u));

                    list.add(mid);
                    break;
                }
            }
        }
        return list;
    }

    @RequestMapping("addAdmin")
    @ResponseBody
    public ResponseBo addAdmin(String adminName, String adminPhone, String allEmail, String allPwd){
        Alluser alluser = new Alluser();

        //密码加密处理
        alluser.setAllPwd(allPwd);
        alluserService.encodePwd(alluser);
        //其他信息填充
        alluser.setAllEmail(allEmail);
        alluser.setAllType("管理员");
        alluser.setAllState("1");
        alluserService.insert(alluser);

        Admin admin = new Admin();
        admin.setAdminName(adminName);
        admin.setAdminPhone(adminPhone);
        admin.setAdminId(alluser.getAllId());
        admin.setAdminImg("default.jpg");
        adminService.insert(admin);

        //add role
        try {
            authorityManager.addRoleToUser(alluser.getAllType(), alluser.getAllId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseBo.ok();
    }


    /**
     * 用户信息页面
     * @return
     */
    @RequestMapping("usersInfo")
    public String userInfo(){
        return "admin/userInfo";
    }

    /**
     * 用户信息列表
     * @return
     */
    @RequestMapping("userList")
    @ResponseBody
    public List<Map<String, Object>> getUsersMap(){
        List<Alluser> allusers = alluserService.selectList(new EntityWrapper<>());
        List<Users> users = usersService.selectList(new EntityWrapper<>());

        List<Map<String, Object>> list = new ArrayList<>();

        for(Users u : users){
            for(Alluser alluser : allusers){
                if(u.getUserId().equals(alluser.getAllId())){

                    Map<String, Object> mid = new HashMap<>();

                    mid.putAll(EnityUtils.entityToMap(alluser));
                    mid.putAll(EnityUtils.entityToMap(u));

                    list.add(mid);
                    break;
                }
            }
        }

        return list;
    }

    /**
     * 发布者信息页面
     * @return
     */
    @RequestMapping("promulInfo")
    public String promulInfo(){
        return "admin/adminPromul";
    }

    /**
     * 发布者信息列表
     * @return
     */
    @RequestMapping("promulList")
    @ResponseBody
    public List<Map<String, Object>> getpromulMap(){
        List<Alluser> allusers = alluserService.selectList(new EntityWrapper<>());
        List<Promulgator> promulgators = promulgatorService.selectList(new EntityWrapper<>());

        List<Map<String, Object>> list = new ArrayList<>();

        for(Promulgator u : promulgators){
            for(Alluser alluser : allusers){
                if(u.getPromId().equals(alluser.getAllId())){

                    Map<String, Object> mid = new HashMap<>();

                    mid.putAll(EnityUtils.entityToMap(alluser));
                    mid.putAll(EnityUtils.entityToMap(u));

                    list.add(mid);
                    break;
                }
            }
        }

        return list;
    }

    /**
     * 工作室信息页面
     * @return
     */
    @RequestMapping("studioInfo")
    public String studioInfo(){
        return "admin/studioInfo";
    }

    /**
     * 工作室信息列表
     * @return
     */
    @RequestMapping("studioList")
    @ResponseBody
    public List<Map<String, Object>> getstudioMap(){
        List<Studio> studios = studioService.selectList(new EntityWrapper<>());

        List<Map<String, Object>> list = new ArrayList<>();

        for(Studio s : studios){
            Users u = usersService.selectById(s.getStuCreatid());
            Map<String, Object> mid = new HashMap<>();

            mid.putAll(EnityUtils.entityToMap(s));
            mid.putAll(EnityUtils.entityToMap(u));

            list.add(mid);
        }

        return list;
    }


    /***********************************************视频相关************************************/

    @RequestMapping("resourceInfo")
    public String resourceInfo(){
        return "admin/admin_resource";
    }

    @RequestMapping("resourceList")
    @ResponseBody
    public List<Map<String, Object>> getresourceMap(){
        List<Resource> enitylist = resourceService.selectList(new EntityWrapper<>());

        List<Map<String, Object>> list = new ArrayList<>();

        for(Resource o : enitylist){

            Map<String, Object> mid = new HashMap<>();

            mid.putAll(EnityUtils.entityToMap(o));
            mid.put("collectNum", collectresService.getCollectedNumByRes(o.getResId()));

            list.add(mid);
        }

        return list;
    }

    /**
     * 更新视频信息
     * @param resId 主码
     * @param intro 简介
     * @param resName 名字
     * @param resPath 路径
     * @return
     */
    @RequestMapping("updateRes")
    @ResponseBody
    public ResponseBo updateRes(Integer resId, String intro, String resName, String resPath){

        Resource res = new Resource();
        res.setResIntro(intro);
        res.setResName(resName);
        res.setResPath(resPath);

        resourceService.update(res, new EntityWrapper<Resource>().eq("res_id", resId));
        return ResponseBo.ok();
    }

    /**
     * 删除视频
     * @param resId
     * @return
     */
    @RequestMapping("deleteRes")
    @ResponseBody
    public ResponseBo deleteRes(Integer resId){
        resourceService.deleteById(resId);
        return ResponseBo.ok();
    }

    /**
     * 点赞数排行图
     * @return
     */
    @RequestMapping("getResLikeDataEchart")
    @ResponseBody
    public ResponseBo getResLikeEchart(){
        List<ResColNum> list = resourceService.getResColNum();

        //key：标签 ， value ： 点赞数
        Map<String, Integer> tagMap = new HashMap<>();
        int likenum;

        for(ResColNum obj : list){
            //记录当前视频点赞的人数
            likenum = obj.getResLikenum();

            //可以替换大部分空白字符， 不限于空格
            String tagStr = CharMatcher.WHITESPACE.or(CharMatcher.INVISIBLE).removeFrom(obj.getResTag());
            //按半角','号分割
            List<String> tags = Lists.newArrayList(
                    Splitter.on(',')
                            .trimResults()
                            .omitEmptyStrings()
                            .split(tagStr)
            );
            //计算每个tag点赞数
            for(String t : tags){
                tagMap.put(t, (likenum + tagMap.getOrDefault(t, 0)));
            }
        }

        Map<String, Integer> result = tagMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        //categories : 标签， data ： 标签点赞数
        return ResponseBo.ok().put("categories", result.keySet()).put("data", result.values());
    }

    /**
     * 收藏数排行图
     * @return
     */
    @RequestMapping("getResCollectDataEchart")
    @ResponseBody
    public ResponseBo getResCollectEchart(){
        List<ResColNum> list = resourceService.getResColNum();

        //key：标签 ， value ： 收藏数
        Map<String, Integer> tagMap = new HashMap<>();
        int num;

        for(ResColNum obj : list){
            //记录当前视频收藏的人数
            num = obj.getResColnum() == null ? 0 : obj.getResColnum();

            //可以替换大部分空白字符， 不限于空格
            String tagStr = CharMatcher.WHITESPACE.or(CharMatcher.INVISIBLE).removeFrom(obj.getResTag());
            //按半角','号分割
            List<String> tags = Lists.newArrayList(
                    Splitter.on(',')
                            .trimResults()
                            .omitEmptyStrings()
                            .split(tagStr)
            );
            //计算每个tag点赞数
            for(String t : tags){
                tagMap.put(t, (num + tagMap.getOrDefault(t, 0)));
            }
        }

        Map<String, Integer> result = tagMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));


        //categories : 标签， data ： 标签收藏数
        return ResponseBo.ok().put("categories", result.keySet()).put("data", result.values());
    }
}
