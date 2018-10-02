package cn.ck.service.impl;

import cn.ck.entity.Danmu;
import cn.ck.entity.bean.DanmuJson;
import cn.ck.mapper.DanmuMapper;
import cn.ck.service.DanmuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
@Service
public class DanmuServiceImpl extends ServiceImpl<DanmuMapper, Danmu> implements DanmuService {

    @Override
    public List<DanmuJson> selectDanmuJsonByResId(Integer resId) {

        return baseMapper.selectDanmuJsonByResId(resId);
    }

    @Override
    public List<DanmuJson> sortListByTime(List<DanmuJson> danmuJsonList) {
        Ordering<DanmuJson> ordering = new Ordering<DanmuJson>() {
            public int compare(DanmuJson left, DanmuJson right) {
                return Ints.compare(left.getTime(), right.getTime());
            }
        };
        return ordering.sortedCopy(danmuJsonList);
    }

    @Override
    public void insertDanmuFromJson(DanmuJson danmuJson) {
        if (danmuJson == null || !cheackDanmuJson(danmuJson)) return;

        Danmu danmu = new Danmu();

        danmu.setDmText(danmuJson.getText());
        danmu.setDmTime(danmuJson.getTime());
        danmu.setDmColor(danmuJson.getColor());
        danmu.setDmPosition(danmuJson.getPosition());
        danmu.setDmResource(danmuJson.getDmResource());

        danmu.setDmSendtime(new Date());

        baseMapper.insert(danmu);
    }

    @Override
    public boolean cheackDanmuJson(DanmuJson danmuJson) {
        if(danmuJson.getTime() == null)
            return false;
        if(danmuJson.getColor() == null)
            return false;
        if(danmuJson.getDmResource() == null)
            return false;
        if(danmuJson.getText() == null)
            return false;
        if(danmuJson.getPosition() == null)
            return false;

        return true;
    }
}
