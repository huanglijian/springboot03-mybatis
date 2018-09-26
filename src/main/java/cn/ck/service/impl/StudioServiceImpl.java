package cn.ck.service.impl;

import cn.ck.entity.Studio;
import cn.ck.entity.Users;
import cn.ck.mapper.StudioMapper;
import cn.ck.mapper.UsersMapper;
import cn.ck.service.StudioService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
@Service
public class StudioServiceImpl extends ServiceImpl<StudioMapper, Studio> implements StudioService {
    @Autowired
    StudioMapper studioMapper;



    @Override
    public String insertStudio(Studio studio,String yhid) {

        studio.setStuCreatid(yhid);
        studio.setStuImg("E:/ck/studio/touxiang/1.png");
        studio.setStuCreattime(new Date());
        studioMapper.insert(studio);
        return  studio.getStuId();

    }
}
