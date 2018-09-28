package cn.ck.mapper;

import cn.ck.entity.Alluser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
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
public interface AlluserMapper extends BaseMapper<Alluser> {

    /**
     * 根据用户注册的邮箱查找
     * @param email
     * @return
     */
    Alluser findByEmail(@Param("email") String email);

    /**
     * 分页查询
     * @param page 自己构造的分页器
     * @return
     */
    List<Alluser> selectUserByPage(Pagination page);
}
