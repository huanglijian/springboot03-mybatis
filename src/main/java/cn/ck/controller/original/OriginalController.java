package cn.ck.controller.original;

import cn.ck.entity.*;

import cn.ck.entity.bean.OriCol;
import cn.ck.entity.bean.OriColUser;
import cn.ck.entity.bean.OriUser;
import cn.ck.service.*;
import cn.ck.utils.ResponseBo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import cn.ck.controller.AbstractController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/original")
public class OriginalController extends AbstractController{
        @Autowired
        UsersService usersService;
        @Autowired
        OriginalService originalService;
        @Autowired
        CollectoriService collectoriService;
        @Autowired
        AlluserService alluserService;
        @Autowired
        GradeoriService gradeoriService;

        int start=1;//记录当前页 默认为“1“
        int size = 4; //记录每页条目数，默认为”10“


        @RequestMapping("/all")
        public String all(){
                return "original/original";
        }


       @RequestMapping("/details/{id}")
        public String details(@PathVariable("id")Integer oriId){
                return "original/ori_details";
        }

        @GetMapping("/search")
        public String search(){
            return "origial/ori_search_result";
        }

        //改变页数（start）
        @RequestMapping("/allpage")
        public String allpage(
                @RequestParam(value = "start", defaultValue = "1")  int start){
              this.start = start;

                return "original/original";
        }

        @RequestMapping("/all1")
        @ResponseBody
        public PageInfo<OriUser> index(){
                PageHelper.startPage(start, size);
                List<OriUser> originals = originalService.selectAllOri();
            /*for (OriUser oriUser:originals) {
                    String[] strings=oriUser.getOrigTag().split(",");
                System.out.println(strings.length);
            }*/


                PageInfo<OriUser> page = new PageInfo<>(originals);



            return page;
        }


        @RequestMapping("getDetails")
        @ResponseBody
        public ResponseBo getdetails(Integer origId) throws Exception{
                if(origId == null) return ResponseBo.error("无法找到此项目");

                boolean isLogined = false, isCollected = false, isScored = false;
                if(getUser() != null){
                        isLogined = true;
                        isCollected = collectoriService.isCollected(getUser().getAllId(), origId);
                        isScored = gradeoriService.isScored(getUser().getAllId(), origId);
                }
            String[] tags=originalService.selectOriUser(origId).getOrigTag().split(",");

            List<OriUser> originals=new ArrayList<>();
            for(int i=0;i<tags.length;i++) {

                originals.addAll(originalService.selectOther(origId,tags[i]));
                System.out.println(originals);
            }
            if(originals.size()>=4)
                originals=originals.subList(0,3);
        else if(originals.size()==0)
            originals=originalService.selectAllOri().subList(0,3);



                return ResponseBo.ok()
                        .put("curOri", originalService.selectOriUser(origId))
                        .put("isLogined", isLogined)
                        .put("isCollected", isCollected)
                        .put("other",originals)
                        .put("isScored", isScored);


        }

    @RequestMapping("insertColOri")
    @ResponseBody
    public ResponseBo insertColOri(String oriId) throws Exception{

            Collectori collectori=new Collectori();
            collectori.setColoTime(new Date());
            collectori.setColoOgi(Integer.parseInt(oriId));
            collectori.setColoUsers(getUser().getAllId());
            if(collectoriService.insert(collectori))
                return ResponseBo.ok();
            else
                return ResponseBo.error("收藏失败");
    }

/*    @RequestMapping("insertColOri")
    @ResponseBody
    public Boolean insertColOri(String oriId,String isCol) throws Exception{
        Boolean isCol1;
        if(isCol.equals("true"))
            isCol1=true;
        else
            isCol1=false;
        if(!isCol1) {
            Collectori collectori = new Collectori();
            collectori.setColoTime(new Date());
            collectori.setColoOgi(Integer.parseInt(oriId));
            collectori.setColoUsers(getUser().getAllId());
            if (collectoriService.insert(collectori)) {
                isCol1=true;
                return isCol1;
            }
            else{
                return isCol1;
            }
        }else{
            if(collectoriService.delete(new EntityWrapper<Collectori>().eq("colo_users",getUser().getAllId()).eq("colo_ogi",Integer.parseInt(oriId))))
            {
                isCol1=false;
                return isCol1;
            }
            else
            {
                return isCol1;
            }
        }
    }*/

    @RequestMapping("deleteColOri")
    @ResponseBody
    public ResponseBo deleteColOri(String oriId) throws Exception{
        if(collectoriService.delete(new EntityWrapper<Collectori>().eq("colo_users",getUser().getAllId()).eq("colo_ogi",Integer.parseInt(oriId))))
           return ResponseBo.ok();
        else
            return ResponseBo.error("取消收藏失败");
    }
    /*@RequestMapping("download")
    @ResponseBody
    public ResponseBo download(String orid){
        EntityWrapper<Original> entityWrapper = new EntityWrapper<Original>().eq
        return collectoriService.selectById()
    }*/

    /*@RequestMapping("getOther")
    @ResponseBody
    public ResponseBo getOther(String origType,String origTag){
        List<Original> list = originalService.selectList(new EntityWrapper<Original>().eq("orig_type"))
    }*/

/*    @RequestMapping("ScoreOri/{origId}")
        @ResponseBody
        public ResponseBo scoreOriginal(@PathVariable("origId")Integer origId){
                Alluser alluser = getUser();
                if(alluser == null) return ResponseBo.error("未登录");
                //取出视频记录
                Original original = originalService.selectById(origId);
                if(original == null) return ResponseBo.error("找不到项目");
                //插入点赞记录
                 Gradeori score = new Gradeori();
                score.setGraoOri(origId);
                score.setGraoUser(alluser.getAllId());
                gradeoriService.insert(score);
           //更新视频点赞人数

        return ResponseBo.ok("评分成功");
        }

    @RequestMapping("tuijie")
    @ResponseBody
    public List<Original> tuijie(String origTag){
        String[] tags=origTag.split(",");
        List<Original> originals=new ArrayList<>();
        for(int i=0;i<tags.length;i++) {
            originals.addAll(originalService.selectList(new EntityWrapper<Original>().like("orig_name",tags[i])));
        }
        originals=originals.subList(2,3);
        return originals;
    }*/


}
