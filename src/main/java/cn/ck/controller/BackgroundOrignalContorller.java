package cn.ck.controller;

import cn.ck.entity.Original;
import cn.ck.service.OriginalService;
import cn.ck.service.ResourceService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 此控制器
 * 用于测试页面
 */


@Controller
@RequestMapping("/backorignal")
public class BackgroundOrignalContorller {


    @Autowired
    ResourceService resourceService ;
    @Autowired
    OriginalService originalService;



    int start=1;//记录当前页 默认为“1“
    int size = 4; //记录每页条目数，默认为”10“
    //不支持多条件查询
    String conditions = "0"; //条件
    String values     = "0";  //值


    @RequestMapping("/mzb")
    public String mzbtest(){
        return "project/Backstage-Article.html";
    }

    @RequestMapping("/deleteOne")
    public  String mzbtest2( @RequestParam(value = "id",defaultValue = "0") int id) {
//        删除 一个
        // 获取id
       if(id==0)
           System.out.println("没有id参数 删除不能");
        else {
           boolean result = originalService.deleteById(id);
           System.out.println("in---deleteOne");
           System.out.println("操作 is" + result);
       }
        return "project/Backstage-Article.html";
    }


    @RequestMapping("/deleteMore")
    public  String mzbtest3( @RequestParam(value = "ids",defaultValue = "0") String ids) {
//        多选 删除 一个
        // 获取id
        int int_id ;
        boolean success;
        if(ids.equals("0"))
            System.out.println("没有ids参数 多选删除不能");
        else {
            //分割字符串
            String[] idcanshu   = ids.split(",");
            for(String id : idcanshu) {
                if (id.equals("")){
                    System.out.println("exist 空格");
                }
                else
                {
                    int_id = Integer.parseInt(id);
                    success= originalService.deleteById(int_id);
                    System.out.println(id + "is" +success);
                    System.out.println("删除");
                }
                System.out.println(id);
            }

        }
        return "project/Backstage-Article.html";
    }

    @RequestMapping("/searchCondition")
        public String mzbtest14(
        @RequestParam(value = "start", defaultValue = "1")  int start,
        @RequestParam(value = "conditions", defaultValue = "0") String conditions,
        @RequestParam(value = "values", defaultValue = "0") String values){

            this.start=start;
        if(!(conditions.equals("0")))
        {
            this.conditions = conditions;
        }
            if(!(values.equals("0"))){
            this.values = values;
        }

            return "project/Backstage-Article.html";
        }

    @RequestMapping("/searchConditionClear")
    public String mzbtest5(){
        //条件清零方法
        this.start = 1;
        this.conditions = "0";
        this.values ="0";
         return "project/Backstage-Article.html";
       }

    @RequestMapping("/backAcritcalpage")
    @ResponseBody
    public  PageInfo<Original> GetMeg1() {
//        查询所有Original的记录

        System.out.println("in---backAcritcalpage");
        PageHelper.startPage(this.start,this.size);
        //条件列出
        System.out.println("条件:"+this.conditions);
        System.out.println("值:"+this.values);
        EntityWrapper<Original>  wrappers = new EntityWrapper<Original>();
        if(!this.conditions.equals("0")) {
            //如果condition不是数据的字段---报错
            if(conditions.equals("orig_id"))
                wrappers.eq(conditions, values);
            else
                wrappers.like(conditions, values);
        }


        List<Original> original = originalService.selectList(wrappers);
        PageInfo<Original> page = new PageInfo<>(original);

        return page;
    }



}