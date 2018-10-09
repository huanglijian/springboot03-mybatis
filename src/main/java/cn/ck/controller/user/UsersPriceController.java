package cn.ck.controller.user;

import cn.ck.entity.*;
import cn.ck.service.AccountService;
import cn.ck.service.FundsService;
import cn.ck.service.NoticeService;
import cn.ck.service.UsersService;
import cn.ck.utils.ResponseBo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/userspri")
public class UsersPriceController {

    @Autowired
    AccountService accountService;
    @Autowired
    UsersService usersService;
    @Autowired
    FundsService fundsService;
    @Autowired
    NoticeService noticeService;


    /*跳转充值页面*/
    @RequestMapping("/pricein")
    public String priceinprom(){
        return "/users/pc_payin";
    }

    /*跳转提现页面*/
    @RequestMapping("/priceout")
    public String priceoutprom(){
        return "/users/pc_payout";
    }

    /**
     * 我的钱包界面信息渲染
     * @return
     */
    @RequestMapping("/priceuser/{num}")
    @ResponseBody
    public ResponseBo priceprom(@PathVariable("num") int num){
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        Account account=accountService.selectOne(new EntityWrapper<Account>().eq("acc_foreid",user.getAllId()));
        Set<String> set = new HashSet<>();
        set.add("fund_datetime");

        PageHelper.startPage(num, 8);
        List<Funds> fundsList=fundsService.selectList(new EntityWrapper<Funds>().eq("fund_income",user.getAllId()).orderDesc(set));
        PageInfo<Funds> fundsPageInfo=new PageInfo<>(fundsList);

        return ResponseBo.ok().put("account",account).put("price",fundsPageInfo);
    }


    /**
     * 充值提现界面渲染、校验提现金额和支付密码的合法性
     * @return
     */
    @RequestMapping("/userspayin")
    @ResponseBody
    public ResponseBo prompayin(){
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        Users users=usersService.selectById(user.getAllId());
        Account account=accountService.selectOne(new EntityWrapper<Account>().eq("acc_foreid",user.getAllId()));
        String prom=users.getUserName();
        double acc=account.getAccMoney();
        String paypwd=users.getUserPaypwd();
        return ResponseBo.ok().put("name",prom).put("money",acc).put("pwd",paypwd);
    }
}
