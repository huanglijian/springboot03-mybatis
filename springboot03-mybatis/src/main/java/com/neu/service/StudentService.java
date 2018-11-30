package com.neu.service;

import com.neu.bean.Student;

import java.util.List;

/**
 * @author lxf
 * @version 1.0
 * @description springboot03-mybatis
 * @date 2018/8/29
 **/
public interface StudentService {
    List<Student> findStuAll();
}
