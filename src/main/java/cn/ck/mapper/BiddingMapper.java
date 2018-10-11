package cn.ck.mapper;

import cn.ck.entity.Bidding;
import cn.ck.entity.bean.ProjectBid;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 搜索建议
     * 字段有proj_id, proj_name, proj_money, proj_intro, bidnum
     * @return
     */
    List<ProjectBid> selectSuggestProj(@Param("keyword") String keyword);

    public List<Bidding> selectbidding1(String stuId);
    public List<Bidding> selectbidding2(String stuId);
    public List<Bidding> selectbidding3(String stuId);
    public List<Bidding> selectbidding4(String stuId);
}
