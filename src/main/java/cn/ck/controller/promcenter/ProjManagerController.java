package cn.ck.controller.promcenter;

import cn.ck.entity.*;
import cn.ck.service.*;
import cn.ck.utils.NoticeInsert;
import cn.ck.utils.ResponseBo;
import cn.hutool.http.HttpRequest;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/promcenter")
public class ProjManagerController {
    @Autowired
    ProjectService projectService;
    @Autowired
    NoticeService noticeService;
    @Autowired
    BiddingService biddingService;
    @Autowired
    StudioService studioService;
    @Autowired
    PromulgatorService promulgatorService;
    @Autowired
    AccountService accountService;
    @Autowired
    FundsService fundsService;

    /**
     * 发布者主动发起项目暂停
     * @param id
     * @return
     */
    @PostMapping("/prompauseproj/{id}")
    @ResponseBody
    public ResponseBo prompauseproj(@PathVariable("id") String id){
        Project project=projectService.selectById(id);
       if(project.getProjState().equals("开发中")){
           project.setProjState("发布者中止");
           projectService.updateAllColumnById(project);
           //发送通知信息
           String nowdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
           String stumessage="您好，您正在开发的项目"+project.getProjName()+"已在"+nowdate+"被甲方发起项目中止,如需查看详细信息，请查看项目管理相关功能";
           String prommessage="您好，您正在开发的项目"+project.getProjName()+"已在"+nowdate+"发起项目中止,请等待乙方的确认，如需查看详细信息，请查看项目管理相关功能";
           Notice notice1= NoticeInsert.insertNotice(stumessage,project.getProjStudio());
           noticeService.insertAllColumn(notice1);
           Notice notice2= NoticeInsert.insertNotice(prommessage,project.getProjProm());
           noticeService.insertAllColumn(notice2);

           return ResponseBo.ok();
       }else{
           return ResponseBo.ok().put("code","1");
       }
    }

    /**
     * 发布者确认承接方发起的项目暂停
     * @param id
     * @return
     */
    @PostMapping("/promaffirm/{id}")
    @ResponseBody
    public ResponseBo promaffirm(@PathVariable("id") String id){
        Project project=projectService.selectById(id);
        if(project.getProjState().equals("承接方中止")){
            project.setProjState("项目中止");
            projectService.updateAllColumnById(project);
            //发送通知信息
            String nowdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String stumessage="您好，您发起中止的项目"+project.getProjName()+"已在"+nowdate+"被甲方确认中止,如需查看详细信息，请查看项目管理相关功能";
            String prommessage="您好，您已确认乙方发起的项目中止操作，"+project.getProjName()+"已在"+nowdate+"中止,如需查看详细信息，请查看项目管理相关功能";
            Notice notice1= NoticeInsert.insertNotice(stumessage,project.getProjStudio());
            noticeService.insertAllColumn(notice1);
            Notice notice2= NoticeInsert.insertNotice(prommessage,project.getProjProm());
            noticeService.insertAllColumn(notice2);

            //资金操作
            double money=Double.valueOf(project.getProjMoney())*0.1-100;
            Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
            Account account=accountService.selectOne(new EntityWrapper<Account>().eq("acc_foreid",user.getAllId()));
            account.setAccMoney(account.getAccMoney()+money);
            accountService.updateAllColumnById(account);
            Account adminacc=accountService.selectOne(new EntityWrapper<Account>().eq("acc_foreid","7f628d5d-2265-49d4-b12d-d65b8f280901"));
            adminacc.setAccMoney(adminacc.getAccMoney()-money);
            accountService.updateAllColumnById(adminacc);

            //资金表操作
            Funds funds=new Funds();
            String uuid1= UUID.randomUUID().toString();
            String fundsid = uuid1.replace("-", "");
            funds.setFundId(fundsid);
            funds.setFundMoney(money);
            funds.setFundType("项目退款");
            funds.setFundIncome(user.getAllId());
            funds.setFundOutlay("平台");
            funds.setFundDatetime(new Date());
            fundsService.insert(funds);
            Funds fund1=new Funds();
            String uuid2= UUID.randomUUID().toString();
            String fund1id = uuid2.replace("-", "");
            fund1.setFundId(fund1id);
            fund1.setFundMoney(-money);
            fund1.setFundType("项目退款");
            fund1.setFundIncome("平台");
            fund1.setFundOutlay(user.getAllId());
            fund1.setFundDatetime(new Date());
            fundsService.insert(fund1);

            return ResponseBo.ok();
        }else{
            return ResponseBo.ok().put("code","1");
        }
    }

    /**
     * 发布者取消中止项目
     * @param id
     * @return
     */
    @PostMapping("/promcancel/{id}")
    @ResponseBody
    public ResponseBo promcancel(@PathVariable("id") String id){
        Project project=projectService.selectById(id);
        if(project.getProjState().equals("发布者中止")){
            project.setProjState("开发中");
            projectService.updateAllColumnById(project);
            //发送通知信息
            String nowdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String stumessage="您好，甲方发起的中止项目操作已在"+nowdate+"被甲方取消中止,项目"+project.getProjName()+"现可继续开发，如需查看详细信息，请查看项目管理相关功能";
            String prommessage="您好，您已取消项目中止操作，"+project.getProjName()+"已在"+nowdate+"取消中止,现可继续开发，如需查看详细信息，请查看项目管理相关功能";
            Notice notice1= NoticeInsert.insertNotice(stumessage,project.getProjStudio());
            noticeService.insertAllColumn(notice1);
            Notice notice2= NoticeInsert.insertNotice(prommessage,project.getProjProm());
            noticeService.insertAllColumn(notice2);

            return ResponseBo.ok();
        }else{
            return ResponseBo.ok().put("code","1");
        }
    }

    /**
     * 发布者选定承接方前信息渲染
     * @param id
     * @return
     */
    @PostMapping("/promselectbid/{id}")
    @ResponseBody
    public ResponseBo promselectbid(@PathVariable("id") String id){
        Bidding bidding=biddingService.selectById(id);
        Project project=projectService.selectById(bidding.getBidProj());
        Studio studio=studioService.selectById(bidding.getBidStudio());
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        Account account=accountService.selectOne(new EntityWrapper<Account>().eq("acc_foreid",user.getAllId()));
        //判断余额是否能够支付押金
        double bidacc=Double.valueOf(bidding.getBidMoney())*0.1;
        double promacc=account.getAccMoney();
        int code=0;
        if(promacc>=bidacc)
            code=1;
        else
            code=0;
        return ResponseBo.ok().put("bid",bidding).put("bidacc",bidacc).put("project",project.getProjName()).put("studio",studio.getStuName()).put("account",account.getAccMoney()).put("code",code);
    }

    /**
     * 发布者选定承接方操作
     * @return
     */
    @PostMapping("/promselbidfinish/{id}/{paypwd}")
    @ResponseBody
    public ResponseBo promselbidfinish(@PathVariable("id") String id,@PathVariable("paypwd") String paypwd, HttpServletRequest request){
//        String paypwd=request.getParameter("paypwd");
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        Promulgator promulgator=promulgatorService.selectById(user.getAllId());
        if(paypwd.equals(promulgator.getPromPaypwd())){
            //更改竞标信息
            Bidding bidding=biddingService.selectById(id);
            List<Bidding> biddingList=biddingService.selectList(new EntityWrapper<Bidding>().eq("bid_proj",bidding.getBidProj()));
            for (Bidding bidding1:biddingList) {
                bidding1.setBidState("竞标结束");
            }
            biddingService.updateAllColumnBatchById(biddingList);
            //更改项目信息
            Project project=projectService.selectById(bidding.getBidProj());
            project.setProjCycletime(Integer.valueOf(bidding.getBidCycle()));
            project.setProjMoney(bidding.getBidMoney());
            project.setProjStarttime(new Date());
            project.setProjStudio(bidding.getBidStudio());
            project.setProjState("开发中");
            projectService.updateAllColumnById(project);
            //更改资金信息
            Account account=accountService.selectOne(new EntityWrapper<Account>().eq("acc_foreid",user.getAllId()));
            double money=Double.valueOf(bidding.getBidMoney())*0.1;
            account.setAccMoney(account.getAccMoney()-money);
            accountService.updateAllColumnById(account);
            Account adminacc=accountService.selectOne(new EntityWrapper<Account>().eq("acc_foreid","7f628d5d-2265-49d4-b12d-d65b8f280901"));
            adminacc.setAccMoney(adminacc.getAccMoney()+money);
            accountService.updateAllColumnById(adminacc);
            //添加资金表信息
            Funds funds=new Funds();
            String uuid1= UUID.randomUUID().toString();
            String fundsid = uuid1.replace("-", "");
            funds.setFundId(fundsid);
            funds.setFundMoney(-money);
            funds.setFundType("项目押金");
            funds.setFundIncome(user.getAllId());
            funds.setFundOutlay("平台");
            funds.setFundDatetime(new Date());
            fundsService.insert(funds);
            Funds fund1=new Funds();
            String uuid2= UUID.randomUUID().toString();
            String fund1id = uuid2.replace("-", "");
            fund1.setFundId(fund1id);
            fund1.setFundMoney(money);
            fund1.setFundType("项目押金");
            fund1.setFundIncome("平台");
            fund1.setFundOutlay(user.getAllId());
            fund1.setFundDatetime(new Date());
            fundsService.insert(fund1);
            //通知信息
            String nowdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String prommessage="您好，您所发布的项目"+project.getProjName()+"已在"+nowdate+"选定承接方，已进入开发状态,如需查看详细信息，请查看项目相关功能";
            String stumessage="您好，您所竞标的项目"+project.getProjName()+"已在"+nowdate+"被选定为承接方，已进入开发状态,如需查看详细信息，请查看项目相关功能";
            Notice promnotice=NoticeInsert.insertNotice(prommessage,user.getAllId());
            Notice stunotice=NoticeInsert.insertNotice(stumessage,bidding.getBidStudio());
            noticeService.insert(promnotice);
            noticeService.insert(stunotice);

            return ResponseBo.ok().put("code",1).put("projid",project.getProjId());
        }else{
            Bidding bidding=biddingService.selectById(id);
            Project project=projectService.selectById(bidding.getBidProj());
            return ResponseBo.ok().put("code",0).put("projid",project.getProjId());
        }
    }

    /**
     * 确认完工前的提示框渲染
     * @param id
     * @return
     */
    @PostMapping("/promfinishproj/{id}")
    @ResponseBody
    public ResponseBo promfinishproj(@PathVariable("id") String id){
        Project project=projectService.selectById(id);
        Studio studio=studioService.selectById(project.getProjStudio());
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        Account account=accountService.selectOne(new EntityWrapper<Account>().eq("acc_foreid",user.getAllId()));
        double money=Double.valueOf(project.getProjMoney())*0.9+100;
        double acc=account.getAccMoney();
        int code=0;
        if(acc>=money)
            code=1;
        else
            code=0;
        return ResponseBo.ok().put("bidacc",money).put("project",project).put("studio",studio.getStuName()).put("account",account.getAccMoney()).put("code",code);

    }

    /**
     * 发布者选定承接方操作
     * @return
     */
    @PostMapping("/promfinproject/{id}/{paypwd}")
    @ResponseBody
    public ResponseBo promfinproject(@PathVariable("id") String id,@PathVariable("paypwd") String paypwd, HttpServletRequest request){
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        Promulgator promulgator=promulgatorService.selectById(user.getAllId());
        if(paypwd.equals(promulgator.getPromPaypwd())){
            //更改项目信息
            Project project=projectService.selectById(id);
            project.setProjEndtime(new Date());
            project.setProjState("开发完成");
            projectService.updateAllColumnById(project);

            //更改资金信息
            Studio studio=studioService.selectById(project.getProjStudio());
            double money=Double.valueOf(project.getProjMoney())*0.9+100;
            double usermoney=Double.valueOf(project.getProjMoney());
            //发布者资金
            Account account=accountService.selectOne(new EntityWrapper<Account>().eq("acc_foreid",user.getAllId()));
            account.setAccMoney(account.getAccMoney()-money);
            accountService.updateAllColumnById(account);
            //用户资金
            Account useracc=accountService.selectOne(new EntityWrapper<Account>().eq("acc_foreid",studio.getStuCreatid()));
            useracc.setAccMoney(useracc.getAccMoney()+usermoney);
            accountService.updateAllColumnById(useracc);
            //平台资金
            Account adminacc=accountService.selectOne(new EntityWrapper<Account>().eq("acc_foreid","7f628d5d-2265-49d4-b12d-d65b8f280901"));
            adminacc.setAccMoney(adminacc.getAccMoney()+money-usermoney);
            accountService.updateAllColumnById(adminacc);

            //添加资金表信息
            //发布者资金记录
            Funds funds=new Funds();
            String uuid1= UUID.randomUUID().toString();
            String promid = uuid1.replace("-", "");
            funds.setFundId(promid);
            funds.setFundMoney(-money);
            funds.setFundType("项目尾款");
            funds.setFundIncome(user.getAllId());
            funds.setFundOutlay("平台");
            funds.setFundDatetime(new Date());
            fundsService.insert(funds);
            //平台资金记录
            Funds fund1=new Funds();
            String uuid2= UUID.randomUUID().toString();
            String adminid = uuid2.replace("-", "");
            fund1.setFundId(adminid);
            fund1.setFundMoney(money);
            fund1.setFundType("项目尾款");
            fund1.setFundIncome("平台");
            fund1.setFundOutlay(user.getAllId());
            fund1.setFundDatetime(new Date());
            fundsService.insert(fund1);

            Funds fund2=new Funds();
            String uuid3= UUID.randomUUID().toString();
            String adminid1 = uuid3.replace("-", "");
            fund2.setFundId(adminid1);
            fund2.setFundMoney(-usermoney);
            fund2.setFundType("项目款项");
            fund2.setFundIncome("平台");
            fund2.setFundOutlay(studio.getStuCreatid());
            fund2.setFundDatetime(new Date());
            fundsService.insert(fund2);
            //用户资金记录
            Funds fund3=new Funds();
            String uuid4= UUID.randomUUID().toString();
            String userid = uuid4.replace("-", "");
            fund3.setFundId(userid);
            fund3.setFundMoney(usermoney);
            fund3.setFundType("项目款项");
            fund3.setFundIncome(studio.getStuCreatid());
            fund3.setFundOutlay("平台");
            fund3.setFundDatetime(new Date());
            fundsService.insert(fund3);

            //通知信息
            String nowdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String prommessage="您好，您正在开发的项目"+project.getProjName()+"已在"+nowdate+"确定完成,如需查看详细信息，请查看项目相关功能";
            String stumessage="您好，您正在开发的项目"+project.getProjName()+"已在"+nowdate+"被确定完成，相关资金已进入工作室创始人账户,如需查看详细信息，请查看项目相关功能";
            String usermessage="您好，您的工作室所开发的项目"+project.getProjName()+"已在"+nowdate+"被确定完成，相关资金已进入您的账户,如需查看详细信息，请查看项目相关功能";
            Notice promnotice=NoticeInsert.insertNotice(prommessage,user.getAllId());
            Notice stunotice=NoticeInsert.insertNotice(stumessage,project.getProjStudio());
            Notice usernotice=NoticeInsert.insertNotice(usermessage,studio.getStuCreatid());
            noticeService.insert(promnotice);
            noticeService.insert(stunotice);
            noticeService.insert(usernotice);

            return ResponseBo.ok().put("code",1).put("projid",project.getProjId());
        }else{

            return ResponseBo.ok().put("code",0);
        }
    }
}
