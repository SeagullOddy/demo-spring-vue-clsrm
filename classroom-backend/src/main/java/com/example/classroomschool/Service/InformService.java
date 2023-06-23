package com.example.classroomschool.Service;


import com.example.classroomschool.Dao.InformMapper;
import com.example.classroomschool.Model.Inform;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InformService {
    @Resource
    InformMapper informMapper;
    public int AddInform(Inform inform) {
        return informMapper.insert(inform);
    }

    public int updateInform(Inform inform) {
        return informMapper.updateByPrimaryKey(inform);
    }

    public List<Inform> getInform(int course) {
        return informMapper.selectInform(course);
    }


    public int STop(int id,String top,int course) {
        return informMapper.SetTheTop(id,top,course);
    }

    public int delete(int informId) {
        return informMapper.deleteByPrimaryKey(informId);
    }

    public Inform getInformById(int InformId) {
        return informMapper.selectByPrimaryKey(InformId);
    }
}
