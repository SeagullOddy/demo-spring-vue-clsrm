package com.example.classroomschool.Service;

import com.example.classroomschool.Dao.TcMapper;
import com.example.classroomschool.Model.Tc;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TcService {
    @Resource
    TcMapper tcMapper;

    public int joinCourse(Tc tc){
        return tcMapper.insert(tc);
    }


    public int deleteCourse(int teacher,int course){
        return tcMapper.CourseDelete(teacher,course);
    }
    public int deleteAllCourse(int course){
        return tcMapper.AllCourseDelete(course);
    }
    public Tc getTc(int teacher,int course){
        return tcMapper.selectTc(teacher,course);
    }

    public int CTop(int id,int CourseId,String top){
        return tcMapper.ChangeTop(id,CourseId,top);
    }

    public int pigeonholeCourse(int teacher,int course,String pigeonhole) {
        return tcMapper.pigeonholeC(teacher,course,pigeonhole);
    }
    public int sortNum(int course,int teacher,int displayNum) {
        return tcMapper.SortNumber(course,teacher,displayNum);
    }

    public List<Tc> getListTc(int teacher) {
        return tcMapper.ListTeacher(teacher);
    }

    public List<Tc> getTcByCourse(int course) {
        return tcMapper.selectTcByCourse(course);
    }

    public int update(Tc tc) {
        return tcMapper.updateByPrimaryKey(tc);
    }

    public List<Tc> getNumber(Integer course) {
        return tcMapper.selectStudentTc(course);
    }

}
