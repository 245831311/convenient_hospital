$(function(){
	initEvent();
});

function initEvent(){
	$(".icon-refresh").click(function(){
		$("#refreshCode").attr("src","validatecode.jsp?time="+new Date().getTime());
	});
	
	$(".loginButton").click(function(){
		var username = $("#username").val();
		var password = $("#password").val();
		var code = $("#code").val();
		$.ajax({
			url:'userController/login.action',
			type:'POST',
			data:{username:username,password:password,code:code},
			async:true,
			cache:false,
			success:function(result){
				result = JSON.parse(result);
				if(result){
					if(result.code!=200 && result.message){
						var errSpan = $(".icon-exclamation-sign + span");
						errSpan.empty();
						errSpan.text(result.message);
						$(".loginWarning").show();
						//alert(result.message);
					}else if(result.code==200){
						window.location.href = "index.jsp";
					}
				}
			}
		});
	});
}