package cn.ck.mapper;

import cn.ck.entity.Project;
import cn.ck.entity.bean.PjCol;
import cn.ck.entity.bean.ProjectBid;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
public interface ProjectMapper extends BaseMapper<Project> {
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

    //关联project表跟collectpj表
    List<PjCol> selectPjCol(Map<String,Object> map);

    List<Project> selectProjlist1(String stuId);

    List<Project> selectProjlist2(String stuId);
}
