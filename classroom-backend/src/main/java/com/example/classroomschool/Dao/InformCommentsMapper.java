package com.example.classroomschool.Dao;

import com.example.classroomschool.Model.InformComments;
import com.example.classroomschool.Model.InformCommentsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InformCommentsMapper {
    int countByExample(InformCommentsExample example);

    int deleteByExample(InformCommentsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(InformComments record);

    int insertSelective(InformComments record);

    List<InformComments> selectByExample(InformCommentsExample example);

    InformComments selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") InformComments record, @Param("example") InformCommentsExample example);

    int updateByExample(@Param("record") InformComments record, @Param("example") InformCommentsExample example);

    int updateByPrimaryKeySelective(InformComments record);

    int updateByPrimaryKey(InformComments record);

    List<InformComments> selectComments(int informId);

    int deleteByMap(String account, Integer informId);

    int deleteInformComment(Integer informId);
}