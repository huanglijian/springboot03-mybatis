package com.neu.service.impl;

import com.neu.bean.Publish;
import com.neu.mapper.PublishMapper;
import com.neu.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;

public class PublishServiceImpl implements PublishService {
    @Autowired
    private PublishMapper m;

    @Override
    public int insert(Publish p) {
        int i = m.insert(p);
        return i;
    }

    @Override
    public int addPub_watchNum(int pub_id) {

        return 0;
    }
}
