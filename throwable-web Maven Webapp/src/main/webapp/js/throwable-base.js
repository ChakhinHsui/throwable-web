var throwable_base = {
		login : {
			isLogin : function(userId){
				$.post("user/userIsLogin",
						{userId: userId},
						function(result){
							if(result.msgCode == 1) {
								throwable_util.url.location(throwable_base.urls.userInfo);
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
			userLogin: "login.html"
		}
};