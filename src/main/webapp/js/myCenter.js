$(function() {
	$("#iframePage").attr("src", "myReport.html");
	$("#myReport").css("color","blue");
	$("#myReport").bind('click', function() {
		$("#myRegister").removeAttr("style");
		$("#iframePage").attr("src", "myReport.html");
		$("#myReport").css("color","blue");
	})
	
	$("#myRegister").bind('click', function() {
		$("#myReport").removeAttr("style");
		$("#myRegister").css("color","blue");
		$("#iframePage").attr("src", "myRegister.html");
	})
});

