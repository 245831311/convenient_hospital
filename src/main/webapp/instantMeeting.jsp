<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="page/base/tag.jsp" %>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
		<meta charset="UTF-8">

		<title></title>

		<link href="${baseurl }/plugins/font-awesome/css/font-awesome.css" rel="stylesheet" />
		<link href="${baseurl }/plugins/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="${baseurl }/plugins/assets/css/ace.min.css" />
		<link href="${baseurl }/css/reset.css" rel="stylesheet" />
		<link href="${baseurl }/css/common.css" rel="stylesheet" />
		<link href="${baseurl }/css/addMeeting.css" rel="stylesheet" />
		<script type="text/javascript">
			var baseurl = '${baseurl}';
		</script>
		<style type="text/css">
			.progress {
			    box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1);
			    background-color: #f7f7f7;
			    margin-bottom: 0px;
    			margin-top: 5px;
			    background-image: url(${baseurl }/images/meeting/scroll-bg.png);
			    background-repeat: no-repeat;
			    background-position: center center;
			    height: 18px;
			    width: 432px;
			    position: relative;
			    -webkit-border-radius: 0px;
			    -moz-border-radius: 0px;
			    border-radius: 0px;
			    overflow: visible;
			}
			
			.progress .bar {
			    float: left;
			    width: 0;
			    height: 100%;
			    font-size: 12px;
			    color: #ffffff;
			    text-align: center;
			    text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
			    background-color: #0e90d2;
			    background-image: -moz-linear-gradient(top, #149bdf, #0480be);
			    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#149bdf), to(#0480be));
			    background-image: -webkit-linear-gradient(top, #149bdf, #0480be);
			    background-image: -o-linear-gradient(top, #149bdf, #0480be);
			    background-image: linear-gradient(to bottom, #149bdf, #0480be);
			    background-repeat: repeat-x;
			    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff149bdf', endColorstr='#ff0480be', GradientType=0);
			    -webkit-box-shadow: inset 0 -1px 0 rgba(0, 0, 0, 0.15);
			    -moz-box-shadow: inset 0 -1px 0 rgba(0, 0, 0, 0.15);
			    box-shadow: inset 0 -1px 0 rgba(0, 0, 0, 0.15);
			    -webkit-box-sizing: border-box;
			    -moz-box-sizing: border-box;
			    box-sizing: border-box;
			    -webkit-transition: width 0.6s ease;
			    -moz-transition: width 0.6s ease;
			    -o-transition: width 0.6s ease;
			    transition: width 0.6s ease;
			}
			.tooltip-arrow {
			    position: absolute;
			    width: 0;
			    height: 0;
			    border-color: transparent;
			    border-style: solid;
			}
			.tooltip.top .tooltip-arrow {
			    bottom: 0;
			    left: 50%;
			    margin-left: -5px;
			    border-top-color: #000000;
			    border-width: 5px 5px 0;
			}
			.tooltip-inner {
			    max-width: 200px;
			    padding: 8px;
			    color: #ffffff;
			    text-align: center;
			    text-decoration: none;
			    background-color: #000000;
			    -webkit-border-radius: 4px;
			    -moz-border-radius: 4px;
			    border-radius: 4px;
			}
			.tooltip.top { height: 32px;background: none;box-shadow:none;}
		</style>
	</head>

	<body>
		<div class="wrapper">
			<div class="rightTop">
				即时会议
			</div>

			<div class="mettingDl">
				<dl>
					<dt>会议类型</dt>
					<dd class="w100 blue">视频会议</dd>
					<dt>会议主题</dt>
					<dd class="border"><input name="subject" class="wb100 h40" type="text" /><em class="fr">20/90字</em></dd>
					<dt>会议时长</dt>
					<dd style="overflow: visible; padding-top: 9px;" id="slideContainer2">
						<!-- <p class="mettingTime" id="slideHandle2"><span class="active"></span>
							<span></span><span></span><span></span>
							<span></span><span></span><span></span>
							<span></span><span></span><span></span>
							<span></span><span></span><em><i class="icon-caret-down"></i>1.5个小时</em>
						</p> -->
						<input type="hidden" name="meetingTimeLen" id="meetingTimeLen" value="0.5">
				        <div class="controls">
				          <div class="progress">
				            <div class="bar" style="width: 36px;"></div> 
				            <div class="tooltip fade top in" style="display: block; margin-top: -32px; margin-left: -23px; left: 18px;">
				              <div class="tooltip-arrow"></div>
				              <div class="tooltip-inner" disabled="" style="padding: 3px;cursor: default;" onmousedown="">0.5小时</div>
				            </div>
				          </div>
				        </div>
					</dd>
					<dt>会议议程</dt>
					<dd>
						<a href="javascript:" class="mettAdd"><i class="icon-plus-sign"></i>添加议程</a>
						<ul id="agendaList">
							<!-- <li id=""><span class="s1">14:00</span><span class="s2">主持人</span><span class="s3">介绍会议内容</span><span class="s4 icon-edit mettedit"></span><span class="s5 icon-remove"></span></li>
							<li><span class="s1">14:00</span><span class="s2">主持人</span><span class="s3">介绍会议内容</span><span class="s4 icon-edit mettedit"></span><span class="s5 icon-remove"></span></li> -->
						</ul>
					</dd>
					<dt>参会人员</dt>
					<dd>
						<a href="javascript:" class="addAttendee"><i class="icon-plus-sign"></i>添加参会人</a>
						<div style=" padding: 10px 0 0 0; border: #e0e1e2 1px solid;">
							<ul class="mettingUL" style="border: none;">
								<li><span class="s6"><em><img src="${baseurl }/plugins/assets/avatars/avatar1.png"></em>伍小婷</span><span class="s7">13566236320</span></li>
								<li><span class="s6"><em><img src="${baseurl }/plugins/assets/avatars/avatar1.png"></em>伍小婷</span><span class="s7">13566236320</span></li>
								<li><span class="s6"><em></em>伍小婷</span><span class="s7">13566236320</span></li>
								<li><span class="s6"><em></em>伍小婷</span><span class="s7">13566236320</span></li>
								<li><span class="s6"><em></em>伍小婷</span><span class="s7">13566236320</span></li>
								<li><span class="s6"><em></em>伍小婷</span><span class="s7">13566236320</span></li>
								<li><span class="s6"><em></em>伍小婷</span><span class="s7">13566236320</span></li>
								<li><span class="s6"><em></em>伍小婷</span><span class="s7">13566236320</span></li>

							</ul>
						</div>

					</dd>
					<dt>会议材料</dt>
					<dd class="formInp">
						<form name='importForm' id='importForm' action="${baseurl }/uploadController/importMeetingFileForCache.action" enctype="multipart/form-data" method="post" target="result1" class="importFileForm">
							<span id="fileName">文件名.zip</span>
							<a id="uploadBtn" href="javascript:">上传材料</a>
							<input type="file" name="importFile" id="importFile"/>
						</form>
					</dd>
					<dd>
						<a class="mettingBtn" href="javascript:">发起会议</a>
					</dd>
				</dl>
				
				<iframe id="result1" name="result1" frameborder="0" scrolling="0" src=""></iframe>

			</div>
		</div>
		<!--添加议程-->
		<div class="popDL addM hide-1">
			<dl>
				<dt>时间</dt>
				<dd><input type="text" name="time"/></dd>
				<dt>姓名</dt>
				<dd><input type="text" name="staff" /></dd>
				<dt>内容</dt>
				<dd><textarea name="content"></textarea>
					<p class="hints">20/90个字</p>
				</dd>
				<dd class="txr">
					<a id="addAgenda" class="dlBtn" href="javascript:void(0);">确定</a>
					<a class="dlBtn cancel ml30" href="javascript:">取消</a>
				</dd>
			</dl>
		</div>
		<!--编辑议程-->
		<div class="popDL eddM hide-1">
			<dl id="editDl">
				<dt>时间</dt>
				<dd><input type="text" name="time"/></dd>
				<dt>姓名</dt>
				<dd><input type="text" name="staff" /></dd>
				<dt>内容</dt>
				<dd><textarea name="content"></textarea>
					<p class="hints">20/90个字</p>
				</dd>
				<dd class="txr">
					<a id="editAgenda" class="dlBtn" href="javascript:void(0);">确定</a>
					<a class="dlBtn cancel ml30" href="javascript:">取消</a>
				</dd>
			</dl>
		</div>
		<!--添加参会人员-->
		<div class="addMettMain hide-1">
			<div class="panel-body row">
				<div class="col-xs-6 group-chat-selectable">
					<div class="form-group searchM"><i class="icon-search fl"></i><input type="text" placeholder="输入手机号码直接添加参会人员">
						<a href="javascript:" class="searchBtn">查询</a>
						<ul class="searchList hide -1"><li>	<img src="${baseurl }/plugins/assets/avatars/avatar.png" class="fl"><span class="search1">周小兰</span><span class="search1">13533626203</span>
</li></ul>
					</div>
					<div class="group-chat-list selectable-list treeUl" id="treeUl">
						
						<!--<ul>
							<li>
								<p><i class="icon-plus"></i><span>企业1</span></p>
								<ul>
									<li>
										<p><i class="icon-plus"></i><span>A部门</span></p>
										<ul>
											<li>
												<p><i class="icon-plus"></i><span>A-1部门</span></p>
												<ul class="">
													<li>
														<p><img src="plugins/assets/avatars/avatar.png" class="fl"><span class="p1">周小兰</span><span class="p2">13533626203</span></p>
													</li>
												</ul>
											</li>
											<li>
														<p><img src="plugins/assets/avatars/avatar.png" class="fl"><span class="p1">周小兰</span><span class="p2">13533626203</span></p>
													</li>
											<li>
												<p><i class="icon-plus"></i><span>A-2部门</span></p>
												<ul>
													<li>
														<p><img src="plugins/assets/avatars/avatar.png" class="fl"><span class="p1">周小兰</span><span class="p2">13533626203</span></p>
													</li>
												</ul>
											</li>
										</ul>
									</li>
									<li>
										<p><i class="icon-plus"></i><span>B部门</span></p>
										<ul class="">
											
											<li>
												<p><i class="icon-plus"></i><span>B-2部门</span></p>
												<ul class="">
													<li>
														<p><img src="plugins/assets/avatars/avatar.png" class="fl"><span class="p1">周小兰</span><span class="p2">13533626203</span></p>
													</li>
													<li>
														<p><img src="plugins/assets/avatars/avatar.png" class="fl"><span class="p1">周小兰</span><span class="p2">13533626203</span></p>
													</li>
													<li>
														<p><img src="plugins/assets/avatars/avatar.png" class="fl"><span class="p1">周小兰</span><span class="p2">13533626203</span></p>
													</li>

												</ul>
											</li>
										</ul>
									</li>

								</ul>

							</li>

						</ul>-->

					</div>
				</div>
				<div class="col-xs-6 group-chat-selected">
					<h5 class="total-selected">参会人员</h5>
					<div class="choosePerson treeUl" id="selectedList">
						<p id="noMetting"><img src="images/base/noMetting.png"></p>
					</div>
					
				</div>
				
			</div>
			<p class="txr mt15 mr50">
					<a class="dlBtn" href="javascript:">确定</a>
					<a class="dlBtn cancel ml30" href="javascript:">取消</a>
				</p>
		</div>
	</body>
	<script src='${baseurl }/plugins/assets/js/jquery-2.0.3.min.js'></script>

	<script type="text/javascript" src="${baseurl }/plugins/layer/layer.js"></script>
	<script type="text/javascript" src="${baseurl }/js/map.js"></script>
	<script type="text/javascript" src="${baseurl }/js/instantMeeting.js"></script>
	<script type="text/javascript" src="${baseurl }/js/jquery.nicescroll.min.js"></script>
	
	<script type="text/javascript" src="${baseurl }/js/common.js"></script>

</html>