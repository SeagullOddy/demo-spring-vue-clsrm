package com.example.classroomschool;

import com.example.classroomschool.service.CourseWithTCService;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClassroomschoolApplicationTests {

    @Resource
    private CourseWithTCService courseWithTCService;

    @Test
    void contextLoads() {
        System.out.println(courseWithTCService.findByTeacherId(3));
    }
}
