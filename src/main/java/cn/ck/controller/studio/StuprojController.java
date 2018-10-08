package cn.ck.controller.studio;

import cn.ck.controller.AbstractController;
import cn.ck.entity.Bidding;
import cn.ck.entity.Project;
import cn.ck.entity.Users;
import cn.ck.entity.bean.ProjectBid;
import cn.ck.service.BiddingService;
import cn.ck.service.ProjectService;
import cn.ck.service.StudioService;
import cn.ck.service.UsersService;
import cn.ck.utils.DateUtils;
import cn.ck.utils.ResponseBo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

/**
 * @author 马圳彬
 * @version 1.0
 * @description 创客
 * @date 2018/10/4 7:29
 **/
@Controller
@RequestMapping("/stuproject")
public class StuprojController extends AbstractController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private BiddingService biddingService;
    @Autowired
    private StudioService studioService;
    @Autowired
    private UsersService usersService;



    /*竞标项目*/
    @GetMapping("/projectbid")
    @ResponseBody
    public ResponseBo bidproject(){
        String userId = getUser().getAllId();
        Users user = usersService.selectOne(new EntityWrapper<Users>().eq("user_id",userId)) ;
        String stuId = user.getUserStudio();
        List<Bidding> bidList = biddingService.selectList(new EntityWrapper<Bidding>().eq("bid_studio",stuId).eq("bid_state","竞标超时").or("bid_state='竞标中'").or("bid_state='竞标中止'"));
        List<ProjectBid> projbidList = new ArrayList<ProjectBid>();
        System.out.println(bidList);
        for(Bidding bidding:bidList){
            int projId = bidding.getBidProj();
            Project project = projectService.selectOne(new EntityWrapper<Project>().eq("proj_id",projId));
            ProjectBid projectBid = new ProjectBid();
            projectBid.setProject(project);
            projectBid.setBidding(bidding);

            //转换时间格式
            String creatdate= DateUtils.format(project.getProjCreattime(),DateUtils.DATE_PATTERN);
            projectBid.setCreatdate(creatdate);
            //竞标剩余时间
            projectBid.setBidday(10-projectService.projBidTimeNum(project.getProjId()));
            projbidList.add(projectBid);
        }
        return ResponseBo.ok().put("projectbid",projbidList);
    }

    /* 服务订单 */
    @GetMapping("/service")
    @ResponseBody
    public ResponseBo service(){
        //工作室ID
        String userId = getUser().getAllId();
        Users user = usersService.selectOne(new EntityWrapper<Users>().eq("user_id",userId)) ;
        String stuId = user.getUserStudio();
        /*进行中的项目*/
        Set<String> set = new HashSet<>();
        set.add("bid_starttime");
        List<Bidding> bidList = biddingService.selectList(new EntityWrapper<Bidding>().eq("bid_studio",stuId).eq("bid_state","开发中").or("bid_state='发布者中止'").or("bid_state='承接方中止'").orderDesc(set));
        List<ProjectBid> projbidList = new ArrayList<ProjectBid>();

        for(Bidding bidding:bidList){
            ProjectBid projBid = new ProjectBid();
             /*开发剩余时间 */
            int projId = bidding.getBidProj();
            Project project = projectService.selectOne(new EntityWrapper<Project>().eq("proj_id",projId));
            int developday = project.getProjCycletime() - projectService.projDevelopTimeNum(project.getProjId());
            projBid.setDevelopday(developday);
            projBid.setBidding(bidding);
            projBid.setProject(project);
            projbidList.add(projBid);
        }

        /*已结束*/
        Set<String> set1 = new HashSet<>();
        set.add("bid_starttime");
        List<Bidding> bidList1 = biddingService.selectList(new EntityWrapper<Bidding>().eq("bid_studio",stuId).eq("bid_state","开发完成").or("bid_state='项目中止'").orderDesc(set1));
        List<ProjectBid> projbidList1 = new ArrayList<ProjectBid>();
        for(Bidding bidding:bidList1){
            int projId = bidding.getBidProj();
            Project project = projectService.selectOne(new EntityWrapper<Project>().eq("proj_id",projId));
            ProjectBid projBid = new ProjectBid();
            projBid.setBidding(bidding);
            projBid.setProject(project);
            projbidList1.add(projBid);
        }

        return ResponseBo.ok().put("projectrun",projbidList).put("projectend",projbidList1);
    }

    /*竞标一览*/
    @GetMapping("/biddingAll")
    public ResponseBo biddingAll(){
        //工作室ID
        String userId = getUser().getAllId();
        Users user = usersService.selectOne(new EntityWrapper<Users>().eq("user_id",userId));
        String stuId = user.getUserStudio();

        List<Project> projList = projectService.selectList(new EntityWrapper<Project>().eq("proj_studio",stuId));

        return null;
    }
}
