package com.neu.controller;

import com.neu.bean.Student;
import com.neu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author lxf
 * @version 1.0
 * @description springboot03-mybatis
 * @date 2018/8/29
 **/
@Controller
public class StudentController {
    @Autowired
    private StudentService service;

    @RequestMapping("/listStu")
    public String listAllStu(Model model) {
        List<Student> stus = service.findStuAll();
        model.addAttribute("stus", stus);

        System.out.println("input");
//        return "/input.jsp";
        return "/listStu.jsp";
    }
}
