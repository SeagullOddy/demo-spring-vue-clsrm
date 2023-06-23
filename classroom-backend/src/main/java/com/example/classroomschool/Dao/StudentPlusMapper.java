package com.example.classroomschool.Dao;

import com.example.classroomschool.Model.StudentPlus;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentPlusMapper {
    StudentPlus selectStudentPlus(Integer student);
}
