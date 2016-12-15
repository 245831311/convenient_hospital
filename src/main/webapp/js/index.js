$(function() {

	size();
	$(window).resize(function() {

		size();
	});
	$("#instantMeeting").bind('click', function() {
		$("#iframePage").attr("src", "instantMeeting.jsp");
	})
	$("#personMain").bind('click', function() {
		$("#iframePage").attr("src", "personMessage.html");
	});
	$("#addressList").bind('click', function() {
		$("#iframePage").attr("src", "addressList.html");
	});
	$("#historyRecord").bind('click', function() {
		$("#iframePage").attr("src", "historyRecord.html");
	});
	$('.dropdown-toggle').click(function(event) {
		if($(this).children('.icon-angle-down').hasClass('icon-angle-up')) {

			$(this).children('.icon-angle-down').removeClass('icon-angle-up');

		} else {

			$(this).children('.icon-angle-down').addClass('icon-angle-up');
			$(this).parent().siblings().find('.icon-angle-down').removeClass('icon-angle-up');
		}
		//$(this).children('.icon-angle-down').toggleClass('icon-angle-up');
	});
	$('.icon-indent-left').click(function() {
		if($(this).hasClass('icon-indent-right')) {
			$('.icon-indent-left').removeClass('icon-indent-right');
			showMenu();

		} else {
			$('.icon-indent-left').addClass('icon-indent-right');
			hideMenu();
		}
	});

	$('#sidebar').hover(function() {
		if($(this).css('width', '60px')) {

			showMenu();

		}

	}, function() {

		if($('.icon-indent-left').hasClass('icon-indent-right')) {

			hideMenu();

		}

	})
	$('.submenu li').click(function() {
		$(this).addClass('active').siblings().removeClass('active');
		$(this).parent().parent().siblings().find('li').removeClass('active');
	})

});

function showMenu() {

	$('#sidebar').addClass("kf-animateright").removeClass("kf-animateleft").width(210);
	//$('#sidebar').css('width', '210px');
	$('.nav-list>li a>.arrow').show();
	$('.nav-list>li a>span').show();
	$('.nav-list>li.open .submenu').show();
	$('.icon-indent-left').next().show();
	$('.main-content').width(($(window).width() - 210) + 'px').css("left", "210px");

}

function hideMenu() {

	$('#sidebar').addClass("kf-animateleft").removeClass("kf-animateright").width(60);
	//$('#sidebar').css('width', '60px');
	$('.nav-list>li a>.arrow').hide();
	$('.nav-list>li a>span').hide();
	$('.nav-list>li.open .submenu').hide();
	$('.icon-indent-left').next().hide();
	$('.main-content').width(($(window).width() - 60) + 'px').css("left", "60px");

}

function size() {

	if($("#sidebar").width() == 60) {
		$('.main-content').width(($(window).width() - 60) + 'px').css("left", "60px");

	} else {
		$('.main-content').width(($(window).width() - 210) + 'px').css("left", "210px");

	}

}