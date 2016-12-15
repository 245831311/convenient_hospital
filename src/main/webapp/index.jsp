<%@page import="com.bkybk.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%> --%>
<%@include file="page/base/tag.jsp" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>云视讯平台</title>
		
		<link href="${baseurl }/plugins/font-awesome/css/font-awesome.css" rel="stylesheet" />
		<link href="${baseurl }/plugins/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link href="${baseurl }/css/reset.css" rel="stylesheet" />
		
		<!--[if IE 7]>
		  <link rel="stylesheet" href="${baseurl }/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->

		<!-- ace styles -->

		<link rel="stylesheet" href="${baseurl }/plugins/assets/css/ace.min.css" />
		<link rel="stylesheet" href="${baseurl }/plugins/assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="${baseurl }/plugins/assets/css/ace-skins.min.css" />
		<link href="${baseurl }/css/common.css" rel="stylesheet" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="${baseurl }/plugins/assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="${baseurl }/plugins/assets/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="${baseurl }/plugins/assets/js/html5shiv.js"></script>
		<script src="${baseurl }/plugins/assets/js/respond.min.js"></script>
		<![endif]-->
	</head>

	<body style="overflow: hidden;">
		<!--头部导航-->
		<div class="navbar navbar-default top" id="navbar">
			<script type="text/javascript">
				try {
					ace.settings.check('navbar', 'fixed')
				} catch(e) {}
			</script>

			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<img src="images/base/loginLogo.png" class="fl" />
					<!-- /.brand -->
				</div>
				<!-- /.navbar-header -->

				<div class="navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">

						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="${baseurl }/plugins/assets/avatars/user.jpg" alt="Jason's Photo" />
								<span class="user-info">
									<small>欢迎光临,</small>
									<%-- <%=((User)session.getAttribute("web_conference_user")).getName() %> --%>
									${ web_conference_user.name}
									<%-- ${pageContext.request.contextPath} --%>
								</span>

								<i class="icon-caret-down"></i>
							</a>

							<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="javascript:">
										<i class="icon-cog"></i> 设置
									</a>
								</li>

								<li>
									<a href="javascript:" id="personMain">
										<i class="icon-user"></i> 个人资料
									</a>
								</li>

								<li class="divider"></li>

								<li>
									<a href="javascript:">
										<i class="icon-off"></i> 退出
									</a>
								</li>
							</ul>
						</li>
					</ul>
					<!-- /.ace-nav -->
				</div>
				<!-- /.navbar-header -->
			</div>
			<!-- /.container -->
		</div>
		<!-- /section:basics/navbar.layout -->
		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try {
					ace.settings.check('main-container', 'fixed')
				} catch(e) {}
			</script>

			<div class="sidebar kf-animateright" id="sidebar">
				<script type="text/javascript">
					try {
						ace.settings.check('sidebar', 'fixed')
					} catch(e) {}
				</script>
				<p class="leftTop"><i class="fr icon-indent-left"></i><span>工作导航</span></p>
				<ul class="nav nav-list">

					<li>
						<a href="#" class="dropdown-toggle">
							<i class="icon-cloud"></i>
							<span class="menu-text">云会议中心 </span>

							<b class="arrow icon-angle-down"></b>
						</a>

						<ul class="submenu">
							<li>
								<a href="javascript:;" id="instantMeeting">
									 即时会议
								</a>
							</li>

							<li>
								<a href="javascript:;" id="bb">
									 预约会议
								</a>
							</li>
							<li>
								<a href="javascript:;" id="bb">
									会议日程
								</a>
							</li>
							<li>
								<a href="javascript:;" id="historyRecord">
									历史会议
								</a>
							</li>
							<li>
								<a href="javascript:;" id="addressList">
									企业通讯录
								</a>
							</li>
						</ul>
					</li>
						<li>
						<a href="#" class="dropdown-toggle">
							<i class="icon-cog"></i>
							<span class="menu-text">会议管理 </span>

							<b class="arrow icon-angle-down"></b>
						</a>

						<ul class="submenu">
							<li>
								<a href="javascript:;" id="instantMeeting">
									 即时会议
								</a>
							</li>

							<li>
								<a href="javascript:;" id="bb">
									预约会议
								</a>
							</li>
						</ul>
					</li>

				</ul>
				<!-- /.nav-list -->

				<script type="text/javascript">
					try {
						ace.settings.check('sidebar', 'collapsed')
					} catch(e) {}
				</script>
			</div>

			<div class="main-content">
<div class="main-content-inner">
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">

					<div class="row">

						<!-- 
						 -->
						<iframe src="${baseurl }/instantMeeting.jsp" id="iframePage" name="iframePage" frameborder="0" width="100%"></iframe>

					</div>
				</div>
				<!-- /.page-content -->
			</div>
</div>
		</div>
		<!-- /.main-content -->

		<!--[if !IE]> -->

		<script type="text/javascript">
			window.jQuery || document.write("<script src='${baseurl }/plugins/assets/js/jquery-2.0.3.min.js'>" + "<" + "script>");
		</script>

		<!-- <![endif]-->
		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='${baseurl }/js/jquery.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script type="text/javascript">
		 window.jQuery || document.write("<script src='${baseurl }/js/jquery1x.js'>"+"<"+"/script>");
		</script>
		<![endif]-->
		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='${baseurl }/plugins/assets/js/jquery.mobile.custom.min.js'>" + "<" + "script>");
		</script>
		<script src="${baseurl }/plugins/assets/js/bootstrap.min.js"></script>
		<script src="${baseurl }/plugins/assets/js/typeahead-bs2.min.js"></script>
		<script src="${baseurl }/plugins/assets/js/ace.min.js"></script>
		<script src="${baseurl }/js/index.js"></script>
	</body>

</html>

