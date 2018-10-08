package cn.ck.service.impl;

import cn.ck.entity.Invite;
import cn.ck.entity.bean.InvitProjStu;
import cn.ck.entity.bean.ProjectBid;
import cn.ck.mapper.InviteMapper;
import cn.ck.service.InviteService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-28
 */
@Service
public class InviteServiceImpl extends ServiceImpl<InviteMapper, Invite> implements InviteService {
    @Autowired
    InviteMapper inviteMapper;

    @Override
    public List<InvitProjStu> invitalllist(String id) {
        List<InvitProjStu> projectBidList=inviteMapper.invitalllist(id);
        return projectBidList;
    }
}
