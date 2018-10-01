package cn.ck.controller.promcenter;

import cn.ck.entity.Account;
import cn.ck.entity.Alluser;
import cn.ck.entity.Funds;
import cn.ck.service.AccountService;
import cn.ck.service.FundsService;
import cn.ck.utils.ResponseBo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/promcenter")
public class MoneypromController {
    @Autowired
    AccountService accountService;
    @Autowired
    FundsService fundsService;

    @RequestMapping("/priceprom")
    @ResponseBody
    public ResponseBo priceprom(){
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        Account account=accountService.selectOne(new EntityWrapper<Account>().eq("acc_foreid",user.getAllId()));
        List<Funds> fundsList=fundsService.selectList(new EntityWrapper<Funds>().eq("fund_income",user.getAllId()));


        return ResponseBo.ok().put("account",account).put("price",fundsList);
    }

}
