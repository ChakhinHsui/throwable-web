var new_model = avalon.define("index_list", function(vm){
	vm.new_posts_list = [];
	vm.hot_posts_list = [];
	vm.wait_posts_list = [];
});

var label_model = avalon.define("label_list", function(vm){
	vm.label_list = [];
});

var new_hot_solved = avalon.define("new_hot_solved", function(vm){
	vm.new_hot_list = [];
});

var login_area = avalon.define({
	$id: "login_area",
	href: "login.html",
	text: "登陆"
});

$(document).ready(function(){
	$.post("question/getPublicQuestion", 
			{}, 
			function(result){
				new_model.new_posts_list = eval('(' + result.questions + ')');
			}, "json");
	$.post("question/getPublicHotQuestion", 
			{}, 
			function(result){
				new_model.hot_posts_list = eval('(' + result.questions + ')');
			}, "json");
	$.post("question/getMostFocusQuestion", 
			{}, 
			function(result){
				new_model.wait_posts_list = eval('(' + result.questions + ')');
			}, "json");
	$.post("label/queryAllLabels", 
			{}, 
			function(result){
				label_model.label_list = result.labels;
			}, "json");
	$.post("question/getNewMostAnswerQuestion", 
			{}, 
			function(result){
				new_hot_solved.new_hot_list = eval('(' + result.questions + ')');
			}, "json");
	throwable_base.login.isLogin("", function(isLogin){
		if(1 == isLogin) {
			login_area.href = "memberinfo.html?id=" + throwable_base.getIdFromCookie("throwable");
			login_area.text = throwable_base.getUserNameFromCookie("throwable");
		}
	});
});
avalon.scan();