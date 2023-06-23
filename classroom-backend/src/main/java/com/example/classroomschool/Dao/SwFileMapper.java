package com.example.classroomschool.Dao;

import com.example.classroomschool.Model.SwFile;
import com.example.classroomschool.Model.SwFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SwFileMapper {
    int countByExample(SwFileExample example);

    int deleteByExample(SwFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SwFile record);

    int insertSelective(SwFile record);

    List<SwFile> selectByExample(SwFileExample example);

    SwFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SwFile record, @Param("example") SwFileExample example);

    int updateByExample(@Param("record") SwFile record, @Param("example") SwFileExample example);

    int updateByPrimaryKeySelective(SwFile record);

    int updateByPrimaryKey(SwFile record);

    List<SwFile> selectHomework(String studentId, int homework);

    int deleteByMap(String account, Integer id);

    int deleteAllFile(Integer homework);

    List<SwFile> selectHomeworkByHomeworkId(Integer homework);
}