package cn.ck.controller;

import cn.ck.entity.Studio;
import cn.ck.service.StudioService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/honor")
public class HonorController {
    @Autowired
    StudioService studioService;

    @RequestMapping("honorPage")
    public String honorPage(){
        return "honor/honor";
        }

    @RequestMapping("getStudios")
    @ResponseBody
    public List<Studio> getStudios() {
        PageHelper.startPage(0,10);
        List<Studio> studios = studioService.selectList(new EntityWrapper<Studio>().orderBy("stu_grade desc"));
        return studios;
    }
}