package cn.ck.controller.promcenter;

import cn.ck.entity.Account;
import cn.ck.entity.Alluser;
import cn.ck.entity.Funds;
import cn.ck.entity.Promulgator;
import cn.ck.service.AccountService;
import cn.ck.service.FundsService;
import cn.ck.service.PromulgatorService;
import cn.ck.utils.AlipayConfig;
import cn.ck.utils.ResponseBo;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/promcenter")
public class MoneypromController {

    final static Logger log = LoggerFactory.getLogger(MoneypromController.class);
    @Autowired
    AccountService accountService;
    @Autowired
    FundsService fundsService;
    @Autowired
    PromulgatorService promulgatorService;

    /**
     * 我的钱包界面信息渲染
     * @return
     */
    @RequestMapping("/priceprom")
    @ResponseBody
    public ResponseBo priceprom(){
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        Account account=accountService.selectOne(new EntityWrapper<Account>().eq("acc_foreid",user.getAllId()));
        Set<String> set = new HashSet<>();
        set.add("fund_datetime");
        List<Funds> fundsList=fundsService.selectList(new EntityWrapper<Funds>().eq("fund_income",user.getAllId()).orderDesc(set));


        return ResponseBo.ok().put("account",account).put("price",fundsList);
    }

    /**
     * 充值提现界面渲染、校验提现金额和支付密码的合法性
     * @return
     */
    @RequestMapping("/prompayin")
    @ResponseBody
    public ResponseBo prompayin(){
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        Promulgator promulgator=promulgatorService.selectID(user.getAllId());
        Account account=accountService.selectOne(new EntityWrapper<Account>().eq("acc_foreid",user.getAllId()));
        String prom=promulgator.getPromName();
        double acc=account.getAccMoney();
        String paypwd=promulgator.getPromPaypwd();
        return ResponseBo.ok().put("name",prom).put("money",acc).put("pwd",paypwd);
    }


    /**
     *充值操作
     * @Description: 前往支付宝第三方网关进行支付
     */
    @RequestMapping("/goAlipay")
    @ResponseBody
    public String goAlipay(String paynumber, HttpServletRequest request, HttpServletRequest response) throws Exception {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String uuid=UUID.randomUUID().toString();
        String out_trade_no = uuid.replace("-", "");
        //付款金额，必填
        String total_amount = paynumber;
        //订单名称，必填
        String subject = "充值";
        //商品描述，可空
        String body = "用户账户充值操作";

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "1c";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();

        return result;
    }

    /**
     *充值操作
     * @Title: AlipayController.java
     * @Package com.sihai.controller
     * @Description: 支付宝同步通知页面
     */
    @GetMapping("/alipayReturnNotice")
    public ModelAndView alipayReturnNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
        ModelAndView mv = new ModelAndView("/promulgator/prom_payinresult");
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
            Promulgator promulgator=promulgatorService.selectID(user.getAllId());
            Account account=accountService.selectOne(new EntityWrapper<Account>().eq("acc_foreid",user.getAllId()));
            String promname=promulgator.getPromName();
            double d1=Double.valueOf(total_amount);
            BigDecimal bg = new BigDecimal(d1);
            double acc = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            double accall=acc+account.getAccMoney();

            account.setAccMoney(accall);
            accountService.updateAllColumnById(account);
            Funds funds=new Funds();
            funds.setFundId(out_trade_no);
            funds.setFundDatetime(new Date());
            funds.setFundType("充值");
            funds.setFundIncome(user.getAllId());
            funds.setFundOutlay("支付宝");
            funds.setFundMoney(acc);
            fundsService.insert(funds);
            System.out.println(funds);

            mv.addObject("account", acc);
            mv.addObject("name",promname);
            mv.addObject("accountall",accall);

        }else {

        }
        return mv;
    }

    /**
     *充值操作
     * @Title: AlipayController.java
     * @Package com.sihai.controller
     * @Description: 支付宝异步 通知页面
     */
    @PostMapping("/alipayNotifyNotice")
    public String alipayNotifyNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {
//        System.out.println("9999999");
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
            if(trade_status.equals("TRADE_FINISHED")){
//                System.out.println("6666666");
            }else if (trade_status.equals("TRADE_SUCCESS")){


            }
//            System.out.println("111111");
            return "success";
        }else {//验证失败
//            System.out.println("123456");
            return "fail";
        }
    }

    /**
     * 支付宝提现操作
     * @return
     */
    @RequestMapping("/alipayout")
    public ModelAndView alipayout(HttpServletRequest req, HttpServletRequest res)throws Exception{
        //商户转账唯一订单号
        String uuid=UUID.randomUUID().toString();
        String out_biz_no = uuid.replace("-", "");
        //收款方账户类型
        String payee_type="ALIPAY_LOGONID";
        //收款方账户
        Alluser user = (Alluser) SecurityUtils.getSubject().getPrincipal();
        Promulgator promulgator=promulgatorService.selectID(user.getAllId());
        String promname=promulgator.getPromName();
        String payee_account=promulgator.getPromAbipay();
        //转账金额
        String amount=req.getParameter("payoutnumber");

        ModelAndView mv = new ModelAndView("/promulgator/prom_payoutresult");

        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl,AlipayConfig.app_id,AlipayConfig.merchant_private_key,"json",AlipayConfig.charset,AlipayConfig.alipay_public_key,AlipayConfig.sign_type);
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        request.setBizContent("{" +
                "    \"out_biz_no\":\""+out_biz_no+"\"," +
                "    \"payee_type\":\"ALIPAY_LOGONID\"," +
                "    \"payee_account\":\""+payee_account+"\"," +
                "    \"amount\":\""+amount+"\"," +
                "  }");
        AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
//            System.out.println(amount);
            Account account=accountService.selectOne(new EntityWrapper<Account>().eq("acc_foreid",user.getAllId()));
            double d1=Double.valueOf(amount);
            BigDecimal bg = new BigDecimal(d1);
            double acc = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            double accall=account.getAccMoney()-acc;
            account.setAccMoney(accall);

            accountService.updateAllColumnById(account);
            Funds funds=new Funds();
            funds.setFundId(out_biz_no);
            funds.setFundDatetime(new Date());
            funds.setFundType("提现");
            funds.setFundIncome(user.getAllId());
            funds.setFundOutlay("支付宝");
            funds.setFundMoney(-acc);
            fundsService.insert(funds);
            System.out.println(funds);

            mv.addObject("account", acc);
            mv.addObject("name",promname);
            mv.addObject("accountall",accall);
        } else {
            System.out.println("error");
        }
        return mv;
    }


}
