package cn.ck.mapper;

import cn.ck.entity.Resource;
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

    List<Resource> getMostLike(Pagination page);
}
