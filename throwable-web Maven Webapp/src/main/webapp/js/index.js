var throwable_index = {
		ask : function(){
			alert("oo");
			alert(throwable_base.login.isLogin(""));
			if(throwable_base.login.isLogin("")) {
				$("#myModal").modal("show");
			} else {
				throwable_util.url.location("login.html");
			}
		}
};