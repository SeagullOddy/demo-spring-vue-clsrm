package com.example.classroomschool.Service;

import com.example.classroomschool.Dao.TeacherPlusMapper;
import com.example.classroomschool.Model.TeacherPlus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TeacherPlusService {

    @Resource
    TeacherPlusMapper teacherPlusMapper;

    public TeacherPlus getTeacher(int teacher, int courseId) {
        return teacherPlusMapper.selectTeacherPlus(teacher,courseId);
    }

}
