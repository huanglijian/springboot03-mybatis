package cn.ck.service;

import cn.ck.entity.Resource;
import cn.ck.entity.bean.ResCol;
import cn.ck.entity.bean.ResColNum;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

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
     * 获取最多人点赞的视频集合
     * 点赞数降序
     * @return
     */
    List<Resource> getMostLikeResPage();

    /**
     * 分页查询最后发布的视频
     * @return
     */
    List<Resource> getLatestResPage();

    /**
     * keyword 模糊查询视频名字，视频介绍
     * 上传时间降序
     * @param keyword
     * @return
     */
    List<Resource> getSuggestPage(String keyword);

    List<ResColNum> getResColNum();

    /**
     * 根据标签查询资源
     * @param tag 标签
     * @return
     */
    List<Resource> getResByTag(String tag);

    List<ResCol> selectDesc(String id);
}
