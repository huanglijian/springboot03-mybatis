package com.neu.service.impl;

import com.neu.bean.Enroll;
import com.neu.mapper.EnrollMapper;
import com.neu.service.EnrollService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class EnrollServiceImpl implements EnrollService {

    @Autowired
    private EnrollMapper m;

    @Override
    public int insert(Enroll e) {
        int i = m.insert(e);
        return i;
    }


    @Override
    public List<Enroll> selectByEnr_publish(int id) {
        List<Enroll> enrolls = m.selectByEnr_publish(id);
        for (Enroll e:enrolls) {
            System.out.println("+++"+e);
        }
        return enrolls;
    }

    @Override
    public List<Enroll> selectByUser_id(String id) {
        return null;
    }
}
