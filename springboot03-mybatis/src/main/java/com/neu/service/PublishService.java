package com.neu.service;

import com.neu.bean.Publish;

public interface PublishService {

    //发布活动
    public int insert(Publish p);

    //增加发布活动信息观看量
    public int addPub_watchNum(int pub_id);


}
