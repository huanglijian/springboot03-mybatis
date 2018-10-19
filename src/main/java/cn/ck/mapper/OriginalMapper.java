package cn.ck.mapper;

import cn.ck.entity.Original;
import cn.ck.entity.bean.OriColUser;
import cn.ck.entity.bean.OriUser;
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
public interface OriginalMapper extends BaseMapper<Original> {
    List<OriColUser> selectDesc(String id);

    List<OriUser> selectAllOri(@Param("type")String type,@Param("tag")String tag,@Param("id")int id);

    OriUser selectOriUser(@Param("id") int id);

    List<OriUser> selectOther(@Param("id") int id,@Param("tag") String tag);

    List<OriUser> selectSearch(@Param("key") String key);
}
