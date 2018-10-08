package cn.ck.service.impl;

import cn.ck.entity.Bidding;
import cn.ck.entity.bean.ProjectBid;
import cn.ck.mapper.BiddingMapper;
import cn.ck.service.BiddingService;
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
public class BiddingServiceImpl extends ServiceImpl<BiddingMapper, Bidding> implements BiddingService {

    @Override
    public List<ProjectBid> selectRecommendProj() {
        return baseMapper.selectRecommendProj();
    }

    @Override
    public List<ProjectBid> selectSuggestProj(String keyword) {
        keyword = "%" + keyword +"%";
        return baseMapper.selectSuggestProj(keyword);
    }
}
