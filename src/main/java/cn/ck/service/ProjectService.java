package cn.ck.service;

import cn.ck.entity.Project;
import cn.ck.entity.bean.PjCol;
import cn.ck.entity.bean.ProjectBid;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
public interface ProjectService extends IService<Project> {
    /**
     * 查询竞标天数小于等于10天，即项目竞标状态正常
     * @return
     */
    List<Project> projBidTimetrue(String id);

    /**
     * 查询竞标天数大于10天，即项目竞标结束，若发布者没有选择承接方，则项目终止
     * @return
     */
    List<Project> projBidTimefalse(String id);

    /**
     *
     * @param id 项目id
     * @return 竞标剩余天数
     */
    Integer projBidTimeNum(int id);

    /**
     *
     * @param id 项目id
     * @return 开发剩余天数
     */
    Integer projDevelopTimeNum(int id);

    List<PjCol> selectPjCol(Map<String,Object> map);

    public List<Project> selectProjlist1(String stuId);

    public List<Project> selectProjlist2(String stuId);
}
