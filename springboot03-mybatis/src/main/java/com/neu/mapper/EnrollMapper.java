package com.neu.mapper;

import com.neu.bean.Enroll;
import com.neu.bean.EnrollExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EnrollMapper {
    long countByExample(EnrollExample example);

    int deleteByExample(EnrollExample example);

    int deleteByPrimaryKey(Integer enrId);

    int insert(Enroll record);

    int insertSelective(Enroll record);

    List<Enroll> selectByExample(EnrollExample example);

    Enroll selectByPrimaryKey(Integer enrId);

    int updateByExampleSelective(@Param("record") Enroll record, @Param("example") EnrollExample example);

    int updateByExample(@Param("record") Enroll record, @Param("example") EnrollExample example);

    int updateByPrimaryKeySelective(Enroll record);

    int updateByPrimaryKey(Enroll record);

    //根据活动id查找
    List<Enroll> selectByEnr_publish(@Param("id") int id);

    //根据用户id查找
    List<Enroll> selectByUserId(@Param("id") String id);

}