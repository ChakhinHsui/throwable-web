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
								console.log(throwable_base.userInfo);
								callback(1);
							} else {
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
			userAnswer : "answer/getUserAnswers"
		},
		userInfo : {
			id : 0,
			username : null,
			user_state : 0
		}
};