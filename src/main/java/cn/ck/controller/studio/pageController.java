package cn.ck.controller.studio;

import cn.ck.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author 马圳彬
 * @version 1.0
 * @description 创客
 * @date 2018/9/29 14:13
 **/
@Controller
@RequestMapping("/studioPage")
public class pageController extends AbstractController {



    /*跳转竞标详情界面*/
    @RequestMapping("/bpManager")
    public String bpmanager(@RequestParam("bidId") String bidId, Model model){
        model.addAttribute("id",bidId);
        return "/studio/studio_bpManager";
    }

    @RequestMapping("/create")
    public String create() {
        return "studio/studio_creat";
    }

    @RequestMapping("/add")
    public String add() {
        return "studio/studio_add";
    }

    @RequestMapping("/index")
    public String index() {
        return "studio/studio_index";
    }

    @RequestMapping("/bidding")
    public String bidding() {
        return "studio/studio_bidding";
    }

    @RequestMapping("/service")
    public String service() {
        return "studio/studio_service";
    }

    @RequestMapping("/biddingAll")
    public String biddingAll() {
        return "studio/studio_biddingAll";
    }

    @RequestMapping("/funds")
    public String funds() {
        return "studio/studio_funds";
    }

    @RequestMapping("/jobInfo")
    public String jobInfo() {
        return "studio/studio_jobInfo";
    }

    @RequestMapping("/jobReview")
    public String jobReview() {
        return "studio/studio_jobReview";
    }
    @RequestMapping("/memberInfo")
    public String memberInfo() {
        return "studio/studio_memberInfo";
    }

    @RequestMapping("/memberReview")
    public String memberReview() {
        return "studio/studio_memberReview";
    }

    @RequestMapping("/infoUpdate")
    public String infoUpdate( )
    {
        return "studio/studio_infoUpdate";
    }

    @RequestMapping("/headUpdate")
    public String headUpdate() {
        return "studio/studio_headUpdate";
    }

}
