package cn.ck.service.impl;

import cn.ck.entity.Resource;
import cn.ck.entity.bean.ResCol;
import cn.ck.mapper.ResourceMapper;
import cn.ck.service.ResourceService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
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
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @Override
    public Page<Resource> getMostLikeResPage(Page<Resource> page) {
        return page.setRecords(baseMapper.getMostLike(page));
    }

    @Override
    public Page<Resource> getSuggestPage(Page<Resource> page, String keyword) {
        return  this.selectPage(page,
                new EntityWrapper<Resource>()
                        .like("res_name", keyword)
                        .or().like("res_intro", keyword)
                        .orderBy("res_uploadtime"));
    }

    @Override
    public List<ResCol> selectDesc(String id) {
        return baseMapper.selectDesc(id);
    }
}
