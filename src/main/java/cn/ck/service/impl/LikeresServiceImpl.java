package cn.ck.service.impl;

import cn.ck.entity.Likeres;
import cn.ck.mapper.LikeresMapper;
import cn.ck.service.LikeresService;
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
public class LikeresServiceImpl extends ServiceImpl<LikeresMapper, Likeres> implements LikeresService {

    @Override
    public int getLikedNumByRes(Integer resId) {
        if(resId == null) return 0;
        return baseMapper.selectCount(new EntityWrapper<Likeres>().eq("likr_res", resId));
    }

    @Override
    public boolean isLiked(String userId, Integer resId) throws Exception {
        if(userId == null || userId.equals("") || resId == null)
            throw new Exception("参数错误");
        return baseMapper.selectList(new EntityWrapper<Likeres>()
                .eq("likr_res", resId)
                .eq("likr_user", userId)).size() > 0;
    }
}
