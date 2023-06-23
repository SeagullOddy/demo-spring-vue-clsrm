package com.example.classroomschool.Dao;

import com.example.classroomschool.Model.TcCourse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TcCourseMapper {
    List<TcCourse> selectCourse(int teacherId);
}
