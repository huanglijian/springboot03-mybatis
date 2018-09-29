package cn.ck.service.impl;

import cn.ck.entity.Notice;
import cn.ck.mapper.NoticeMapper;
import cn.ck.service.NoticeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
    @Autowired
    NoticeMapper noticeMapper;

    public List<Notice> selectNoti(String id){
        List<Notice> notices=noticeMapper.selectNoti(id);
        return notices;
    }
}
