package cn.ck.service;

import cn.ck.entity.Danmu;
import cn.ck.entity.bean.DanmuJson;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
public interface DanmuService extends IService<Danmu> {

    /**
     * 根据视频主键查询弹幕
     * @param resId
     * @return
     */
    List<DanmuJson> selectDanmuJsonByResId(Integer resId);

    /**
     * 根据弹幕在视频中出现的时间排序
     * @param danmuJsonList
     * @return
     */
    List<DanmuJson> sortListByTime(List<DanmuJson> danmuJsonList);

    /**
     * 将danmuJson转成danmu插入数据库
     * @param danmuJson
     */
    void insertDanmuFromJson(DanmuJson danmuJson);

    /**
     * 检查实体合法性
     * @param danmuJson
     * @return
     */
    boolean cheackDanmuJson(DanmuJson danmuJson);
}
