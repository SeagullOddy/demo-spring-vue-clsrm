package com.example.classroomschool.Service;

import com.example.classroomschool.Dao.CourseMapper;
import com.example.classroomschool.Model.Course;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseService {
    @Resource
    CourseMapper courseMapper;
    public Course getCourse(String courseKey) {
        return courseMapper.selectCourse(courseKey);
    }

    public int AddCourse(Course course){
        return courseMapper.insert(course);
    }

    public int stopKey(int id,String stopKey){
        return courseMapper.updateDeleting(id,stopKey);
    }
    public int resetTheKey(int id,String newKey){
        return courseMapper.updateKey(id,newKey);
    }

    public int deleteCourse(int id){
        return courseMapper.deleteByPrimaryKey(id);
    }
    public int changeCourse(int id,int master,String name,String classname,String year,String term){
        return courseMapper.updateCourse(id,master,name,classname,year,term);
    }

    public Course getCourseById(int courseId) {
        return courseMapper.selectByPrimaryKey(courseId);
    }

    public int update(int courseId, int teacherId, int id) {
        return courseMapper.updateTeacher(courseId,teacherId,id);
    }

}
