package com.neu.service.impl;

import com.neu.bean.Student;
import com.neu.mapper.StudentMapper;
import com.neu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lxf
 * @version 1.0
 * @description springboot03-mybatis
 * @date 2018/8/29
 **/
@Service("stuService")
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper mapper;

    @Override
    public List<Student> findStuAll() {
        List<Student> stus = mapper.selectStuAll();
        return stus;
    }
}
