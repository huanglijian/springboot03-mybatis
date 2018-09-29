package cn.ck.controller.studio;

import cn.ck.controller.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 马圳彬
 * @version 1.0
 * @description 创客
 * @date 2018/9/29 14:13
 **/
@Controller
@RequestMapping("/studioPage")
public class pageController extends AbstractController {

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

}
