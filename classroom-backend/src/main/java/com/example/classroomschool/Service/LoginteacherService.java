package com.example.classroomschool.Service;

import com.example.classroomschool.Dao.TeacherMapper;
import com.example.classroomschool.Model.Teacher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginteacherService {
    @Resource
    TeacherMapper teacherMapper;
    public Teacher SignIn(String account){
        return teacherMapper.selectTeacher(account);
    }

   public Teacher existenceMailbox(String mailbox){
        return teacherMapper.selectMailbox(mailbox);
    }
    public Teacher existencePhone(String telephone){
        return teacherMapper.selectPhone(telephone);
    }

    public Teacher existenceAccount(String account){
        return teacherMapper.selectAccount(account);
    }
    public int insertUser(Teacher teacher){
        return teacherMapper.insert(teacher);
    }

    public Teacher teachersName(int id) {
        return teacherMapper.selectByPrimaryKey(id);
    }

    public Teacher getTeacher(Integer teacher) {
        return teacherMapper.selectByPrimaryKey(teacher);
    }

}
