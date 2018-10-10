package cn.ck.service.impl;

import cn.ck.entity.Gradeori;
import cn.ck.mapper.GradeoriMapper;
import cn.ck.service.GradeoriService;
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
public class GradeoriServiceImpl extends ServiceImpl<GradeoriMapper, Gradeori> implements GradeoriService {
    @Override
    public boolean isScored(String userId, Integer origId) throws Exception {
        if(userId == null || userId.equals("") || origId == null)
            throw new Exception("参数错误");
        return baseMapper.selectList(new EntityWrapper<Gradeori>()
                .eq("grao_ori", origId)
                .eq("grao_user", userId)).size() > 0;
    }
}
