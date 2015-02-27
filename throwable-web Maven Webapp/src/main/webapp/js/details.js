var throwable_details = {
		addAnswer : function() {
			throwable_base.login.isLogin("", function(result){
				console.log(result);
				if(1 == result) {
					var description = ue.getContent();
					var length = description.length > 50 ? 50 : description.length;
					var jsonObject = {
							question_id:throwable_util.url.getUrlParam("qid"),
							answer_abstract:description.substring(0, length),
							answer_description:description,
							user_id:throwable_base.userInfo.id
					};
					console.log(jsonObject);
					$.ajax({
							type: "post",
							url: "answer/addAnswer",
							data: jsonObject,
							success: function(data){
								console.log(data);
							}
						});
				} else {
					throwable_util.url.location("login.html");
				}
			});
		}
};