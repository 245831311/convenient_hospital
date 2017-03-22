/*获取背景高度*/
function changeDivHeight(){               
        var heig = $(window).height(); 
		$(".jx_topbj").css("height", heig-2); 
}
window.onresize=function(){  
         changeDivHeight();  
}  
$(function(){
changeDivHeight(); 
initEvent();
});

/*获取背景高度 end*/
/*导航出现 */
$(function () {
    $("#jx_cp").mouseover(function () {
        $("#jx_cp_01").css("display", "block");
    });
    $("#jx_cp").mouseleave(function () {
        $("#jx_cp_01").css("display", "none");
    });
}); 
$(function () {
    $("#jx_jjfa").mouseover(function () {
        $("#jx_jjfa_01").css("display", "block");
    });
    $("#jx_jjfa").mouseleave(function () {
        $("#jx_jjfa_01").css("display", "none");
    });
}); 

$(function () {
    $("#jxnr_top_icon").mouseover(function () {
        $("#jx_login_01").css("display", "block");
    });
    $("#jxnr_top_icon").mouseleave(function () {
        $("#jx_login_01").css("display", "none");
    });
}); 

//var validEmail = jiaxin.util.getUrlParamVal("email");
function initEvent(){
	//发送邮件
	$("#toEmailValid").click(function(){
		checkMobile($.trim($("#mobile").val()));
	});
	//从邮箱校验返回将返回的email回填到邮件输入框中并获取焦点
	/*if(validEmail){
		$("#email").val(validEmail);
		$("#email").focus();
	}*/
}

function isMobile(str){
	//带国际码的电话号
	var reg = /^(\+[0-9]{2})*(13|14|15|17|18)[0-9]{9}$/;
	return reg.test(str);
}

/**
 * 验证手机号码是否已存在
 * 
 * @author yubing
 * @param {} mobile
 */
function isMobileExists(mobile){
	var flag = false;
	
	$.ajax({
		url:"common/isMobileExists.form",
		data:{'mobile':mobile},
		type:'POST',
		async:false,
		cache:false,
		success:function(resp){
			resp = JSON.parse(resp);
		
			if(resp){
				if(!resp.state){
					flag = true;
				}
			}
		},
		error:function(){
			closeLoading();
			layer.msg("验证手机号码失败!");
		}
	});
	
	return flag;
}

var checkMobile = function(mobile){
	if($.trim(mobile).length==0){
		layer.msg("手机号码不能为空!");
		return false;
	}else if(!isMobile($.trim(mobile))){
		layer.msg("手机号码为11位数字!");
		return false;
	}else if(isMobileExists(mobile)){
		layer.msg("手机号码已存在!");
		return false;
	}
	
	submitButton(mobile);
}

/*马上体验邮箱参数传值begin*/
function checkEmall(newMail){
	var tips = "";
	var emailTest  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	
	if(newMail.length==0){
		tips = '请输入邮箱！';
		layer.msg(tips);
		return;
	} else {
		if (!emailTest.test(newMail)) {
			tips = '邮箱格式不正确，请重新输入！';
			layer.msg(tips);
			return;
		}
	}
	
	submitButton(newMail);
}
var isSendMobile = false;
/**
 * 提交
 */
function submitButton(mobile) {
	if(isSendMobile){
		return;
	}
	isSendMobile = true;
	//验证元素的合法性
 	var parms = {
		"mobile" : mobile
	};
	var url = "common/sendActiveMobile.form";
	$.post(url, parms, function(data) {
		if (data) {
			var result = JSON.parse(data);
			var message = result.message;
			var sendURL = result.sendURL;
			
			if (result.state) {
				forwardToSendMobileSul(mobile,sendURL);
			} else {
				layer.msg(message);
			}
		}
		isSendEmail = false;
	});
}

/**
 * 跳到发送成功页面
 */
function forwardToSendMobileSul(mobile,sendURL){
	var contextPath = jiaxin.util.getConfigureValue("DEVCENTER_URL");
	if(contextPath==null){
		return;
	}
	if(getCookie("jiaxincloud.com") == "")
		window.location = contextPath+sendURL+"?mobile="+mobile;
	else
		window.location = contextPath + sendURL + "?mobile=" + mobile + "&cId=" + getCookie("jiaxincloud.com");
}
/*马上体验邮箱参数传值end*/

/*导航出现 end*/
/*视频出现 */
function isSupport() {
	var v = 3;
	var div = document.createElement('div');
	var i = div.getElementsByTagName('i');
	while (div.innerHTML = '<!--[if gt ie ' + (++v) +']><i></i><![endif]-->', i[0]);    
    return v > 4 ? v : false;
}
function PlayFlv(filename) {
	    /*var videoname = filename; //视频文件名 
	    var floder = "video"; //存放Flash视频的文件夹,注意是相对flvplayer.swf文件的位置（images/flvplayer.swf） 
	*/  var div = document.getElementById("jx_vdieo_nr");
	    div.innerHTML = "<img src='images/wait.gif' alt=''>"; //增加等待图片已增加用户体验; 
	    div.style.display = "none";
	    var result = "<table align='center'><tr><td align='center'><object id='jx_vdieoIE8Play'classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000'  codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0' width='890' height='500'> ";
	    result += "<param name='movie' value='Flvplayer.swf'/>";   
	    result += "<param name='quality' value='high' />";
	    result += "<param name='allowfullscreen' value='true' />";
	    result += "<param name='loop' value='true'>"
	    result += "<param name='FlashVars' value='vcastr_file=http://jxcdn.jiaxincloud.com/jiaxin-introduce.flv&IsAutoPlay=1&IsContinue=1' />";
	    result += "<param name='autostart' value='true' />";
	    result += "</object></td></tr></table> ";
	    div.innerHTML = result;
	    div.style.display = "block"; //div显示方式 
} 

$(function () {
    $("#jx_vdieo").click(function () {
        $(".jx_vdieo").css("display", "block");
        $('video').trigger('play');
    });
    /*alert(isSupport());*/
    if(isSupport()==8||isSupport()==9){
    	$("#jx_vdieo").click(function () {	    	
        	$(".jx_vdieo").hide();
        	$("#videoPlay").trigger('pause');
        	$(".jx_vdieoIE8").css("display", "block");
        	PlayFlv(); 
        });
    	
    	$(".jx_vdieo_gb").click(function () {      		
    		$(".jx_vdieoIE8").css("display", "none");            
            $('#jx_vdieo_nr').html('');
        });
    } 
}); 
$(function () {
    $("#jx_vdieo_01").click(function () {
        $(".jx_vdieo").css("display", "block");
        $('video').trigger('play');        
    });
    $(".jx_vdieo_gb").click(function () {
        $(".jx_vdieo").css("display", "none");
        $('video').trigger('pause');
    });
    if(isSupport()==8||isSupport()==9){
    	$("#jx_vdieo_01").click(function () {
        	$(".jx_vdieo").hide();
        	$("#videoPlay").trigger('pause');
        	$(".jx_vdieoIE8").css("display", "block");
        	PlayFlv(); 
        });
    	
    	$(".jx_vdieo_gb").click(function () {      		
    		$(".jx_vdieoIE8").css("display", "none");            
            $('#jx_vdieo_nr').html('');
        });
    } 
}); 
$(function () {
    $("#jx_vdieo_img").click(function () {
        $(".jx_vdieo").css("display", "block");
        $('video').trigger('play');        
    });
    $(".jx_vdieo_gb").click(function () {
        $(".jx_vdieo").css("display", "none");
        $('video').trigger('pause');
    });
}); 
$(function () {
    $("#jx_vdieo_02").click(function () {
        $(".jx_vdieo").css("display", "block");
        $('video').trigger('play');
        
    });
    $(".jx_vdieo_gb").click(function () {
        $(".jx_vdieo").css("display", "none");
        $('video').trigger('pause');
    });
}); 
/*视频出现 end*/

/*电商解决方案和保险解决方案立即注册按钮begin*/
$(function(){
	$(window).resize(function(event) {
    	var winWidth= $(window).width(); 
    	 if(winWidth<=640){
    	        $(".gotoLogin").hide();
    	        $(".gotoLoginIn").hide();   
    	 }

    	  else{
	    	    	$(".gotoLogin").show();
	    	    	$(".gotoLoginIn").show();
    	    }
    });
});
/*电商解决方案和保险解决方案立即注册按钮end*/
$(function(){
	$(".jx-td-animate").hover(function () {
		var index=$(".jx-td-animate").index(this);
                if (!$(this).is(":animated")) {
                $(this).animate({top:'-14px' },300);
                }
            },function () {
            	var index=$(".jx-td-animate").index(this);
                $(this).animate({top:'0px' },300);
            })
})
/* 手机端手向上滑顶部导航显示*/
$(function(){
		
})

var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?76912b7c063a7fcf51dbcb6efcec306e";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();

//跳转到注册页面
function locationToRegister()
{
	var link = "//dev.jiaxincloud.com/#/registerstepone";
	var value = getCookie("jiaxincloud.com");
	if(value != "")
		link = link + "?cId=" + value;
	window.location.href = link;
}

//跳转到登录页面
function locationToLogin()
{
	var link = "//agent.jiaxincloud.com/#/agentlogin";
	var value = getCookie("jiaxincloud.com");
	if(value != "")
		link = link + "?cId=" + value;
	window.location.href = link;
}

//刷新页面
function refreshIndex(){
	var link = "index.html";
	var value = getCookie("jiaxincloud.com");
	if(value != "")
		link = link + "?cId=" + value;
	window.location.href = link;
}

