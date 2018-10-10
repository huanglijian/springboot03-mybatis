package cn.ck.service;

import cn.ck.entity.Studio;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
public interface StudioService extends IService<Studio> {

     public String insertStudio(Studio studio);

     public Studio selectByzzid(String zzid);

     /**
      * 查询出优秀工作室
      * stu_id,stu_creattime,stu_name,stu_membernum,stu_intro,stu_img,stu_grade,stu_projectnum
      * 排序按照stu_projectnum desc, stu_grade desc, stu_creattime asc
      * 项目数大于projNum
      * @return
      */
     List<Studio> selectSuperStudio(Integer projNum);

     List<Studio> selectForHall(String type,String local,String studioName,String sort);

}
