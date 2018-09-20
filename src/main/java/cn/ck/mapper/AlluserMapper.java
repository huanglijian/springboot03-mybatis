package cn.ck.mapper;

import cn.ck.entity.Alluser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-09-19
 */
public interface AlluserMapper extends BaseMapper<Alluser> {

    Alluser findByEmail(@Param("email") String email);

}
