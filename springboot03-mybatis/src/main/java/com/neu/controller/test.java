package com.neu.controller;

import com.neu.bean.Student;
import com.neu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.List;

public class test {
    @Autowired
    private StudentService service;

    public void listAllStu() {
        List<Student> stus = null;
        stus = service.findStuAll();
        System.out.println(stus);
    }


}
