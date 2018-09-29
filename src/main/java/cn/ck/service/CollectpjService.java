package cn.ck.service;

import cn.ck.entity.Collectpj;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-26
 */
public interface CollectpjService extends IService<Collectpj> {
    List<Collectpj> selectColpj(String id);
}
