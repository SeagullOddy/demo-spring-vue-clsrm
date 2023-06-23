package com.example.classroomschool.Service;

import com.example.classroomschool.Dao.ScMapper;
import com.example.classroomschool.Model.Sc;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ScService {
    @Resource
    ScMapper scMapper;

    public int CTop(int id,int CourseId,String top){
        return scMapper.ChangeTop(id,CourseId,top);
    }
    public int joinCourse(Sc sc){
        return scMapper.insert(sc);
    }

    public int deleteCourse(int student,int course){
        return scMapper.CourseDelete(student,course);
    }

    public int delete(int course){
        return scMapper.deleteByCourse(course);
    }

    public int pigeonholeCourse(int student,int course,String pigeonhole) {
        return scMapper.pigeonholeC(student,course,pigeonhole);
    }

    public Sc getSc(int student, int course) {
        return scMapper.selectSc(student,course);
    }
    public List<Sc> getNumber(int course) {
        return scMapper.selectAllSc(course);
    }

    public int sortNum(int course,int student,int displayNum) {
        return scMapper.SortNumber(course,student,displayNum);
    }

    public List<Sc> getListSc(int student) {
        return scMapper.ListStudent(student);
    }

    public int pigeonholeByCourse(int course, String pigeonhole) {
        return scMapper.pigeonholeTheCourse(course,pigeonhole);
    }

    public List<Sc> selectTop(int student) {
        return scMapper.selectByStudentTop(student);
    }

    public List<Sc> selectPigeonhole(int student) {
        return scMapper.selectByStudentPigeonhole(student);
    }
}
