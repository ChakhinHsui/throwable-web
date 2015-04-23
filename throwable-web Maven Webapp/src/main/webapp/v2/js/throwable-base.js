var throwable_base = {
		login : {
			isLogin : function(userId, callback){
				$.post("user/userIsLogin",
						{userId: userId},
						function(result){
							if(result.msgCode == 1) {
								console.log(result);
								throwable_base.userInfo.id = result.userId;
								throwable_base.userInfo.username = result.username;
								//将信息存入cookie中
								throwable_util.cookie.setCookie("throwable", JSON.stringify(throwable_base.userInfo), 24);
								callback(1);
							} else {
								if(null != throwable_util.cookie.getCookie("throwable")) {
									throwable_util.cookie.deleteCookie("throwable");
								}
								callback(2);
							}
						}, "json");
			},
			isAllowed : function(right) {
				$.post("user/isAllowed",
						{right: right},
						function(result){
							alert(JSON.stringinfy(result));
						}, "json");
			}
		},
		rights : {
			gu : "general",
			cu : "context",
			wu : "web",
			su : "superU"
		},
		urls: {
			userInfo: "memberinfo.html",
			userLogin: "login.html",
			userQuestion : "question/queryUserQuestion",
			userFocusQuestion : "question/queryUserFocusQuestion",
			userAnswer : "answer/getUserAnswers",
			userQuestionNumber : "question/queryUserQuestionNum",
			userAnswerNumber : "answer/getUserAnswerNum",
			userExtendInfo : "user/getUserExtendInfo",
			UserSaveExtendInfo : "user/saveUserExtendInfo"
		},
		userInfo : {
			id : 0,
			username : null,
			user_state : 0
		},
		getIdFromCookie : function(name) {
			var str = throwable_util.cookie.getCookie("throwable");
			if(null == str) {
				return null;
			}
			return JSON.parse(str).id;
		},
		getUserNameFromCookie : function(name) {
			var str = throwable_util.cookie.getCookie("throwable");
			if(null == str) {
				return null;
			}
			return JSON.parse(str).username;
		}
};
