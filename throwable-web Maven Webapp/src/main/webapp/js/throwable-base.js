var throwable_base = {
		login : {
			isLogin : function(userId){
				$.post("user/userIsLogin",
						{userId: userId},
						function(result){
							alert(JSON.stringify(result));
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
		}
};