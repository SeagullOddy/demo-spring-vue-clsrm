package com.example.classroomschool.Controller.Teacher;


import com.example.classroomschool.Controller.File.Chines2PingUtils;
import com.example.classroomschool.Service.QiNiuSupport;
import com.example.classroomschool.Controller.Token.TokenUtil;
import com.example.classroomschool.Controller.encryption.MD5;
import com.example.classroomschool.Model.*;
import com.example.classroomschool.Service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiniu.common.QiniuException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/teacher")
public class teacherController {
    @Resource
    TcCourseService tcCourseService;
    @Resource
    LoginteacherService loginteacherService;
    @Resource
    TcService tcService;
    @Resource
    CourseService courseService;
    @Resource
    ScService scService;
    @Resource
    PictureService pictureService;
    @Resource
    HomeworkService homeworkService;
    @Resource
    StudentHomeWorkService studentHomeWorkService;
    @Resource
    LoginstudentService loginstudentService;
    @Resource
    SwService swService;
    @Resource
    InformService informService;
    @Resource
    CommentsService commentsService;
    @Resource
    InformCommentsService informCommentsService;
    @Resource
    SwFileService swFileService;
    @Resource
    QiNiuSupport qiNiuSupport;


    /**
     * 老师课程
     */
    @RequestMapping(value = "/course")
    public List<TcCourse> AllCourse(@RequestBody Map<String, String> token) {
        String account = TokenUtil.getUsername(token.get("token"));
        Teacher teacher = loginteacherService.existenceAccount(account);
        List<TcCourse> tcCourses = tcCourseService.getCourse(teacher.getId());
        for (int i = 0; i < tcCourses.size(); i++) {
            List<Sc> sc = scService.getNumber(tcCourses.get(i).getCourseId());
            tcCourses.get(i).setStudentNum(sc.size());
        }
        return tcCourses;
    }

    /**
     * 课程置顶
     */
    @RequestMapping(value = "/changeTop")
    public String changeTheTop(@RequestBody Map<String, String> data) {
        String top = "0";
        int id = Integer.parseInt(data.get("id"));
        int CourseId = Integer.parseInt(data.get("courseId"));
        if (data.get("top").equals("0")) {
            top = "1";
        } else {
            top = "0";
        }
        int index = tcService.CTop(id, CourseId, top);
        if (index == 1) {
            if (top.equals("1")) {
                return "置顶成功";
            } else {
                return "已取消置顶";
            }
        } else {
            return "失败";
        }
    }

    /**
     * 加课码停用与恢复
     */
    @RequestMapping(value = "/changeCourseKey")
    public String CourseKeyStop(@RequestBody Map<String, String> data) {
        int id = Integer.parseInt(data.get("id"));

        if (data.get("deleting").equals("0")) {
            if (courseService.stopKey(id, "1") == 1) {
                return "加课码已停用";
            } else {
                return "失败";
            }
        } else {
            if (courseService.stopKey(id, "0") == 1) {
                return "加课码已恢复";
            } else {
                return "失败";
            }
        }
    }

    /**
     * 加课码重置
     */
    @RequestMapping(value = "/resetCourseKey")
    public String reset(@RequestBody Map<String, String> data) {
        int id = Integer.parseInt(data.get("id"));

        String newKey = "";
        String list = "ABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
        int index = 0;
        while (true) {
            for (int i = 0; i < 6; i++) {
                int value = (int) (Math.random() * 36 + 0);
                newKey = newKey + list.charAt(value);
            }
            Course course1 = courseService.getCourse(newKey);
            if (course1 == null) {
                index = courseService.resetTheKey(id, newKey);
                break;
            }
        }
        if (index == 1) {
            return "加课码已重置";
        } else {
            return "失败";
        }
    }

    /**
     * 教师加入课程
     */
    @RequestMapping(value = "/courseJoin")
    public String joinCourse(@RequestBody Map<String, String> data) {
        String courseKey = data.get("key");
        int teacher = Integer.parseInt(data.get("id"));
        Course course = courseService.getCourse(courseKey);
        List<Tc> tcTeacher = tcService.getListTc(teacher);
        if (course != null) {
            Tc tc = tcService.getTc(teacher, course.getId());
            if (tc != null) {
                if (tc.getPigeonhole().equals("1")) {
                    return "加课码已失效";
                }
                if (tc.getPigeonhole().equals("0")) {
                    return "此课程已加入";
                }
            }
        }
        if (course == null) {
            return "加课码不存在";
        } else if (course.getDeleting().equals("1")) {
            return "加课码已停用";
        } else {
            Tc tc = new Tc();
            tc.setTeacher(teacher);
            tc.setCourse(course.getId());
            tc.setPigeonhole("0");
            tc.setTop("1");
            tc.setRole("学生");
            if (tcTeacher == null || tcTeacher.size() == 0) {
                tc.setDisplayNum(1);
            } else {
                tc.setDisplayNum(tcTeacher.size() + 1);
            }
            int index = tcService.joinCourse(tc);
            return "加课成功";
        }
    }

    /**
     * 老师课程创建与修改
     */
    @RequestMapping(value = "/courseEdit")
    public String establish(@RequestBody Map<String, String> data) {
        String info = data.get("new");
        if (info.equals("1")) {
            Course course = new Course();
            course.setName(data.get("name"));
            course.setClassName(data.get("className"));
            course.setMaster(Integer.parseInt(data.get("master")));
            String CourseKey = "";
            String list = "ABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
            while (true) {
                for (int i = 0; i < 6; i++) {
                    int value = (int) (Math.random() * 36 + 0);
                    CourseKey = CourseKey + list.charAt(value);
                }
                Course course1 = courseService.getCourse(CourseKey);
                if (course1 == null) {
                    course.setCourseKey(CourseKey);
                    break;
                }
            }
            course.setDeleting("0");
            course.setYear(data.get("year"));
            course.setTerm(data.get("term"));
            int index = courseService.AddCourse(course);
            List<Tc> tcTeacher = tcService.getListTc(Integer.parseInt(data.get("master")));
            if (index == 1) {
                Tc tc = new Tc();
                Course course1 = courseService.getCourse(CourseKey);
                tc.setTeacher(Integer.parseInt(data.get("master")));
                tc.setCourse(course1.getId());
                tc.setPigeonhole("0");
                tc.setTop("1");
                tc.setRole("管理员");
                if (tcTeacher == null || tcTeacher.size() == 0) {
                    tc.setDisplayNum(1);
                } else {
                    tc.setDisplayNum(tcTeacher.size() + 1);
                }
                int index2 = tcService.joinCourse(tc);

                int value = (int) (Math.random() * 44 + 1);
                String address;
                if (value < 10) {
                    address = "https://assets.ketangpai.com/theme/teacher/min/0" + value + ".png";
                } else {
                    address = "https://assets.ketangpai.com/theme/teacher/min/" + value + ".png";
                }
                Picture picture = new Picture();
                picture.setPicid(course1.getId() + "");
                picture.setAddress(address);
                int i = pictureService.inserPic(picture);

                if (index2 == 1) {
                    return "创建课程成功";
                } else {
                    return "失败";
                }
            } else {
                return "失败";
            }
        } else {
            int id = Integer.parseInt(data.get("id"));
            int master = Integer.parseInt(data.get("master"));
            String name = data.get("name");
            String classname = data.get("className");
            String year = data.get("year");
            String term = data.get("term");
            courseService.changeCourse(id, master, name, classname, year, term);
            return "课程已编辑";
        }

    }

    /**
     * 老师退课
     */
    @RequestMapping(value = "/courseOut")
    public String outCourse(@RequestBody Map<String, String> data) throws UnsupportedEncodingException, NoSuchAlgorithmException, QiniuException {
        int classId = Integer.parseInt(data.get("courseId"));
        String password = data.get("password");
        int teacher = Integer.parseInt(data.get("id"));
        String account = TokenUtil.getUsername(data.get("token"));
        Teacher teacher1 = loginteacherService.existenceAccount(account);
        MD5 md5 = new MD5();

        if (teacher1.getPassword().equals(md5.encoderByMd5(password))) {
            if (tcService.getTc(teacher1.getId(), classId).getRole().equals("管理员")) {
                int index = tcService.deleteAllCourse(classId);
                int index1 = scService.delete(classId);
                int index2 = courseService.deleteCourse(classId);

                //所有作业相关
                for(Homework homework : homeworkService.getHomework(classId)){
                    //文件删除
                    for (SwFile swFile : swFileService.getHomeworkFileByHomeworkId(homework.getId())) {
                        String name = swFile.getStudent() + "-" + swFile.getHomework() + "-" + swFile.getFilename();
                        qiNiuSupport.delete(Chines2PingUtils.getFullSpell(name));
                    }

                    int index3 = swService.delete(homework.getId());
                    int index5 = homeworkService.delete(homework.getId());
                    int index6 = commentsService.delete(homework.getId());
                    int index8 = swFileService.deleteFile(homework.getId());
                }

                //所有公告相关
                for (Inform inform : informService.getInform(classId)) {
                    int index4 = informService.delete(inform.getId());
                    int index7 = informCommentsService.delete(inform.getId());
                }
                return "退课成功";
            } else {
                tcService.deleteCourse(teacher, classId);
                for(Homework homework : homeworkService.getHomework(classId)) {
                    String accounts = loginteacherService.getTeacher(teacher).getAccount();
                    int homeId = homework.getId();
                    //文件删除
                    for (SwFile swFile : swFileService.getHomeworkFile(accounts,homeId)) {
                        String name = accounts + "-" + homeId + "-" + swFile.getFilename();
                        qiNiuSupport.delete(Chines2PingUtils.getFullSpell(name));
                    }

                    //删除老师课程中的作业
                    int index2 = swService.deleteHomework(accounts, homeId);
                    int index5 = swFileService.deleteHomework(accounts, homeId);
                    //删除老师课程中的作业评论
                    int index3 = commentsService.deleteUserComments(accounts,homeId);
                }

                for (Inform inform : informService.getInform(classId)){
                    int index4 = informCommentsService.deleteUserComments(loginteacherService.getTeacher(teacher).getAccount(),inform.getId());//删除老师公告中评论
                }
                return "退课成功";
            }
        } else {
            return "密码错误";
        }
    }

    /**
     * 老师归档课程
     */
    @RequestMapping(value = "/changePigeonhole")
    public String PigeonholeStudentCourse(@RequestBody Map<String, String> data) {
        int course = Integer.parseInt(data.get("courseId"));
        int teacher = Integer.parseInt(data.get("id"));
        int pigeonhole = Integer.parseInt(data.get("pigeonhole"));
        int all = Integer.parseInt(data.get("all"));

        if (all == 0) {

            if (pigeonhole == 0) {
                int index = tcService.pigeonholeCourse(teacher, course, "1");
                if (index == 1) {
                    return "归档成功";
                } else {
                    return "失败";
                }
            } else {
                int index = tcService.pigeonholeCourse(teacher, course, "0");
                if (index == 1) {
                    return "取消归档";
                } else {
                    return "失败";
                }
            }
        } else {

            if (pigeonhole == 0) {
                int index = tcService.pigeonholeCourse(teacher, course, "1");
                int index2 = scService.pigeonholeByCourse(course, "1");
                if (index == 1) {
                    return "归档成功";
                } else {
                    return "失败";
                }
            } else {
                int index = tcService.pigeonholeCourse(teacher, course, "0");
                int index2 = scService.pigeonholeByCourse(course, "0");
                if (index == 1) {
                    return "取消归档";
                } else {
                    return "失败";
                }
            }
        }

    }

    /**
     * 添加助教
     * */
    @RequestMapping(value = "/addTeacher")
    public String AddTeacher(@RequestBody Map<String, String> data) {
        Teacher teacher = loginteacherService.SignIn(data.get("teacherInfo"));
        Student student = loginstudentService.SignIn(data.get("teacherInfo"));
        if(student!=null){
            return "邀请人不能为学生";
        }else {
            if (teacher == null) {
                return "邀请人不存在";
            } else {
                if (tcService.getTc(teacher.getId(), Integer.parseInt(data.get("courseId"))) != null) {
                    return "该教师已在这门课中";
                } else {
                    List<Tc> tcTeacher = tcService.getListTc(teacher.getId());
                    Tc tc = new Tc();
                    tc.setTeacher(teacher.getId());
                    tc.setCourse(Integer.parseInt(data.get("courseId")));
                    tc.setPigeonhole("0");
                    tc.setTop("1");
                    tc.setRole("助教");
                    if (tcTeacher == null || tcTeacher.size() == 0) {
                        tc.setDisplayNum(1);
                    } else {
                        tc.setDisplayNum(tcTeacher.size() + 1);
                    }
                    int index = tcService.joinCourse(tc);
                    return "邀请成功";
                }
            }
        }
    }

    /**
     * 老师课程排序
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/courseSort")
    public String SortNum(@RequestBody Map<String, Object> list) {
        List<Object> tcCourses = (List<Object>) list.get("list");
        for (int i = 0; i < tcCourses.size(); i++) {
            int course = (int) ((Map<String, Object>) tcCourses.get(i)).get("courseId");
            int teacher = (int) ((Map<String, Object>) tcCourses.get(i)).get("teacher");
            int index = tcService.sortNum(course, teacher, i + 1);
        }
        return "成功";
    }

    /**
     * 老师发布作业与修改
     */
    @RequestMapping(value = "/homeworkEdit")
    public String PublishHomework(@RequestBody Map<String, String> data) throws ParseException {

        Homework homework = new Homework();
        String overtime = "0";
        homework.setCourse(Integer.parseInt(data.get("courseId")));
        homework.setName(data.get("name"));
        homework.setIntro(data.get("intro"));
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        homework.setStartTime(df.format(new Date()));
        homework.setEndTime(data.get("endTime"));
        homework.setMaxScore(data.get("maxScore"));
        homework.setType("0");
        if (data.get("overtime").equals("true")) {
            overtime = "1";
        } else {
            overtime = "0";
        }
        homework.setOvertime(overtime);
        if (data.get("new").equals("1")) {
            int index = homeworkService.AddHomework(homework);
            return "作业发布成功";
        } else {
            int homeworkId = Integer.parseInt(data.get("homeworkId"));
            homework.setId(homeworkId);
            int index = homeworkService.updateHomework(homework);
            return "修改成功";
        }

    }
    /**
     * 老师发布公告与修改
     */
    @RequestMapping(value = "/informEdit")
    public String PublishInform(@RequestBody Map<String, String> data) throws ParseException {
        Inform inform = new Inform();
        inform.setCourse(Integer.parseInt(data.get("courseId")));
        inform.setName(data.get("name"));
        inform.setIntro(data.get("intro"));
        inform.setUser(Integer.parseInt(data.get("user")));
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        inform.setTime(df.format(new Date()));


        if (data.get("new").equals("1")) {
            inform.setTop("0");
            int index = informService.AddInform(inform);
            return "公告发布成功";
        } else {
            inform.setTop(data.get("top"));
            int informId = Integer.parseInt(data.get("informId"));
            inform.setId(informId);
            int index = informService.updateInform(inform);
            return "编辑成功";
        }

    }
    /**
     * 置顶与取消公告
     */
    @RequestMapping(value = "/setTop")
    public String SetTop(@RequestBody Map<String, String> data) throws ParseException {
        String top = "0";
        int id = Integer.parseInt(data.get("id"));
        int course = Integer.parseInt(data.get("course"));
        if (data.get("top").equals("0")) {
            top = "1";
        } else {
            top = "0";
        }
        int index = informService.STop(id,top,course);
        if (index == 1) {
            if (top.equals("1")) {
                return "置顶成功";
            } else {
                return "已取消置顶";
            }
        } else {
            return "失败";
        }

    }

    /**
     * 删除公告
     */
    @RequestMapping(value = "/informDelete")
    public String InformDelete(@RequestBody Map<String, String> data) throws ParseException {
        int informId = Integer.parseInt(data.get("informId"));
        informService.delete(informId);
        informCommentsService.delete(informId);
        return "删除成功";

    }


    /**
     * 作业删除
     * */
    @RequestMapping(value = "/homeworkDelete")
    public String HomeworkDelete(@RequestBody Map<String, String> data) throws ParseException {
        int homeworkId = Integer.parseInt(data.get("homeworkId"));
        homeworkService.delete(homeworkId);
        swService.delete(homeworkId);
        commentsService.delete(homeworkId);
        return "删除成功";
    }

    /**
     * 老师交作业作业
     * */
    @RequestMapping(value = "/homeworkSubmit")
    public String HandInHomework(@RequestBody Map<String,String> data) throws ParseException {
        String account = TokenUtil.getUsername(data.get("token"));
        Teacher student = loginteacherService.existenceAccount(account);
        String studentId = student.getAccount();
        int homework = Integer.parseInt(data.get("homeworkId"));
        Homework stu_homework = homeworkService.getHomeworkById(homework);
        Sw sw2 = swService.getHomework(studentId,homework);
        Sw sw = new Sw();
        sw.setStudent(studentId);
        sw.setHomework(homework);
        sw.setStatus("0");
        sw.setAnswer(data.get("answer"));
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        sw.setTime(df.format(new Date()));
        sw.setReview("0");

        if(stu_homework.getOvertime().equals("1")){
            ParsePosition pos = new ParsePosition(0);
            if(!new Date().before(df.parse(stu_homework.getEndTime(), pos))){
                return "超时不能提交";
            }

        }
        if(sw2==null) {
            swService.handIn(sw);
            return "提交成功";
        }else {
            sw.setId(sw2.getId());
            swService.update(sw);
            return "修改成功";
        }
    }

    /**
     * 老师查看学生作业
     * */
    @RequestMapping(value = "/homeworkShow")
    public Object ShowHomework(@RequestBody Map<String, String> data) throws ParseException, JsonProcessingException {
        int homeworkId = Integer.parseInt(data.get("homeworkId"));
        //已批人数
        int people = 0;
        //已交的作业
        List<StudentHomeWork> studentHomeWorks = studentHomeWorkService.getHomeWork(homeworkId);
        HashMap<String,Object> hs = new HashMap<>();
        hs.put("studentHomework",studentHomeWorks);
        for(StudentHomeWork studentHomeWork : studentHomeWorks){
            if(studentHomeWork.getReview().equals("1")){
                people++;
            }
        }
        hs.put("number",people);
        hs.put("homework",homeworkService.getHomeworkById(homeworkId));
        ObjectMapper objectMapper = new ObjectMapper();
        Homework homework = homeworkService.getHomeworkById(homeworkId);

        Course course = courseService.getCourseById(homework.getCourse());
        //课程信息
        hs.put("course",course);

        //学生未交作业
        List<Sc> sc = scService.getNumber(homework.getCourse());

        for (StudentHomeWork studentHomeWork : studentHomeWorks) {
            for(int h=0;h<sc.size();h++) {
                if((loginstudentService.existenceAccount(studentHomeWork.getAccount()))!=null) {
                    if (sc.get(h).getStudent().equals((loginstudentService.existenceAccount(studentHomeWork.getAccount())).getId())) {
                        sc.remove(h);
                        break;
                    }
                }
            }
        }
        List<Object> students = new ArrayList<Object>();
        for (Sc value : sc) {
            //未交作业的学生
            students.add(loginstudentService.getStudent(value.getStudent()));
        }

        //老师中学生角色未交作业
        List<Tc> tc = tcService.getNumber(homework.getCourse());
        for (StudentHomeWork studentHomeWork : studentHomeWorks) {
            for(int h=0;h<tc.size();h++) {
                if((loginteacherService.existenceAccount(studentHomeWork.getAccount()))!=null) {
                    if (tc.get(h).getTeacher().equals((loginteacherService.existenceAccount(studentHomeWork.getAccount())).getId())) {
                        tc.remove(h);
                        break;
                    }
                }
            }
        }
//        List<Teacher> teachers = new ArrayList<Teacher>();
        for(int i = 0; i < tc.size(); i++){
            //未交作业的老师
            students.add(loginteacherService.getTeacher(tc.get(i).getTeacher()));
        }
        //未交作业学生
        hs.put("unHandIn",students);

        return objectMapper.writeValueAsString(hs);
    }


    /**
     * 老师批改学生作业
     * */
    @RequestMapping(value = "/homeworkCheck")
    public String CorrectionHomework(@RequestBody Map<String,String> data){
        String student = data.get("studentId");
        int homeworkId = Integer.parseInt(data.get("homeworkId"));
        if(!data.get("score").equals("")){
            int score = Integer.parseInt(data.get("score"));
            //阅读并打分
            swService.scoringHomework(student,homeworkId,"1",score);
            if(Integer.parseInt(homeworkService.getHomeworkById(homeworkId).getMaxScore())<score){
                return "超过成绩最大值";
            }
            return "批改成功";
        }else {
            swService.readHomework(student,homeworkId,"1");
            return "请输入成绩";
        }

    }

    /**
     * 老师转让课程
     * */
    @RequestMapping(value = "/courseSend")
    public String CourseSend(@RequestBody Map<String,String> data) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        int teacherId = Integer.parseInt(data.get("id"));
        int courseId = Integer.parseInt(data.get("courseId"));
        Teacher teacherPlus = loginteacherService.teachersName(Integer.parseInt(data.get("id")));
        Teacher teacher = loginteacherService.SignIn(data.get("account"));
        Student student = loginstudentService.SignIn(data.get("account"));
        MD5 md5 = new MD5();
        if(teacherPlus.getPassword().equals(md5.encoderByMd5(data.get("password")))){
            if(student!=null){
                return "转让人不能为学生";
            }else {
                if (teacher == null) {
                    return "邀请人不存在";
                }else {
                    if(teacherId==teacher.getId()){
                        return "不能转让给自己";
                    }else {
                        tcService.deleteCourse(teacherId,courseId);
                        //查看转让的人是否在此课程组
                        Tc tc = tcService.getTc(teacher.getId(),courseId);
                        if(tc==null){
                            List<Tc> tcTeacher = tcService.getListTc(teacher.getId());
                            Tc tc1 = new Tc();
                            tc1.setTeacher(teacher.getId());
                            tc1.setCourse(courseId);
                            tc1.setPigeonhole("0");
                            tc1.setTop("1");
                            tc1.setRole("管理员");
                            if (tcTeacher == null || tcTeacher.size() == 0) {
                                tc1.setDisplayNum(1);
                            } else {
                                tc1.setDisplayNum(tcTeacher.size() + 1);
                            }
                            tcService.joinCourse(tc1);
                        }else {
                            tc.setRole("管理员");
                            tcService.update(tc);
                        }
                        courseService.update(courseId,teacherId,teacher.getId());
                    }
                }
                return "转让成功";
            }
        }else {
            return "密码错误";
        }

    }

    /**
     * 老师删除成员
     * */
    @RequestMapping(value = "/deleteUser")
    public String DeleteUser(@RequestBody Map<String,String> data) throws QiniuException {
        int courseId = Integer.parseInt(data.get("courseId"));
        Teacher teacher = loginteacherService.SignIn(data.get("account"));
        Student student = loginstudentService.SignIn(data.get("account"));
        Course course = courseService.getCourseById(courseId);

        if(student!=null){
            scService.deleteCourse(student.getId(),courseId);
            for(Homework homework : homeworkService.getHomework(courseId)) {
                String accounts = loginstudentService.getStudent(student.getId()).getAccount();
                int homeId = homework.getId();

                //文件删除
                for (SwFile swFile : swFileService.getHomeworkFile(accounts,homeId)) {
                    String name = accounts + "-" + homeId + "-" + swFile.getFilename();
                    qiNiuSupport.delete(Chines2PingUtils.getFullSpell(name));
                }
                //删除学生课程中的作业
                int index2 = swService.deleteHomework(accounts, homeId);
                int index5 = swFileService.deleteHomework(accounts, homeId);
                //删除学生课程中的作业评论
                int index3 = commentsService.deleteUserComments(accounts,homeId);



            }

            for (Inform inform : informService.getInform(courseId)){
                //删除学生公告中评论
                int index4 = informCommentsService.deleteUserComments(loginstudentService.getStudent(student.getId()).getAccount(),inform.getId());
            }
            return "删除成功";
        }else{
            if(course.getMaster().equals(teacher.getId())){
                return "不能删除管理员";
            }else {
                tcService.deleteCourse(teacher.getId(),courseId);
                for(Homework homework : homeworkService.getHomework(courseId)) {
                    String accounts = loginteacherService.getTeacher(teacher.getId()).getAccount();
                    int homeId = homework.getId();
                    //文件删除
                    for (SwFile swFile : swFileService.getHomeworkFile(accounts,homeId)) {
                        String name = accounts + "-" + homeId + "-" + swFile.getFilename();
                        qiNiuSupport.delete(Chines2PingUtils.getFullSpell(name));
                    }

                    //删除老师课程中的作业
                    int index2 = swService.deleteHomework(accounts, homeId);
                    int index5 = swFileService.deleteHomework(accounts, homeId);
                    //删除老师课程中的作业评论
                    int index3 = commentsService.deleteUserComments(accounts,homeId);
                }

                for (Inform inform : informService.getInform(courseId)){
                    //删除老师公告中评论
                    int index4 = informCommentsService.deleteUserComments(loginteacherService.getTeacher(teacher.getId()).getAccount(),inform.getId());
                }
                return "删除成功";
            }
        }


    }
}
