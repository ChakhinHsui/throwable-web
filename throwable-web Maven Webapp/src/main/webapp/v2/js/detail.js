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
	qid: "",
	userId: "",
	image:"default.jpg",
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
		},
		/**
		 * 添加评论
		 * @param belongid     评论所属id   最大的一层  问题id  回答id
		 * @param userId       被评论的用户的id
		 * @param belongType   评论所属类型  1评论给问题  2评论给答案
		 * @param type         类型 1评论给问题和答案  2评论给评论
		 * @param commentId    被评论的评论id
		 */
		openComment : function(belongid, userId, belongType, type, commentId) {
			throwable_base.login.isLogin("", function(isLogin){
				if(1 == isLogin) {
					if(belongid == throwable_detail.temp['belongId'] && belongType == throwable_detail.temp['belongType'] && userId == throwable_detail.temp['toUserId'] && commentId == throwable_detail.temp['commentId'] ) {
						console.log(belongid);
						$("#comment_write").toggle();
						throwable_detail.temp = {};
						throwable_detail.togger = 1;
						return;
					}
					throwable_detail.temp['belongId'] = belongid;
					throwable_detail.temp['belongType'] = belongType;
					throwable_detail.temp['toUserId'] = userId;
					throwable_detail.temp['commentId'] = commentId;
					var username = "";
					if(type == 1) {
						username = throwable_detail.users[belongType + "_" + belongid];
					} else {
						username = throwable_detail.users[belongType + "_" + commentId + "_" + belongid];
					}
					throwable_detail.temp['toUserName'] = username;
					throwable_detail.temp['fromUserId'] = throwable_base.getIdFromCookie("throwable");
					throwable_detail.temp['fromUserName'] = throwable_base.getUserNameFromCookie("throwable");
					$("#ctoUserName").html("评论 @" + username + "");
					if(throwable_detail.togger == 1) {
						$("#comment_write").toggle();
						throwable_detail.togger = 2;
					}
					console.log(throwable_detail.temp);
				} 
			});
		},
		addComment : function() {
			var comment = $("#comment_write_area").val();
			throwable_detail.temp["comment"] = comment;
			$.post("../comment/addComment", throwable_detail.temp, function(result){
				console.log(result);
				if(1 == result.msgCode) {
					$("#name").val("");
					//清空富文本的内容
					$("#comment_write_area").val("");
					throwable_util.url.refresh();
					throwable_detail.temp = {};
				}
			}, "json");
		},
		users : {
			
		},
		temp : {
			
		},
		togger : 1
};

$(document).ready(function(){
	throwable_base.login.isLogin("", function(isLogin){
		if(1 == isLogin) {
			login_area.text = throwable_base.getUserNameFromCookie("throwable");
		}
	});
	var questionId = throwable_util.url.getUrlParam("qid");
//	var userId = throwable_base.getIdFromCookie("throwable");
	if(!questionId) {
		throwable_util.url.location("index.html");
	}
//	if(!userId) {
//		throwable_util.url.location("register.html");
//	}
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
		question_model.qid = result.id;
		question_model.userId = result.user_id;
		question_model.image = !result.image ? question_model.image : result.image;
		var comments = eval('(' + result.comments + ')');
		question_model.comments = comments;
		throwable_detail.users["1_" + result.id] = result.username;
		for(var i = 0; i < comments.length; i++) {
			throwable_detail.users["1_" + comments[i].id + "_" + comments[i].belongId] = comments[i].fromUserName;
		}
	}, "json");
	
	$.post("../answer/getAnswers", jsonObject, function(result){
		var answers = eval('(' + result.answers + ')');
		question_model.answers = answers;
		for(var i = 0; i < answers.length; i++) {
			throwable_detail.users["2_" + answers[i].id] = answers[i].username;
			console.log(answers[i].comments);
			for(var j = 0; j < answers[i].comments.length; j++) {
				throwable_detail.users["2_" + answers[i].comments[j].id + "_" + answers[i].comments[j].belongId] = answers[i].comments[j].fromUserName;
			}
		}
		console.log(throwable_detail.users);
	}, "json");
	$.post("../question/addViewer", 
			jsonObject, 
			function(result){
				
			}, "json");
});


avalon.scan();