package cn.ck.service;

import cn.ck.entity.Likeres;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
public interface LikeresService extends IService<Likeres> {

    /**
     * 根据视频id，获取点赞人数
     * @param resId
     * @return
     */
    int getLikedNumByRes(Integer resId);

    /**
     * 用户是否已经点赞该视频
     * @param userId 用户id
     * @param resId 视频id
     * @return
     */
    boolean isLiked(String userId, Integer resId) throws Exception;
}
