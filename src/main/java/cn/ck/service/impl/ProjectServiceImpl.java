package cn.ck.service.impl;

import cn.ck.entity.Project;
import cn.ck.entity.bean.PjCol;
import cn.ck.entity.bean.ProjectBid;
import cn.ck.mapper.ProjectMapper;
import cn.ck.service.ProjectService;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-09-21
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
    @Autowired
    ProjectMapper projectMapper;

    @Override
    public List<Project> projBidTimetrue(String id) {
        List<Project> projectList=projectMapper.projBidTimetrue(id);
        return projectList;
    }

    @Override
    public List<Project> projBidTimefalse(String id) {
        List<Project> projectList=projectMapper.projBidTimefalse(id);
        return projectList;
    }

    @Override
    public Integer projBidTimeNum(int id) {
        return projectMapper.projBidTimeNum(id);
    }

    @Override
    public Integer projDevelopTimeNum(int id) {
        return projectMapper.projDevelopTimeNum(id);
    }

    @Override
    public List<PjCol> selectPjCol(Map<String,Object> map) {
        return projectMapper.selectPjCol(map);
    }

    @Override
    public List<Project> selectProjlist1(String stuId){
        return projectMapper.selectProjlist1(stuId);
    }

    @Override
    public List<Project> selectProjlist2(String stuId){
        return projectMapper.selectProjlist2(stuId);
    }


}