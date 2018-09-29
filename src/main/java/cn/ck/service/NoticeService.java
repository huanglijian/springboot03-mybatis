package cn.ck.service;

import cn.ck.entity.Notice;
import com.baomidou.mybatisplus.service.IService;
import org.aspectj.weaver.ast.Not;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
public interface NoticeService extends IService<Notice> {
    List<Notice> selectNoti(String id);
}
