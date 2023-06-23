package com.example.classroomschool.Controller.publics;


import com.example.classroomschool.Controller.Token.TokenUtil;
import com.example.classroomschool.Controller.encryption.MD5;
import com.example.classroomschool.Model.Picture;
import com.example.classroomschool.Model.Student;
import com.example.classroomschool.Model.Teacher;
import com.example.classroomschool.Service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class LoginController {
    @Resource
    LoginteacherService teacherService;
    @Resource
    LoginstudentService studentService;
    @Resource
    PictureService pictureService;

    /**
     * 登录
     * */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String loginStudent(@RequestBody Map<String,String> date) throws JsonProcessingException, UnsupportedEncodingException, NoSuchAlgorithmException {

        Student student = studentService.SignIn(date.get("account"));
        Teacher teacher = teacherService.SignIn(date.get("account"));

        if(student==null && teacher == null){
            //用户不存在
            return "0";
        }else {
            if(student != null) {
                MD5 md5 = new MD5();
                if (!student.getPassword().equals(md5.encoderByMd5(date.get("password")))) {
                    //密码错误
                    return "1";
                } else {
                    String token = TokenUtil.sign("ROLE_student", student.getAccount());
                    HashMap<String, Object> hs = new HashMap<>();
                    if (TokenUtil.verify(token)) {
                        hs.put("code", 200);
                    } else {
                        hs.put("code", 500);
                    }
                    hs.put("role", 0);
                    hs.put("token", token);
                    String account = TokenUtil.getUsername(token);
                    Student stu= studentService.existenceAccount(account);
                    hs.put("user",stu);
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.writeValueAsString(hs);
                }
            }else {
                MD5 md5 = new MD5();
                if (!teacher.getPassword().equals(md5.encoderByMd5(date.get("password")))) {
                    return "1";
                } else {
                    String token = TokenUtil.sign("ROLE_teacher", teacher.getAccount());
                    HashMap<String, Object> hs = new HashMap<>();
                    if (TokenUtil.verify(token)) {
                        hs.put("code", 200);
                    } else {
                        hs.put("code", 500);
                    }
                    hs.put("role", 1);
                    hs.put("token", token);
                    String account = TokenUtil.getUsername(token);
                    Student stu= studentService.existenceAccount(account);
                    hs.put("user",stu);
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.writeValueAsString(hs);
                }
            }
        }
    }

    /**
     * 学生注册
     * */
    @RequestMapping(value = "/register/student")
    public String addstudent(@RequestBody Map<String,String> date) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String account;
        String mailbox = null;
        String phone = null;
        Student student = new Student();
        MD5 md5 = new MD5();
        if(!md5.encoderByMd5(date.get("answer")).equals(date.get("answerPlus"))){
            return "验证码错误";
        }
        if(date.get("account").contains("@")){
            mailbox = date.get("account");
            student.setMailbox(mailbox);
        }else {
            phone = date.get("account");
            student.setPhone(phone);
        }
        Student studentMailbox = studentService.existenceMailbox(mailbox);
        Student studentPhone = studentService.existencePhone(phone);
        Teacher teacherMailbox = teacherService.existenceMailbox(mailbox);
        Teacher teacherPhone = teacherService.existencePhone(phone);
        if(studentMailbox!=null || teacherMailbox!=null) {
            //邮箱存在
            return "邮箱已存在";
        }else if(studentPhone!=null || teacherPhone!=null){
            //电话存在
            return "电话已存在";
        }
        else {
            while (true){
                account ="s" + String.valueOf((int)((Math.random()*9+1)*100000));
                if(studentService.existenceAccount(account) == null){
                    student.setAccount(account);
                    break;
                }
            }
            student.setPassword(md5.encoderByMd5(date.get("password")));
            student.setName(date.get("name"));
            student.setSchool(date.get("school"));
            student.setSchoolNum(date.get("schoolNum"));
            int as=studentService.insertUser(student);

            int value = (int) (Math.random() * 31 + 10);
            String address = "https://www.ketangpai.com/Public/Common/img/40/"+value+".png";
            Picture picture = new Picture();
            picture.setPicid(account);
            picture.setAddress(address);
            int index = pictureService.inserPic(picture);
            return "注册成功";
        }
    }

    /**
     * 老师注册
     * */
    @RequestMapping(value = "/register/teacher")
    public String addTeacher(@RequestBody Map<String,String> date) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String account;
        String mailbox = null;
        String phone = null;
        Teacher teacher = new Teacher();
        MD5 md5 = new MD5();
        if(!md5.encoderByMd5(date.get("answer")).equals(date.get("answerPlus"))){
            return "验证码错误";
        }
        if(date.get("account").contains("@")){
            mailbox = date.get("account");
            teacher.setMailbox(mailbox);
        }else {
            phone = date.get("account");
            teacher.setPhone(phone);
        }

        Student studentMailbox = studentService.existenceMailbox(mailbox);
        Student studentPhone = studentService.existencePhone(phone);
        Teacher teacherMailbox = teacherService.existenceMailbox(mailbox);
        Teacher teacherPhone = teacherService.existencePhone(phone);
        if(studentMailbox!=null || teacherMailbox!=null) {
            return "邮箱已存在";
        }else if(studentPhone!=null || teacherPhone!=null){
            return "电话已存在";
        }
        else {
            while (true){
                account ="t" + String.valueOf((int)((Math.random()*9+1)*100000));
                if(teacherService.existenceAccount(account) == null){
                    teacher.setAccount(account);
                    break;
                }
            }
            teacher.setName(date.get("name"));
            teacher.setSchool(date.get("school"));
            teacher.setPassword(md5.encoderByMd5(date.get("password")));
            int as=teacherService.insertUser(teacher);

            //添加头像图片
            int value = (int) (Math.random() * 31 + 10);
            String address = "https://www.ketangpai.com/Public/Common/img/40/"+value+".png";
            Picture picture = new Picture();
            picture.setPicid(account);
            picture.setAddress(address);
            int index = pictureService.inserPic(picture);
            return "注册成功";
        }
    }

    /**
     * 验证码
     * */
    @RequestMapping(value = "/validCode")
    public Object checkNumber() throws ScriptException, UnsupportedEncodingException, NoSuchAlgorithmException, JsonProcessingException {
        String number = "123456789";
        String icon = "加减乘";
        String icon2 = "+-*";
        String equation = "";
        int result;
        ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
        while (true) {
            int value = (int) (Math.random() * 9 + 0);
            int value2 = (int) (Math.random() * 9 + 0);
            int value3 = (int) (Math.random() * 3 + 0);
            equation = number.charAt(value) + "" + icon.charAt(value3) + "" + number.charAt(value2) + "=";
            result = (int) jse.eval(Integer.parseInt(String.valueOf(number.charAt(value))) + "" + icon2.charAt(value3)
                    + "" + Integer.parseInt(String.valueOf(number.charAt(value2))));
            if (result > 0) {
                break;
            }
        }
        HashMap<String,String> hs = new HashMap<>();
        hs.put("code",equation);
        MD5 md5 = new MD5();
        hs.put("answerPlus", md5.encoderByMd5(result+""));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }
}
