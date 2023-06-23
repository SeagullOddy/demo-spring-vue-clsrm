package com.example.classroomschool.Controller.File;

import com.example.classroomschool.Model.Homework;
import com.example.classroomschool.Model.SwFile;
import com.example.classroomschool.Service.HomeworkService;
import com.example.classroomschool.Service.QiNiuSupport;
import com.example.classroomschool.Service.SwFileService;
import com.qiniu.common.QiniuException;

import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1")
public class files {

    @Resource
    QiNiuSupport qiNiuSupport;
    @Resource
    HomeworkService homeworkService;
    @Resource
    SwFileService swFileService;

        /**
         * 根据文件名删除

         */
        @RequestMapping("delete")
        public String delete(@RequestBody Map<String,String> data) throws QiniuException {
            String name = data.get("studentId")+"-"+data.get("homeworkId")+"-"+data.get("name");
            qiNiuSupport.delete(Chines2PingUtils.getFullSpell(name));
            swFileService.delete(Integer.parseInt(data.get("id")));
            return "删除成功";
        }

        /**
         * 直接传输文件 到七牛

         */
        @RequestMapping("/upload")
        public String upload(@RequestParam String homeworkIds,@RequestParam String studentIds,@RequestParam MultipartFile file) throws Exception {

            int homework = Integer.parseInt(homeworkIds);
            Homework stu_homework = homeworkService.getHomeworkById(homework);
            List<SwFile> swFilk2 = swFileService.getHomeworkFile(studentIds,homework);


            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");//设置日期格式
            if(stu_homework.getOvertime().equals("1")){
                ParsePosition pos = new ParsePosition(0);
                if(!new Date().before(df.parse(stu_homework.getEndTime(), pos))){
                    return "超时不能提交";
                }

            }
                SwFile swFile = new SwFile();
                swFile.setFileSize(new BigDecimal(file.getSize() / 1024).setScale(2, BigDecimal.ROUND_DOWN).doubleValue());
                swFile.setStudent(studentIds);
                swFile.setHomework(homework);
                swFile.setFilename(file.getOriginalFilename());
                swFile.setFileurl(qiNiuSupport.uploadFile(homeworkIds,studentIds,file));
                swFileService.handIn(swFile);
            return "提交成功";

        }

        /**
         * 七牛云文件下载
         *
         *
         */
        @RequestMapping("/file/{filename}")
        public String download(@PathVariable("filename") String filename) {
            if (filename.isEmpty()) {
                return "null";
            }
            try {
                String privateFile = qiNiuSupport.getPublicFile(filename);
                return privateFile;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }



}
