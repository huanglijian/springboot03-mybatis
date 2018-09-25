package cn.ck.service.impl;

import cn.ck.entity.Project;
import cn.ck.entity.bean.ProjectBid;
import cn.ck.mapper.ProjectMapper;
import cn.ck.service.ProjectService;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
