package com.example.classroomschool.Service;

import com.example.classroomschool.Dao.CommentsMapper;
import com.example.classroomschool.Model.Comments;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentsService {
    @Resource
    CommentsMapper commentsMapper;

    public List<Comments> getComments(Integer homeworkId){
        return commentsMapper.selectComments(homeworkId);
    }
    public void setComments(Comments comments){
        commentsMapper.insert(comments);
    }

    public int deleteComments(Integer id){
        return commentsMapper.deleteByPrimaryKey(id);
    }

    public int deleteUserComments(String account, Integer homeworkId) {
        return commentsMapper.deleteByMap(account,homeworkId);
    }

    public int delete(Integer homeworkId) {
        return commentsMapper.deleteComments(homeworkId);
    }
}
