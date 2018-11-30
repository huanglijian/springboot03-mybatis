package com.neu.service;

import com.neu.bean.Enroll;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface EnrollService {
    //报名活动
    public int insert(Enroll e);

    //根据活动id查找所有报名信息
    public List<Enroll> selectByEnr_publish(int id);

    //查找某个用户报过名的活动
    public List<Enroll> selectByUser_id(String id);


}
