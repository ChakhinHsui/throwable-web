var member_model = avalon.define({
	$id: "member_info",
	username: "",
	askNum: "",
	answerNum: "",
	liveAddr: "",
	nowJob:  "",
	graduateCollege: "",
	motto: "",
	interest: "",
	goodAt: "",
	create_time: "",
	image: ""
});

var login_area = avalon.define({
	$id: "login_area",
	href: "register.html",
	text: "登陆"
});

var user_question_model =  avalon.define("userQuestion", function(vm){
	vm.question_list = [];
	vm.answer_list = [];
	vm.focus_list = [];
});

var member_info = {
		subscribe : function() {
			throwable_util.subPubTool.subscribe("baseInfo", function(obj){
				member_model.liveAddr = obj.live_address;
				member_model.nowJob = obj.now_job;
				member_model.graduateCollege = obj.graduate_school;
				member_model.motto = obj.motto;
				member_model.interest = obj.interest;
				member_model.goodAt = obj.goodAt;
				member_model.create_time = obj.create_time;
				member_model.image = obj.image;
			});
			throwable_util.subPubTool.subscribe("askNum", function(obj){
				member_model.askNum = obj;
				console.log(member_model);
			});
			throwable_util.subPubTool.subscribe("answerNum", function(obj){
				member_model.answerNum = obj;
			});
			throwable_util.subPubTool.subscribe("login", function(obj){
				login_area.text = obj;
			});
			throwable_util.subPubTool.subscribe("login", function(obj){
				member_model.username = obj;
			});
			throwable_util.subPubTool.subscribe("questions", function(obj){
				user_question_model.question_list = obj;
				console.log(obj);
			});
		},
		publish : function(channel, obj) {
			throwable_util.subPubTool.publish(channel, obj);
		},
		getBaseUserInfo : function(id) {
			$.post("../question/queryUserQuestionNum", {userId : id}, function(result){
					member_info.publish("askNum", result.number);
			},"json");
			$.post("../answer/getUserAnswerNum", {userId : id}, function(result){
					member_info.publish("answerNum", result.number);
			},"json");
			$.post("../user/getUserExtendInfo", {userId : id}, function(result){
				console.debug(result);
				if(1 == result.msgCode) {
					member_info.publish("baseInfo", result);
					member_info.liveAddr = result.live_address;
					$("#now_job").html(result.now_job);
					$("#graduate_school").html(result.graduate_school);
					$("#motto").html(result.motto);
					$("#interest").html(result.interest);
					$("#goodAt").html(result.goodAt);
					$("#register_time").html(result.create_time);
					$("#image").val(result.image);
					var str = '<img  alt="露个相吧朋友" class=" box-shadow" src="static/h-ui/images/avatar_default.jpg"/>';
					str = '<img  alt="露个相吧朋友" style="width:70%; height:100%" class=" box-shadow" src="photo/'+result.image+'"/>';
					$("#image_area").html(str);
				}
			},"json");
		},
		openModifyDialog : function() {
			$("#add_live_location").val(member_model.liveAddr);
			$("#add_now_job").val(member_model.nowJob);
			$("#add_graduate_school").val(member_model.graduateCollege);
			$("#add_motto").val(member_model.motto);
			$("#add_interest").val(member_model.interest);
			$("#add_goodAt").val(member_model.goodAt);
			$("#myModal").modal("show");
			$("#add_userId").val(throwable_util.url.getUrlParam("id"));
			$("#add_image").val(member_model.image);
		},
		ajaxUploadImage : function() {
			$.ajaxFileUpload({
				url : '../user/saveUserExtendInfo',
				secureuri : false,
				fileElementId : 'photo',
				dataType : 'json',
				data : {userId:throwable_base.getIdFromCookie("throwable"), live_address:$("#add_live_location").val(), image:$("#add_image").val(), now_job:$("#add_now_job").val(), graduate_school:$("#add_graduate_school").val(),motto:$("#add_motto").val(),interest:$("#add_interest").val(),goodAt:$("#add_goodAt").val()},
				success : function(data, status) {
					if(data.msg) {
						console.log(data.msg);
					}
				},
				error : function(data, status, e) {
					console.log("error");
				}
			});
			return false;
		},
		getUserQuestion : function(id) {
			$.post("../question/queryUserQuestion", {userId : id}, function(result) {
				member_info.publish("questions", eval('(' + result.questions + ')'));
			}, "json");
		},
		getUserAnswer : function(id) {
			$.post(throwable_base.urls.userAnswer, {userId : id}, function(result) {
				member_info.publish("answers", eval('(' + result.answers + ')'));
			}, "json");
		},
};


$(document).ready(function(){
	member_info.subscribe();
	throwable_base.login.isLogin("", function(isLogin){
		if(1 == isLogin) {
			var username = throwable_base.getUserNameFromCookie("throwable");
			var userId = throwable_base.getIdFromCookie("throwable");
			member_info.publish("login", username);
			member_info.getBaseUserInfo(userId);
			member_info.getUserQuestion(userId);
		}
	});
});


avalon.scan();