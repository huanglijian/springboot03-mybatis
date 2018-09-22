package cn.ck.service.impl;

import cn.ck.entity.Promulgator;
import cn.ck.mapper.PromulgatorMapper;
import cn.ck.service.PromulgatorService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PromulgatorServiceImpl extends ServiceImpl<PromulgatorMapper, Promulgator> implements PromulgatorService {

    @Autowired
    private PromulgatorMapper promMapper;

    @Override
    public Promulgator selectID(String id) {
        Promulgator promulgator=new Promulgator();
        promulgator=promMapper.selectID(id);
        return promulgator;
    }
}
