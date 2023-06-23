package com.example.classroomschool.Service;

import com.example.classroomschool.Dao.SwFileMapper;
import com.example.classroomschool.Model.SwFile;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SwFileService {
    @Resource
    SwFileMapper swFileMapper;
    public List<SwFile> getHomeworkFile(String studentId, int homework) {
        return swFileMapper.selectHomework(studentId,homework);
    }

    public int handIn(SwFile swFile) {
        return swFileMapper.insert(swFile);
    }

    public int update(SwFile swFile) {
        return swFileMapper.updateByPrimaryKey(swFile);
    }


    public int delete(int id) {
        return swFileMapper.deleteByPrimaryKey(id);
    }

    public int deleteHomework(String account, Integer id) {
        return  swFileMapper.deleteByMap(account,id);
    }

    public int deleteFile(Integer homework) {
        return swFileMapper.deleteAllFile(homework);
    }

    public List<SwFile> getHomeworkFileByHomeworkId(Integer homework) {
        return swFileMapper.selectHomeworkByHomeworkId(homework);
    }
}
