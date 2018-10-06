package cn.ck.mapper;

import cn.ck.entity.Bidding;
import cn.ck.entity.bean.ProjectBid;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
public interface BiddingMapper extends BaseMapper<Bidding> {

    /**
     * 取出推荐的项目
     * 字段有proj_id, proj_name, proj_money, bidnum
     * @return
     */
    List<ProjectBid> selectRecommendProj();

}
