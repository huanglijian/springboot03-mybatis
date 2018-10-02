package cn.ck.mapper;

import cn.ck.entity.Danmu;
import cn.ck.entity.bean.DanmuJson;
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
public interface DanmuMapper extends BaseMapper<Danmu> {

    List<DanmuJson> selectDanmuJsonByResId(@Param("resId")Integer resId);
}
