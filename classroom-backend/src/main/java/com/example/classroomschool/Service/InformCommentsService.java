package com.example.classroomschool.Service;

import com.example.classroomschool.Dao.InformCommentsMapper;
import com.example.classroomschool.Model.Comments;
import com.example.classroomschool.Model.InformComments;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InformCommentsService {
    @Resource
    InformCommentsMapper informCommentsMapper;
    public List<InformComments> getComments(int informId) {
        return informCommentsMapper.selectComments(informId);
    }
    public void setComments(InformComments comments){
        informCommentsMapper.insert(comments);
    }

    public int deleteComments(Integer id){
        return informCommentsMapper.deleteByPrimaryKey(id);
    }

    public int deleteUserComments(String account, Integer informId) {
        return informCommentsMapper.deleteByMap(account,informId);
    }

    public int delete(Integer informId) {
        return informCommentsMapper.deleteInformComment(informId);
    }
}
