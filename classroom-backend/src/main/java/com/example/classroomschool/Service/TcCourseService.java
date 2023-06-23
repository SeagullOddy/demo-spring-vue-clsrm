package com.example.classroomschool.Service;

import com.example.classroomschool.Dao.TcCourseMapper;
import com.example.classroomschool.Model.TcCourse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TcCourseService {
    @Resource
    TcCourseMapper tcCourseMapper;
    public List<TcCourse> getCourse(int teacherId) {
        return tcCourseMapper.selectCourse(teacherId);
    }
}
