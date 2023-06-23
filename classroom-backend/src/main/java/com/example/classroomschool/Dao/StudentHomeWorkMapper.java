package com.example.classroomschool.Dao;

import com.example.classroomschool.Model.StudentHomeWork;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentHomeWorkMapper {
    List<StudentHomeWork> selectHomework(int homeworkId);
}
