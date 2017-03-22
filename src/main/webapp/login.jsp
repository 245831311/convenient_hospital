<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@page import="java.util.Date" %>
<%@include file="page/base/tag.jsp" %>
<html>
<head>
		<meta charset="UTF-8">
		<title>后台登录界面</title>
		<link href="${baseurl }/css/reset.css" rel="stylesheet" />
		<link href="${baseurl }/plugins/font-awesome/css/font-awesome.css" rel="stylesheet" />
		<link href="${baseurl }/plugins/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link href="${baseurl }/css/login.css" rel="stylesheet" />
	</head>

<body>
		<!--头部-->
		<div class="top">
			<div class="fr">
				<p><a href="javascript:">便捷医疗系统介绍</a></p>
				<ul>
					<li><i class="icon-phone"></i>15766954687</li>
					<li><i class="icon-envelope"></i>245831311@qq.com</li>
				</ul>
			</div><%-- <img src="${baseurl }/images/base/loginLogo.png" class="fl" /> --%></div>
		<!--登录部分-->
		<div class="loginMain">
			<div class="fl"><img src="${baseurl }/images/base/loginImg.png"></div>
			<div class="fr">
				<p class="tit"><img src="${baseurl }/images/base/loginBtn.png"></p>
				<form class="loginInput">

					<label>
						<input id="username" type="text" class="inpu" placeholder="手机号/固定号码" />
						<i class="icon-user"></i>
					</label>
					
					<label>
						<input id="password" type="password" class="inpu" placeholder="请输入密码" />
						<i class="icon-lock"></i>
					</label>
					<label>
						<input id="code" type="text" class="icheck" placeholder="请输入验证码" />
						<i class="icon-check"></i>
						<i class="icon-refresh"></i>
						<img id="refreshCode" src="${baseurl }/validatecode.jsp?time=<%=new Date().getTime() %>" />
					</label>
					<p class="loginWarning" style="display:none;"><i class="icon-exclamation-sign"></i><span>号码有误，该号码未开通会议业务</span></p>
					
				</form>
				<a href="javascript:;" class="loginButton">登录</a>
					<a href="javascript:;" class="findPassword">忘记密码</a>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="${baseurl }/plugins/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="${baseurl }/js/base/login.js"></script>
</html>