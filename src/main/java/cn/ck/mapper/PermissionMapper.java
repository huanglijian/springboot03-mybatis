package cn.ck.mapper;

import cn.ck.entity.Permission;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-09-19
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> findByUserEmail(@Param("email") String email);
}
