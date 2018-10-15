package cn.ck.mapper;

import cn.ck.entity.Resource;
import cn.ck.entity.bean.ResCol;
import cn.ck.entity.bean.ResColNum;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 获取最多人点赞的视频集合
     * @return
     */
    List<Resource> getMostLike();

    /**
     * 取出视频信息和收藏人数
     * @return
     */
    List<ResColNum> getResColNum();

    //关联resource表跟collectres表
    List<ResCol> selectDesc(String id);
}
