package cn.ck.mapper;

import cn.ck.entity.Studio;
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
public interface StudioMapper extends BaseMapper<Studio> {
        public Studio selectByzzid(String zzid);

        List<Studio> selectSuperStudio(@Param("projNum") Integer projNum);

        List<Studio> selectForHall(@Param("type") String type, @Param("local") String local, @Param("studioName") String studioName,@Param("sort") String sort);
}
