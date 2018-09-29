package cn.ck.service;

import cn.ck.entity.Resource;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
public interface ResourceService extends IService<Resource> {

    /**
     * 分页查询点赞数最多的视频
     * @param page 分页器
     * @return
     */
    Page<Resource> getMostLikeResPage(Page<Resource> page);

    /**
     * 获取搜索建议
     * @param page 分页器
     * @param keyword 搜索关键字
     * @return
     */
    Page<Resource> getSuggestPage(Page<Resource> page, String keyword);
}
