var question_model = avalon.define({
	$id: "question",
	title: "",
	author: "",
	time: "",
	focus: "",
	collect:  "",
	agree: "",
	disagree: "",
	details: "",
	comments: [],
	answers: []
});

var login_area = avalon.define({
	$id: "login_area",
	href: "register.html",
	text: "登陆"
});

var throwable_detail = {
		addAnswer : function(){
			throwable_base.login.isLogin("", function(isLogin){
				if(1 == isLogin) {
					$("#up_button").attr('disabled',"true");
					var description = ue.getContent();
					console.log(description);
					if(!description || description.search("请输入内容") > -1) {
						$("#up_button").removeAttr("disabled");
						alert("请输入内容");
						return;
					}
					var length = description.length > 50 ? 50 : description.length;
					var jsonObject = {
							question_id:throwable_util.url.getUrlParam("qid"),
							answer_abstract:description.substring(0, length),
							answer_description:description,
							user_id:throwable_base.getIdFromCookie("throwable")
					};
//					console.log("fds");
					$.ajax({
							type: "post",
							url: "../answer/addAnswer",
							data: jsonObject,
							success: function(data){
								var obj = eval('(' + data + ')');
								if(1 == obj.msgCode) {
									throwable_util.url.refresh();
								} else {
									alert(obj.errorMsg);
								}
								$("#up_button").removeAttr("disabled");
							}
						});
					console.log("fds");
				} else {
					throwable_util.url.location("register.html");
				}
			});
		}
};

$(document).ready(function(){
	throwable_base.login.isLogin("", function(isLogin){
		if(1 == isLogin) {
			login_area.text = throwable_base.getUserNameFromCookie("throwable");
		}
	});
	var questionId = throwable_util.url.getUrlParam("qid");
	var userId = throwable_base.getIdFromCookie("throwable");
	if(!questionId) {
		throwable_util.url.location("index.html");
	}
	if(!userId) {
		throwable_util.url.location("register.html");
	}
	var jsonObject = {
			questionId: throwable_util.url.getUrlParam("qid"),
			userId: throwable_base.getIdFromCookie("throwable")
	};
	$.post("../question/getOneQuestion", jsonObject, function(result){
		console.log(result);
		question_model.title = result.question_name;
		question_model.author = result.username;
		question_model.time = result.create_time;
		question_model.agree = result.agrees;
		question_model.disagree = result.degrees;
		question_model.details = result.question_description;
		question_model.comments = eval('(' + result.comments + ')');
	}, "json");
	
	$.post("../answer/getAnswers", jsonObject, function(result){
		question_model.answers = eval('(' + result.answers + ')');
	}, "json");
	$.post("../question/addViewer", 
			jsonObject, 
			function(result){
				
			}, "json");
});


avalon.scan();