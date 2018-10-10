package cn.ck.service.impl;

import cn.ck.entity.Collectori;
import cn.ck.mapper.CollectoriMapper;
import cn.ck.service.CollectoriService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
@Service
public class CollectoriServiceImpl extends ServiceImpl<CollectoriMapper, Collectori> implements CollectoriService {
    @Override
    public boolean isCollected(String userId, Integer origId) throws Exception {
        if(userId == null || userId.equals("") || origId == null)
            throw new Exception("参数错误");
        return baseMapper.selectList(new EntityWrapper<Collectori>()
                .eq("colo_ogi", origId)
                .eq("colo_users", userId)).size() > 0;
    }
}
