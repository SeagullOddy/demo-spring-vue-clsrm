package com.example.classroomschool.Dao;

import com.example.classroomschool.Model.TeacherPlus;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherPlusMapper {
    TeacherPlus selectTeacherPlus(int teacher,int courseId);
}
