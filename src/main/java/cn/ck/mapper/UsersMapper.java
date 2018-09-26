package cn.ck.mapper;

import cn.ck.entity.Users;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
public interface UsersMapper extends BaseMapper<Users> {
      public void updateStuid(String stuId,String userId);
}
