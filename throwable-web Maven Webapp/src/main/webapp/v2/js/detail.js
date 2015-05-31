var question_model = avalon.define({
	$id: "question",
	title: "",
	author: "",
	time: "",
	focus: "",
	collect:  "",
	agree: 0,
	disagree: 0,
	details: "",
	qid: "",
	userId: "",
	image:"default.jpg",
	solved:0,
	solveText:"未解决",
	agreeText:"赞同",
	disagreeText:"反对",
	userAsks:0,
	userAnswers:0,
	comments: [],
	answers: [],
	sameQuestions: []
});

var login_area = avalon.define({
	$id: "login_area",
	href: "register.html",
	text: "登陆",
	newText : '<a href="register.html">登陆</a>'
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
					if(userId == throwable_base.getIdFromCookie("throwable")) {
						alert("不能对自己进行评论");
						return;
					}
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
				} else {
					alert("请先登陆");
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
		agreeQuestion : function(qid) {
			throwable_base.login.isLogin("", function(isLogin){
				if(1 == isLogin) {
					$.post("../question/agreeQuestion", 
							{questionId:qid, userId:throwable_base.getIdFromCookie("throwable")}, 
							function(result){
								console.log(result);
								if(1 == result.msgCode) {
									question_model.agree = parseInt(question_model.agree) + 1;
									console.log(question_model.agree);
								}
							}, "json");
				} else {
					alert("请先登陆");
				}
			});
			
		},
		disAgreeQuestion : function(qid) {
			throwable_base.login.isLogin("", function(isLogin){
				if(1 == isLogin) {
					$.post("../question/disagreeQuestion", 
							{questionId:qid, userId:throwable_base.getIdFromCookie("throwable")}, 
							function(result){
								if(1 == result.msgCode) {
									question_model.disagree = parseInt(question_model.disagree) + 1;
									console.log(question_model.disagree);
								}
							}, "json");
				} else {
					alert("请先登陆");
				}
			});
		},
		agreeAnswer : function(answerId) {
			throwable_base.login.isLogin("", function(isLogin){
				if(1 == isLogin) {
					$.post("../answer/agreeAnswer", 
							{answerId:answerId,userId:throwable_base.getIdFromCookie("throwable")}, 
							function(result){
								if(1 == result.msgCode) {
									var str = $(".a_a_" + answerId).html();
									var temp = str.substring(0, str.indexOf("\<"));
									var temp2 = str.substring(str.indexOf("\<"));
									temp = parseInt(temp);
									temp = temp + 1;
									str = temp + temp2;
									$(".a_a_" + answerId).html(str);
								}
							}, "json");
				} else {
					alert("请先登陆");
				}
			});
		},
		disagreeAnswer : function(answerId) {
			throwable_base.login.isLogin("", function(isLogin){
				if(1 == isLogin) {
					$.post("../answer/disagreeAnswer", 
							{answerId:answerId,userId:throwable_base.getIdFromCookie("throwable")}, 
							function(result){
								if(1 == result.msgCode) {
									var str = $(".a_d_" + answerId).html();
									var temp = str.substring(0, str.indexOf("\<"));
									var temp2 = str.substring(str.indexOf("\<"));
									temp = parseInt(temp);
									temp = temp + 1;
									str = temp + temp2;
									$(".a_d_" + answerId).html(str);
								}
							}, "json");
				} else {
					alert("请先登陆");
				}
			});
		},
		acceptAnswer : function(answerId) {
			throwable_base.login.isLogin("", function(isLogin){
				if(1 == isLogin) {
					var userId = throwable_base.getIdFromCookie("throwable");
					$.post("../answer/acceptAnswer", 
							{questionId:question_model.qid,answerId:answerId,userId:userId}, 
							function(result){
								console.log(result);
								if(1 == result.msgCode) {
									throwable_util.url.refresh();
								} else {
									alert(result.errorMsg);
								}
							}, "json");
				} else {
					alert("请先登陆");
				}
			});
		},
		getIndexFromId : function(id) {
			for(var index = 0; index < answer_id_index; index++) {
				if(id == answer_id_index[index]) {
					return index;
				}
			}
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
			var id = throwable_base.getIdFromCookie("throwable");
			login_area.newText = throwable_base.initUserArea(id, throwable_base.getUserNameFromCookie("throwable"));
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
		question_model.solved = result.solved;
		question_model.userAsks = result.asks;
		question_model.userAnswers = result.answers;
		if(result.solved == 1) {
			question_model.solveText = "已解决";
		}
		if(result.user_question_relation) {
			if(result.user_question_relation == '1') {
				question_model.agreeText = '已赞同';
				question_model.disagreeText = '反对';
			} else {
				console.log(result.user_question_relation);
				question_model.agreeText = '赞同';
				question_model.disagreeText = '已反对';
			}
		} else {
			question_model.agreeText = '<a href="javascript:throwable_detail.agreeQuestion('+result.id+');">赞同</a>';
			question_model.disagreeText = '<a href="javascript:throwable_detail.disAgreeQuestion('+result.id+');">反对</a>';
		}
		var comments = eval('(' + result.comments + ')');
		question_model.comments = comments;
		throwable_detail.users["1_" + result.id] = result.username;
		for(var i = 0; i < comments.length; i++) {
			throwable_detail.users["1_" + comments[i].id + "_" + comments[i].belongId] = comments[i].fromUserName;
		}
	}, "json");
	
	$.post("../answer/getAnswers", jsonObject, function(result){
		console.log(result);
		var answers = eval('(' + result.answers + ')');
		for(var i = 0; i < answers.length; i++) {
			if(1 == question_model.solved || answers[0].correct_type == 1) {
				answers[i].accptText = '采纳';
				answers[i].class1 = 'c-gray';
			} else {
				answers[i].accptText = '<a href="javascript:void(0);" onclick="throwable_detail.acceptAnswer('+answers[i].id+')">采纳</a>';
				answers[i].class1 = 'c-orange';
				answers[i].class2 = 'a_' + answers[i].id;
			}
			if(answers[i].user_answer_relation) {
				if(answers[i].user_answer_relation == 1) {
					answers[i].agreeText = "已赞同";
					answers[i].disagreeText = "反对";
				} else {
					answers[i].agreeText = "赞同";
					answers[i].disagreeText = "已反对";
				}
			} else {
				answers[i].agreeText = '<a href="javascript:throwable_detail.agreeAnswer('+answers[i].id+')">赞同</a>';
				answers[i].disagreeText = '<a href="javascript:throwable_detail.disagreeAnswer('+answers[i].id+')">反对</a>';
			}
		}
		if(1 == question_model.solved || answers[0].correct_type == 1) {
			answers[0].accptText = '已采纳';
			answers[0].class1 = 'c-green';
		}
		question_model.answers = answers;
		console.log(answers);
		for(var i = 0; i < answers.length; i++) {
			throwable_detail.users["2_" + answers[i].id] = answers[i].username;
			for(var j = 0; j < answers[i].comments.length; j++) {
				throwable_detail.users["2_" + answers[i].comments[j].id + "_" + answers[i].comments[j].belongId] = answers[i].comments[j].fromUserName;
			}
		}
	}, "json");
	$.post("../question/addViewer", 
			jsonObject, 
			function(result){
				
			}, "json");
	$.post("../question/querySameQuestions", jsonObject, function(result){
		if(result.msgCode == 1) {
			for(var i = 0; i < result.questions.length; i++) {
				result.questions[i] = eval('(' + result.questions[i] + ')');
			}
			question_model.sameQuestions = result.questions;
		}
	}, "json");
});


avalon.scan();