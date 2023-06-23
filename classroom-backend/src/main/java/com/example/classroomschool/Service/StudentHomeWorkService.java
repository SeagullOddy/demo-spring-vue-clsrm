package com.example.classroomschool.Service;

import com.example.classroomschool.Dao.StudentHomeWorkMapper;
import com.example.classroomschool.Model.StudentHomeWork;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentHomeWorkService {
    @Resource
    StudentHomeWorkMapper studentHomeWorkMapper;

    public List<StudentHomeWork> getHomeWork(int homeworkId) {
        return studentHomeWorkMapper.selectHomework(homeworkId);
    }
}
