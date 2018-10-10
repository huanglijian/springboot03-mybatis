package cn.ck.mapper;

import cn.ck.entity.Original;
import cn.ck.entity.bean.OriColUser;
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
public interface OriginalMapper extends BaseMapper<Original> {
    List<OriColUser> selectDesc(String id);

    List<OriColUser> selectAll();
}
