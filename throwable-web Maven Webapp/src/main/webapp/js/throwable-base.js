var throwable_base = {
		login : {
			isLogin : function(userId){
				$.post("user/userIsLogin",
						{userId: userId},
						function(result){
							if(result.msgCode == 1) {
								throwable_base.userInfo.id = result.id;
								throwable_base.userInfo.username = result.username;
								return true;
							} else {
								return false;
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
			userAnswer : "answer/getUserAnswers"
		},
		userInfo : {
			id : 0,
			username : null,
			user_state : 0
		}
};