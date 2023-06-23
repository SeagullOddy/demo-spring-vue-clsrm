package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.classroomschool.Dao")
public class ClassroomschoolApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClassroomschoolApplication.class, args);
    }
}
