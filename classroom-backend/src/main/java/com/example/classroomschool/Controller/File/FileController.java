//package com.example.classroomschool.Controller.File;
//
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.File;
//import java.io.IOException;
//import java.util.UUID;
//
///**
// * 文件上传
// */
//@RestController
//@RequestMapping("/api/v1")
//public class FileController {
//
//    @GetMapping(value = "/file")
//    public String file() {
//        return "file";
//    }
//
//    @PostMapping(value = "/upload")
//    public String fileUpload(@RequestParam(value = "file") MultipartFile file, Model model, HttpServletRequest request) {
//        if (file.isEmpty()) {
//            System.out.println("文件为空空");
//        }
//        String fileName = file.getOriginalFilename();  // 文件名
//        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
//        String filePath = "D://ktpPhotos//"; // 上传后的路径
//        fileName = UUID.randomUUID() + suffixName; // 新文件名
//        File dest = new File(filePath + fileName);
//        if (!dest.getParentFile().exists()) {
//            dest.getParentFile().mkdirs();
//        }
//        try {
//            file.transferTo(dest);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String filename = "/ktpPhotos/" + fileName;
//        model.addAttribute("filename", filename);
//        return "file";
//    }
//}
