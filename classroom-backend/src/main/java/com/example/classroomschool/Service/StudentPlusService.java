package com.example.classroomschool.Service;

import com.example.classroomschool.Dao.StudentPlusMapper;
import com.example.classroomschool.Dao.TeacherPlusMapper;
import com.example.classroomschool.Model.Course;
import com.example.classroomschool.Model.StudentPlus;
import com.example.classroomschool.Model.TeacherPlus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StudentPlusService {

    @Resource
    StudentPlusMapper studentPlusMapper;

    public StudentPlus getStudent(Integer student) {
        return studentPlusMapper.selectStudentPlus(student);
    }

}
