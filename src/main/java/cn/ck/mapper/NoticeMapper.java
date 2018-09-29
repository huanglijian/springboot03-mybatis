package cn.ck.mapper;

import cn.ck.entity.Notice;
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
public interface NoticeMapper extends BaseMapper<Notice> {

    //按时间倒序获取通知消息
    List<Notice> selectNoti(String id);
}
