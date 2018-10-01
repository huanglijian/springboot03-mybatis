package cn.ck.service.impl;

import cn.ck.entity.Collectres;
import cn.ck.mapper.CollectresMapper;
import cn.ck.service.CollectresService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
public class CollectresServiceImpl extends ServiceImpl<CollectresMapper, Collectres> implements CollectresService {

    @Override
    public int getCollectedNumByRes(Integer resId) {
        if(resId == null) return 0;
        return baseMapper.selectCount(new EntityWrapper<Collectres>().eq("colr_res", resId));
    }

    @Override
    public boolean isCollected(String userId, Integer resId) throws Exception {
        if(userId == null || userId.equals("") || resId == null)
            throw new Exception("参数错误");
        return baseMapper.selectList(new EntityWrapper<Collectres>()
                .eq("colr_res", resId)
                .eq("colr_users", userId)).size() > 0;
    }
}
