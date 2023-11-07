package ctrl;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
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
import util.*;

/**
 * Servlet implementation class GetProduct
 */
@WebServlet("/GetProduct")
public class GetProduct extends HttpServlet {
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
		
 		if(session.getAttribute("user_id")==null) {
 			request.setAttribute("msg","登陆过期，请重新登陆");
			request.getRequestDispatcher("loginagain.jsp").forward(request, response);
			return;
 		}
		
		/*
		 * 产品表单
		 * */
 		String productFormHead=
 				"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n" + 
 				"<html>\r\n" + 
 				"<head>\r\n" + 
 				"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n" + 
 				"<title>本站产品</title>\r\n" + 
 				"</head>\r\n" + 
 				"<body>\r\n" + 
 				"<p align=\"right\">\r\n" + 
 				"<a href=\"GetOrder\">[我的订单]</a>\r\n" + 
 				"<a href=\"GetProduct\">[本站产品]</a>\r\n" + 
 				"<a href=\"login.jsp\">[退出]</a>\r\n" + 
 				"<center>\r\n" +
 				"<h1>产品列表：</h1>\r\n" +
 				"<form action=\"GetProduct\" method=\"post\">\r\n" +
 		        "<table border=1 align=center>\r\n" +
 		        "<th>产品号<th>产品名<th>产品价格<th>选择\r\n";
 				
 		String productFormEnd=
  				"</table>\r\n" +
  				"<br>\r\n" +
  		 		"<input type=\"submit\" value=\"下单\">\r\n" +
  			    "</form>\r\n" +
 				"</center>\r\n" + 
 				"</body>\r\n" +
 				"</html>";
 		
 		//将选定商品封装成订单
 		String[] paramValues = request.getParameterValues("selectedProduct");
 		if(paramValues!=null) {
 			for(int i=0; i<paramValues.length; i++) {
// 			  productDao.deleteProductById(Integer.valueOf(paramValues[i]));
 				Order order = new Order(
 						IdGenerator.generateSnowflakeId(),//order_id
 						Integer.valueOf(paramValues[i]),//product_id
 						(int)session.getAttribute("user_id"),//user_id
 						new Date()//time
 						);
 				orderDao.insertOrder(order);
 				sqlSession.commit();//提交事务
			}
 		}

		out.print(productFormHead);
 		
 		List<Product> allProduct = productDao.selectAllProduct();
 		out.println();
			for(Product product:allProduct){
			out.println(
					"<tr><td>" + product.getId() + //产品号
					"<td>" + product.getProduct_name() + //产品名
					"<td>" + product.getPrice() + //产品价格
					"<td><input type=\"checkbox\" name=\"selectedProduct\" value="+product.getId()+">"
					);
		}
 		
 		out.print(productFormEnd);
 		
	}
 	
}
