package com.example.classroomschool.Service;

import com.example.classroomschool.Dao.PictureMapper;
import com.example.classroomschool.Model.Picture;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PictureService {
    @Resource
    PictureMapper pictureMapper;
    public int inserPic(Picture picture) {
        return pictureMapper.insert(picture);
    }

    public Picture getAddress(String account) {
        return pictureMapper.selectAddress(account);
    }
}
