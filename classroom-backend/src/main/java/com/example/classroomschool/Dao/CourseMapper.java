package com.example.classroomschool.Dao;

import com.example.classroomschool.Model.Course;
import com.example.classroomschool.Model.CourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseMapper {
    int countByExample(CourseExample example);

    int deleteByExample(CourseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Course record);

    int insertSelective(Course record);

    List<Course> selectByExample(CourseExample example);

    Course selectByPrimaryKey(Integer id);

    Course selectCourse(String key);

    int updateByExampleSelective(@Param("record") Course record, @Param("example") CourseExample example);

    int updateByExample(@Param("record") Course record, @Param("example") CourseExample example);

    int updateByPrimaryKeySelective(Course record);

    int updateDeleting(int id,String stopKey);

    int updateKey(int id,String newKey);

    int updateByPrimaryKey(Course record);

    int updateCourse(int id,int master,String name,String classname,String year,String term);

    int updateTeacher(int courseId, int teacherId, int id);
}