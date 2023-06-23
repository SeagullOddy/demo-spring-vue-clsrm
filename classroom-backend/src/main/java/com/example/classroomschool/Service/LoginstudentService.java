package com.example.classroomschool.Service;

import com.example.classroomschool.Dao.StudentMapper;
import com.example.classroomschool.Model.Student;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginstudentService {
    @Resource
    StudentMapper studentMapper;


    //登录
    public Student SignIn(String account){
        return studentMapper.selectStudent(account);
    }

    public Student existenceMailbox(String mailbox){
        return studentMapper.selectMailbox(mailbox);
    }
    public Student existencePhone(String phone){
        return studentMapper.selectPhone(phone);
    }
    public Student existenceAccount(String account){
        return studentMapper.selectAccount(account);
    }
    public int insertUser(Student student){
        return studentMapper.insert(student);
    }

    public Student getStudent(Integer student) {
        return studentMapper.selectByPrimaryKey(student);
    }
}
