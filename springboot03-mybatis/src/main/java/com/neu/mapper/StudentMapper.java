package com.neu.mapper;

import com.neu.bean.Student;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * @author lxf
 * @version 1.0
 * @description mybatis01first
 * @date 2018/8/20
 **/
public interface StudentMapper {
    //添加
    int insertStu(Student stu);

    //修改
    int updateStu(Student stu);

    //删除
    int deleteStu(int id);

    //根据id查询
    Student selectStuById(int id);

    //查找所有
    List<Student> selectStuAll();

    //根据名字模糊查询
    List<Student> selectStuByName(String name);

}
