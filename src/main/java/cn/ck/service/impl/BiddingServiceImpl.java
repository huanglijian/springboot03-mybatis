package cn.ck.service.impl;

import cn.ck.entity.Bidding;
import cn.ck.entity.bean.ProjectBid;
import cn.ck.mapper.BiddingMapper;
import cn.ck.service.BiddingService;
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
public class BiddingServiceImpl extends ServiceImpl<BiddingMapper, Bidding> implements BiddingService {

    @Autowired
    BiddingMapper biddingMapper;

    public List<Bidding> selectbidding1(String stuId){
        return biddingMapper.selectbidding1(stuId);
    }

    public List<Bidding> selectbidding2(String stuId){
        return biddingMapper.selectbidding2(stuId);
    }

    public List<Bidding> selectbidding3(String stuId){
        return biddingMapper.selectbidding3(stuId);
    }

    public List<Bidding> selectbidding4(String stuId){
        return biddingMapper.selectbidding4(stuId);
    }

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
