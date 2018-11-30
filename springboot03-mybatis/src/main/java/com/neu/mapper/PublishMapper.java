package com.neu.mapper;

import com.neu.bean.Publish;
import com.neu.bean.PublishExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PublishMapper {
    long countByExample(PublishExample example);

    int deleteByExample(PublishExample example);

    int deleteByPrimaryKey(Integer pubId);

    int insert(Publish record);

    int insertSelective(Publish record);

    List<Publish> selectByExample(PublishExample example);

    Publish selectByPrimaryKey(Integer pubId);

    int updateByExampleSelective(@Param("record") Publish record, @Param("example") PublishExample example);

    int updateByExample(@Param("record") Publish record, @Param("example") PublishExample example);

    int updateByPrimaryKeySelective(Publish record);

    int updateByPrimaryKey(Publish record);


    int addWatchNum(@Param("id") int id);

}