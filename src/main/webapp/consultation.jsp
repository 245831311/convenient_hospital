<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>医生咨询</title>
    <link type="text/css" href="./css/doctor_intro.css"  rel="stylesheet">
    <link type="text/css" href="./css/consult.css"  rel="stylesheet">
    </head>
  <body>
    <div id="header">
    <div class="inner">
        <h1 class="logo"><a href="./consultation.jsp" ka="header-home-logo" title="便捷医疗"><span>便捷医疗</span></a></h1>
        <div class="nav">
            <ul>
                <li class="cur"><a ka="header-home" href="./main.html" style="padding:5px;font-size:20px">首页</a></li>
            </ul>
        </div>
        
        <div class="user-nav">
                <!--未登录-->
                <div class="btns"><a href="/user/signup.html" ka="header-register" class="btn btn-outline">注册</a><a href="/user/login.html" ka="header-login" class="btn btn-outline">登录</a></div>
        </div>
    </div>
    <div class="search-box" >
                    <div class="search-form" style="margin-left:550px">
                            <div class="search-form-con">
                                <p class="ipt-wrap"><input type="text" ka="search-keyword" name="query" autocomplete="off" class="ipt-search" placeholder="搜索医生" style="height:48px;font-size:20px;"></p>
                            </div>
                            <button type="submit" class="btn btn-search" ka="search-btn">搜索</button>
                    </div>
                    <div class="search-hot" style="margin-left:550px">
                        <b>科目:</b>
                        
                            <a href="#" ka="hot-position-1" id="eye">眼科</a>

                            <a href="#" ka="hot-position-2" id="surgery">外科</a>
                        
                            <a href="#" ka="hot-position-3" id="orthopaedics">骨科</a>
                        
                            <a href="#" ka="hot-position-4" id="mouth">口腔科</a>
                        
                            <a href="#" ka="hot-position-5" id="medicine">内科</a>
                        
                            <a href="#" ka="hot-position-6" id="psychology">心理科</a>
                        
                        
                    </div>
                </div>
    <div class="container">
    	<div class="whole" style="width: 80%;
    margin-left: 250px">
        	<ul>
            	<div style="margin-bottom:40px">
                    	<li>
                              <div class="left"><img src="./images/doctor/doctor.jpg"/></div>
                              
                                <div class="middle right">
                                	<div class="name" > 陈景明</div>
                                
                                    <div class="scope" >年龄:35岁</div>
                                    <div class="age" >入职时间:2007</div>
                                    <div class="major">就医时间:10年</div>
                                    <div class="intro">简介:XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                                    XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</div>
                                	<div class="infoRight" >
                                		<a href="./doctor_info.jsp" target="_blank" ka="header-register" class="btn btn-outline">详情</a>
                                		&nbsp;&nbsp;&nbsp;
                                		<a href="/user/login.html" ka="header-login" class="btn btn-outline">预约挂号</a>
                                		&nbsp;&nbsp;&nbsp;
                                		<a href="/user/login.html" ka="header-login" class="btn btn-outline">在线咨询</a>
                               		</div>
                                </div>
                   		</li>
                </div>
                <div style="margin-bottom:40px">
                    	<li>
                              <div class="left"><img src="./images/doctor/doctor.jpg"/></div>
                              
                                <div class="middle right">
                                	<div class="name" > 梁新</div>
                                
                                    <div class="scope" >年龄:29岁</div>
                                    <div class="age" >入职时间:2013</div>
                                    <div class="major">就医时间:4年</div>
                                    <div class="intro">简介:XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                                    XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</div>
                                	<div class="infoRight" >
                                		<a href="./doctor_info.jsp" target="_blank" ka="header-register" class="btn btn-outline">详情</a>
                                		&nbsp;&nbsp;&nbsp;
                                		<a href="/user/login.html" ka="header-login" class="btn btn-outline">预约挂号</a>
                                		&nbsp;&nbsp;&nbsp;
                                		<a href="/user/login.html" ka="header-login" class="btn btn-outline">在线咨询</a>
                               		</div>
                                </div>
                   		</li>
                </div>    
            </ul>
        </div>
    
    </div>
  </body>
 
</html>
