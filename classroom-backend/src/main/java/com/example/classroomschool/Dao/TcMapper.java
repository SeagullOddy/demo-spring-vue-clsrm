package com.example.classroomschool.Dao;

import com.example.classroomschool.Model.Tc;
import com.example.classroomschool.Model.TcExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TcMapper {
    int countByExample(TcExample example);

    int deleteByExample(TcExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Tc record);

    int insertSelective(Tc record);

    List<Tc> selectByExample(TcExample example);

    Tc selectByPrimaryKey(Integer id);

    int CourseDelete(int teacher,int course);

    Tc selectTc(int teacher,int course);

    int ChangeTop(int id,int CourseId,String top);

    int pigeonholeC(int teacher,int course,String pigeonhole);

    int SortNumber(int course,int teacher,int displayNum);


    int updateByExampleSelective(@Param("record") Tc record, @Param("example") TcExample example);

    int updateByExample(@Param("record") Tc record, @Param("example") TcExample example);

    int updateByPrimaryKeySelective(Tc record);

    int updateByPrimaryKey(Tc record);

    List<Tc> ListTeacher(int teacher);

    List<Tc> selectTcByCourse(int course);

    List<Tc> selectStudentTc(Integer course);

    int AllCourseDelete(int course);

}