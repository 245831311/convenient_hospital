$(function(){
	$(".ff_lxwm_01").click(function() {
		$(".ff_lxwm_nr").toggle();
	});
})
 $(function() {
    $("#zk_01").click(function() {
        $("#zk_02").slideToggle();
    });
});
 $(function() {
    $("#zk_03").click(function() {
        $("#zk_04").slideToggle();
    });
});
function ff_00() {
	var scroll_offset = $("body").offset(); //得到pos这个div层的offset，包含两个值，top和left
	$("body,html").animate({
	scrollTop:scroll_offset.top //让body的scrollTop等于pos的top，就实现了滚动
	},1000);
} 

$(document).ready(function () { 
	$(".zc_cpny_01_03_02").click(function () {
	 	$(".zc_cpny_01_04").css("display", "block");
	 	$(".zc_cpny_01_05").css("display", "block");
	 });
	    $(".zc_cpny_01_05").click(function(){
        	$(".zc_cpny_01_04").css("display", "none");
	 	$(".zc_cpny_01_05").css("display", "none");
    }); 
});

$(document).ready(function () { 
	$(".zc_cpny_mk_xzhb_xz img").click(function () {
	 	$(".zc_gdhd_bottom_01_01").html('支持：¥99.9');
	 	$("#zk_05").attr('src','img/46.png');
	 });
	 $(".zc_cpny_01_05").click(function(){
        	$(".zc_cpny_01_04").css("display", "none");
	 	$(".zc_cpny_01_05").css("display", "none");
    }); 
});

$(document).ready(function () { 
	$("#zk_06").click(function () {
	 	$("#zk_08").css("display", "block");
	 });
	    $("#zk_07").click(function(){
        	$("#zk_08").css("display", "none");
    }); 
});

$(document).ready(function () { 
	$("#zk_09").click(function () {
	 	$("#zk_10").css("display", "none");
	 });
	  $("#zk_11").click(function(){
        	$("#zk_08").css("display", "none");
        	$("#zk_10").css("display", "block");
    }); 
});


function changeDivHeight(){               
        var heig = $(window).height(); 
        $(".zc_login").css("height", heig);
        $(".zc_wyzcdfs").css("height", heig);
}
window.onresize=function(){  
         changeDivHeight();  
}  
$(function(){
changeDivHeight(); 
});
$(function() {
    $('.zc_cpny_nr_02').each(function(index, el) {
        var num = $(this).find('span').text() * 3.6;
        if (num<=180) {
            $(this).find('.right').css('transform', "rotate(" + num + "deg)");
        } else {
            $(this).find('.right').css('transform', "rotate(180deg)");
            $(this).find('.left').css('transform', "rotate(" + (num - 180) + "deg)");
        };
    });

});