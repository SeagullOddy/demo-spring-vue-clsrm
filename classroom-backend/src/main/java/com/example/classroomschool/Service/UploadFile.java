package com.example.classroomschool.Service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFile {
    String uploadFile(MultipartFile file);
}
