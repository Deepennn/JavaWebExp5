package ctrl;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

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

import dao.*;
import pojo.*;

/**
 * Servlet implementation class GetOrder
 */
@WebServlet("/GetOrder")
public class GetOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}   
 	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
 		request.setCharacterEncoding("UTF-8");
 		response.setCharacterEncoding("UTF-8");
 		response.setContentType("text/html;charset=UTF-8");
 		HttpSession session=request.getSession();
 		PrintWriter out = response.getWriter();
 		
 		ProductDao productDao;
 		OrderDao orderDao;
 		UserDao userDao;
		SqlSession sqlSession;
		String resource = "mybatis-config.xml";
		// 读取配置文件
		InputStream is = Resources.getResourceAsStream(resource);
		// 构建SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		// 获取sqlSession
		sqlSession = sqlSessionFactory.openSession();
		productDao = new ProductDaoImpl(sqlSession);
		orderDao = new OrderDaoImpl(sqlSession);
		userDao = new UserDaoImpl(sqlSession);
		
		if(session.getAttribute("user_id")==null) {
 			request.setAttribute("msg","登陆过期，请重新登陆");
			request.getRequestDispatcher("loginagain.jsp").forward(request, response);
			return;
 		}
		
		/*
		 * 订单表单
		 * */
		String msg = request.getAttribute("msg")==null?"":(String)request.getAttribute("msg");
		
 		String orderFormHead=
 				"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n" + 
 				"<html>\r\n" + 
 				"<head>\r\n" + 
 				"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n" + 
 				"<title>我的订单</title>\r\n" + 
 				"</head>\r\n" + 
 				"<body>\r\n" + 
 				"<p align=\"right\">\r\n" + 
 				"<a href=\"GetOrder\">[我的订单]</a>\r\n" + 
 				"<a href=\"GetProduct\">[本站产品]</a>\r\n" + 
 				"<a href=\"login.jsp\">[退出]</a>\r\n" + 
 				"<center>\r\n" +
 				"<h1 align=\"center\"><span style=\"color:green\">"+
 				msg + 
 				"</span></h1>" +
 				"<h1>订单列表：</h1>\r\n" +
 				"<form action=\"GetOrder\" method=\"post\">\r\n" +
 		        "<table border=1 align=center>\r\n" +
 		        "<th>订单号<th>产品<th>用户<th>金额<th>下单时间<th>选择\r\n";
 				
 		String orderFormEnd=
  				"</table>\r\n" +
  				"<br>\r\n" +
  		 		"<input type=\"submit\" value=\"删除\">\r\n" +
  			    "</form>\r\n" +
 				"</center>\r\n" + 
 				"</body>\r\n" +
 				"</html>";
 		
 		//删除选定订单
 		String[] paramValues = request.getParameterValues("selectedOrder");
 		if(paramValues!=null) {
 			for(int i=0; i<paramValues.length; i++) {
 			  orderDao.deleteOrderById(Integer.valueOf(paramValues[i]));
 			  sqlSession.commit();//提交事务
			}
 		}

		out.print(orderFormHead);
 		
		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
		
 		List<Order> allOrder = orderDao.selectOrderByUser_id((int)session.getAttribute("user_id"));
 		out.println();
			for(Order order:allOrder){
			out.println(
					"<tr><td>" + order.getOrder_id() + //订单号
					"<td>" + productDao.selectProductById(order.getProduct_id()).getProduct_name() + //产品
					"<td>" + userDao.selectUserById((int)session.getAttribute("user_id")).getUserName() + //用户
					"<td>" + productDao.selectProductById(order.getProduct_id()).getPrice() + //金额
					"<td>" + sdf.format(order.getTime()) + //下单时间
					"<td><input type=\"checkbox\" name=\"selectedOrder\" value="+order.getOrder_id()+">"
					);
		}
 		
 		out.print(orderFormEnd);
 		
	}
 	
}
