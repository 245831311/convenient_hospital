<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- <%@ taglib uri="/struts-tags" prefix="s" %> --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册页面</title>
<link type="text/css" href="./css/register.css"  rel="stylesheet">
<script type="text/javascript">
	function authImg()
	{
		document.getElementById("register_btn").submit();
	}

</script>
</head>



<body>
<div id="header">
    	<div class="logo">
        	OnlineCource
        </div>
        <div class="r_help">
        	<ul>
            	<li><a href="${pageContext.request.contextPath}">首页</a></li>
                |
            	<li><a href="${pageContext.request.contextPath}">帮助</a></li>
            </ul>
        </div>	
    	<div class="clear"></div>
</div>
<div id="middle">
    	<div class="logo_img">
        	<img src="../image/logo.jpg" height="130px"/>
        </div>
         <hr/>
            <div class="r_remind">
            
                <font color="#FF0000">提示 : </font><font color="#96F" size="-1">如果已有账号,可用账号直接登陆</font><button type="button" name="button" class="login_btn" onclick="window.location.href='${pageContext.request.contextPath}/user/login.jsp'">登陆</button></b>
               
             </div>
         <hr />
		<div class="r_signin">
			<form action="register!register.do" method="post">            
				<div id="r_login" class="r">
              		<span class="r-sign">填写登录名和密码</span>
               		<div class="field">
                   		<span class="star">*</span>登陆用户:
                   		<span class="r_input">
                        	<input type="text" name="user.username" value="${user.username}"/>
                  		 <s:if test="#request.errorInfo==null">
		              	 <font color="red">6-20位</font>   	   	 
	            	   	</s:if>
	            	   	<s:elseif test="#request.errorInfo.username=='通过'">
	            	   		<font color="green">${errorInfo.username }</font>	
	            	   	</s:elseif>
	            	   	<s:else>
	            	   		<font color="red">${errorInfo.username }</font>	
	            	   	</s:else>
                  		 </span>
               		</div>
	                   <div class="field"><span class="star">*</span>创建密码:<span class="r_input"><input type="password" name="user.password" value="${user.password }"/>
	                   <s:if test="#request.errorInfo==null">
		              	 <font color="red">6-20位</font>   	   	 
	            	   	</s:if>
	            	   	<s:elseif test="#request.errorInfo.password=='通过'">
	            	   		<font color="green">${errorInfo.password }</font>	
	            	   	</s:elseif>
	            	   	<s:else>
	            	   		<font color="red">${errorInfo.password }</font>	
	            	   	</s:else>
	                   </span></div>
	    			   <div class="field"><span class="star">*</span>确认密码:<span class="r_input"><input type="password" name="password2" value="${password2}"/> <font color="red">必填&nbsp&nbsp</font> </span></div>
            	</div>
            	<div id="r_myinfo" class="r">
		                <span class="r-sign">填写个人资料</span>
		               <div class="field">昵称:<span class="r_input"><input type="text" name="nickname"/>&nbsp&nbsp</span></div>
		               <div class="field"><span class="star">*</span>个人邮箱:<span class="r_input"><input type="text" name="user.email" value="${user.email }"/>
		               <s:if test="#request.errorInfo==null">
		              	 <font color="red">密码找回</font>   	   	 
	            	   	</s:if>
	            	   	<s:elseif test="#request.errorInfo.email=='通过'">
	            	   		<font color="green">${errorInfo.email }</font>	
	            	   	</s:elseif>
	            	   	<s:else>
	            	   		<font color="red">${errorInfo.email }</font>	
	            	   	</s:else>
	            	   	 </span></div>
		        </div>
				<div id="r_checkcode" class="r">
	                <span class="r-sign">填写验证码</span>
	                <div class="field">
	                	<span class="star">*</span>
	            	   	 验证码:<span><input type="text" name="checkcode"/>
	            	   	 <img src="${pageContext.request.contextPath}/servlet/CheckCode"><a href="#" onclick="authImg()">换一张</a>
						 </span>
	            	</div>
  					<div class="btn">
	                	<div class="left">
		                	  <input type="submit" value="注册" name="register_btn" class="register_btn" id="register_btn"/>
		                	  <span class="right">
		                	  <input type="reset" value="重置" name="reset_btn" class="reset_btn"/>
		                	  </span>
	   	      			</div>
	   	      		</div>
         			 <div class="clear"></div>
         		</div>
    </form>
          
  </div>
</div>
<s:debug></s:debug>
</body>
</html>
