<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆 · 注册</title>
</head>
<body>

	<H1 align="center">
		<font color="black">登陆 · 注册</font>
	</H1>

	<form method="post" action="GetLogin">
		<p align="center">
			用户名： <input type="text" name="username">
		<p align="center">
			密码： <input type="password" name="pwd">
		<p align="center">
			<input type="submit" name="login" value="登陆"> <input
				type="submit" name="login" value="注册"> <br> <br> <input
				type="reset" value="重置">
		</p>
</body>
</html>