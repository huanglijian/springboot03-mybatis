package cn.ck.controller;

import cn.ck.entity.Alluser;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractController {

    /**
     * 获取登录的用户实例
     * @return 登录用户实例
     */
    protected Alluser getUser() {
        return (Alluser) SecurityUtils.getSubject().getPrincipal();
    }

//    protected String getUserEmail() {
//        return getUser().getAllEmail();
//    }
//
//    protected String getUserUUID() {
//        return getUser().getAllId();
//    }
}
