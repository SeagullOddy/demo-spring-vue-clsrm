package com.example.classroomschool.Service;


import com.example.classroomschool.Dao.ScCourseMapper;
import com.example.classroomschool.Model.ScCourse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ScCourseService {
    @Resource
    ScCourseMapper scCourseMapper;
    public List<ScCourse> getCourse(int StudentId) {
        return scCourseMapper.selectCourse(StudentId);
    }
}
