package cn.ck.service.impl;

import cn.ck.entity.Original;
import cn.ck.entity.bean.OriCol;
import cn.ck.entity.bean.OriColUser;

import cn.ck.entity.bean.OriUser;
import cn.ck.mapper.OriginalMapper;
import cn.ck.service.OriginalService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
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
 * @since 2018-09-21
 */
@Service
public class OriginalServiceImpl extends ServiceImpl<OriginalMapper, Original> implements OriginalService {

    @Autowired
    OriginalMapper originalMapper;

    @Override
    public List<OriColUser> selectDesc(String id) {
        return originalMapper.selectDesc(id);
    }

    @Override
    public OriUser selectOriUser(int id) {
        return baseMapper.selectOriUser(id);
    }

    @Override
    public List<OriUser> selectAllOri(String type,String tag,int id) {
        return baseMapper.selectAllOri(type,tag,id);
    }

    @Override
    public List<OriUser> selectOther(int id, String tag) {
        return baseMapper.selectOther(id,tag);
    }

    @Override
    public List<Original> selectHeigestGradeOrigin() {
        List<Original> list = baseMapper.selectList(new EntityWrapper<Original>().orderBy("orig_grade", false));
        return list;
    }

    @Override
    public List<OriUser> selectSearch(String key) {
        List<OriUser> list =originalMapper.selectSearch(key);
        return list;
    }
}

