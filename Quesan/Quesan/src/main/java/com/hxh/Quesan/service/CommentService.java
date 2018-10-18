package com.hxh.Quesan.service;

import com.hxh.Quesan.dao.CommentDAO;
import com.hxh.Quesan.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class CommentService {
@Autowired
    CommentDAO commentDAO;
@Autowired
    SensivtiveService sensivtiveService;
public List<Comment> getComments(int entityId, int entityType){
    return commentDAO.selectcommentByentity(entityId,entityType);
}
public int getCommentCount(int entityId,int entityType){
    return commentDAO.getCommentcount(entityId,entityType);
}
public int addComment(Comment comment){
    comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
    comment.setContent(sensivtiveService.filter(comment.getContent()));
    return commentDAO.addComment(comment)>0?comment.getId():0;
}
public boolean deleteComment(int id){
    return commentDAO.updateStatus(id,1)>0;
}
}
