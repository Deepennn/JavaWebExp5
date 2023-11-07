package ctrl;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import pojo.*;
import util.IdGenerator;
import dao.*;

/**
 * Servlet implementation class GetLogin
 */
@WebServlet("/GetLogin")
public class GetLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}    
 	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
 		request.setCharacterEncoding("UTF-8");
 		response.setCharacterEncoding("UTF-8");
 		HttpSession session=request.getSession();
 		
 		UserDao userDao;
		SqlSession sqlSession;
		String resource = "mybatis-config.xml";
		// 读取配置文件
		InputStream is = Resources.getResourceAsStream(resource);
		// 构建SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		// 获取sqlSession
		sqlSession = sqlSessionFactory.openSession();
		userDao = new UserDaoImpl(sqlSession);
 		
 		//获取登陆/注册区分标记
 		String login=request.getParameter("login");
 		
 		//创建临时User对象
 		User user = new User(
 				IdGenerator.generateSnowflakeId(),//id
 				request.getParameter("username"),//userName
 				request.getParameter("pwd")//password
 				);
 		
		//初始化后继url为前驱url
 		String forwardUrl="login.jsp";
 		
 		//从数据库中查询是否有用户名匹配的用户
		User user_db = userDao.selectUserByUserName(user.getUserName());
 		
 		if(login.equals("登陆")) {//登陆逻辑
 			
 	 		if (user_db!=null&&user_db.getPassword()!=null&&user_db.getPassword().equals(user.getPassword())){
 	 			//有效登陆
 	 			session.setAttribute("user_id", user_db.getId());//原来的id（user_db的id）
 	 			request.setAttribute("msg","登陆成功，"+user.getUserName()+"，欢迎您来");
 	 			forwardUrl="GetOrder";
 	 		}
 	 		else{
 	 			//请用户重新登录
 	 			request.setAttribute("msg","用户名或密码错误，请重新登陆");
 	 			forwardUrl="loginagain.jsp";
 	 		} 			
 		}else if(login.equals("注册")) {//注册逻辑
 			
 			if (user_db==null&&user.getUserName()!=null&&user.getPassword()!=null&&!user.getUserName().equals("")&&!user.getPassword().equals("")){
 				//有效注册
 				userDao.insertUser(user);
 				sqlSession.commit();//提交事务

				session.setAttribute("user_id", user.getId());//新的id
				request.setAttribute("msg","注册成功，"+user.getUserName()+"，欢迎您来");
 				forwardUrl="GetOrder";
 			}else {
 				//请用户重新注册
 				request.setAttribute("msg","用户名已存在、用户名为空或密码为空，请重新注册");
 				forwardUrl="loginagain.jsp";
 			}
 		}else {
 			//出现异常，返回前驱url
 			forwardUrl="login.jsp";
 		}
 		//转发到对应页面
 		RequestDispatcher dispatcher =
	 				request.getRequestDispatcher(forwardUrl);
	 				dispatcher.forward(request, response);
	 				return;
	}
 	
}