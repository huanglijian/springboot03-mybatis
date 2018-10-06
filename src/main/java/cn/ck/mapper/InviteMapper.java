package cn.ck.mapper;

import cn.ck.entity.Invite;
import cn.ck.entity.bean.InvitProjStu;
import cn.ck.entity.bean.ProjectBid;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-09-28
 */
public interface InviteMapper extends BaseMapper<Invite> {
    //根据发布者id找所有的信息
    List<InvitProjStu> invitalllist(String id);
            ;
}
