package com.example.classroomschool.Controller.publics;

import com.example.classroomschool.Controller.Token.TokenUtil;
import com.example.classroomschool.Model.*;
import com.example.classroomschool.Service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/api/start")
public class PublicController {
    @Resource
    ScService scService;
    @Resource
    PictureService pictureService;
    @Resource
    TcService tcService;
    @Resource
    CourseService courseService;
    @Resource
    HomeworkService homeworkService;
    @Resource
    TeacherPlusService teacherPlusService;
    @Resource
    StudentPlusService studentPlusService;
    @Resource
    CommentsService commentsService;
    @Resource
    InformCommentsService informCommentsService;
    @Resource
    LoginteacherService teacherService;
    @Resource
    LoginstudentService studentService;
    @Resource
    InformService informService;
    @Resource
    LoginteacherService loginteacherService;

    /**
     * 获取账号信息
     * */
    @RequestMapping(value = "/user")
    public Object  getUser(@RequestBody Map<String,String> token) throws JsonProcessingException {
        String account = TokenUtil.getUsername(token.get("token"));
        Object student = studentService.existenceAccount(account);
        Object teacher = teacherService.existenceAccount(account);
        HashMap<String, Object> hs = new HashMap<>();
        if(student==null) {
            hs.put("user", teacher);
        }else {
            hs.put("user",student);
        }
        Picture picture = pictureService.getAddress(account);
        hs.put("address",picture.getAddress());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }
    /**
     * 获取发布人信息
     * */
    @RequestMapping(value = "/issuer")
    public Object  getUserTeacher(@RequestBody Map<String,String> data) throws JsonProcessingException {
        int account = Integer.parseInt(data.get("userId"));
        Teacher teacher = teacherService.teachersName(account);
        HashMap<String, Object> hs = new HashMap<>();
        hs.put("user", teacher);
        Picture picture = pictureService.getAddress(teacher.getAccount());
        hs.put("address",picture.getAddress());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping(value = "/teacherName")
    public String name(@RequestBody Map<String,String> data){
        int id = Integer.parseInt(data.get("id"));
        Teacher teacher = loginteacherService.teachersName(id);
        return teacher.getName();
    }

    /**
     * 学生人数
     * */
    @RequestMapping(value = "/studentNum")
    public int number(@RequestBody Map<String,String> data){
        int course = Integer.parseInt(data.get("courseId"));
        List<Sc> sc = scService.getNumber(course);
        System.out.println(course+"  "+sc.size());
        return sc.size();
    }


    /**
     * 老师课程所有信息
     * */
    @RequestMapping(value = "/courseInfo")
    public Object CourseInfo(@RequestBody Map<String,String> data) throws JsonProcessingException {
        int courseId = Integer.parseInt(data.get("courseId"));
        HashMap<String,Object> hs = new HashMap<>();
        Course course = courseService.getCourseById(courseId);
        List<Homework> homework = homeworkService.getHomework(courseId);
        List<Tc> tc = tcService.getTcByCourse(courseId);
        Collections.reverse(tc);
        List<TeacherPlus> teachers = new ArrayList<TeacherPlus>();
        List<TeacherPlus> teachersStudent = new ArrayList<TeacherPlus>();
        for (Tc value : tc) {
            if(!value.getRole().equals("学生")) {
                teachers.add(teacherPlusService.getTeacher(value.getTeacher(),value.getCourse()));
            }else {
                teachersStudent.add(teacherPlusService.getTeacher(value.getTeacher(),value.getCourse()));
            }
        }

        List<Sc> sc = scService.getNumber(courseId);
        List<Object> students = new ArrayList<Object>();

        for (Sc value : sc) {
            students.add(studentPlusService.getStudent(value.getStudent()));
        }
        for(TeacherPlus value : teachersStudent){
            students.add(value);
        }
        hs.put("course",course);
        hs.put("homework",homework);
        hs.put("teacher",teachers);
        hs.put("tc",tc);
        hs.put("student",students);
        hs.put("address",pictureService.getAddress(courseId+"").getAddress().replace("min","big"));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(hs);
    }
    /**
     * 作业信息
     * */
    @RequestMapping(value = "/homework")
    public Homework homework(@RequestBody Map<String,String> data) throws ParseException {
        int course = Integer.parseInt(data.get("homeworkId"));
        return homeworkService.getHomeworkById(course);
    }
    /**
     * 公告信息
     * */
    @RequestMapping(value = "/inform")
    public List<Inform> inform(@RequestBody Map<String,String> data) throws ParseException {
        int course = Integer.parseInt(data.get("courseId"));
        return informService.getInform(course);
    }
    /**
     * 具体公告信息
     * */
    @RequestMapping(value = "/informId")
    public Inform informId(@RequestBody Map<String,String> data) throws ParseException {
        int course = Integer.parseInt(data.get("InformId"));
        return informService.getInformById(course);
    }
    /**
     * 查找老师
     * */
    @RequestMapping(value = "/master")
    public String master(@RequestBody Map<String,String> data) throws ParseException {
        int master = Integer.parseInt(data.get("master"));
        return teacherService.teachersName(master).getAccount();
    }
    /**
     * 作业评论
     * */
    @RequestMapping(value = "/comments")
    public List<Comments> comment(@RequestBody Map<String,String> data) throws ParseException {
        int homeworkId = Integer.parseInt(data.get("homeworkId"));
        return commentsService.getComments(homeworkId);
    }
    /**
     * 公告评论
     * */
    @RequestMapping(value = "/InformComments")
    public List<InformComments> InformComment(@RequestBody Map<String,String> data) throws ParseException {
        int informId = Integer.parseInt(data.get("informId"));
        return informCommentsService.getComments(informId);
    }
    /**
     * 发表评论
     * */
    @RequestMapping(value = "/discuss")
    public String discuss(@RequestBody Map<String,String> data) throws ParseException {
        Comments comments = new Comments();
        comments.setAccount(data.get("account"));
        comments.setHomeworkid(Integer.parseInt(data.get("homeworkid")));
        comments.setImage(data.get("image"));
        comments.setWord(data.get("word"));
        comments.setTime(data.get("time"));
        comments.setName(data.get("name"));
        commentsService.setComments(comments);
        return "发表成功";
    }
    /**
     * 公告发表评论
     * */
    @RequestMapping(value = "/InformDiscuss")
    public String InformDiscuss(@RequestBody Map<String,String> data) throws ParseException {
        InformComments comments = new InformComments();
        comments.setAccount(data.get("account"));
        comments.setInformid(Integer.parseInt(data.get("informId")));
        comments.setImage(data.get("image"));
        comments.setWord(data.get("word"));
        comments.setTime(data.get("time"));
        comments.setName(data.get("name"));
        informCommentsService.setComments(comments);
        return "发表成功";
    }
    /**
     * 删除评论
     * */
    @RequestMapping(value = "/deleteComments")
    public String deleteComments(@RequestBody Map<String,String> data) throws ParseException {
        commentsService.deleteComments(Integer.parseInt(data.get("id")));
        return "删除成功";
    }
    /**
     * 删除公告评论
     * */
    @RequestMapping(value = "/deleteInformComments")
    public String deleteInformComments(@RequestBody Map<String,String> data) throws ParseException {
        informCommentsService.deleteComments(Integer.parseInt(data.get("id")));
        return "删除成功";
    }
}
