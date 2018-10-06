package cn.ck.service;

import cn.ck.entity.Invite;
import cn.ck.entity.bean.InvitProjStu;
import cn.ck.entity.bean.ProjectBid;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-28
 */
public interface InviteService extends IService<Invite> {
    //根据发布者id找所有的信息
    List<InvitProjStu> invitalllist(String id);
}
