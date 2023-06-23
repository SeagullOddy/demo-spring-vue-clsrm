package com.example.classroomschool.Service;

import com.example.classroomschool.Dao.HomeworkMapper;
import com.example.classroomschool.Model.Homework;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HomeworkService {
    @Resource
    HomeworkMapper homeworkMapper;
    public int AddHomework(Homework homework) {
        return homeworkMapper.insert(homework);
    }

    public List<Homework> getHomework(int courseId) {
        return homeworkMapper.selectHomework(courseId);
    }

    public int updateHomework(Homework homework) {
        return homeworkMapper.updateByPrimaryKey(homework);
    }

    public Homework getHomeworkById(int homeworkId) {
        return homeworkMapper.selectByPrimaryKey(homeworkId);
    }

    public int delete(int homeworkId) {
        return homeworkMapper.deleteByPrimaryKey(homeworkId);
    }

}
