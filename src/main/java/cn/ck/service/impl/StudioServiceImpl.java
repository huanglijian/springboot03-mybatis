package cn.ck.service.impl;

import cn.ck.entity.Studio;
import cn.ck.mapper.StudioMapper;
import cn.ck.service.StudioService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    public String insertStudio(Studio studio) {

        baseMapper.insert(studio);
        return  null;

    }

    public Studio selectByzzid(String zzid){
        Studio stu = studioMapper.selectByzzid(zzid);
        return stu;
    }

    @Override
    public List<Studio> selectSuperStudio(Integer projNum) {
        List<Studio> list = baseMapper.selectSuperStudio(projNum);
        return list;
    }

    @Override
    public List<Studio> selectForHall(String type, String local, String studioName,String sort) {
        return studioMapper.selectForHall(type,local,studioName,sort);
    }
}
