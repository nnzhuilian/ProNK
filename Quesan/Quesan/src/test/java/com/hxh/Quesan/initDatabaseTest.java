package com.hxh.Quesan;

import com.hxh.Quesan.dao.QuestionDAO;
import com.hxh.Quesan.dao.UserDAO;
import com.hxh.Quesan.model.EntityType;
import com.hxh.Quesan.model.Question;
import com.hxh.Quesan.model.User;
import com.hxh.Quesan.service.FollowService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes =QuesanApplication.class)
@Sql("/init.sql")
public class initDatabaseTest {
	@Autowired
	UserDAO userDAO;
	@Autowired
	QuestionDAO questionDAO;
	@Autowired
	FollowService followService;
	@Test
	public void initDatabase() {
		Random r=new Random();
		for(int i=0;i<11;i++){
			User user=new User();
			user.setName(String.format("User%d",i));
			user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",r.nextInt(1000)));
			user.setPassword("");
			user.setSalt("");
			userDAO.addUser(user);

			//互相关注
			for(int j=1;j<i;j++){
				followService.follow(EntityType.USER,i,j);
			}
			user.setPassword("12345");
			userDAO.updatePassword(user);

			Question question=new Question();
			question.setCommentCount(i);
			Date date=new Date();
			date.setTime(date.getTime()+1000 * 3600 * 5 * i);
			question.setCreatedDate(date);
			question.setUserId(i+1);
			question.setTitle(String.format("Title%d",i));
			question.setContent(String.format("zyl%dboyunjianriweilaikeqi",i));
			questionDAO.addQuestion(question);
			//questionDAO.selectLatestQuestions(3,2,2);
/*			for(int j=1;j<i;j++){
				followService.follow(EntityType.Comment_to_Question,i,j);
			}*/
		}
		Assert.assertEquals("12345",userDAO.selectById(1).getPassword());
		userDAO.deleteById(1);
		Assert.assertNull(userDAO.selectById(1));
		System.out.print(questionDAO.selectLatestQuestions(0,0,8));
	}

}
