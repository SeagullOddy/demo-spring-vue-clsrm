package com.example.classroomschool.Dao;

import com.example.classroomschool.Model.ScCourse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScCourseMapper {
    List<ScCourse> selectCourse(int StudentId);
}
