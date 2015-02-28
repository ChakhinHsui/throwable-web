var throwable_base = {
		login : {
			isLogin : function(userId, callBack){
				$.post("user/userIsLogin",
						{userId: userId},
						function(result){
							if(result.msgCode == 1) {
								callBack(1);
							} else {
								callBack(0);
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